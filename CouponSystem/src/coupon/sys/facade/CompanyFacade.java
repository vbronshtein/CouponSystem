package coupon.sys.facade;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.dao.db.CompanyCouponDbDao;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CompanyFacade implements CouponClientFacade {

	private Company company;
	private CompanyDbDao companyDbDao;
	private CouponDbDao couponDbDao;
	private CompanyCouponDbDao companyCouponDbDao;

	public CompanyFacade(Company company) {
		this.company = company;
		companyDbDao = new CompanyDbDao();
		couponDbDao = new CouponDbDao();
		companyCouponDbDao = new CompanyCouponDbDao();
	}

	public void createCoupon(Coupon coupon) throws CouponSystemException {
		
		if(couponDbDao.getCouponByTitle(coupon.getTitle()) == null) {
			couponDbDao.create(coupon);
			companyCouponDbDao.create(this.company, coupon);
		} else {
			throw new CouponSystemException(
					"Coupon with title : " + coupon.getTitle() + " is already exist on Data Base");
		}
		
		
		
		
		
		
		
		// Check if Coupon with same title already exist on DB
		Collection<Coupon> coupons = couponDbDao.getAllCoupon();
		for (Coupon dbCoupon : coupons) {
			if (coupon.getTitle().equals(dbCoupon.getTitle())) {
				throw new CouponSystemException(
						"Same coupon title already Exist on DataBase  ,  create new coupon fail");
			}
		}
		// if Not create coupon
		couponDbDao.create(coupon);
	}

	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		couponDbDao.delete(coupon);
		// add deleting all already bued coupons
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon couponFromDb = couponDbDao.read(coupon.getId());
		// update only price and end Date
		couponFromDb.setEndDate(coupon.getEndDate());
		couponFromDb.setPrice(coupon.getPrice());
		couponDbDao.update(couponFromDb);
	}

	public Coupon getCoupon(long id) throws CouponSystemException {
		Coupon coupon = couponDbDao.read(id);
		return coupon;
	}

	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		Collection<Coupon> coupons = couponDbDao.getAllCoupon();
		return coupons;
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
