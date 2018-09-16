package coupon.sys.core.main;

import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CouponClientFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.jobs.DailyCouponExparationTask;

/**
 * Coupon System main class ( Singleton )
 * 
 * 1.Perform logins to all facades 2.Init DayliExparation task
 * 
 * @author vbronshtein
 *
 */
public class CouponSystem {

	private CompanyDbDao companyDbDao;
	private CustomerDbDao customerDbDao;

	private static CouponSystem instance ;//= new CouponSystem();

	DailyCouponExparationTask dailyExpTask;

	// CTOR
	private CouponSystem() {
		initDao();
		initDailyThread();
	}

	public static CouponSystem getInstance() {
		if(instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}

	/**
	 * Login method ,return facade after login
	 * 
	 * @param name  Admin/Company/Customer name
	 * @param password  Password
	 * @param type  User Type : Admin/Company/Customer 
	 * @return facade Admin, Company or Customer
	 * @throws CouponSystemException
	 */
	public CouponClientFacade login(String name, String password, ClientType type) throws CouponSystemException {
		switch (type) {
		case ADMIN:
			if (name.equals("admin") && password.equals("1234")) {
				return new AdminFacade();
			} else {
				throw new CouponSystemException("Incorrect username or password");
			}
		case COMPANY:
			if (companyDbDao.login(name, password)) {
				return new CompanyFacade(name);
			} else {
				throw new CouponSystemException("Incorrect username or password");
			}
		case CUSTOMER:
			if (customerDbDao.login(name, password)) {
				return new CustomerFacade(name);
			} else {
				throw new CouponSystemException("Incorrect username or password");
			}

		default:
			return null;
		}

	}

	/**
	 * Shutdown all threads and close all SQL connections
	 * 
	 * @throws CouponSystemException
	 */
	public void shutdown() throws CouponSystemException {
		ConnectionPool pool = ConnectionPool.getInstance();

		dailyExpTask.stopTask();
		pool.closeAllConnection();

	}

	// init Dao for login to facades
	private void initDao() {
		companyDbDao = new CompanyDbDao();
		customerDbDao = new CustomerDbDao();
	}

	// init Daily thread
	private void initDailyThread() {

		dailyExpTask = new DailyCouponExparationTask();
		Thread dailyThread = new Thread(dailyExpTask, "dailyExpired");
		dailyThread.start();

	}
}
