package coupon.sys.core.connectionPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Class Configure connection to Database ( current configure to work with Derby
 * DB , without password)
 * 
 * @author vbronshtein
 *
 */
public final class DataBaseConfig {

	public final static String DB_URL;
	public final static int DB_MAX_CONNECTIONS;

	static {
		DB_MAX_CONNECTIONS = 10;
		DB_URL = "jdbc:derby://localhost:1527/coupon_sys_db";
	}
		
}

