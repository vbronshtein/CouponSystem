package test;

import java.sql.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.helper.TruncateAllTables;
import coupon.sys.facade.AdminFacade;
import coupon.sys.facade.CompanyFacade;
import coupon.sys.facade.CustomerFacade;

public class CustomerFacadeTest {
	public static void main(String[] args) {

		//Generate Basic Database records
		
		
		Company company1 = new Company("company1", "1111", "111@company.com");
		Company company2 = new Company("company2", "1112", "112@company.com");
		Company company3 = new Company("company3", "1113", "113@company.com");
		Company company4 = new Company("company4", "1114", "114@company.com");

		Customer customer11 = new Customer("David", "pass4");
		Customer customer12 = new Customer("Yossi", "pass5");
		Customer customer13 = new Customer("Neta", "pass6");
		Customer customer14 = new Customer("Nataly", "pass6");

		Coupon coupon1 = new Coupon("coupon1", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 100, CouponType.HEALTH, "Stam coupon14", 1000,
				"http://google4.com");
		Coupon coupon2 = new Coupon("coupon2", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 200, CouponType.CAMPING, "Stam coupon3", 2000,
				"http://google5.com");
		Coupon coupon3 = new Coupon("coupon3", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 300, CouponType.HEALTH, "Stam coupon5", 3000,
				"http://google6.com");
		Coupon coupon4 = new Coupon("coupon4", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 400, CouponType.CAMPING, "Stam coupon7", 4000,
				"http://google4.com");
		Coupon coupon5 = new Coupon("coupon5", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 500, CouponType.ELECTRICITY, "Stam coupon8", 5000,
				"http://google5.com");
		Coupon coupon6 = new Coupon("coupon6", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 100, CouponType.ELECTRICITY, "Stam coupon26", 1000,
				"http://google6.com");
		Coupon coupon7 = new Coupon("coupon7", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 200, CouponType.HEALTH, "Stam coupon34", 2000,
				"http://google4.com");
		Coupon coupon8 = new Coupon("coupon8", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 300, CouponType.HEALTH, "Stam coupon35", 3000,
				"http://google5.com");
		Coupon coupon9 = new Coupon("coupon9", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 400, CouponType.HEALTH, "Stam coupon36", 4000,
				"http://google6.com");
		Coupon coupon10 = new Coupon("coupon10", new Date(System.currentTimeMillis() - 1000 * 3600 * 24 * 30),
				new Date(System.currentTimeMillis() - 500 * 3600 * 24 * 30), 500, CouponType.HEALTH, "Stam coupon36",
				5000, "http://google6.com");
		Coupon coupon11 = new Coupon("coupon11", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 1, CouponType.HEALTH, "Stam coupon36", 5000,
				"http://google6.com");
		Coupon coupon11111 = new Coupon("coupon11111", new Date(System.currentTimeMillis()),
				new Date(System.currentTimeMillis()), 1, CouponType.HEALTH, "Stam coupon36", 5000,
				"http://google6.com");

		try {
		
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
		compFacade2.createCoupon(coupon11);

		
		
		adminFacade.createCustomer(customer11);
		adminFacade.createCustomer(customer12);
		adminFacade.createCustomer(customer13);
		CustomerFacade customerFacade11 = new CustomerFacade(customer11.getCustName());
		CustomerFacade customerFacade12 = new CustomerFacade(customer12.getCustName());
		CustomerFacade customerFacade13 = new CustomerFacade(customer13.getCustName());

		
		
		
		System.out.println("\n\n\n");
		// buy coupon - check amount -=1
		System.out.println("buy coupon - check amount -=1");
		customerFacade11.purshaseCoupon(coupon1);
		// Try to buy coupon twice
		System.out.println("Try to buy coupon twice");
//		customerFacade11.purshaseCoupon(coupon1);

		// buy coupon twice from two different customers
		System.out.println("buy coupon twice from two different customers");
		customerFacade11.purshaseCoupon(coupon2);
		customerFacade11.purshaseCoupon(coupon3);
		customerFacade12.purshaseCoupon(coupon2);
		customerFacade12.purshaseCoupon(coupon3);
		
		// try to by coupon with 0 amount
		System.out.println("try to by coupon with 0 amount");
		customerFacade11.purshaseCoupon(coupon11);
//		customerFacade13.purshaseCoupon(coupon11);

		// try to buy non exist coupon ( not any company coupon )
		System.out.println("try to buy non exist coupon ( not any company coupon )");
//		customerFacade13.purshaseCoupon(coupon11111);

		
		//get all client coupon history
		System.out.println("get all client coupon history");
		System.out.println(customerFacade11.getAllPurchesedCoupons());
		System.out.println(customerFacade12.getAllPurchesedCoupons());
		
		//get all client coupon history - empty case
		System.out.println("get all client coupon history - empty case");
		System.out.println(customerFacade13.getAllPurchesedCoupons());
		
		//get client coupon history by couponType
		System.out.println("get client coupon history by couponType");
		System.out.println(customerFacade11.getAllPurchasedCouponsByType(CouponType.CAMPING));
		
		//get client coupon history by couponType - empty case
		System.out.println("get client coupon history by couponType - empty case");
		System.out.println(customerFacade11.getAllPurchasedCouponsByType(CouponType.TRAVELLING));
		
		//get client coupon history up to price
		System.out.println("get client coupon history up to price");
		System.out.println(customerFacade11.getAllPurchasedCouponsUpToPrice(100000));
		System.out.println(customerFacade11.getAllPurchasedCouponsUpToPrice(2500));
		
		//get client coupon history up to price - empty case
		System.out.println("get client coupon history up to price - empty case");
		System.out.println(customerFacade11.getAllPurchasedCouponsUpToPrice(20));

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
