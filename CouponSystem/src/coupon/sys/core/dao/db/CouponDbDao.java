package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CouponDbDao implements CouponDao {
	private ConnectionPool pool;

	public CouponDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	/**
	 * Create method will be implemented bu two diffrent classes :
	 * 1.CompanyCouponDbDao.create for company coupon 2.CustomerCouponDbDao.create
	 * for customer coupon
	 */
	@Deprecated
	@Override
	public void create(Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "INSERT INTO coupon VALUES(" + coupon.getId() + ",'" + coupon.getTitle() + "','"
				+ coupon.getStartDate() + "','" + coupon.getEndDate() + "'," + coupon.getAmount() + ",'"
				+ coupon.getType() + "','" + coupon.getMessage() + "'," + coupon.getPrice() + ",'" + coupon.getImage()
				+ "')";

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to create new coupon ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public Coupon read(long id) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT * FROM coupon WHERE ID=" + id;

		try {
			Coupon coupon = new Coupon();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				return coupon;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to read coupon ", e);
		} finally {
			pool.returnConnection(connection);
		}
		return null;
	}

	@Override
	public void update(Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "UPDATE coupon SET TITLE ='" + coupon.getTitle() + "',START_DATE='" + coupon.getStartDate()
				+ "',END_DATE='" + coupon.getEndDate() + "',AMOUNT=" + coupon.getAmount() + ",TYPE='" + coupon.getType()
				+ "',MESSAGE='" + coupon.getMessage() + "',PRICE=" + coupon.getPrice() + ",IMAGE='" + coupon.getImage()
				+ "' WHERE id=" + coupon.getId();

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to update coupon ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void delete(Coupon coupon) throws CouponSystemException {

		Connection connection = pool.getConnection();
		String sql = "DELETE FROM coupon WHERE ID=" + coupon.getId();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to delete coupon ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public Collection<Coupon> getAllCoupon() throws CouponSystemException {
		Connection connection = pool.getConnection();
		Collection<Coupon> coupons = new ArrayList<>();

		String sql = "SELECT * FROM coupon";
		try {
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

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to get all coupons list", e);
		} finally {
			pool.returnConnection(connection);
		}

		return coupons;

	}

	/**
	 *  method will be implemented bu two diffrent classes :
	 * 1.CompanyCouponDbDao for company coupon 2.CustomerCouponDbDao
	 * for customer coupon
	 */
	@Deprecated
	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) {
		return null;
	}

	/**
	 * 
	 * @param title
	 * @return
	 * @throws CouponSystemException
	 */
	public Coupon getCouponByTitle(String title) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT * FROM coupon WHERE TITLE='" + title + "'";

		try {
			Coupon coupon = new Coupon();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
				return coupon;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to get coupon by Title", e);
		} finally {
			pool.returnConnection(connection);
		}
		return null;

	}

	/**
	 * 
	 * @param title
	 * @return
	 * @throws CouponSystemException
	 */
	public int getCouponAmount(String title) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT AMOUNT FROM coupon WHERE TITLE='" + title + "'";

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt("AMOUNT");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to get coupon amount", e);
		} finally {
			pool.returnConnection(connection);
		}

		return -1;
	}

	/**
	 * 
	 * @param title
	 * @return
	 * @throws CouponSystemException
	 */
	public boolean isExpired(String title) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT END_DATE FROM coupon WHERE TITLE='" + title + "'";

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Date endDate = rs.getDate("END_DATE");
				if (endDate.before(new Date(System.currentTimeMillis()))) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to check if coupon is expired", e);
		} finally {
			pool.returnConnection(connection);
		}

		return false;
	}

	// /**
	// *
	// * @param date
	// * @return Collection<Long> of removed Coupon IDs from "coupon" table
	// * @throws CouponSystemException
	// */
	// public Collection<Long> RemoveExpiredCoupons(Date date) throws
	// CouponSystemException {
	// Connection connection = pool.getConnection();
	//
	// String sqlCheck = "SELECT * FROM coupon WHERE END_DATE>'" + date + "'";
	// String sqlDelete = "DELETE FROM coupon where END_DATE<'" + date + "'";
	//
	// try {
	// Collection<Long> expiredIds = new ArrayList<>();
	//
	// Statement stmt = connection.createStatement();
	// ResultSet rs = stmt.executeQuery(sqlCheck);
	// while (rs.next()) {
	// expiredIds.add(rs.getLong("ID"));
	// }
	// stmt.executeUpdate(sqlDelete);
	// return expiredIds;
	//
	// } catch (SQLException e) {
	// throw new CouponSystemException("Fail to remove expired coupons" , e);
	// } finally {
	// pool.returnConnection(connection);
	// }
	// return null;
	//
	// }

	public void deleteAllExpiriedCoupons(Date date) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sqlCustCoupon = "DELETE FROM customer_coupon WHERE COUPON_ID IN (select ID from coupon where end_date <='"
				+ date + "')";
		String sqlCompCoupon = "DELETE FROM company_coupon WHERE COUPON_ID IN (select ID from coupon where end_date <='"
				+ date + "')";
		String sqlCoupon = "DELETE FROM coupon WHERE END_DATE<='" + date + "'";

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sqlCustCoupon);
			stmt.executeUpdate(sqlCompCoupon);
			stmt.executeUpdate(sqlCoupon);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to remove expired coupons", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

}
