package test;

import java.sql.Date;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.beans.Customer;
import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.helper.TruncateAllTables;
import coupon.sys.facade.CompanyFacade;

public class CompanyFacadeTest {
	public static void main(String[] args) {
		Company company1 = new Company(1, "company1", "1111", "111@company.com");
		Company company2 = new Company(2, "company2", "1112", "112@company.com");
		Company company3 = new Company(3, "company3", "1113", "113@company.com");
		Company company4 = new Company(4, "company4", "1114", "114@company.com");

		Customer customer11 = new Customer(11, "David", "pass4");
		Customer customer12 = new Customer(12, "Yossi", "pass5");
		Customer customer13 = new Customer(13, "Neta", "pass6");
		Customer customer14 = new Customer(14, "Nataly", "pass6");

		Coupon coupon1 = new Coupon(1, "coupon1", new Date(2018, 07, 22), new Date(2018, 07, 22), 400,
				CouponType.HEALTH, "Stam coupon14", 4000, "http://google4.com");
		Coupon coupon2 = new Coupon(2, "coupon2", new Date(2018, 07, 22), new Date(2018, 07, 22), 500,
				CouponType.CAMPING, "Stam coupon3", 5000, "http://google5.com");
		Coupon coupon3 = new Coupon(3, "coupon3", new Date(2018, 07, 22), new Date(2018, 07, 22), 600,
				CouponType.HEALTH, "Stam coupon5", 5000, "http://google6.com");
		Coupon coupon4 = new Coupon(4, "coupon4", new Date(2018, 07, 22), new Date(2018, 07, 22), 400,
				CouponType.CAMPING, "Stam coupon7", 4000, "http://google4.com");
		Coupon coupon5 = new Coupon(5, "coupon5", new Date(2018, 07, 22), new Date(2018, 07, 22), 500,
				CouponType.ELECTRICITY, "Stam coupon8", 5000, "http://google5.com");
		Coupon coupon6 = new Coupon(6, "coupon6", new Date(2018, 07, 22), new Date(2018, 07, 22), 600,
				CouponType.ELECTRICITY, "Stam coupon26", 5000, "http://google6.com");
		Coupon coupon7 = new Coupon(7, "coupon7", new Date(2018, 07, 22), new Date(2018, 07, 22), 400,
				CouponType.HEALTH, "Stam coupon34", 4000, "http://google4.com");
		Coupon coupon8 = new Coupon(8, "coupon8", new Date(2018, 07, 22), new Date(2018, 07, 22), 500,
				CouponType.HEALTH, "Stam coupon35", 5000, "http://google5.com");
		Coupon coupon9 = new Coupon(9, "coupon9", new Date(2018, 07, 22), new Date(2018, 07, 22), 600,
				CouponType.HEALTH, "Stam coupon36", 5000, "http://google6.com");
		Coupon coupon10 = new Coupon(10, "coupon10", new Date(2018, 07, 22), new Date(2018, 07, 22), 600,
				CouponType.HEALTH, "Stam coupon36", 5000, "http://google6.com");

		CompanyFacade compF1 = new CompanyFacade(company1);
		CompanyFacade compF2 = new CompanyFacade(company2);
		CompanyFacade compF3 = new CompanyFacade(company3);

		try {

			TruncateAllTables trAllTables = new TruncateAllTables();
			trAllTables.trancateAll();

			// createCoupon(Coupon coupon) - pass

			compF1.createCoupon(coupon1);
			compF1.createCoupon(coupon2);
			compF1.createCoupon(coupon3);
			compF1.createCoupon(coupon4);
			compF1.createCoupon(coupon5);

			compF2.createCoupon(coupon6);
			compF2.createCoupon(coupon7);
			compF2.createCoupon(coupon8);
			// //bl
			// compF2.createCoupon(coupon7);
			compF2.createCoupon(coupon9);
			compF2.createCoupon(coupon10);

			
			
			// removeCoupon(Coupon coupon) - fail
			// *no check if coupon is company coupon

			compF1.removeCoupon(coupon4);
			compF1.removeCoupon(coupon9); //bug here coupon not this.company_coupon
			compF3.removeCoupon(coupon7); //bug here coupon not this.company_coupon
			

			// updateCoupon(Coupon coupon)
			compF2.updateCoupon(coupon10);
			
			coupon10.setAmount(10);
			compF2.updateCoupon(coupon10); //Check if required exception on this case 
			
			coupon10.setPrice(10);
			compF2.updateCoupon(coupon10);
			
			coupon10.setEndDate(new Date(2022, 1, 1));
			compF2.updateCoupon(coupon10);  
			
			
			coupon1.setPrice(1);
			compF2.updateCoupon(coupon1); // bug update not this.company coupon

			coupon1.setPrice(2);
			compF3.updateCoupon(coupon1); // bug update not this.company coupon
			//Spesific coupon
			System.out.println(compF2.getCoupon(coupon10.getId()));
			System.out.println(compF2.getCoupon(coupon1.getId()));
			System.out.println(compF2.getCoupon(100));
			
			//get All company coupons
			System.out.println("Comp1");
			Collection<Coupon> coupons = compF1.getAllCoupons();
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
			
			System.out.println();
			System.out.println("Comp3");
			
			Collection<Coupon> coupons3 = compF3.getAllCoupons();
			for (Coupon coupon : coupons3) {
				System.out.println(coupon);
			}
			
			//get company coupons by Coupon type
			compF1.getCouponByType(CouponType.ELECTRICITY);
			
			//get company coupons up to price
			//get company coupons up to end date
			
			
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
