package test;

import java.sql.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.facade.AdminFacade;

public class AdminFacadeTest {
	public static void main(String[] args) {
		AdminFacade adminFacade = new AdminFacade();

		Company company4 = new Company(4, "company4", "1114", "114@company.com");
		Company company5 = new Company(5, "company5", "1115", "115@company.com");
		Company company6 = new Company(6, "company6", "1116", "116@company.com");

		
		Customer customer4 = new Customer(4, "David", "pass4");
		Customer customer5 = new Customer(5, "Yossi", "pass5");
		Customer customer6 = new Customer(6, "Neta", "pass6");
		
		
		Coupon coupon4 = new Coupon(4, "coupon4", new Date(2018, 07, 22), new Date(2018, 07, 22), 400,
				CouponType.HEALTH, "Stam coupon4", 4000, "http://google4.com");
		Coupon coupon5 = new Coupon(5, "coupon5", new Date(2018, 07, 22), new Date(2018, 07, 22), 500,
				CouponType.HEALTH, "Stam coupon5", 5000, "http://google5.com");
		Coupon coupon6 = new Coupon(6, "coupon5", new Date(2018, 07, 22), new Date(2018, 07, 22), 600,
				CouponType.HEALTH, "Stam coupon6", 5000, "http://google6.com");

		CompanyDbDao companyDbDao = new CompanyDbDao();
		CouponDbDao couponDbDao = new CouponDbDao();
		CustomerDbDao customerDbDao = new CustomerDbDao();
		try {

			// create company
			System.out.println("Create :");
			adminFacade.createCompany(company4);
			adminFacade.createCompany(company4);
			System.out.println(companyDbDao.read(4));
			System.out.println();

			// delete company
			System.out.println("Delete :");
			adminFacade.createCompany(company5);
			System.out.println("before delete :" + companyDbDao.read(5));
			adminFacade.removeCompany(company5);
			System.out.println("After delete :" + companyDbDao.read(5));

			System.out.println();

			// update company
			System.out.println("update :");
			adminFacade.createCompany(company5);
			System.out.println("before update :" + companyDbDao.read(5));
			company5.setEmail("newmail@1234");
			company5.setPassword("654321");
			adminFacade.updateCompany(company5);
			System.out.println("after update :" + companyDbDao.read(5));
			
			System.out.println();
			company5.setEmail("222222newmail@1234");
			company5.setPassword("2222654321");
			company5.setName("newName");
			adminFacade.updateCompany(company5);
			System.out.println("after update 2222:" + companyDbDao.read(5));
			

			adminFacade.removeCompany(company5);
			
			// all companies list
			System.out.println("all company list :");
			
			System.out.println();
			System.out.println(adminFacade.getAllCompanies());

			// see spesific company
			System.out.println();
			System.out.println("Company 4 :");
			
			System.out.println(adminFacade.getCompany(4));

			System.out.println();
			System.out.println("!!!!!!Check with eldad this case !!!!!!");
			System.out.println("Non exist company :");
			System.out.println(adminFacade.getCompany(1111111));
			
			System.out.println();
			System.out.println();
		

			// create customer
			System.out.println("Create customer :");
			adminFacade.createCustomer(customer4);
			adminFacade.createCustomer(customer4);
			System.out.println(customerDbDao.read(4));
			System.out.println();

			// delete customer
			System.out.println("Delete customer :");
			adminFacade.createCustomer(customer5);
			System.out.println("before delete :" + customerDbDao.read(5));
			adminFacade.removeCustomer(customer5);
			System.out.println("After delete :" + customerDbDao.read(5));
			
			System.out.println();

			// update customer
			System.out.println("update :");
			adminFacade.createCustomer(customer5);
			System.out.println("before update :" + customerDbDao.read(5));
			company5.setEmail("newmail@1234");
			company5.setPassword("654321");
			adminFacade.updateCustomer(customer5);
			System.out.println("after update :" + customerDbDao.read(5));
			
			System.out.println();
			// company5.setEmail("222222newmail@1234");
			// company5.setPassword("2222654321");
			// company5.setName("newName");
			customer5.setPassword("newpass");
			customer5.setCustName("New name");
			adminFacade.updateCustomer(customer5);
			System.out.println("after update 2222:" + customerDbDao.read(5));
			
			
			adminFacade.removeCustomer(customer5);

			// all customer list
			System.out.println();
			System.out.println("all customer list :");
			
			System.out.println();
			System.out.println(adminFacade.getAllCustomers());

			// see specific customer
			System.out.println();
			System.out.println("Customer 4 :");
			
			System.out.println(adminFacade.getCustomer(4));
			
			System.out.println();
			
			System.out.println("Non exist company :");
			System.out.println(adminFacade.getCompany(1111111));
			
			System.out.println();
			System.out.println();

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
