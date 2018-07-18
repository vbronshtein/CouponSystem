package coupon.sys.core.jobs;

import java.sql.Date;
import java.util.Collection;

import coupon.sys.core.dao.db.CompanyCouponDbDao;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.dao.db.CustomerCouponDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class DailyCouponExparationTask implements Runnable {

	private CouponDbDao couponDbDao;
	private CompanyCouponDbDao companyCouponDbDao;
	private CustomerCouponDbDao customerCouponDbDao;
	private boolean quit;

	public DailyCouponExparationTask() {
		couponDbDao = new CouponDbDao();
		companyCouponDbDao = new CompanyCouponDbDao();
		customerCouponDbDao = new CustomerCouponDbDao();
		this.quit = false;
	}

	@Override
	public void run() {
		int counter = 0;
		while (!quit) {
			try {
				Date currentDate = new Date(System.currentTimeMillis());
				System.out.println("Execute delete  number : " + counter);
				couponDbDao.deleteAllExpiriedCoupons(currentDate);
				Thread.sleep(1000 * 60 *60 * 24); // 1000msec * 60sec * 60min * 24Hours
			} catch (InterruptedException e) {
			} catch (CouponSystemException e ) {
				e.printStackTrace();
			}
		}
		System.out.println("Daily Thread is stoped !!");
	}

	public void stopTask() {
		this.quit = true;
	}
}
