package coupon.sys.core.helper;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.dao.db.GlobalCouponDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Phase 1 helper , was created for fix dessign issue of creating new Objects (
 * Companies/Customers/Coupons ) without IDs , ID was generated automaticly by
 * DB Methods of this class update object ID fields from DB records
 * 
 * @author vbronshtein
 *
 */
public class IdUpdater {
	CompanyDbDao compDao = new CompanyDbDao();
	CustomerDbDao customerDao = new CustomerDbDao();
	GlobalCouponDbDao couponDao = new GlobalCouponDbDao();

	/**
	 * Method get Company and update ID field according to read ID from DataBase
	 * 
	 * @param company
	 * @throws CouponSystemException
	 */
	public void updateCompanyId(Company company) throws CouponSystemException {
		long id = compDao.getCompanyByName(company.getName()).getId();
		company.setId(id);
	}

	/**
	 * Method get Customer and update ID field according to read ID from DataBase
	 * 
	 * @param customer
	 * @throws CouponSystemException
	 */
	public void updateCustomerId(Customer customer) throws CouponSystemException {
		long id = customerDao.getCustomerByName(customer.getCustName()).getId();
		customer.setId(id);
	}

	/**
	 * Method get Coupon and update ID field according to read ID from DataBase
	 * 
	 * @param coupon
	 * @throws CouponSystemException
	 */
	public void updateCouponID(Coupon coupon) throws CouponSystemException {
		long id = couponDao.getCouponByTitle(coupon.getTitle()).getId();
		coupon.setId(id);
	}

}
