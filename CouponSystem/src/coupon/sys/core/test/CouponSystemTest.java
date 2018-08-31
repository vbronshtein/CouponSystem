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
			// Truncate old records in database
			TruncateAllTables truncateAll = new TruncateAllTables();
			truncateAll.trancateAll();

			// get CouponSystem instance
			CouponSystem couponSystem = CouponSystem.getInstance();

			/*
			 * Admin Facade Tests
			 */

			// login as admin
			System.out.println("Login as admin ...");
			AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
			if (adminFacade != null) {
				System.out.println("Logged successfully ");
			}

			// create company
			Company teva = new Company("Teva", "1234", "support@teva.com");
			Company hilton = new Company("Hilton", "1235", "support@hilton.com");
			Company osem = new Company("Osem", "1236", "support@osem.com");

			adminFacade.createCompany(teva);
			adminFacade.createCompany(hilton);
			adminFacade.createCompany(osem);

			// helper for Phase1 - get Company/Customer/Coupon ID from DB and update
			// relevant object
			IdUpdater idUpdater = new IdUpdater();
			idUpdater.updateCompanyId(teva);
			idUpdater.updateCompanyId(hilton);
			idUpdater.updateCompanyId(osem);

			// update company
			teva.setEmail("updatedmail@teva.com");
			adminFacade.updateCompany(teva);

			// read company
			System.out.println("Read updated company: " + adminFacade.getCompany(teva.getId()));

			// remove company
			adminFacade.removeCompany(teva);

			// get all companies
			System.out.println("All Companies list: " + adminFacade.getAllCompanies());

			// create customer
			Customer vitaly = new Customer("Vitaly", "12345");
			Customer yossi = new Customer("Yossi", "12346");
			Customer tal = new Customer("Tal", "12347");

			adminFacade.createCustomer(vitaly);
			adminFacade.createCustomer(yossi);
			adminFacade.createCustomer(tal);

			// Phase1 helper for have object IDs
			idUpdater.updateCustomerId(vitaly);
			idUpdater.updateCustomerId(yossi);
			idUpdater.updateCustomerId(tal);

			// update customer
			vitaly.setPassword("654321");
			adminFacade.updateCustomer(vitaly);

			// read customer
			System.out.println("Read updated customer: " + adminFacade.getCustomer(vitaly.getId()));

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
			CompanyFacade compFacade = (CompanyFacade) couponSystem.login(osem.getName(), osem.getPassword(),
					ClientType.COMPANY);
			if (compFacade != null) {
				System.out.println("Logged as client successfully ");
			}

			// create coupon
			Coupon hiltonVacation = new Coupon("Hilton", new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 2), 100, CouponType.TRAVELLING,
					"Hilton vacation", 1000, "http://hilton.com");
			Coupon asianDinner = new Coupon("Asian Food", new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 1), 200, CouponType.FOOD, "Asian Food",
					150, "http://japanica.com");
			Coupon jerusalemTrip = new Coupon("Trip in Jerusalem", new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 20), 10, CouponType.TRAVELLING,
					"Day trip in Jerusalem", 200, "http://israeltours.com");

			compFacade.createCoupon(hiltonVacation);
			compFacade.createCoupon(asianDinner);
			compFacade.createCoupon(jerusalemTrip);

			// Phase1 helper for have object IDs
			idUpdater.updateCouponID(hiltonVacation);
			idUpdater.updateCouponID(asianDinner);
			idUpdater.updateCouponID(jerusalemTrip);

			// update coupon
			hiltonVacation.setEndDate(new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 20));
			compFacade.updateCoupon(hiltonVacation);

			// read coupon
			System.out.println("Read updated coupon: " + compFacade.getCoupon(hiltonVacation.getId()));

			// remove coupon
			compFacade.removeCoupon(hiltonVacation);

			// get All coupons
			System.out.println("Get all company coupons :" + compFacade.getAllCoupons());
			// get Coupon by Type
			System.out.println("Get company coupons by Type :" + compFacade.getCouponByType(CouponType.TRAVELLING));
			System.out.println();

			/*
			 * Customer Facade Tests
			 */

			// login as Company
			System.out.println("Login as client ...");
			CustomerFacade custFacade = (CustomerFacade) couponSystem.login("Yossi", "12346", ClientType.CUSTOMER);
			if (custFacade != null) {
				System.out.println("Logged as customer successfully ");
			}

			// purchase coupon
			custFacade.purchaseCoupon(asianDinner);
			custFacade.purchaseCoupon(jerusalemTrip);

			// get all purchased Coupon
			System.out.println("Read all purchased coupons: " + custFacade.getAllPurchesedCoupons());
			// get all purchased coupons by type
			System.out.println(
					"Read purchased coupons by Type: " + custFacade.getAllPurchasedCouponsByType(CouponType.FOOD));

			// get all purchased coupons by price
			System.out
					.println("Read purchased coupons up to Price: " + custFacade.getAllPurchasedCouponsUpToPrice(170));

			System.out.println();

			/*
			 * Advanced business logic test
			 */

			// Delete Company cause to delete all coupons also already bought ( Admin +
			// Company )
			System.out.println("Advanced Remove Logic:");
			CompanyFacade compHiltonFacade = (CompanyFacade) couponSystem.login(hilton.getName(), hilton.getPassword(),
					ClientType.COMPANY);

			Coupon hiltonCouponEilat = new Coupon("Hilton Eilat", new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 2), 100, CouponType.TRAVELLING,
					"Weekend in Hilton Eilat", 1000, "http://hilton.com");
			Coupon hiltonCouponCouponJerusalem = new Coupon("Hilton Jerusalem", new Date(System.currentTimeMillis()),
					new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 2), 100, CouponType.TRAVELLING,
					"Weekend in Hilton Jerusalem", 1000, "http://hilton.com");

			compHiltonFacade.createCoupon(hiltonCouponEilat);
			compHiltonFacade.createCoupon(hiltonCouponCouponJerusalem);

			CustomerFacade talCastFacade = (CustomerFacade) couponSystem.login("Tal", "12347", ClientType.CUSTOMER);
			talCastFacade.purchaseCoupon(hiltonCouponEilat);
			talCastFacade.purchaseCoupon(hiltonCouponCouponJerusalem);

			System.out.println("Company coupons before remove: " + compHiltonFacade.getAllCoupons());
			System.out.println("Customer coupons before remove: " + talCastFacade.getAllPurchesedCoupons());

			adminFacade.removeCompany(hilton);
			System.out.println("Company coupons after remove: " + compHiltonFacade.getAllCoupons());
			System.out.println("Customer coupons after remove: " + talCastFacade.getAllPurchesedCoupons());

			/*
			 * Negative Tests
			 */

			// ---Add company with same name fail---
			// Company newOsem = new Company("Osem", "newPass", "support@newosem.com");
			// adminFacade.createCompany(newOsem);

			// ---add customer with same name fail---
			// Customer newTal = new Customer("Tal", "newPass");
			// adminFacade.createCustomer(newTal);

			// ---add coupon with same title fail---
			// Coupon newHiltonVacation = new Coupon("Hilton", new
			// Date(System.currentTimeMillis()),
			// new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 20), 500,
			// CouponType.TRAVELLING,
			// "Hilton vacation", 5000, "http://hilton.com");
			//
			// compFacade.createCoupon(hiltonVacation);
			// compFacade.createCoupon(newHiltonVacation);

			// ---try to update company name---
			// Company newNameOsem = new Company("newOsemName", "newPass",
			// "support@newosem.com");
			// newNameOsem.setId(osem.getId());
			// adminFacade.updateCompany(newNameOsem);

			// ---update customer all except name---
			// Customer newNameTal = new Customer("newTal", "newPass");
			// newNameTal.setId(tal.getId());
			// adminFacade.updateCustomer(newNameTal);

			// ---purchase same coupon available only ones---
			// custFacade.purchaseCoupon(asianDinner);
		} catch (CouponSystemException e) {
			System.err.println(e.getMessage());
		}
	}
}
