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
	 * @param company
	 * @throws CouponSystemException
	 */
	void create(Company company) throws CouponSystemException;

	/**
	 * 
	 * @param id
	 * @return
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
	 * @return
	 * @throws CouponSystemException
	 */
	Collection<Company> getAllCompanies() throws CouponSystemException;

	/**
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	Collection<Coupon> getCoupons() throws CouponSystemException;

	/**
	 * 
	 * @param compName
	 * @param password
	 * @return
	 * @throws CouponSystemException
	 */
	boolean login(String compName, String password) throws CouponSystemException;

}
