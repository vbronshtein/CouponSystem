package coupon.sys.core.test;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import coupon.sys.core.exceptions.CouponSystemException;
import coupon.sys.core.helper.GeneralInitialDatabese;
import coupon.sys.core.helper.TruncateAllTables;

public class TempTest {

	public static void main(String[] args) {
		TruncateAllTables tr = new TruncateAllTables();
		try {
			tr.trancateAll();
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GeneralInitialDatabese gd = new GeneralInitialDatabese();
	}
}