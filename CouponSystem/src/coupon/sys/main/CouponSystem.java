package coupon.sys.main;

import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.jobs.DailyCouponExparationTask;
import coupon.sys.facade.AdminFacade;
import coupon.sys.facade.CompanyFacade;
import coupon.sys.facade.CouponClientFacade;
import coupon.sys.facade.CustomerFacade;

public class CouponSystem {

	private CompanyDbDao companyDbDao;
	private CustomerDbDao customerDbDao;

	private static CouponSystem instance = new CouponSystem();

	private Thread t1;

	private CouponSystem() {
		initDao();
		// initDailyThread();
	}

	public static CouponSystem getInstance() {
		return instance;
	}

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
				System.out.println("Login succed !!!!");
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

	public void shutdown() throws CouponSystemException {
		ConnectionPool pool = ConnectionPool.getInstance();
		// DailyCouponExparationTask exparationTask = new DailyCouponExparationTask();

		// exparationTask.stopTask();
		// t1.interrupt();

		pool.closeAllConnection();

	}

	private void initDao() {
		companyDbDao = new CompanyDbDao();
		customerDbDao = new CustomerDbDao();
	}

	// private void initDailyThread() {
	//
	// DailyCouponExparationTask d = new DailyCouponExparationTask();
	// t1 = new Thread(d, "dailyExpired");
	// t1.start();

	// }
}
