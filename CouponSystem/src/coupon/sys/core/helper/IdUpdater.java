package coupon.sys.core.helper;


import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.dao.db.GlobalCouponDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class IdUpdater {
	CompanyDbDao compDao = new CompanyDbDao();
	CustomerDbDao customerDao = new CustomerDbDao();
	GlobalCouponDbDao couponDao = new GlobalCouponDbDao();
	
	
	public void updateCompanyId (Company company) throws CouponSystemException {
		 long id = compDao.getCompanyByName(company.getName()).getId();
		 company.setId(id);
	}
	
	public void updateCustomerId (Customer customer) throws CouponSystemException {
		long id = customerDao.getCustomerByName(customer.getCustName()).getId();
		customer.setId(id);
	}
	
	public void updateCouponID ( Coupon coupon) throws CouponSystemException {
		long id = couponDao.getCouponByTitle(coupon.getTitle()).getId();
		coupon.setId(id);
	}

}
