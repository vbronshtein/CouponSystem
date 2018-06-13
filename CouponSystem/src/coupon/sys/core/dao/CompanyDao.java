package coupon.sys.core.dao;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.exceptions.CouponSystemException;

public interface CompanyDao {
	void create(Company company) throws CouponSystemException;

	Company read(long id) throws CouponSystemException;

	void update(Company company) throws CouponSystemException;

	void delete(Company company) throws CouponSystemException;

	Collection<Company> getAllCompanies() throws CouponSystemException;

	Collection<Coupon> getCoupons() throws CouponSystemException;

	boolean login(String compName, String password) throws CouponSystemException;
	
}
