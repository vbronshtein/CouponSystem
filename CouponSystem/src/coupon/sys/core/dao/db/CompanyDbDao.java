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
 * CompanyDbDao class implement methods for connect to "Company" table on
 * Database
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
	/**
	 * Create new company on DB
	 */
	@Override
	public void create(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			long id = getLastAvailableId();
			String sql = "INSERT INTO company VALUES(" + id + ", " + "'" + company.getName() + "'" + ", " + "'"
					+ company.getPassword() + "'" + ", " + "'" + company.getEmail() + "'" + ")";

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to create new company :" + company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * Read company from DB
	 */
	@Override
	public Company read(long id) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "SELECT * FROM company WHERE ID= " + id;
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
			throw new CouponSystemException("Fail to read company ", e);
		} finally {
			pool.returnConnection(connection);
		}

		return null;

	}

	/**
	 * Update Company fields on DB
	 */
	@Override
	public void update(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "UPDATE company SET COMP_NAME='" + company.getName() + "',PASSWORD='" + company.getPassword()
				+ "',EMAIL='" + company.getEmail() + "' WHERE COMP_NAME='" + company.getName() + "'";
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to update company ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * Delete company from DB
	 */
	@Override
	public void delete(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM company WHERE COMP_NAME='" + company.getName() + "'";

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to delete company ", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * Get all companies from DB
	 */
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
			throw new CouponSystemException("Fail to read all company ", e);
		} finally {
			pool.returnConnection(connection);
		}

		return companies;

	}

	/**
	 * Method non in Use , Mark as depricated
	 */
	@Deprecated
	@Override
	public Collection<Coupon> getCoupons() throws CouponSystemException {
		Connection connection = pool.getConnection();
		pool.returnConnection(connection);
		return null;
	}

	/**
	 * Get Company by Name from DB
	 * 
	 * @param name  Comapny name
	 * @return Company from DB
	 * @throws CouponSystemException
	 */
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
			throw new CouponSystemException("Fail to read Company by Name ", e);
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
				if (password.equals(rs.getString("PASSWORD"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Fail to login to Company account ", e);
		} finally {
			pool.returnConnection(connection);
		}

		return false;

	}

	/**
	 * Get last available ID for creating new company
	 * 
	 * @return available ID from DB
	 * @throws CouponSystemException
	 */
	public long getLastAvailableId() throws CouponSystemException {
		Connection connection = pool.getConnection();
		long id;

		String sql = "SELECT id FROM last_id WHERE type='Company'";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				id = rs.getLong("ID");
				long nextId = id + 1;
				String sqlUpdate = "UPDATE last_id SET id=" + nextId + " WHERE Type='Company'";
				stmt.executeUpdate(sqlUpdate);
				return nextId;
			} else {
				String sqlInitTable = "INSERT INTO last_id VALUES('Company',1)";
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
