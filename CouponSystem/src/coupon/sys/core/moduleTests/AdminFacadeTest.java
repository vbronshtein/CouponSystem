package coupon.sys.core.moduleTests;

import java.sql.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.GlobalCouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.helper.TruncateAllTables;

/**
 * Class for test Admin facade class
 * 
 * @author vbronshtein
 *
 */
public class AdminFacadeTest {
	public static void main(String[] args) {

		Company company1 = new Company("company1", "1111", "111@company.com");
		Company company2 = new Company("company2", "1112", "112@company.com");
		Company company3 = new Company("company3", "1113", "113@company.com");
		Company company4 = new Company("company4", "1114", "114@company.com");
		Company company5 = new Company("company5", "1115", "114@company.com");

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
			// truncate all old records on DataBase
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

			customerFacade11.purshaseCoupon(coupon1);
			customerFacade11.purshaseCoupon(coupon2);
			customerFacade11.purshaseCoupon(coupon3);
			customerFacade12.purshaseCoupon(coupon2);
			customerFacade12.purshaseCoupon(coupon3);
			customerFacade12.purshaseCoupon(coupon9);
			customerFacade12.purshaseCoupon(coupon10);

			System.out.println("\n\n\n");

			// create new company
			System.out.println("create new company :");
			adminFacade.createCompany(company4);

			// create company with same name will fail
			System.out.println("create company with same name will fail");
			// adminFacade.createCompany(company4);

			// delete company - check all coupons was delete also customer coupon will
			// delete
			System.out.println("delete company");
			adminFacade.removeCompany(company1);

			// delete un-exist company
			System.out.println("delete un-exist company");
			adminFacade.removeCompany(company5);

			// company update --all except company name
			System.out.println("company update");
			company2.setPassword("new pass");
			adminFacade.updateCompany(company2);

			// update company with different name
			System.out.println("update company with different name");
			// company3.setPassword("new pass3");
			// company3.setName("newName");
			// adminFacade.updateCompany(company3);

			// update un-exist company
			// System.out.println("update un-exist company");
			// company5.setPassword("new pass3");
			// adminFacade.updateCompany(company5);

			// get All company
			System.out.println("get All company ");
			System.out.println(adminFacade.getAllCompanies());

			// get specific company
			System.out.println("get spesific company");
			System.out.println(adminFacade.getCompany(4));

			// get specific un-exist company
			System.out.println("get spesific  un-exist company");
			// System.out.println(adminFacade.getCompany(5));

			// Create new client
			System.out.println("Create new client");
			adminFacade.createCustomer(customer14);

			// craate client with existed name
			System.out.println("craate client with existed name");
			// Customer customer15 =new Customer();
			// customer15.setCustName(customer14.getCustName());
			// adminFacade.createCustomer(customer14);

			// delete client - check all coupons was deleted
			System.out.println("delete client - check all coupons was deleted ");
			customerFacade11.purshaseCoupon(coupon6);
			customerFacade11.purshaseCoupon(coupon7);
			customerFacade11.purshaseCoupon(coupon8);
			customerFacade13.purshaseCoupon(coupon6);
			customerFacade13.purshaseCoupon(coupon7);
			customerFacade13.purshaseCoupon(coupon8);

			adminFacade.removeCustomer(customer13);

			// delete un-exist client
			System.out.println("delete un-exist client");
			adminFacade.removeCustomer(customer13);

			// update client - all without name
			System.out.println("update client - all without name");
			customer12.setPassword("New pass");
			adminFacade.updateCustomer(customer12);

			// update client name will fail
			// System.out.println("update client name will fail");
			// customer12.setCustName("New Name");
			// adminFacade.updateCustomer(customer12);

			// update un-exist client
			System.out.println("update un-exist client");

			Customer customer111 = new Customer("Natly1111", "pass6");
			// adminFacade.updateCustomer(customer111);

			// get al clients
			System.out.println("get al clients");
			System.out.println(adminFacade.getAllCustomers());

			// get spesific client
			System.out.println("get spesific client");
			System.out.println(adminFacade.getCustomer(12));

			// get un-exist client
			System.out.println("get un-exist client");
			System.out.println(adminFacade.getCustomer(1111111));

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
