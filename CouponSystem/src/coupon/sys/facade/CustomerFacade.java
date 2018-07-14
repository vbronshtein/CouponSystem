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

	public CustomerFacade(String customerName) throws CouponSystemException {
		customerDbDao = new CustomerDbDao();
		couponDbDao = new CouponDbDao();
		customerCouponDbDao = new CustomerCouponDbDao();

		this.customer = customerDbDao.getCustomerByName(customerName);
	}

	public void purshaseCoupon(Coupon coupon) throws CouponSystemException {
		// can buy coupon only ones ,amount > 0 , not expiried
		if (customerCouponDbDao.read(this.customer, coupon) == null
				&& couponDbDao.getCouponAmount(coupon.getTitle()) > 0 ) {//&& couponDbDao.isExpired(coupon.getTitle())) {
			customerCouponDbDao.create(this.customer, coupon);
			// couponDbDao.updateCouponAmount(coupon.getTitle());

		} else {
			throw new CouponSystemException("purshase coupon fail , no available coupons");
		}

	}

	public Collection<Coupon> getAllPurchesedCoupons() throws CouponSystemException {
		return customerCouponDbDao.getAllCustomerCoupons(this.customer);
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws CouponSystemException {
		return customerCouponDbDao.getCouponByType(this.customer, couponType);
	}

	public Collection<Coupon> getAllPurchasedCouponsUpToPrice(double price) throws CouponSystemException {
		return customerCouponDbDao.getCouponUpToPrice(this.customer, price);
	}

	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
