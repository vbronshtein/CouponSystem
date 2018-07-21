package coupon.sys.core.facade;

import java.sql.Date;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.dao.db.CompanyCouponDbDao;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.GlobalCouponDbDao;
import coupon.sys.core.dao.db.CustomerCouponDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CompanyFacade implements CouponClientFacade {

	private Company company;
	private CompanyDbDao companyDbDao;
	private CompanyCouponDbDao companyCouponDbDao;
	private CustomerCouponDbDao customerCouponDbDao;

	// CTOR
	public CompanyFacade(String companyName) throws CouponSystemException {
		companyDbDao = new CompanyDbDao();
		companyCouponDbDao = new CompanyCouponDbDao();
		customerCouponDbDao = new CustomerCouponDbDao();

		this.company = companyDbDao.getCompanyByName(companyName);
	}

	/**
	 * Create new coupon on DB
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void createCoupon(Coupon coupon) throws CouponSystemException {

		if (!companyCouponDbDao.isCouponTytleAlresdyExist(coupon.getTitle())) {
			// couponDbDao.create(coupon);
			companyCouponDbDao.create(this.company, coupon);
		} else {
			throw new CouponSystemException(
					"Coupon with title : " + coupon.getTitle() + " is already exist on Data Base");
		}

	}

	/**
	 * Remove coupon from DB
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void removeCoupon(Coupon coupon) throws CouponSystemException {
		companyCouponDbDao.delete(this.company, coupon);
	}

	/**
	 * Update Coupon info ( Update can be perform only on price or end_date )
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon couponFromDb = companyCouponDbDao.read(this.company.getId(), coupon.getId());

		if (couponFromDb == null) {
			throw new CouponSystemException("Update Coupon Fail , coupon not found on DB");
		} else {
			// update only price and end Date
			couponFromDb.setEndDate(coupon.getEndDate());
			couponFromDb.setPrice(coupon.getPrice());
			companyCouponDbDao.update(this.company, couponFromDb);
		}
	}

	/**
	 * Read coupon from DB
	 * 
	 * @param couponId
	 * @return
	 * @throws CouponSystemException
	 */
	public Coupon getCoupon(long couponId) throws CouponSystemException {
		Coupon coupon = companyCouponDbDao.read(this.company.getId(), couponId);
		return coupon;
	}

	/**
	 * Get specific coupon from Database
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getAllCoupons() throws CouponSystemException {
		return companyCouponDbDao.getAllCoupon(this.company);
	}

	/**
	 * Get all company coupons by Type
	 * 
	 * @param couponType
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponByType(CouponType couponType) throws CouponSystemException {
		return companyCouponDbDao.getCouponByType(this.company, couponType);
	}

	/**
	 * Get all company coupons up to price
	 * 
	 * @param price
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponUptoPrice(double price) throws CouponSystemException {
		return companyCouponDbDao.getCouponUpToPrice(this.company, price);
	}

	/**
	 * Get all company coupons up to End date
	 * 
	 * @param date
	 * @return
	 * @throws CouponSystemException
	 */
	public Collection<Coupon> getCouponUpToDate(Date date) throws CouponSystemException {
		return companyCouponDbDao.getCouponUpToDate(this.company, date);
	}

	/**
	 * Login method implemented on other classes
	 */
	@Deprecated
	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
