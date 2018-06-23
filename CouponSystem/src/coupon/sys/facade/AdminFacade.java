package coupon.sys.facade;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyCouponDbDao;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class AdminFacade implements CouponClientFacade {
	private CompanyDbDao companyDbDao;
	private CouponDbDao couponDbDao;
	private CompanyCouponDbDao companyCouponDbDao;
	private CustomerDbDao customerDbDao ;

	public AdminFacade() {
		this.companyDbDao = new CompanyDbDao();
		this.couponDbDao = new CouponDbDao();
		this.companyCouponDbDao = new CompanyCouponDbDao();
		this.customerDbDao = new CustomerDbDao();
	}

	public void createCompany(Company company) throws CouponSystemException {

		if (companyDbDao.getCompanyByName(company.getName()) != null) {
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

		// update company except for the company name
		if (!company.getName().equals(companyFromDB.getName())) {
			company.setName(companyFromDB.getName());
			companyDbDao.update(company);

			throw new CouponSystemException(
					"Company info was secessfully updated except Company name ( Company name cant' be override by buseness logic ");
		} else {
			companyDbDao.update(company);
		}
	}

	public Company getCompany(long id) throws CouponSystemException {

		Company company = companyDbDao.read(id);

		return company;
	}

	public Collection<Company> getAllCompanies() throws CouponSystemException {

		Collection<Company> companies = companyDbDao.getAllCompanies();

		return companies;
	}

	public void createCustomer(Customer customer) {

		try {
			// Check if customer with same name is exist on DB
			Collection<Customer> customers = customerDbDao.getAllCustomer();
			boolean nameIsExist = false;
			for (Customer customerTmp : customers) {
				if (customer.isNameEqualTo(customerTmp)) {
					nameIsExist = true;
					break;
				}
			}

			// Add customer to DB if was not found
			if (!nameIsExist) {
				customerDbDao.create(customer);
			} else {
				throw new CouponSystemException(
						"Customer with name : " + customer.getCustName() + " is already exist on Data Base");
			}

		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	public void removeCustomer(Customer customer) {
		CustomerDbDao customerDb = new CustomerDbDao();
		CouponDbDao couponDb = new CouponDbDao();

		try {
			// Delete all customer coupons
			Collection<Coupon> currentCustomerCoupons = customer.getCoupons();
			if (currentCustomerCoupons != null) {
				for (Coupon coupon : currentCustomerCoupons) {
					couponDb.delete(coupon);
				}
			}
			// Delete customer
			customerDb.delete(customer);

		} catch (CouponSystemException e) {
			e.printStackTrace();
		}

	}

	public void updateCustomer(Customer customer) {

		try {
			CustomerDbDao customerDb = new CustomerDbDao();
			Customer customerFromDb = customerDb.read(customer.getId());

			if (customerFromDb.getCustName() == null) {
				throw new CouponSystemException(
						"Update Database fail , Customer : " + customer.getCustName() + " was not found");
			} else if (customerFromDb.getCustName().equals(customer.getCustName())) {
				customerDb.update(customer);
			} else {
				customer.setCustName(customerFromDb.getCustName());
				customerDb.update(customer);
				throw new CouponSystemException(
						"Customer info was secessfully updated except name ( Customer name cant' be override by buseness logic ");
			}

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Customer getCustomer(long id) {

		CustomerDbDao customerDb = new CustomerDbDao();
		Customer customer = null;
		try {
			customer = customerDb.read(id);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customer;
	}

	public Collection<Customer> getAllCustomers() {
		CustomerDbDao customerDb = new CustomerDbDao();
		Collection<Customer> customers = null;

		try {
			customers = customerDb.getAllCustomer();
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customers;

	}

	@Override
	public CouponClientFacade login(String name, String password, String clientType) {
		// TODO Auto-generated method stub
		return null;
	}

}
