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
		while (!Thread.currentThread().isInterrupted()) {
			try {
				
				System.out.println("Start thread cicle ");
				Date currentDate = new Date(System.currentTimeMillis());
				System.out.println("Execute delete  number : " + counter);
				couponDbDao.deleteAllExpiriedCoupons(currentDate);
				Thread.sleep(10 * 1000);
				// Thread.sleep(3600 * 24 * 1000);
				counter++;
			} catch (InterruptedException e) {
				System.out.println("!!!! Thread was interupted here ");
			} catch (CouponSystemException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// while (!quit) {
		// try {
		// Thread.sleep(20 * 1000);
		// System.out.println("Thread Started");
		// Date currentDate = new Date(System.currentTimeMillis());
		// couponDbDao.deleteAllExpiriedCoupons(currentDate);
		// System.out.println("Before sleep");
		// // Thread.sleep(3600 * 24 * 1000);
		// } catch (CouponSystemException | InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		System.out.println("Daily Thread is stoped !!");
	}

	public void stopTask() {
		this.quit = true;
	}
}
