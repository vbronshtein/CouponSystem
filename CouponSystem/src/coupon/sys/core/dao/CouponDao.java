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
	void create(Coupon coupon) throws CouponSystemException;

	Coupon read(long id) throws CouponSystemException;

	void update(Coupon coupon) throws CouponSystemException;

	void delete(Coupon coupon) throws CouponSystemException;

	Collection<Coupon> getAllCoupon() throws CouponSystemException;

	Collection<Coupon> getCouponByType(CouponType couponType);

}
