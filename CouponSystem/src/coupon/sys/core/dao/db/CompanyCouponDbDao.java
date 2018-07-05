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
			String sql = "INSERT INTO COMPANY_COUPON VALUES(" + company.getId() + ", " + coupon.getId() + ")";
			;
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Incert company : " + company.getName() + "intro table fail", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void delete(Company company, Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM COMPANY_COUPON WHERE COMP_ID=" + company.getId() + " and COUPON_ID=" + coupon.getId();

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
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}
		return null;
	}

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
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}
		return null;

	}

	public void deleteAllCompanyCoupons(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			String sql_coupon = "DELETE FROM Coupon WHERE ID IN (SELECT COUPON_ID FROM Coupon INNER JOIN Company_Coupon ON Coupon.Id =Company_Coupon.COUPON_Id WHERE COMP_Id = "
					+ company.getId();
			String sql_customerCoupon = "DELETE FROM Customer_coupon WHERE coupon_id IN (SELECT COUPON_ID FROM Customer_coupon INNER JOIN Company_Coupon ON Customer_Coupon.COUPON_Id =Company_Coupon.COUPON_Id WHERE COMP_Id = "
					+ company.getId();
			String sql_companyCoupon = "DELETE FROM company_coupon WHERE COMP_ID=" + company.getId();

			Statement stmt = connection.createStatement();
			stmt.executeQuery(sql_coupon);
			stmt.executeQuery(sql_customerCoupon);
			stmt.executeQuery(sql_companyCoupon);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}
	}

	public void deleteCompany(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM COMPANY_COUPON WHERE COMP_ID=" + company.getId();

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
	
	public Collection<Coupon> getCouponByType(Company company, CouponType type) throws CouponSystemException{
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id="+ company.getId() +" and c.type="+ type;
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

	public Collection<Coupon> getCouponUpToPrice(Company company, double price) throws CouponSystemException{
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id="+ company.getId() +" and c.price<"+price;
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
	
	public Collection<Coupon> getCouponUpToDate(Company company, Date date) throws CouponSystemException{
		Connection connection = pool.getConnection();
		
		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id="+ company.getId() +" and c.date<"+ date;
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

	public void deleteCoupon(Long couponId) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM COMPANY_COUPON WHERE COUPON_ID=" + couponId;

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