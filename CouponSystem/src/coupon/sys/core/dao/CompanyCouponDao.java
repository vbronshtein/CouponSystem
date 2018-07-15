package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Company Coupon Dao interface 
 * Define "must implement" methods 
 * 
 * @author vbronshtein
 *
 */
public interface CompanyCouponDao {
	
	/**
	 * 
	 * @param company
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void create(Company company , Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @param company
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void delete(Company company , Coupon coupon) throws CouponSystemException;

}
