package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Coupon Dao interface 
 * Define "must implement" methods 
 * 
 * @author vbronshtein
 *
 */
public interface CouponDao {
	// create,read,update,delete
	/**
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void create(Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws CouponSystemException
	 */
	Coupon read(long id) throws CouponSystemException;

	/**
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void update(Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	void delete(Coupon coupon) throws CouponSystemException;

	/**
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	Collection<Coupon> getAllCoupon() throws CouponSystemException;

	/**
	 * 
	 * @param couponType
	 * @return
	 */
	Collection<Coupon> getCouponByType(CouponType couponType);

}
