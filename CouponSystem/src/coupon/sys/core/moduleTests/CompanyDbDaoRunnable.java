package coupon.sys.core.moduleTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CompanyDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Class for test multi thread on connection pool will use on
 * ConPoolTestWithRunnableDbDao class
 * 
 * @author vbronshtein
 *
 */
public class CompanyDbDaoRunnable implements CompanyDao, Runnable {

	private ConnectionPool pool;

	public CompanyDbDaoRunnable(ConnectionPool pool) {
		super();
		this.pool = pool;
	}

	@Override
	public void create(Company company) throws CouponSystemException {
		Connection connection = pool.getConnection();
		System.out.println(Thread.currentThread().getName() + " : Connection taken" + " , Connection pool size: "
				+ pool.poolSize());
		// String sql1 = "INSERT INTO company
		// VALUES(111,'electra','password','el@electra.com')";
		String sql1 = "INSERT INTO company VALUES(" + company.getId() + ",'electra','password','el@electra.com')";
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Statement stmt = con.createStatement();
		// // execution
		// stmt.executeUpdate(sql1);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pool.returnConnection(connection);
		System.out.println(Thread.currentThread().getName() + " : Connection returned" + " , Connection pool size: "
				+ pool.poolSize());

	}

	@Override
	public Company read(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Company company) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Company company) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Company> getAllCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Coupon> getCoupons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String compName, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	// runable will generate company with rundom ID
	@Override
	public void run() {
		try {
			long id = (long) (Math.random() * 10000);
			Company company = new Company();
			company.setId(id);
			create(company);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
