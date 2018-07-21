package coupon.sys.core.facade;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.GlobalCouponDbDao;
import coupon.sys.core.dao.db.CustomerCouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CustomerFacade implements CouponClientFacade {

	private Customer customer;
	private CustomerDbDao customerDbDao;
	private GlobalCouponDbDao couponDbDao;
	private CustomerCouponDbDao customerCouponDbDao;

	// CTOR
	public CustomerFacade(String customerName) throws CouponSystemException {
		customerDbDao = new CustomerDbDao();
		couponDbDao = new GlobalCouponDbDao();
		customerCouponDbDao = new CustomerCouponDbDao();

		this.customer = customerDbDao.getCustomerByName(customerName);
	}

	/**
	 * Purchase coupon for a client
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void purshaseCoupon(Coupon coupon) throws CouponSystemException {
		// can buy coupon only ones ,amount > 0 , not expired
		if (customerCouponDbDao.read(this.customer, coupon) == null
				&& couponDbDao.getCouponAmount(coupon.getTitle()) > 0 && !couponDbDao.isExpired(coupon.getTitle())) {
			customerCouponDbDao.purshase(this.customer, coupon);

		} else {
			throw new CouponSystemException("purshase coupon fail , no available coupons");
		}

	}

	/**
	 * Get all client Purchased coupons from DB
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchesedCoupons() throws CouponSystemException {
		return customerCouponDbDao.getAllCustomerCoupons(this.customer);
	}

	/**
	 * Get all client purchased coupons by required type
	 * 
	 * @param couponType
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType couponType) throws CouponSystemException {
		return customerCouponDbDao.getCouponByType(this.customer, couponType);
	}

	/**
	 * Get all client purchased coupons up to price
	 * 
	 * @param price
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllPurchasedCouponsUpToPrice(double price) throws CouponSystemException {
		return customerCouponDbDao.getCouponUpToPrice(this.customer, price);
	}

	/**
	 * Login method will implement on other classes
	 */
	@Deprecated
	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
