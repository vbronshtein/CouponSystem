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
	 * @param customer  Customer
	 * @throws CouponSystemException
	 */
	void create(Customer customer) throws CouponSystemException;

	/**
	 * 
	 * @param id Customer Id
	 * @return
	 * @throws CouponSystemException
	 */
	Customer read(long id) throws CouponSystemException;

	/**
	 * 
	 * @param customer  Customer
	 * @throws CouponSystemException
	 */
	void update(Customer customer) throws CouponSystemException;

	/**
	 * 
	 * @param customer  Customer
	 * @throws CouponSystemException
	 */
	void delete(Customer customer) throws CouponSystemException;

	/**
	 * 
	 * @return List of customers
	 * @throws CouponSystemException
	 */
	Collection<Customer> getAllCustomer() throws CouponSystemException;


	/**
	 * 
	 * @param custName  Customer name
	 * @param password  Customer password
	 * @return Boolean ( success , fail )
	 * @throws CouponSystemException
	 */
	boolean login(String custName, String password) throws CouponSystemException;

}
