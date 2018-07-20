package coupon.sys.core.test;

import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.facade.AdminFacade;
import coupon.sys.core.facade.CompanyFacade;
import coupon.sys.core.facade.CustomerFacade;
import coupon.sys.core.helper.GeneralInitialDatabese;
import coupon.sys.core.main.ClientType;
import coupon.sys.core.main.CouponSystem;

public class CouponSystemTest {
	public static void main(String[] args) {
		try {
			GeneralInitialDatabese generateDb = new GeneralInitialDatabese();
			generateDb.generateBasicRecords();

			CouponSystem couponSystem = CouponSystem.getInstance();

			CompanyFacade companyFacade = (CompanyFacade) couponSystem.login("company2", "1112", ClientType.COMPANY);
			System.out.println("Get all  company2 coupons:");
			System.out.println(companyFacade.getAllCoupons());

			 CustomerFacade customerFacade = (CustomerFacade) couponSystem.login("Yossi",
			 "pass12", ClientType.CUSTOMER);
			 System.out.println("Get all  customer coupons:");
			 System.out.println(customerFacade.getAllPurchesedCoupons());

			 AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234",
			 ClientType.ADMIN);
			 System.out.println("Get all companies:");
			 System.out.println(adminFacade.getAllCompanies());

			 Thread.sleep(90 * 1000);
			 couponSystem.shutdown();
			 System.out.println("exit from program");

		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
	}
}
