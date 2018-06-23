package coupon.sys.facade;

import java.util.Collection;

import coupon.sys.core.beans.Coupon;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CouponDbDao;

public class CompanyFacade implements CouponClientFacade {

	private CompanyDbDao compDbDao = new CompanyDbDao();
	private CouponDbDao couponDbDao = new CouponDbDao();

	public CompanyFacade() {
		// TODO Auto-generated constructor stub
	}

	public void createCoupon(Coupon coupon) {
		
	}

	public void removeCoupon(Coupon coupon) {

	}

	public void updateCoupon(Coupon coupon) {

	}

	public Coupon getCoupon(long id) {
		return null;
	}

	public Collection<Coupon> getAllCoupons() {
		return null;
	}

	public Collection<Coupon> getCouponByType() {
		return null;
	}
	
	

	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
