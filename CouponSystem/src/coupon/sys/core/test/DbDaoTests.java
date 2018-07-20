package coupon.sys.core.test;

import java.sql.Date;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.db.CompanyDbDao;
import coupon.sys.core.dao.db.CouponDbDao;
import coupon.sys.core.dao.db.CustomerDbDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class DbDaoTests {
	public static void main(String[] args) {

		CompanyDbDao companyDbDao = new CompanyDbDao();
		CustomerDbDao customerDbDao = new CustomerDbDao();
		CouponDbDao couponDbDao = new CouponDbDao();

		Company company1 = new Company("company1", "1111", "111@company.com");
		Company company2 = new Company("company2", "1112", "112@company.com");
		Company company3 = new Company("company3", "1113", "113@company.com");

		Customer customer1 = new Customer("David", "pass1");
		Customer customer2 = new Customer("Yossi", "pass2");
		Customer customer3 = new Customer("Neta", "pass3");

		Coupon coupon1 = new Coupon("coupon1", new Date(2018, 07, 22), new Date(2018, 07, 22), 100, CouponType.FOOD,
				"Stam coupon1", 1000, "http://google.com");
		Coupon coupon2 = new Coupon("coupon2", new Date(2018, 07, 22), new Date(2018, 07, 22), 200,
				CouponType.CAMPING, "Stam coupon2", 2000, "http://google2.com");
		Coupon coupon3 = new Coupon("coupon3", new Date(2018, 07, 22), new Date(2018, 07, 22), 300,
				CouponType.HEALTH, "Stam coupon3", 3000, "http://google3.com");

		try {

			// create
			// companyDbDao.create(company1);
			// companyDbDao.create(company2);
			// companyDbDao.create(company3);

			// customerDbDao.create(customer1);
			// customerDbDao.create(customer2);
			// customerDbDao.create(customer3);
			//
			// couponDbDao.create(coupon1);
			// couponDbDao.create(coupon2);
			// couponDbDao.create(coupon3);

			// read
			System.out.println();
			System.out.println(companyDbDao.read(1));
			System.out.println(companyDbDao.read(2));
			System.out.println(companyDbDao.read(3));

			System.out.println();
			System.out.println(customerDbDao.read(1));
			System.out.println(customerDbDao.read(2));
			System.out.println(customerDbDao.read(3));

			System.out.println();
			System.out.println(couponDbDao.read(1L));
			System.out.println(couponDbDao.read(2));
			System.out.println(couponDbDao.read(3));

			// update

			company1.setPassword("new_pass");
			customer1.setPassword("new_pass");
			customer1.setCustName("New Name");
			coupon1.setAmount(222222);
			coupon1.setPrice(33333);

			companyDbDao.update(company1);
			customerDbDao.update(customer1);
			couponDbDao.update(coupon1);

			System.out.println();
			System.out.println(companyDbDao.read(1));
			System.out.println(customerDbDao.read(1));
			System.out.println(couponDbDao.read(1));

			// delete
			companyDbDao.delete(company2);
			customerDbDao.delete(customer2);
			couponDbDao.delete(coupon2);

			System.out.println();
			System.out.println(companyDbDao.read(2));
			System.out.println(customerDbDao.read(2));
			System.out.println(couponDbDao.read(2));

			// get all *
			System.out.println("Read all *");
			System.out.println(companyDbDao.getAllCompanies());
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}
}
