package test;

import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.helper.GeneralInitialDatabese;
import coupon.sys.facade.AdminFacade;
import coupon.sys.facade.CompanyFacade;
import coupon.sys.facade.CustomerFacade;
import coupon.sys.main.ClientType;
import coupon.sys.main.CouponSystem;

public class CouponSystemTest {
	public static void main(String[] args) {
		try {
			GeneralInitialDatabese generateDb = new GeneralInitialDatabese();
			generateDb.generateBasicRecords();
			
			
			CouponSystem couponSystem = CouponSystem.getInstance();
			CompanyFacade companyFacade = (CompanyFacade) couponSystem.login("company2", "1112", ClientType.COMPANY);
			System.out.println(companyFacade.getAllCoupons());
//			CompanyFacade companyFacade2 = (CompanyFacade) couponSystem.login("company3", "new pass", ClientType.COMPANY);
//			System.out.println(companyFacade2.getAllCoupons());
			CustomerFacade customerFacade = (CustomerFacade) couponSystem.login("Yossi","pass5", ClientType.CUSTOMER);
			System.out.println(customerFacade.getAllPurchesedCoupons());
			
//			CustomerFacade customerFacade1 = (CustomerFacade) couponSystem.login("Yossi1","New pass", ClientType.CUSTOMER);
			
			AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", ClientType.ADMIN);
			System.out.println(adminFacade.getAllCompanies());
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
