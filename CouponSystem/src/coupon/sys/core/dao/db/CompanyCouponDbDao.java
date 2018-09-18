package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * CompanyCouponDbDao class implement methods to connect to DB for update
 * company records
 * 
 * @author vbronshtein
 *
 */
public class CompanyCouponDbDao implements CouponDao {

	private ConnectionPool pool;

	/**
	 * Default Constructor, Also assigning of Connection pool Instance to local
	 * variable
	 */
	public CompanyCouponDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	@Override
	public void create(Company company, Coupon coupon) throws CouponSystemException {

		Connection connection = pool.getConnection();

		try {
			long id = getLastAvailableId();
			// generate "create coupon" statement for coupon table
			String sql_coupon = String.format(SQL_QUERY.SQL_Create_CompCoupon_Coupon, id, coupon.getTitle(),
					coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(), coupon.getType(),
					coupon.getMessage(), coupon.getPrice(), coupon.getImage());
			// generate "create coupon" statement for company_coupon table
			String sql_compCoupon = String.format(SQL_QUERY.SQL_Create_CompCoupon_CompCoupon, company.getId(), id);

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql_coupon);
			stmt.executeUpdate(sql_compCoupon);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to create new company : " + company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void update(Company company, Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = String.format(SQL_QUERY.SQL_Update_CompCoupon, coupon.getTitle(), coupon.getStartDate(),
				coupon.getEndDate(), coupon.getAmount(), coupon.getType(), coupon.getMessage(), coupon.getPrice(),
				coupon.getImage(), coupon.getId());
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to update company : " + company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void delete(Company company, Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();
		// generate String for delete coupon from : coupon table
		String sql_coupon = String.format(SQL_QUERY.SQL_Delete_CompCoupon_Coupon, company.getId(), coupon.getId());
		String sql_customerCoupon = String.format(SQL_QUERY.SQL_Delete_CompCoupon_CustCoupon, company.getId(),
				coupon.getId());
		// generate String for delete coupon from : customer_coupon table
		String sql_companyCoupon = String.format(SQL_QUERY.SQL_Delete_CompCoupon_CompCoupon, company.getId(),
				coupon.getId());

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql_coupon);
			stmt.executeUpdate(sql_customerCoupon);
			stmt.executeUpdate(sql_companyCoupon);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to delete company : " + company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public Coupon read(long compId, long couponId) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			// read company coupon
			String sql = String.format(SQL_QUERY.SQL_Read_CompCoupon, compId, couponId);

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
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
				return coupon;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to read Company coupon", e);
		} finally {
			pool.returnConnection(connection);
		}
		return null;
	}

	/**
	 * Get all company coupons
	 * 
	 * @param company
	 *            Company
	 * @return Collection of Coupons from DB
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllCoupon(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			String sql = String.format(SQL_QUERY.SQL_Get_All_CompCoupon, company.getId());

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
			throw new CouponSystemException("Fail to get all Company coupons", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * Delete all company coupons
	 * 
	 * @param company
	 *            Company
	 * @throws CouponSystemException
	 */
	public void deleteAllCompanyCoupons(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			String sql_coupon = String.format(SQL_QUERY.SQL_Delete_All_CompCoupons_Coupon, company.getId());
			String sql_customerCoupon = String.format(SQL_QUERY.SQL_Delete_All_CompCoupons_CustCoupon, company.getId());
			String sql_companyCoupon = String.format(SQL_QUERY.SQL_Delete_All_CompCoupons_CompCoupon, company.getId());

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql_coupon);
			stmt.executeUpdate(sql_customerCoupon);
			stmt.executeUpdate(sql_companyCoupon);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to delete all Company coupons", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public Collection<Coupon> getCouponByType(Company company, CouponType type) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = String.format(SQL_QUERY.SQL_Get_CompCoupon_By_Type, company.getId(), type);

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
			throw new CouponSystemException("Fail to read coupon by type", e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	/**
	 * Get coupons up to price
	 * 
	 * @param company
	 *            Company
	 * @param price
	 *            Coupon Price
	 * @return Collection of coupons from DB
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponUpToPrice(Company company, double price) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = String.format(SQL_QUERY.SQL_Get_CompCoupon_By_Price, company.getId(), price);

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
			throw new CouponSystemException("Fail to read coupon up tp price " + price, e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	/**
	 * Get coupons up to date
	 * 
	 * @param company
	 *            Coupon
	 * @param date
	 *            coupon end Date
	 * @return Collection of coupons from DB
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponUpToDate(Company company, Date date) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = String.format(SQL_QUERY.SQL_Get_CompanyCoupon_Upto_Date,
					company.getId() + " and c.end_date<'" + date);
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
			throw new CouponSystemException("Fail to read coupon up tp date " + date, e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	/**
	 * Check if coupon with same title already exist
	 * 
	 * @param title
	 *            Coupon title
	 * @return boolean param if coupon is exist on DB
	 * @throws CouponSystemException
	 */
	public boolean isCouponTytleAlresdyExist(String title) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_is_Coupon_Expired, title);

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to check if coupon exist ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * Get last available ID for creating new coupon
	 * 
	 * @return available ID from DB
	 * @throws CouponSystemException
	 */
	public long getLastAvailableId() throws CouponSystemException {
		Connection connection = pool.getConnection();
		long id;
		String sql = String.format(SQL_QUERY.SQL_Get_Last_Available_id, "Coupon");

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				id = rs.getLong("ID");
				long nextId = id + 1;
				String sqlUpdate = String.format(SQL_QUERY.SQL_Get_Last_Available_id_Update, nextId, "Coupon");
				stmt.executeUpdate(sqlUpdate);
				return nextId;
			} else {
				String sqlInitTable = String.format(SQL_QUERY.SQL_Get_Last_Available_id_Init, "Coupon");
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