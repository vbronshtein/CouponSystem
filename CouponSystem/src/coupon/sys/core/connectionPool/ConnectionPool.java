package coupon.sys.core.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import coupon.sys.core.exceptions.CouponSystemException;

public class ConnectionPool {
	Set<Connection> availableConnections = new HashSet<>();

	// singleton declaration
	private static ConnectionPool instance = new ConnectionPool();

	private ConnectionPool() {
		initialyzeConnectionPool();
	}

	public static ConnectionPool getInstance() {
		return instance;
	}

	// create single SQL connection
	private Connection createNewConnectionForPool() throws CouponSystemException {
		Configuration configuration = Configuration.getInstance();
		try {
			Connection connection = DriverManager.getConnection(configuration.DB_URL);
			return connection;
		} catch (SQLException e) {
			throw new CouponSystemException("Cant create connection for pool", e);
		}
	}

	// fill all pool
	private void initialyzeConnectionPool() {
		while (availableConnections.size() < Configuration.getInstance().DB_MAX_CONNECTIONS) {
			try {
				availableConnections.add(createNewConnectionForPool());
			} catch (CouponSystemException e) {
				e.printStackTrace();
			}
		}
	}

	// Get-Return Connection methods
	public synchronized Connection getConnection() throws CouponSystemException {
		while (availableConnections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("Cant wait() to availableConnection ", e);
			}
		}
		// notify();

		Iterator<Connection> it = availableConnections.iterator();
		Connection currentConnection = it.next();
		it.remove();

		return currentConnection;

	}

	public synchronized void returnConnection(Connection connection) {
		availableConnections.add(connection);
		notify();
	}

	public int poolSize() {
		return availableConnections.size();
	}

}
