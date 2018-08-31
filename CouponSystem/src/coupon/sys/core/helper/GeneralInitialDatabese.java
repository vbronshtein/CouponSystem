package coupon.sys.core.helper;

import java.sql.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;

/**
 * Class created for fill Database with initial random data
 * 
 * @author vbronshtein
 *
 */
public class GeneralInitialDatabese {

	// CTOR
	public GeneralInitialDatabese() {
	}

	/**
	 * Method generate pre-defined record on DataBase
	 */
	public void generateBasicRecords() {

		Company company1 = new Company("company1", "1111", "111@company.com");
		Company company2 = new Company("company2", "1112", "112@company.com");
		Company company3 = new Company("company3", "1113", "113@company.com");
		Company company4 = new Company("company4", "1114", "114@company.com");

		Customer customer11 = new Customer("David", "pass11");
		Customer customer12 = new Customer("Yossi", "pass12");
		Customer customer13 = new Customer("Neta", "pass13");
		Customer customer14 = new Customer("Nataly", "pass14");

		Coupon coupon1 = new Coupon("coupon1", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 1), 100, CouponType.HEALTH, "Stam coupon14",
				1000, "http://google4.com");
		Coupon coupon2 = new Coupon("coupon2", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 2), 200, CouponType.CAMPING, "Stam coupon3",
				2000, "http://google5.com");
		Coupon coupon3 = new Coupon("coupon3", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 3), 300, CouponType.HEALTH, "Stam coupon5",
				3000, "http://google6.com");
		Coupon coupon4 = new Coupon("coupon4", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 4), 400, CouponType.CAMPING, "Stam coupon7",
				4000, "http://google4.com");
		Coupon coupon5 = new Coupon("coupon5", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 5), 500, CouponType.ELECTRICITY,
				"Stam coupon8", 5000, "http://google5.com");
		Coupon coupon6 = new Coupon("coupon6", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 1), 100, CouponType.ELECTRICITY,
				"Stam coupon26", 1000, "http://google6.com");
		Coupon coupon7 = new Coupon("coupon7", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 2), 200, CouponType.HEALTH, "Stam coupon34",
				2000, "http://google4.com");
		Coupon coupon8 = new Coupon("coupon8", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 3), 300, CouponType.HEALTH, "Stam coupon35",
				3000, "http://google5.com");
		Coupon coupon9 = new Coupon("coupon9", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 4), 400, CouponType.HEALTH, "Stam coupon36",
				4000, "http://google6.com");
		Coupon coupon10 = new Coupon("coupon10", new Date(System.currentTimeMillis() - 1000 * 3600 * 24 * 30),
				new Date(System.currentTimeMillis() + 1000 * 3600 * 24 * 5), 500, CouponType.HEALTH, "Stam coupon36",
				5000, "http://google6.com");
		Coupon coupon11 = new Coupon("coupon11", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 600, CouponType.HEALTH, "Stam coupon36", 5000,
				"http://google6.com");

		try {
			// delete all tables
			TruncateAllTables trAllTables = new TruncateAllTables();
			trAllTables.trancateAll();
			// add new coupon
			AdminFacade adminFacade = new AdminFacade();
			adminFacade.createCompany(company1);
			adminFacade.createCompany(company2);
			adminFacade.createCompany(company3);

			CompanyFacade compFacade1 = new CompanyFacade(company1.getName());
			CompanyFacade compFacade2 = new CompanyFacade(company2.getName());
			CompanyFacade compFacade3 = new CompanyFacade(company3.getName());

			// create coupons for company 1 and 2
			compFacade1.createCoupon(coupon1);
			compFacade1.createCoupon(coupon2);
			compFacade1.createCoupon(coupon3);
			compFacade1.createCoupon(coupon4);
			compFacade1.createCoupon(coupon5);

			compFacade2.createCoupon(coupon6);
			compFacade2.createCoupon(coupon7);
			compFacade2.createCoupon(coupon8);
			compFacade2.createCoupon(coupon9);
			compFacade2.createCoupon(coupon10);

			adminFacade.createCustomer(customer11);
			adminFacade.createCustomer(customer12);
			adminFacade.createCustomer(customer13);
			CustomerFacade customerFacade11 = new CustomerFacade(customer11.getCustName());
			CustomerFacade customerFacade12 = new CustomerFacade(customer12.getCustName());
			CustomerFacade customerFacade13 = new CustomerFacade(customer13.getCustName());

			// Purchase coupons for customer 1 and 2
			customerFacade11.purchaseCoupon(coupon1);
			customerFacade11.purchaseCoupon(coupon2);
			customerFacade11.purchaseCoupon(coupon3);
			customerFacade11.purchaseCoupon(coupon4);
			customerFacade11.purchaseCoupon(coupon5);
			customerFacade11.purchaseCoupon(coupon6);
			customerFacade11.purchaseCoupon(coupon7);
			customerFacade11.purchaseCoupon(coupon8);
			customerFacade11.purchaseCoupon(coupon9);
			customerFacade11.purchaseCoupon(coupon10);

			customerFacade12.purchaseCoupon(coupon1);
			customerFacade12.purchaseCoupon(coupon2);
			customerFacade12.purchaseCoupon(coupon3);
			customerFacade12.purchaseCoupon(coupon4);
			customerFacade12.purchaseCoupon(coupon5);

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
	}
}
