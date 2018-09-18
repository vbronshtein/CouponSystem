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

	/**
	 * Connection pool constructor , initialize pool of connection Constructor is
	 * private for single ton class
	 */
	private ConnectionPool() {

		try {
			initializeConnectionPool();
		} catch (CouponSystemException e) {
		}
		shutdown = false;
	}

	/**
	 * Get SQL Connection from pool of connections
	 * 
	 * @return instance - SQL Connection
	 */
	public static ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	//
	/**
	 * Create single SQL connection ( private method , helper for fill all pool )
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	private Connection createNewConnectionForPool() throws CouponSystemException {
		try {
			Connection connection = DriverManager.getConnection(DataBaseConfig.DB_URL);
			return connection;
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to create connection for pool", e);
		}
	}

	// fill all pool
	/**
	 * Fill all Connection pool ( up to DB_MAX_CONNECTIONS from configuration class
	 * )
	 * 
	 * @throws CouponSystemException
	 */
	private void initializeConnectionPool() throws CouponSystemException {
		while (availableConnections.size() < DataBaseConfig.DB_MAX_CONNECTIONS) {
			try {
				availableConnections.add(createNewConnectionForPool());
				connectionBackup.addAll(availableConnections);
			} catch (CouponSystemException e) {
				throw new CouponSystemException("Fail to initialyze Connecton Pool", e);
			}
		}
	}

	/**
	 * Get SQL Connection from pool ( connection removed from pool on return action
	 * ) Method is synchronize (wait until connection pool have connection )
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
	 * Return Connection to pool ( and notify all all wait connections ) Synchronize
	 * method
	 * 
	 * @param connection
	 *            Connection to DataBase.
	 */
	public synchronized void returnConnection(Connection connection) {
		availableConnections.add(connection);
		notify();
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
	 * Close all pool SQL Connections ( if all connections is in pool , method will
	 * close all immediately , otherwise wait for 30 sec and then close all
	 * connection using helper backup pool )
	 * 
	 * @throws CouponSystemException
	 */
	public void closeAllConnection() throws CouponSystemException {
		shutdown = true;
		try {
			// if all connections in pool close all immediately
			if (availableConnections.size() == DataBaseConfig.DB_MAX_CONNECTIONS) {
				for (Connection connection : availableConnections) {
					connection.close();
				}
			} else {
				// Else wait 30 sec and close all connections using backup pool
				Thread.sleep(30000);
				for (Connection connection : connectionBackup) {
					connection.close();
				}
			}
		} catch (SQLException | InterruptedException e) {
			throw new CouponSystemException("Close SQL connection fail", e);
		}

	}

}
