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

	/**
	 * Default Constructor, Also assigning of Connection pool Instance to local
	 * variable
	 */
	public CustomerDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	@Override
	public void create(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();

		long id = getLastAvailableId();
		String sql = String.format(SQL_QUERY.SQL_Create_Customer, id, customer.getCustName(), customer.getPassword());
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

	@Override
	public Customer read(long id) throws CouponSystemException {
		Connection connection = pool.getConnection();
		Customer customer = new Customer();
		String sql = String.format(SQL_QUERY.SQL_Read_Customer, id);

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

	@Override
	public void update(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_Update_Customer, customer.getCustName(), customer.getPassword(),
				customer.getId());

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

	@Override
	public void delete(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_Delete_Customer, customer.getCustName());

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

	@Override
	public Collection<Customer> getAllCustomer() throws CouponSystemException {
		Connection connection = pool.getConnection();
		Collection<Customer> customers = new ArrayList<>();
		String sql = String.format(SQL_QUERY.SQL_Get_All_Customer);

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
	 * @param name
	 *            Customer name
	 * @return customer
	 * @throws CouponSystemException
	 */
	public Customer getCustomerByName(String name) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_Get_Customer_By_Name, name);

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

	@Override
	public boolean login(String custName, String password) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_Login_As_Customer, custName);

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
		String sql = String.format(SQL_QUERY.SQL_Get_Last_Available_id, "Customer");

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				id = rs.getLong("ID");
				long nextId = id + 1;
				String sqlUpdate = String.format(SQL_QUERY.SQL_Get_Last_Available_id_Update, nextId, "Customer");
				stmt.executeUpdate(sqlUpdate);
				return nextId;
			} else {
				String sqlInitTable = String.format(SQL_QUERY.SQL_Get_Last_Available_id_Init, "Customer");
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
