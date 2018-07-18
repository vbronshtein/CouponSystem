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
import coupon.sys.core.dao.CompanyCouponDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * CompanyCouponDbDao class implement methods to connect to DB for update company records
 * 
 * @author vbronshtein
 *
 */
public class CompanyCouponDbDao implements CompanyCouponDao {

	private ConnectionPool pool;

	public CompanyCouponDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	@Override
	public void create(Company company, Coupon coupon) throws CouponSystemException {

		Connection connection = pool.getConnection();

		try {
			String sql_coupon = "INSERT INTO coupon VALUES(" + coupon.getId() + ",'" + coupon.getTitle() + "','"
					+ coupon.getStartDate() + "','" + coupon.getEndDate() + "'," + coupon.getAmount() + ",'"
					+ coupon.getType() + "','" + coupon.getMessage() + "'," + coupon.getPrice() + ",'"
					+ coupon.getImage() + "')";
			String sql_compCoupon = "INSERT INTO company_coupon VALUES(" + company.getId() + ", " + coupon.getId()
					+ ")";

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql_coupon);
			stmt.executeUpdate(sql_compCoupon);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to create new company : "+ company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * 
	 * @param company
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void update(Company company, Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "UPDATE coupon SET TITLE ='" + coupon.getTitle() + "',START_DATE='" + coupon.getStartDate()
				+ "',END_DATE='" + coupon.getEndDate() + "',AMOUNT=" + coupon.getAmount() + ",TYPE='" + coupon.getType()
				+ "',MESSAGE='" + coupon.getMessage() + "',PRICE=" + coupon.getPrice() + ",IMAGE='" + coupon.getImage()
				+ "' WHERE id=" + coupon.getId();

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to update company : "+ company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void delete(Company company, Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql_coupon = "DELETE FROM Coupon WHERE ID IN (SELECT COUPON_ID FROM Coupon INNER JOIN Company_Coupon ON Coupon.Id =Company_Coupon.COUPON_Id WHERE COMP_Id = "
				+ company.getId() + " and COUPON_Id= " + coupon.getId() + " )";
		String sql_customerCoupon = "DELETE FROM Customer_coupon WHERE coupon_id IN (SELECT Customer_coupon.COUPON_ID FROM Customer_coupon INNER JOIN Company_Coupon ON Customer_Coupon.COUPON_Id =Company_Coupon.COUPON_Id WHERE COMP_Id = "
				+ company.getId() + " and Company_Coupon.COUPON_Id= " + coupon.getId() + " )";
		String sql_companyCoupon = "DELETE FROM company_coupon WHERE COMP_ID=" + company.getId() + " and COUPON_Id= "
				+ coupon.getId();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql_coupon);
			stmt.executeUpdate(sql_customerCoupon);
			stmt.executeUpdate(sql_companyCoupon);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to delete company : "+ company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * 
	 * @param compId
	 * @param couponId
	 * @return
	 * @throws CouponSystemException
	 */
	public Coupon readCompanyCoupon(long compId, long couponId) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id="
					+ compId + "and c.id =" + couponId;
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
	 * 
	 * @param company
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllCompanyCoupons(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id="
					+ company.getId();
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
			throw new CouponSystemException("Fail to get all Company coupons", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public void deleteAllCompanyCoupons(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			String sql_coupon = "DELETE FROM Coupon WHERE ID IN (SELECT COUPON_ID FROM Coupon INNER JOIN Company_Coupon ON Coupon.Id =Company_Coupon.COUPON_Id WHERE COMP_Id = "
					+ company.getId() + ")";
			String sql_customerCoupon = "DELETE FROM Customer_coupon WHERE coupon_id IN (SELECT Customer_coupon.COUPON_ID FROM Customer_coupon INNER JOIN Company_Coupon ON Customer_Coupon.COUPON_Id =Company_Coupon.COUPON_Id WHERE COMP_Id = "
					+ company.getId() + ")";
			String sql_companyCoupon = "DELETE FROM company_coupon WHERE COMP_ID=" + company.getId();

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

	/**
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public void deleteCompany(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM COMPANY_COUPON WHERE COMP_ID=" + company.getId();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to delete Company :" + company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * 
	 * @param company
	 * @param type
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponByType(Company company, CouponType type) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id="
					+ company.getId() + " and c.type='" + type + "'";
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
	 * 
	 * @param company
	 * @param price
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponUpToPrice(Company company, double price) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id="
					+ company.getId() + " and c.price<" + price;
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
			throw new CouponSystemException("Fail to read coupon up tp price " + price , e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	/**
	 * 
	 * @param company
	 * @param date
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponUpToDate(Company company, Date date) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id="
					+ company.getId() + " and c.end_date<'" + date + "'";
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
			throw new CouponSystemException("Fail to read coupon up tp date " + date , e);
		} finally {
			pool.returnConnection(connection);
		}
	}

	/**
	 * 
	 * @param title
	 * @return
	 * @throws CouponSystemException
	 */
	public boolean isCouponTytleAlresdyExist(String title) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT * FROM coupon WHERE TITLE = '" + title + "'";

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to check if coupon exist " , e);
		} finally {
			pool.returnConnection(connection);
		}

	}


}