package coupon.sys.core.facade;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyCouponDbDao;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CustomerCouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Admin facade , used for perform actions of Admin user
 * 
 * @author vbronshtein
 *
 */
public class AdminFacade implements CouponClientFacade {
	private CompanyDbDao companyDbDao;
	private CompanyCouponDbDao companyCouponDbDao;
	private CustomerDbDao customerDbDao;
	private CustomerCouponDbDao customerCouponDbDao;

	// CTOR
	public AdminFacade() {
		this.companyDbDao = new CompanyDbDao();
		this.companyCouponDbDao = new CompanyCouponDbDao();
		this.customerDbDao = new CustomerDbDao();
		this.customerCouponDbDao = new CustomerCouponDbDao();
	}

	/*
	 * Company section
	 */
	/**
	 * Admin facade method for create new method
	 * 
	 * @param company Company
	 * @throws CouponSystemException
	 */
	public void createCompany(Company company) throws CouponSystemException {
		// check if its no company with same name on DB
		if (companyDbDao.getCompanyByName(company.getName()) == null) {
			companyDbDao.create(company);
		} else {
			throw new CouponSystemException(
					"Fail to create new company , Company : " + company.getName() + " already exist on Data Base");
		}

	}

	/**
	 * Delete company from Database
	 * 
	 * @param company  Company
	 * @throws CouponSystemException
	 */
	public void removeCompany(Company company) throws CouponSystemException {

		// delete all company coupons
		companyCouponDbDao.deleteAllCompanyCoupons(company);
		// delete company
		companyDbDao.delete(company);

	}

	/**
	 * Update company info ( all fields except company name)
	 * 
	 * @param company  Company
	 * @throws CouponSystemException
	 */
	public void updateCompany(Company company) throws CouponSystemException {

		Company companyFromDB = companyDbDao.read(company.getId());
		// check if company exist on DB
		if (companyFromDB == null) {
			throw new CouponSystemException("Update Fail , company was not found on DataBase");
			// case when company exist by user try to update also company name
		} else if (!company.getName().equals(companyFromDB.getName())) {
			company.setName(companyFromDB.getName());
			companyDbDao.update(company);

			throw new CouponSystemException(
					"Company info was secessfully updated except Company name ( Company name cant' be override by buseness logic ");
			// correct update company case
		} else {
			companyDbDao.update(company);
		}
	}

	/**
	 * Get all companies exist on Database
	 * 
	 * @return Collection of companies
	 * @throws CouponSystemException
	 */
	public Collection<Company> getAllCompanies() throws CouponSystemException {

		Collection<Company> companies = companyDbDao.getAllCompanies();

		return companies;
	}

	/**
	 * Get Company from DataBase
	 * 
	 * @param id Company ID
	 * @return Company
	 * @throws CouponSystemException
	 */
	public Company getCompany(long id) throws CouponSystemException {

		Company company = companyDbDao.read(id);
		if (company.getName() == null) {
			throw new CouponSystemException("Company :" + id + " Not found on Data Base");
		}
		return company;
	}

	/*
	 * Customer section
	 */

	/**
	 * Create new customer on DB
	 * 
	 * @param customer Customer
	 * @throws CouponSystemException
	 */
	public void createCustomer(Customer customer) throws CouponSystemException {

		if (customerDbDao.getCustomerByName(customer.getCustName()) == null) {
			customerDbDao.create(customer);
		} else {
			throw new CouponSystemException("Customer : " + customer.getCustName() + " already exist on Data Base");
		}

	}

	/**
	 * Remove customer from DB
	 * 
	 * @param customer Customer
	 * @throws CouponSystemException
	 */
	public void removeCustomer(Customer customer) throws CouponSystemException {
		// delete all company coupons
		customerCouponDbDao.deleteAllCustomerCoupons(customer);
		// delete company
		customerDbDao.delete(customer);

	}

	/**
	 * Update Customer from DB ( all fields except customer name)
	 * 
	 * @param customer Customer
	 * @throws CouponSystemException
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {

		Customer customerFromDB = customerDbDao.read(customer.getId());
		// check if customer exist on DB
		if (customerFromDB.getCustName() == null) {
			throw new CouponSystemException(
					"Update Fail , customer " + customer.getCustName() + " not found on DataBase");
			// if found but update try to update also customer name
		} else if (!customer.getCustName().equals(customerFromDB.getCustName())) {
			customer.setCustName(customerFromDB.getCustName());
			customerDbDao.update(customer);

			throw new CouponSystemException(
					"Customer info was secessfully updated except Customer name ( Customer name cant' be override by buseness logic ");
			// correct update case
		} else {
			customerDbDao.update(customer);
		}
	}

	/**
	 * Get all customers from Database
	 * 
	 * @return Collection of customers
	 * @throws CouponSystemException
	 */
	public Collection<Customer> getAllCustomers() throws CouponSystemException {
		Collection<Customer> customers = null;
		customers = customerDbDao.getAllCustomer();
		return customers;

	}

	/**
	 * Get specific customer from database
	 * 
	 * @param id Customer ID
	 * @return customer
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(long id) throws CouponSystemException {

		Customer customer = customerDbDao.read(id);
		if (customer.getCustName() == null) {
			throw new CouponSystemException("Customer :" + id + " Not found on Data Base");
		}
		return customer;
	}

	/**
	 * Method not in use , login will perform on different classes
	 */
	@Deprecated
	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		return null;
	}

}
