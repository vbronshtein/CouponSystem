package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Company Dao interface Define "must implement" methods
 * 
 * @author vbronshtein
 *
 */
public interface CompanyDao {

	/**
	 * Create new company in DataBase
	 * 
	 * @param company
	 *            New Company
	 * @throws CouponSystemException
	 */
	void create(Company company) throws CouponSystemException;

	/**
	 * Read company from DataBase
	 * 
	 * @param id
	 *            Id for read Company from Database.
	 * @return Company from DataBase
	 * @throws CouponSystemException
	 */
	Company read(long id) throws CouponSystemException;

	/**
	 * Update Company in Database
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	void update(Company company) throws CouponSystemException;

	/**
	 * Delete company from DataBase
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	void delete(Company company) throws CouponSystemException;

	/**
	 * Get all companies from Database
	 * 
	 * @return List of Company from DataBase
	 * @throws CouponSystemException
	 */
	Collection<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * Get Company Coupons from DataBase
	 * 
	 * @return List of Coupons from DataBase
	 * @throws CouponSystemException
	 */
	Collection<Coupon> getCoupons() throws CouponSystemException;

	/**
	 * Login as Comppany method ( compare between DataBase record and received user
	 * and password )
	 * 
	 * @param compName
	 *            Company name
	 * @param password
	 *            Password for login
	 * @return boolean ( success or fail )
	 * @throws CouponSystemException
	 */
	boolean login(String compName, String password) throws CouponSystemException;

}
