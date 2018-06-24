package coupon.sys.core.dao.db;

import java.sql.Connection;
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

	public Collection<Coupon> getAllCompanyCoupons(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			Collection<Coupon> coupons = new ArrayList<>();
			// read all company coupons
			String sql = "SELECT * FROM COMPANY_COUPON WHERE COMP_ID= " + company.getId();
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
			return coupons ;
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
			// read all company coupons
			String sql = "SELECT * FROM COMPANY_COUPON WHERE COMP_ID= " + company.getId();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			// delete all company coupons from coupon table
			while (rs.next()) {
				String sqlCoupon = "DELETE FROM coupon WHERE ID=" + rs.getLong("COUPON_ID");
				Statement stmt2 = connection.createStatement();
				stmt2.executeUpdate(sqlCoupon);

			}
			// delete all company coupons from CompanyCoupons table
			deleteCompany(company);

		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
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
}