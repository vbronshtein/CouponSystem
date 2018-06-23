package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CompanyCouponDao;
import coupon.sys.core.dao.CustomerCouponDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CustomerCouponDbDao implements CustomerCouponDao {

	private ConnectionPool pool;

	public CustomerCouponDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	@Override
	public void create(Customer customer, Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			String sql = "INSERT INTO CUSTOMER_COUPON VALUES(" + customer.getId() + ", " + coupon.getId() + ")";
			;
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Incert customer : " + customer.getCustName() + "intro table fail", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void delete(Customer customer, Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM CUSTOMER_COUPON WHERE COMP_ID=" + customer.getId() + " and COUPON_ID=" + coupon.getId();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

	}

	public void deleteAllCustomerCoupons(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			// read all company coupons
			String sql = "SELECT * FROM CUSTOMER_COUPON WHERE CUST_ID= " + customer.getId();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			// delete all company coupons from coupon table
			while (rs.next()) {
				String sqlCoupon = "DELETE FROM customer WHERE ID=" + rs.getLong("CUSTOMER_ID");
				Statement stmt2 = connection.createStatement();
				stmt2.executeUpdate(sqlCoupon);

			}
			// delete all company coupons from CompanyCoupons table
			deleteCustomer(customer);

		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	public void deleteCustomer(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM CUSTOMER_COUPON WHERE CUST_ID=" + customer.getId();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

	}
}