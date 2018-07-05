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
		while (!quit) {
			try {
				Date currentDate = new Date(System.currentTimeMillis());
				Collection<Long> removedCouponIds = null;
				removedCouponIds = couponDbDao.RemoveExpiredCoupons(currentDate);
				if(removedCouponIds != null) {
					for (Long id : removedCouponIds) {
						companyCouponDbDao.deleteCoupon(id);
						customerCouponDbDao.deleteCoupon(id);
					}
				}
				Thread.sleep(3600 * 24 * 1000); 
			} catch (CouponSystemException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void stopTask() {
		this.quit = true;
	}
}
