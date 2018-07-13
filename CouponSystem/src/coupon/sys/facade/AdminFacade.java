package coupon.sys.facade;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyCouponDbDao;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.dao.db.CustomerCouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class AdminFacade implements CouponClientFacade {
	private CompanyDbDao companyDbDao;
	// private CouponDbDao couponDbDao;
	private CompanyCouponDbDao companyCouponDbDao;
	private CustomerDbDao customerDbDao;
	private CustomerCouponDbDao customerCouponDbDao;

	public AdminFacade() {
		this.companyDbDao = new CompanyDbDao();
		// this.couponDbDao = new CouponDbDao();
		this.companyCouponDbDao = new CompanyCouponDbDao();
		this.customerDbDao = new CustomerDbDao();
		this.customerCouponDbDao = new CustomerCouponDbDao();
	}

	public void createCompany(Company company) throws CouponSystemException {

		if (companyDbDao.getCompanyByName(company.getName()) == null) {
			companyDbDao.create(company);
		} else {
			throw new CouponSystemException(
					"Company with name : " + company.getName() + " is already exist on Data Base");
		}

	}

	public void removeCompany(Company company) throws CouponSystemException {

		// delete all company coupons
		companyCouponDbDao.deleteAllCompanyCoupons(company);
		// delete company
		companyDbDao.delete(company);

	}

	public void updateCompany(Company company) throws CouponSystemException {

		Company companyFromDB = companyDbDao.read(company.getId());

		if (companyFromDB == null) {
			throw new CouponSystemException("Update Fail , company not found on DataBase");

		} else if (!company.getName().equals(companyFromDB.getName())) {
			company.setName(companyFromDB.getName());
			companyDbDao.update(company);

			throw new CouponSystemException(
					"Company info was secessfully updated except Company name ( Company name cant' be override by buseness logic ");
		} else {
			companyDbDao.update(company);
		}
	}

	public Collection<Company> getAllCompanies() throws CouponSystemException {

		Collection<Company> companies = companyDbDao.getAllCompanies();

		return companies;
	}

	public Company getCompany(long id) throws CouponSystemException {

		Company company = companyDbDao.read(id);
		if(company.getName() == null) {
			throw new CouponSystemException("Company :"+ id + " Not found on Data Base");
		}
		return company;
	}

	// Customer section
	public void createCustomer(Customer customer) throws CouponSystemException {

		if (customerDbDao.getCustomerByName(customer.getCustName()) == null) {
			customerDbDao.create(customer);
		} else {
			throw new CouponSystemException(
					"Customer with name : " + customer.getCustName() + " is already exist on Data Base");
		}

	}

	public void removeCustomer(Customer customer) throws CouponSystemException {
		// delete all company coupons
		customerCouponDbDao.deleteAllCustomerCoupons(customer);
		// delete company
		customerDbDao.delete(customer);

	}

	public void updateCustomer(Customer customer) throws CouponSystemException {

		Customer customerFromDB = customerDbDao.read(customer.getId());

		if (customerFromDB.getCustName() == null) {
			throw new CouponSystemException(
					"Update Fail , customer " + customer.getCustName() + " not found on DataBase");

		} else if (!customer.getCustName().equals(customerFromDB.getCustName())) {
			customer.setCustName(customerFromDB.getCustName());
			customerDbDao.update(customer);

			throw new CouponSystemException(
					"Customer info was secessfully updated except Customer name ( Customer name cant' be override by buseness logic ");
		} else {
			customerDbDao.update(customer);
		}
	}

	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		Collection<Customer> customers = null;
		customers = customerDbDao.getAllCustomer();
		return customers;

	}

	public Customer getCustomer(long id) throws CouponSystemException {

		Customer customer = customerDbDao.read(id);
		if(customer.getCustName() == null) {
			throw new CouponSystemException("Customer :"+ id + " Not found on Data Base");
		}
		return customer;
	}

	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
