package coupon.sys.facade;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.dao.db.CouponDbDao;

public class CustomerFacade implements CouponClientFacade {

	private CouponDbDao couponDbDao = new CouponDbDao();

	public CustomerFacade() {
		super();
	}

	public void purshaseCoupon(Coupon coupon) {

	}

	public void getAllPurchesedCoupons() {

	}

	public void getAllPurchasedCouponsByType(CouponType couponType) {

	}

	public void getAllPurchasedCouponsByPrice(double price) {

	}

	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
