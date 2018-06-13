package coupon.sys.core.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coupon.sys.core.exceptions.CouponSystemException;

public class ConnectionPoooldl {

	// Singleton class declaration
	private static ConnectionPoooldl instance = new ConnectionPoooldl();

	private ConnectionPoooldl() {
		while(connections.size() < MAX) {
			try {
				connections.add(init());
			} catch (CouponSystemException e) {
			}
		}
	}

	public static ConnectionPoooldl getInstance() {
		return instance;
	}
	// end of declaration

	// push-pop thread definition
	private Set<Connection> connections = new HashSet<>();
	private final int MAX = 10;
	

	public synchronized Connection getConnection() throws CouponSystemException {
		// need to check how to remove from Set and return removed object
		while (connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("getConnection func fail", e);
			}
		}
		notify();

		// remove and return Connection from the list
		Iterator<Connection> it = connections.iterator();
		Connection curr = it.next();
		it.remove();
		return curr;
	}

	public synchronized void returnConnection(Connection connection) throws CouponSystemException {
		while (connections.size() == MAX) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new CouponSystemException("returnConnection func is fail", e);
			}
		}
		connections.add(connection);
		notify(); // !check if actual
	}

	public void closeAllConnections() {
		connections.clear();
	}
	
	private Connection init() throws CouponSystemException {
		String url = "jdbc:derby://localhost:1527/db2";
		Connection con;
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to connect ot database",e);
		}
		return con;
	}
	

}
