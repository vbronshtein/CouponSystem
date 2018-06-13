package test;

import coupon.sys.core.exceptions.CouponSystemException;
import create.db.DerbyDb;

public class DatabaceTest {

	public static void main(String[] args) {
		try {
			DerbyDb.createDb();
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
