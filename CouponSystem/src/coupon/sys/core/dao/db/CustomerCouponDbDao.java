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
		String sql = "DELETE FROM CUSTOMER_COUPON WHERE COMP_ID=" + customer.getId() + " and COUPON_ID="
				+ coupon.getId();

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
			String sql_coupon = "DELETE FROM Coupon WHERE ID IN (SELECT COUPON_ID FROM Coupon INNER JOIN Customer_Coupon ON Coupon.Id =Customer_Coupon.COUPON_Id WHERE Customer_Id = "
					+ customer.getId();
			String sql_companyCoupon = "DELETE FROM Company_coupon WHERE coupon_id IN (SELECT COUPON_ID FROM Company_coupon INNER JOIN Customer_Coupon ON Company_Coupon.COUPON_Id =Customer_Coupon.COUPON_Id WHERE customer_Id = "
					+ customer.getId();

			String sql_customerCoupon = "DELETE FROM customer_coupon WHERE Customer_ID=" + customer.getId();

			Statement stmt = connection.createStatement();
			stmt.executeQuery(sql_coupon);
			stmt.executeQuery(sql_companyCoupon);
			stmt.executeQuery(sql_customerCoupon);

		} catch (SQLException e) {
			e.printStackTrace();
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

	public void deleteListOfCustomerCoupons(Collection<Coupon> coupons) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			for (Coupon coupon : coupons) {
				String sql = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID=" + coupon.getId();
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

	}

	public void deleteCoupon(Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID=" + coupon.getId();

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