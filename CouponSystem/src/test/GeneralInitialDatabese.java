package test;

import java.sql.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.helper.TruncateAllTables;
import coupon.sys.facade.AdminFacade;
import coupon.sys.facade.CompanyFacade;
import coupon.sys.facade.CustomerFacade;

public class GeneralInitialDatabese {

	public GeneralInitialDatabese() {
	}

	public void generateBasicRecords() {

		Company company1 = new Company(1, "company1", "1111", "111@company.com");
		Company company2 = new Company(2, "company2", "1112", "112@company.com");
		Company company3 = new Company(3, "company3", "1113", "113@company.com");
		Company company4 = new Company(4, "company4", "1114", "114@company.com");

		Customer customer11 = new Customer(11, "David", "pass4");
		Customer customer12 = new Customer(12, "Yossi", "pass5");
		Customer customer13 = new Customer(13, "Neta", "pass6");
		Customer customer14 = new Customer(14, "Nataly", "pass6");

		Coupon coupon1 = new Coupon(1, "coupon1", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 100, CouponType.HEALTH, "Stam coupon14", 1000,
				"http://google4.com");
		Coupon coupon2 = new Coupon(2, "coupon2", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 200, CouponType.CAMPING, "Stam coupon3", 2000,
				"http://google5.com");
		Coupon coupon3 = new Coupon(3, "coupon3", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 300, CouponType.HEALTH, "Stam coupon5", 3000,
				"http://google6.com");
		Coupon coupon4 = new Coupon(4, "coupon4", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 400, CouponType.CAMPING, "Stam coupon7", 4000,
				"http://google4.com");
		Coupon coupon5 = new Coupon(5, "coupon5", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 500, CouponType.ELECTRICITY, "Stam coupon8", 5000,
				"http://google5.com");
		Coupon coupon6 = new Coupon(6, "coupon6", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 100, CouponType.ELECTRICITY, "Stam coupon26", 1000,
				"http://google6.com");
		Coupon coupon7 = new Coupon(7, "coupon7", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 200, CouponType.HEALTH, "Stam coupon34", 2000,
				"http://google4.com");
		Coupon coupon8 = new Coupon(8, "coupon8", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 300, CouponType.HEALTH, "Stam coupon35", 3000,
				"http://google5.com");
		Coupon coupon9 = new Coupon(9, "coupon9", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 400, CouponType.HEALTH, "Stam coupon36", 4000,
				"http://google6.com");
		Coupon coupon10 = new Coupon(10, "coupon10", new Date(System.currentTimeMillis() - 1000 * 3600 * 24 * 30),
				new Date(System.currentTimeMillis() - 500 * 3600 * 24 * 30), 500, CouponType.HEALTH, "Stam coupon36",
				5000, "http://google6.com");
		Coupon coupon11 = new Coupon(11, "coupon11", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 600, CouponType.HEALTH, "Stam coupon36", 5000,
				"http://google6.com");

		try {
			// delete all tables
			TruncateAllTables trAllTables = new TruncateAllTables();
			trAllTables.trancateAll();
			// add new coupon
			System.out.println("add new coupon");
			AdminFacade adminFacade = new AdminFacade();
			adminFacade.createCompany(company1);
			adminFacade.createCompany(company2);
			adminFacade.createCompany(company3);

			CompanyFacade compFacade1 = new CompanyFacade(company1.getName());
			CompanyFacade compFacade2 = new CompanyFacade(company2.getName());
			CompanyFacade compFacade3 = new CompanyFacade(company3.getName());

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

			customerFacade11.purshaseCoupon(coupon4);
			customerFacade11.purshaseCoupon(coupon5);
			customerFacade12.purshaseCoupon(coupon6);
			customerFacade12.purshaseCoupon(coupon7);

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
