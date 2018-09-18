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

	/**
	 * Default Constructor, Also assigning of Connection pool Instance to local
	 * variable
	 */
	public CompanyDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	@Override
	public void create(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();

		try {
			long id = getLastAvailableId();
			String sql = String.format(SQL_QUERY.SQL_Create_Company, id, company.getName(), company.getPassword(),
					company.getEmail());

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new CouponSystemException("Fail to create new company :" + company.getName(), e);
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public Company read(long id) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_Read_Company, id);

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

	@Override
	public void update(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_Update_Company, company.getName(), company.getPassword(),
				company.getEmail(), company.getName());

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

	@Override
	public void delete(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_Delete_Company, company.getName());

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

	@Override
	public Collection<Company> getAllCompanies() throws CouponSystemException {
		Connection connection = pool.getConnection();
		Collection<Company> companies = new ArrayList<>();
		String sql = String.format(SQL_QUERY.SQL_Get_All_Companies);

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
	 * @param name
	 *            Comapny name
	 * @return Company from DB
	 * @throws CouponSystemException
	 */
	public Company getCompanyByName(String name) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = String.format(SQL_QUERY.SQL_Get_Company_By_Name, name);

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
		String sql = String.format(SQL_QUERY.SQL_Login_As_Company, compName);

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
		String sql = String.format(SQL_QUERY.SQL_Get_Last_Available_id, "Company");

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				id = rs.getLong("ID");
				long nextId = id + 1;
				String sqlUpdate = String.format(SQL_QUERY.SQL_Get_Last_Available_id_Update, nextId, "Company");
				stmt.executeUpdate(sqlUpdate);
				return nextId;
			} else {
				String sqlInitTable = String.format(SQL_QUERY.SQL_Get_Last_Available_id_Init, "Company");
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
