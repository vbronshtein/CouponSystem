package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Customer Coupon Dao interface Define "must implement" methods
 * 
 * @author vbronshtein
 *
 */
public interface CustomerCouponDao {
	
	/**
	 * 
	 * @param customer  Customer
	 * @param coupon  Coupon
	 * @throws CouponSystemException
	 */
	void create(Customer customer, Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @param customer  Customer
	 * @param coupon  Coupon
	 * @throws CouponSystemException
	 */
	void delete(Customer customer, Coupon coupon) throws CouponSystemException;

}
