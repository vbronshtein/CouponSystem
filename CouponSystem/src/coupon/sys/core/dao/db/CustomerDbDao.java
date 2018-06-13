package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
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

	@Override
	public void create(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "INSERT INTO CUSTOMER VALUES(" + customer.getId() + ",'" + customer.getCustName() + "','"
				+ customer.getPassword() + "')";
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Incert company : " + customer.getCustName()
			// + "intro table fail", e);

		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public Customer read(long id) throws CouponSystemException {

		Connection connection = pool.getConnection();
		Customer customer = new Customer();

		String sql = "SELECT * FROM customer WHERE ID=" + id;
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				customer.setId(rs.getLong("ID"));
				customer.setCustName(rs.getString("CUST_NAME"));
				customer.setPassword(rs.getString("PASSWORD"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of customer ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}

		return customer;

	}

	@Override
	public void update(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "UPDATE customer SET CUST_NAME='" + customer.getCustName() + "',PASSWORD='" + customer.getPassword()
				+ "' WHERE id=" + customer.getId();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void delete(Customer customer) throws CouponSystemException {

		Connection connection = pool.getConnection();
		String sql = "DELETE FROM customer WHERE ID=" + customer.getId();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public Collection<Customer> getAllCustomer() throws CouponSystemException {
		Connection connection = pool.getConnection();
		Collection<Customer> customers = new ArrayList<>();
		String sql = "SELECT * FROM customer";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getLong("ID"));
				customer.setCustName(rs.getString("CUST_NAME"));
				customer.setPassword(rs.getString("PASSWORD"));
				
				customers.add(customer);
			
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}
		
		return customers;
		
	}

	@Override
	public Collection<Coupon> getCoupons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String custName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
