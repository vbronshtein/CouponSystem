package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Company Dao interface Define "must implement" methods
 * 
 * @author vbronshtein
 *
 */
public interface CustomerDao {

	/**
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	void create(Customer customer) throws CouponSystemException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws CouponSystemException
	 */
	Customer read(long id) throws CouponSystemException;

	/**
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	void update(Customer customer) throws CouponSystemException;

	/**
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	void delete(Customer customer) throws CouponSystemException;

	/**
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	Collection<Customer> getAllCustomer() throws CouponSystemException;

	/**
	 * 
	 * @return
	 */
	Collection<Coupon> getCoupons();

	/**
	 * 
	 * @param custName
	 * @param password
	 * @return
	 * @throws CouponSystemException
	 */
	boolean login(String custName, String password) throws CouponSystemException;

}
