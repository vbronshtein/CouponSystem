package coupon.sys.facade;

import java.sql.Date;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.dao.db.CompanyCouponDbDao;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.dao.db.CustomerCouponDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CompanyFacade implements CouponClientFacade {

	private Company company;
	private CompanyDbDao companyDbDao;
	private CouponDbDao couponDbDao;
	private CompanyCouponDbDao companyCouponDbDao;
	private CustomerCouponDbDao customerCouponDbDao;

	
	public CompanyFacade(String companyName) throws CouponSystemException {
		companyDbDao = new CompanyDbDao();
		couponDbDao = new CouponDbDao();
		companyCouponDbDao = new CompanyCouponDbDao();
		customerCouponDbDao = new CustomerCouponDbDao();

		this.company = companyDbDao.getCompanyByName(companyName);
	}
	

	public void createCoupon(Coupon coupon) throws CouponSystemException {

		if (couponDbDao.getCouponByTitle(coupon.getTitle()) == null) {
			couponDbDao.create(coupon);
			companyCouponDbDao.create(this.company, coupon);
		} else {
			throw new CouponSystemException(
					"Coupon with title : " + coupon.getTitle() + " is already exist on Data Base");
		}

	}

	/*
	 * Functional but no protection
	 * !!!Need to add protection ( with join delete only this company coupons and not each coupon
	 */
	public void removeCoupon(Coupon coupon) throws CouponSystemException {

		companyCouponDbDao.delete(this.company, coupon);
		customerCouponDbDao.deleteCoupon(coupon);
		couponDbDao.delete(coupon);
	}

	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon couponFromDb = couponDbDao.read(coupon.getId());

		if (couponFromDb == null) {
			throw new CouponSystemException("Update Coupon Fail , coupon not found on DB");
		} else {
			// update only price and end Date
			couponFromDb.setEndDate(coupon.getEndDate());
			couponFromDb.setPrice(coupon.getPrice());
			couponDbDao.update(couponFromDb);
		}
	}

	public Coupon getCoupon(long couponId) throws CouponSystemException {
		Coupon coupon = companyCouponDbDao.readCompanyCoupon(this.company.getId(), couponId);
		return coupon;
	}

	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		return companyCouponDbDao.getAllCompanyCoupons(this.company);
	}

	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException {
		return companyCouponDbDao.getCouponByType(this.company, couponType);
	}

	public Collection<Coupon> getCouponUptoPrice(double price) throws CouponSystemException {
		return companyCouponDbDao.getCouponUpToPrice(this.company, price);
	}

	public Collection<Coupon> getCouponUpToDate(Date date) {
		/*
		 * I'm here
		 */
		return null;
	}

	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}

