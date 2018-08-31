package coupon.sys.core.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Class manage pool of the connections to Data Base (get/return connection)
 * 
 * @author vbronshtein
 *
 */
public class ConnectionPool {
	Set<Connection> availableConnections = new HashSet<>();
	Set<Connection> connectionBackup = new HashSet<>();
	private boolean shutdown;

	// singleton declaration
	private static ConnectionPool instance;

	private ConnectionPool() {

		try {
			initialyzeConnectionPool();
		} catch (CouponSystemException e) {
		}
		shutdown = false;
	}

	public static ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	// create single SQL connection
	private Connection createNewConnectionForPool() throws CouponSystemException {
		Configuration configuration = Configuration.getInstance();
		try {
			Connection connection = DriverManager.getConnection(configuration.DB_URL);
			return connection;
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to create connection for pool", e);
		}
	}

	// fill all pool
	private void initialyzeConnectionPool() throws CouponSystemException {
		while (availableConnections.size() < Configuration.getInstance().DB_MAX_CONNECTIONS) {
			try {
				availableConnections.add(createNewConnectionForPool());
				connectionBackup.add(createNewConnectionForPool());
			} catch (CouponSystemException e) {
				throw new CouponSystemException("Fail to initialyze Connecton Pool", e);
			}
		}
	}

	/**
	 * Get Connection to DB from Pool
	 * 
	 * @return return Connection to Data base
	 * @throws CouponSystemException
	 */
	public synchronized Connection getConnection() throws CouponSystemException {
		while (availableConnections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("Getconnection from pool was interupt", e);
			}
		}
		if (!shutdown) {
			Iterator<Connection> it = availableConnections.iterator();
			Connection currentConnection = it.next();
			it.remove();

			return currentConnection;
		}
		return null;
	}

	/**
	 * Return Connection to pool
	 * 
	 * @param connection
	 */
	public synchronized void returnConnection(Connection connection) {
		if (!shutdown) {
			availableConnections.add(connection);
			notify();
		}
	}

	/**
	 * Check current pool size
	 * 
	 * @return number of Available connections
	 */
	public int poolSize() {
		return availableConnections.size();
	}

	/**
	 * Close all Connections from Connection pool
	 * 
	 * @throws CouponSystemException
	 */
	public void closeAllConnection() throws CouponSystemException {
		System.out.println("Pool size : " + availableConnections.size());
		for (Connection connection : connectionBackup) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponSystemException("Close SQL connection fail", e);
			}
		}
	}

}
