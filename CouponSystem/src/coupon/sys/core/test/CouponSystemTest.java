package coupon.sys.core.test;

import coupon.sys.core.beans.Company;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.helper.GeneralInitialDatabese;
import coupon.sys.core.helper.TruncateAllTables;
import coupon.sys.core.main.ClientType;
import coupon.sys.core.main.CouponSystem;

public class CouponSystemTest {
	public static void main(String[] args) {
		try {
			// Truncate old records and create new recods in database
			TruncateAllTables truncateAll = new TruncateAllTables();
			truncateAll.trancateAll();
			//			GeneralInitialDatabese generateDb = new GeneralInitialDatabese();

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
			
			// update company
			teva.setEmail("updatedmail@teva.com");
			adminFacade.updateCompany(teva);

			// read company
			System.out.print("Updated company: ");
			System.out.println(adminFacade.getCompany(teva.getName()));
			
			// remove company
			adminFacade.removeCompany(teva);
			// get all companies
			System.out.println( adminFacade.getAllCompanies() );
			// create customer
			// update customer
			// read customer
			// remove customer
			// get all customers

			/*
			 * Company Facade Tests
			 */

			// login as Company
			// create coupon
			// read coupon
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

	
			
			
			
			
			Thread.sleep(30 * 1000);
			couponSystem.shutdown();
			System.out.println("exit from program");
			
			
			
			
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
		} catch (InterruptedException e) {
			e.getMessage();
//			e.printStackTrace();
		}
	}
}
