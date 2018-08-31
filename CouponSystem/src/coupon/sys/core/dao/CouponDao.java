package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Coupon Dao interface Define "must implement" methods
 * 
 * @author vbronshtein
 *
 */
public interface CouponDao {
	// create,read,update,delete
	/**
	 * 
	 * @param coupon  Coupon for create on DataBase
	 * @throws CouponSystemException
	 */
	void create(Company company, Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @param compId  Company Id
	 * @param couponId  Coupon Id
	 * @return Coupon from DataBase
	 * @throws CouponSystemException
	 */
	Coupon read(long compId, long couponId) throws CouponSystemException;

	/**
	 * 
	 * @param company  Company
	 * @param coupon  Coupon 
	 * @throws CouponSystemException
	 */
	void update(Company company, Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @param company Company
	 * @param coupon  Coupon
	 * @throws CouponSystemException
	 */
	void delete(Company company, Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @return List of Coupons
	 * @throws CouponSystemException
	 */
	Collection<Coupon> getAllCoupon(Company company) throws CouponSystemException;

	/**
	 * 
	 * @param company Comapny
	 * @param type  Coupon Type : Travel , Food , Health ...
	 * @return List of Coupons
	 * @throws CouponSystemException
	 */
	Collection<Coupon> getCouponByType(Company company, CouponType type) throws CouponSystemException;

}
