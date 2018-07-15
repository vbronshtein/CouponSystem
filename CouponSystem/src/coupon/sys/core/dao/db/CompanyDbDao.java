package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * CompanyDbDao class implement methods for connect to "Company" table on Database
 * 
 * @author vbronshtein
 *
 */
public class CompanyDbDao extends Thread implements CompanyDao {

	private ConnectionPool pool;

	public CompanyDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	// Interface methods
	
	
	@Override
	public void create(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			String sql = "INSERT INTO company VALUES(" + company.getId() + ", " + "'" + company.getName() + "'" + ", "
					+ "'" + company.getPassword() + "'" + ", " + "'" + company.getEmail() + "'" + ")";

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Incert company : " + company.getName() + "intro table fail", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	
	@Override
	public Company read(long id) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT * FROM company WHERE ID=" + id;
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Company company = new Company();
				company.setId(rs.getLong("ID"));
				company.setName(rs.getString("COMP_NAME"));
				company.setPassword(rs.getString("PASSWORD"));
				company.setEmail(rs.getString("EMAIL"));
				return company;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}

		return null;

	}

	@Override
	public void update(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "UPDATE company SET COMP_NAME='" + company.getName() + "',PASSWORD='" + company.getPassword()
				+ "',EMAIL='" + company.getEmail() + "' WHERE id=" + company.getId();

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

	@Override
	public void delete(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM company WHERE ID=" + company.getId();

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

	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		Connection connection = pool.getConnection();
		Collection<Company> companies = new ArrayList<>();

		String sql = "SELECT * FROM company ";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getLong("ID"));
				company.setName(rs.getString("COMP_NAME"));
				company.setPassword(rs.getString("PASSWORD"));
				company.setEmail(rs.getString("EMAIL"));

				companies.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}

		return companies;

	}

	@Override
	public Collection<Coupon> getCoupons() throws CouponSystemException {
		Connection connection = pool.getConnection();
		// TODO Auto-generated method stub
		pool.returnConnection(connection);
		return null;
	}

	public Company getCompanyByName(String name) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT * FROM company WHERE COMP_NAME= '" + name + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Company company = new Company();
				company.setId(rs.getLong("ID"));
				company.setName(rs.getString("COMP_NAME"));
				company.setPassword(rs.getString("PASSWORD"));
				company.setEmail(rs.getString("EMAIL"));
				return company;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}

		return null;

	}

	@Override
	public boolean login(String compName, String password) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT PASSWORD FROM company WHERE COMP_NAME='" + compName + "'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				if(password.equals(rs.getString("PASSWORD"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// throw new CouponSystemException("Cant read info of company ID:" + id, e);
		} finally {
			pool.returnConnection(connection);
		}

		return false;

	}

}
