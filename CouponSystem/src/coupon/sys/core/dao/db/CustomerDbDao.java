package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CustomerDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CustomerDbDao implements CustomerDao {

	private ConnectionPool pool;

	public CustomerDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	/**
	 * Create new customer in DB
	 */
	@Override
	public void create(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();

		long id = getLastAvailableId();
		String sql = "INSERT INTO CUSTOMER VALUES(" + id + ",'" + customer.getCustName() + "','"
				+ customer.getPassword() + "')";
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to create new customer :" + customer.getCustName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * Get customer from DB
	 */
	@Override
	public Customer read(long id) throws CouponSystemException {

		Connection connection = pool.getConnection();
		Customer customer = new Customer();

		String sql = "SELECT * FROM customer WHERE ID=" + id;
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				customer.setId(rs.getLong("ID"));
				customer.setCustName(rs.getString("CUST_NAME"));
				customer.setPassword(rs.getString("PASSWORD"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to read customer ", e);
		} finally {
			pool.returnConnection(connection);
		}

		return customer;

	}

	/**
	 * Update Existed customer
	 */
	@Override
	public void update(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "UPDATE customer SET CUST_NAME='" + customer.getCustName() + "',PASSWORD='"
				+ customer.getPassword() + "' WHERE id=" + customer.getId();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to update customer ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * Delete customer from DB
	 */
	@Override
	public void delete(Customer customer) throws CouponSystemException {

		Connection connection = pool.getConnection();
		String sql = "DELETE FROM customer WHERE CUST_NAME='" + customer.getCustName() + "'";

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to delete customer ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * Get all Customers from DB
	 */
	@Override
	public Collection<Customer> getAllCustomer() throws CouponSystemException {
		Connection connection = pool.getConnection();
		Collection<Customer> customers = new ArrayList<>();
		String sql = "SELECT * FROM customer";

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong("ID"));
				customer.setCustName(rs.getString("CUST_NAME"));
				customer.setPassword(rs.getString("PASSWORD"));

				customers.add(customer);

			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to get all customers ", e);
		} finally {
			pool.returnConnection(connection);
		}

		return customers;

	}


	/**
	 * Get Customer from DB by Name input
	 * 
	 * @param name  Customer name
	 * @return customer
	 * @throws CouponSystemException
	 */
	public Customer getCustomerByName(String name) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT * FROM customer WHERE CUST_NAME= '" + name + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong("ID"));
				customer.setCustName(rs.getString("CUST_NAME"));
				customer.setPassword(rs.getString("PASSWORD"));
				return customer;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to get customer by name", e);
		} finally {
			pool.returnConnection(connection);
		}

		return null;

	}

	/**
	 * Login as Custommer ( verify credentials with DB )
	 */
	@Override
	public boolean login(String custName, String password) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT PASSWORD FROM customer WHERE CUST_NAME='" + custName + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				if (password.equals(rs.getString("PASSWORD"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to login to customer account", e);
		} finally {
			pool.returnConnection(connection);
		}

		return false;

	}

	/**
	 * Check last available ID
	 * 
	 * @return available ID
	 * @throws CouponSystemException
	 */
	public long getLastAvailableId() throws CouponSystemException {
		Connection connection = pool.getConnection();
		long id;

		String sql = "SELECT id FROM last_id WHERE type='Customer'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				id = rs.getLong("ID");
				long nextId = id + 1;
				String sqlUpdate = "UPDATE last_id SET id=" + nextId + " WHERE Type='Customer'";
				stmt.executeUpdate(sqlUpdate);
				return nextId;
			} else {
				String sqlInitTable = "INSERT INTO last_id VALUES('Customer',1)";
				stmt.executeUpdate(sqlInitTable);
				return 1;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to get last available ID from Database  ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

}
