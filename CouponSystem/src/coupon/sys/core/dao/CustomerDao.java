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
	 * Create new customer on DataBase
	 * 
	 * @param customer
	 *            Customer
	 * @throws CouponSystemException
	 */
	void create(Customer customer) throws CouponSystemException;

	/**
	 * Read customer from DataBase
	 * 
	 * @param id
	 *            Customer Id
	 * @return
	 * @throws CouponSystemException
	 */
	Customer read(long id) throws CouponSystemException;

	/**
	 * Update customer on Database
	 * 
	 * @param customer
	 *            Customer
	 * @throws CouponSystemException
	 */
	void update(Customer customer) throws CouponSystemException;

	/**
	 * Delete customer from DataBase
	 * 
	 * @param customer
	 *            Customer
	 * @throws CouponSystemException
	 */
	void delete(Customer customer) throws CouponSystemException;

	/**
	 * Read all customers from DataBase
	 * 
	 * @return List of customers
	 * @throws CouponSystemException
	 */
	Collection<Customer> getAllCustomer() throws CouponSystemException;

	/**
	 * Login as customer ( compare between received user and password and DataBase
	 * record )
	 * 
	 * @param custName
	 *            Customer name
	 * @param password
	 *            Customer password
	 * @return Boolean ( success , fail )
	 * @throws CouponSystemException
	 */
	boolean login(String custName, String password) throws CouponSystemException;

}
