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
	 * 
	 * @param company New Company 
	 * @throws CouponSystemException
	 */
	void create(Company company) throws CouponSystemException;

	/**
	 * 
	 * @param id  Id for read Company from Database.
	 * @return Company from DataBase 
	 * @throws CouponSystemException
	 */
	Company read(long id) throws CouponSystemException;

	/**
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	void update(Company company) throws CouponSystemException;

	/**
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	void delete(Company company) throws CouponSystemException;

	/**
	 * 
	 * @return List of Company from DataBase
	 * @throws CouponSystemException
	 */
	Collection<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * 
	 * @return List of Coupons from DataBase 
	 * @throws CouponSystemException
	 */
	Collection<Coupon> getCoupons() throws CouponSystemException;

	/**
	 * 
	 * @param compName  Company name
	 * @param password  Password for login
	 * @return boolean ( success or fail )
	 * @throws CouponSystemException
	 */
	boolean login(String compName, String password) throws CouponSystemException;

}
