package test;

import java.sql.Date;
import java.util.Collection;
import java.util.Random;

import org.apache.derby.tools.sysinfo;

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

public class CompanyFacadeTest {
	public static void main(String[] args) {

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

			// try to add coupon with existed name
			System.out.println("try to add coupon with existed name");
			// compFacade2.createCoupon(coupon8);

			// try to add coupon with existed name from different company
			System.out.println("try to add coupon with existed name from different company");
			// compFacade3.createCoupon(coupon8);

			// delete coupon - check also client coupons will delete
			System.out.println("delete coupon - check also client coupons will delete");
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

			// End helper

			compFacade1.removeCoupon(coupon4);

			// delete non-exist coupon
			System.out.println("delete non-exist coupon");
			compFacade1.removeCoupon(coupon11);

			// delete coupon of other company
			System.out.println("delete coupon of other company");
			compFacade1.removeCoupon(coupon6);

			// update coupon - only endDate and price
			System.out.println("update coupon - only endDate and price");
			coupon6.setEndDate(new Date(System.currentTimeMillis()+ 1000*3600*48 ));
			coupon6.setPrice(500000);
			compFacade2.updateCoupon(coupon6);
			
			
			// update non exist coupon
			System.out.println("update non exist coupon");

			// update coupon of other company
			System.out.println("update coupon of other company");
			coupon2.setEndDate(new Date(System.currentTimeMillis()+ 1000*3600*48 ));
			coupon2.setPrice(500000);
			compFacade2.updateCoupon(coupon2);

			// get this company info
			System.out.println("get this company info");
			System.out.println( compFacade1.getCoupon(coupon3.getId()));

			
			// check coupon by CouponType
			System.out.println("check coupon by CouponType");
			System.out.println(compFacade2.getCouponByType(CouponType.HEALTH));
			// check coupon by CouponType - empty case
			System.out.println("check coupon by CouponType - empty case");

			// check coupon upTo Price
			System.out.println("check coupon upTo Price");
			System.out.println(compFacade2.getCouponUptoPrice(3000));
			// check coupon upTo Price - empty case
			System.out.println("check coupon upTo Price - empty case");
			System.out.println(compFacade2.getCouponUptoPrice(10));

			// check coupon upTo End Date
			System.out.println("check coupon upTo End Date");
			coupon7.setEndDate(new Date(System.currentTimeMillis() - 1000*3600*48 ));
			long curr = System.currentTimeMillis();
			System.out.println(compFacade2.getCouponUpToDate(new Date(curr)));

			// check coupon upTo End Date - empty case
			System.out.println("check coupon upTo End Date - empty case");
			System.out.println(compFacade3.getCouponUpToDate(new Date(curr)));

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
