package coupon.sys.facade;

import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class AdminFacade implements CouponClientFacade {

	public AdminFacade() {
	}

	public void createCompany(Company company) {
		CompanyDbDao compDbDao = new CompanyDbDao();

		try {
			// check if received company name doesn't exist on DB , if not create new record
			Collection<Company> companies = compDbDao.getAllCompanies();
			boolean nameIsExist = false;
			for (Company companyTmp : companies) {
				if (company.isNameEqualTo(companyTmp)) {
					nameIsExist = true;
					break;
				}
			}
			// Create new company if doesn't exist in Data Base
			if (!nameIsExist) {
				compDbDao.create(company);
			} else {
				throw new CouponSystemException(
						"Company with name : " + company.getName() + " is already exist on Data Base");
			}

		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	public void removeCompany(Company company) {
		CompanyDbDao compDbDao = new CompanyDbDao();
		CouponDbDao couponDbDao = new CouponDbDao();

		try {

			// delete all company coupons
			Collection<Coupon> CurrentCompanycoupons = company.getCoupons();
			if (CurrentCompanycoupons != null) {
				for (Coupon coupon : CurrentCompanycoupons) {
					couponDbDao.delete(coupon);
				}
			}

			// delete company
			compDbDao.delete(company);

		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	public void updateCompany(Company company) {
		CompanyDbDao compDbDao = new CompanyDbDao();

		try {
			Company companyFromDB;
			companyFromDB = compDbDao.read(company.getId());

			// Validate if company exist in DB and if Company name was the same
			if (companyFromDB.getName() == null) {
				throw new CouponSystemException(
						"Update Database fail , Company : " + company.getName() + " was not found");
			} else if (companyFromDB.getName().equals(company.getName())) { //if company exist ,and client doesnt try update client name 
				compDbDao.update(company); 
			} else { 														// if client try to update attributes include company name
				company.setName(compDbDao.getName());  						//override company name that received from data base
				compDbDao.update(company);
				throw new CouponSystemException(
						"Company info was secessfully updated except Company name ( Company name cant' be override by buseness logic ");
			}

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Company getCompany(long id) {
		CompanyDbDao companyDb = new CompanyDbDao();
		Company company = null;
		try {
			company = companyDb.read(id);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return company;
	}

	public Collection<Company> getAllCompanies() {
		CompanyDbDao companyDb = new CompanyDbDao();
		Collection<Company> companies = null;

		try {
			companies = companyDb.getAllCompanies();
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return companies;
	}

	
	/**
	 * 
	 * @param customer
	 */
	public void createCustomer(Customer customer) {
		CustomerDbDao customerDb = new CustomerDbDao();

		try {
			// Check if customer with same name is exist on DB
			Collection<Customer> customers = customerDb.getAllCustomer();
			boolean nameIsExist = false;
			for (Customer customerTmp : customers) {
				if (customer.isNameEqualTo(customerTmp)) {
					nameIsExist = true;
					break;
				}
			}

			// Add customer to DB if was not found
			if (!nameIsExist) {
				customerDb.create(customer);
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
