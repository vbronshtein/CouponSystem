package coupon.sys.facade;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.dao.db.CustomerCouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CustomerFacade implements CouponClientFacade {

	private Customer customer;
	private CustomerDbDao customerDbDao;
	private CouponDbDao couponDbDao;
	private CustomerCouponDbDao customerCouponDbDao;

	public CustomerFacade(Customer customer) {
		this.customer = customer;
		customerDbDao = new CustomerDbDao();
		couponDbDao = new CouponDbDao();
		customerCouponDbDao = new CustomerCouponDbDao();
	}

	public void purshaseCoupon(Coupon coupon) throws CouponSystemException {

		if (couponDbDao.getCouponByTitle(coupon.getTitle()) == null
				&& couponDbDao.getCouponAmount(coupon.getTitle()) > 0 && couponDbDao.isExpired(coupon.getTitle())) {
			customerCouponDbDao.create(this.customer, coupon);
			couponDbDao.updateCouponAmount(coupon.getTitle());
		} else {
			throw new CouponSystemException("purshase coupon fail");
		}

	}

	public Collection<Coupon> getAllPurchesedCoupons() {
		
		
		return null;
		
	}

	public void getAllPurchasedCouponsByType(CouponType couponType) {

	}

	public void getAllPurchasedCouponsByPrice(double price) {

	}

	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
