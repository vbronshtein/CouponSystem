package coupon.sys.core.test;

import java.sql.Date;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.derby.tools.sysinfo;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.helper.GeneralInitialDatabese;
import coupon.sys.core.helper.IdUpdater;
import coupon.sys.core.helper.TruncateAllTables;
import coupon.sys.core.main.ClientType;
import coupon.sys.core.main.CouponSystem;

public class CouponSystemTest {
	public static void main(String[] args) {
		try {
			// Truncate old records and create new recods in database
			TruncateAllTables truncateAll = new TruncateAllTables();
			truncateAll.trancateAll();

			IdUpdater idUpdater = new IdUpdater();
			
			
			// get CouponSystem instance
			CouponSystem couponSystem = CouponSystem.getInstance();

			// login as admin
			System.out.println("Login as admin ...");
			AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
			if(adminFacade != null ) {
				System.out.println("Logged successfully ");
			}
			
			
			// create company
			Company teva = new Company("Teva", "1234", "support@teva.com");
			Company pepsi = new Company("Pepsi", "1235", "support@pepsi.com");
			Company osem = new Company("Osem", "1236", "support@osem.com");

			adminFacade.createCompany(teva);
			adminFacade.createCompany(pepsi);
			adminFacade.createCompany(osem);
			
			idUpdater.updateCompanyId(teva);
			idUpdater.updateCompanyId(pepsi);
			idUpdater.updateCompanyId(osem);

			
			// update company
			teva.setEmail("updatedmail@teva.com");
			adminFacade.updateCompany(teva);

			// read company
			System.out.print("Updated company: ");
			System.out.println(adminFacade.getCompany(teva.getId()));
			
			// remove company
			adminFacade.removeCompany(teva);
			// get all companies
			System.out.println( adminFacade.getAllCompanies() );

			// create customer
			Customer vitaly = new Customer("Vitaly", "12345");
			Customer yossi = new Customer("Yossi", "12346");
			Customer tal = new Customer("Tal", "12347");

			adminFacade.createCustomer(vitaly);
			adminFacade.createCustomer(yossi);
			adminFacade.createCustomer(tal);
			
			idUpdater.updateCustomerId(vitaly);
			idUpdater.updateCustomerId(yossi);
			idUpdater.updateCustomerId(tal);
			
			
			
			
			
			// update customer
			vitaly.setPassword("654321");
			adminFacade.updateCustomer(vitaly);
			// read customer
			System.out.println(adminFacade.getCustomer(vitaly.getId()));
			// remove customer
			adminFacade.removeCustomer(vitaly);
			// get all customers
			System.out.println("Get all customers :" + adminFacade.getAllCustomers());
			System.out.println();
			
			
			/*
			 * Company Facade Tests
			 */

			// login as Company
			System.out.println("Login as client ...");
			CompanyFacade compFacade = (CompanyFacade) couponSystem.login(osem.getName(), osem.getPassword(), ClientType.COMPANY);
			if(compFacade != null ) {
				System.out.println("Logged as client successfully ");
			}
			
			// create coupon
			Coupon hiltonVacation = new Coupon("Hilton", new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 2), 100, CouponType.TRAVELLING, "Hilton vacation", 1000,
					"http://hilton.com");
			Coupon asianDinner = new Coupon("Asian Food", new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 1), 200, CouponType.FOOD, "Asian Food", 150,
					"http://japanica.com");
			Coupon jerusalemTrip = new Coupon("Trip in Jerusalem", new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 20), 10, CouponType.TRAVELLING, "Day trip in Jerusalem", 200,
					"http://israeltours.com");
			
			
			compFacade.createCoupon(hiltonVacation);
			compFacade.createCoupon(asianDinner);
			compFacade.createCoupon(jerusalemTrip);
			
			
			idUpdater.updateCouponID(hiltonVacation);
			idUpdater.updateCouponID(asianDinner);
			idUpdater.updateCouponID(jerusalemTrip);

			// read coupon
			System.out.println(compFacade.getCoupon(hiltonVacation.getId()));
			
			// update coupon
			// remove coupon
			// get All coupons
			// get Coupon by Type

			/*
			 * Customer Facade Tests
			 */

			// purchase coupon
			// get all purchased Coupon
			// get all purchased coupons by type
			// get all purchased coupons by type

	
			
			
			
			
			
			
			
			
			// // login to Company ( company 2 )
			// System.out.println("Login as company ...");
			// CompanyFacade companyFacade = (CompanyFacade) couponSystem.login("company2",
			// "1112", ClientType.COMPANY);
			//
			// // login as Customer "Yossi"
			// System.out.println("Login as customer ...");
			// CustomerFacade customerFacade = (CustomerFacade) couponSystem.login("Yossi",
			// "pass12", ClientType.CUSTOMER);
			//
			// // create
			// // read
			// // update
			// // delete

			// generateDb.generateBasicRecords();
			//
			//
			// System.out.println("Login as company succed!!");
			// System.out.print("Get all company2 coupons: ");
			// // read company 2 coupons
			// System.out.println(companyFacade.getAllCoupons());
			// System.out.println("-------------------------------");
			//
			// System.out.println("Login as customer succed!!");
			// System.out.print("Get all customer coupons: ");
			// // get all customer (Yossi) purchased coupons
			// System.out.println(customerFacade.getAllPurchesedCoupons());
			// System.out.println("-------------------------------");
			//
			// System.out.println("Login as admin succed!!");
			// System.out.print("Get all companies: ");
			// // get all companies
			// System.out.println(adminFacade.getAllCompanies());
			// System.out.println("-------------------------------");
			//

		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
	}
}
