package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.connectionPool.ConnectionPool;
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
			String sqlCreate = "INSERT INTO CUSTOMER_COUPON VALUES(" + customer.getId() + ", " + coupon.getId() + ")";
			String getAmount = "SELECT AMOUNT FROM coupon WHERE TITLE='" + coupon.getTitle() + "'";

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sqlCreate);

			int tempAmount = 0;
			ResultSet rs = stmt.executeQuery(getAmount);
			if (rs.next()) {
				tempAmount = rs.getInt("AMOUNT");
			}
			String updatedAmount = "UPDATE coupon SET AMOUNT=" + (tempAmount - 1) + " WHERE TITLE='" + coupon.getTitle()
					+ "'";
			stmt.executeUpdate(updatedAmount);

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

	public Coupon read(Customer customer, Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();
		Coupon customerCoupon = null;

		try {
			String sql = "SELECT * FROM customer_coupon WHERE CUST_ID=" + customer.getId() + " and COUPON_ID="
					+ coupon.getId();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				customerCoupon = new Coupon();
				customerCoupon.setId(rs.getLong("ID"));
				customerCoupon.setTitle(rs.getString("TITLE"));
				customerCoupon.setStartDate(rs.getDate("START_DATE"));
				customerCoupon.setEndDate(rs.getDate("END_DATE"));
				customerCoupon.setAmount(rs.getInt("AMOUNT"));
				customerCoupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				customerCoupon.setMessage(rs.getString("MESSAGE"));
				customerCoupon.setPrice(rs.getDouble("PRICE"));
				customerCoupon.setImage(rs.getString("IMAGE"));

			}
			return customerCoupon;

		} catch (SQLException e) {
			throw new CouponSystemException("Incert customer : " + customer.getCustName() + " intro table fail", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	public void deleteAllCustomerCoupons(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			String sql_coupon = "DELETE FROM Coupon WHERE ID IN (SELECT COUPON_ID FROM Coupon INNER JOIN Customer_Coupon ON Coupon.Id =Customer_Coupon.COUPON_Id WHERE Cust_Id = "
					+ customer.getId() + ")";
			String sql_companyCoupon = "DELETE FROM Company_coupon WHERE coupon_id IN (SELECT Company_coupon.COUPON_ID FROM Company_coupon INNER JOIN Customer_Coupon ON Company_Coupon.COUPON_Id =Customer_Coupon.COUPON_Id WHERE cust_Id = "
					+ customer.getId() + ")";

			String sql_customerCoupon = "DELETE FROM customer_coupon WHERE CUST_ID=" + customer.getId();

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql_coupon);
			stmt.executeUpdate(sql_companyCoupon);
			stmt.executeUpdate(sql_customerCoupon);

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

//	public void deleteListOfCustomerCoupons(Collection<Coupon> coupons) throws CouponSystemException {
//		Connection connection = pool.getConnection();
//
//		try {
//			for (Coupon coupon : coupons) {
//				String sql = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID=" + coupon.getId();
//				Statement stmt = connection.createStatement();
//				stmt.executeUpdate(sql);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			pool.returnConnection(connection);
//		}
//
//	}

//	public void deleteCoupon(Coupon coupon) throws CouponSystemException {
//		Connection connection = pool.getConnection();
//		String sql = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID=" + coupon.getId();
//
//		Statement stmt;
//		try {
//			stmt = connection.createStatement();
//			stmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			pool.returnConnection(connection);
//		}
//
//	}

//	public void deleteCoupon(Long couponId) throws CouponSystemException {
//		Connection connection = pool.getConnection();
//		String sql = "DELETE FROM CUSTOMER_COUPON WHERE COUPON_ID=" + couponId;
//
//		Statement stmt;
//		try {
//			stmt = connection.createStatement();
//			stmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			pool.returnConnection(connection);
//		}
//
//	}

	public Collection<Coupon> getAllCustomerCoupons(Customer customer) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all customer coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN customer_coupon cc ON c.id=cc.coupon_id WHERE cc.cust_id="
					+ customer.getId();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			// delete all company coupons from coupon table
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);

			}
			return coupons;
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}
		return null;
	}

	public Collection<Coupon> getCouponByType(Customer customer, CouponType type) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN customer_coupon cc ON c.id=cc.coupon_id WHERE cc.cust_id="
					+ customer.getId() + " and c.type='" + type + "'";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);

			}
			return coupons;
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}
		return null;
	}

	public Collection<Coupon> getCouponUpToPrice(Customer customer, double price) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN customer_coupon cc ON c.id=cc.coupon_id WHERE cc.cust_id="
					+ customer.getId() + " and c.price<" + price;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				coupons.add(coupon);

			}
			return coupons;
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}
		return null;
	}

//	public boolean isCouponExistOnDb(long id) throws CouponSystemException {
//		Connection connection = pool.getConnection();
//
//		try {
//			String sql = "SELECT * FROM customer_coupon WHERE COUPON_ID=" + id;
//			Statement stmt = connection.createStatement();
//			ResultSet rs = stmt.executeQuery(sql);
//			if (rs.next()) {
//				return true;
//			} else {
//				return false;
//			}
//
//		} catch (SQLException e) {
//			throw new CouponSystemException("Read customer_coupon table fail", e);
//		} finally {
//			pool.returnConnection(connection);
//		}
//
//	}

}