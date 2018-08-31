package coupon.sys.core.jobs;

import java.sql.Date;
//import java.util.Collection;

import coupon.sys.core.dao.db.GlobalCouponDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Thread that delete all expired coupons Thread run ones in 24 hours
 * 
 * @author vbronshtein
 *
 */
public class DailyCouponExparationTask implements Runnable {

	private GlobalCouponDbDao couponDbDao;
	private long sleepTime = 1000 * 60 * 60 * 24;
	private boolean quit;

	public DailyCouponExparationTask() {
		couponDbDao = new GlobalCouponDbDao();
		this.quit = false;
	}

	@Override
	public void run() {
		while (!quit) {
			try {
				Date currentDate = new Date(System.currentTimeMillis());
				couponDbDao.deleteAllExpiriedCoupons(currentDate);
				Thread.sleep(sleepTime); // 1000msec * 60sec * 60min * 24Hours
			} catch (InterruptedException e) {
			} catch (CouponSystemException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopTask() {
		this.quit = true;
	}

}
