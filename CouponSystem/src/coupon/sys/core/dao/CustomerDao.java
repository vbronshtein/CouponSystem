package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Company Dao interface 
 * Define "must implement" methods 
 * 
 * @author vbronshtein
 *
 */
public interface CustomerDao {
	
	void create(Customer customer) throws CouponSystemException;

	Customer read(long id) throws CouponSystemException;

	void update(Customer customer) throws CouponSystemException;

	void delete(Customer customer) throws CouponSystemException;

	Collection<Customer> getAllCustomer() throws CouponSystemException;

	Collection<Coupon> getCoupons();

	boolean login(String custName, String password) throws CouponSystemException;

}
