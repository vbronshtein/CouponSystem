package coupon.sys.main;

import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.jobs.DailyCouponExparationTask;
import coupon.sys.facade.CouponClientFacade;

public class CouponSystem {
	
	private CompanyDbDao companyDbDao;
	private CustomerDbDao customerDbDao;
	
	
	private static CouponSystem instance = new CouponSystem();
	
	private CouponSystem() {
	 initDao();
	 initDailyThread();
	}
	
	public static CouponSystem getInstance() {
		return instance;
	}
	
	private void initDao() {
		companyDbDao = new CompanyDbDao();
		customerDbDao =new CustomerDbDao();
	}
	
	private void initDailyThread() {
		
		DailyCouponExparationTask d = new DailyCouponExparationTask();
		Thread t1 = new Thread(d, "dailyExpired");
		t1.start();
		
	}
	
	//Add client type
	public CouponClientFacade login(String name,String password) {
		
		
		return null;
	}
	
	
	public void shutdown() {
		
	}
}
