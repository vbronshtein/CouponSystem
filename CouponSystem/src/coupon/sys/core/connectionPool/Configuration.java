package coupon.sys.core.connectionPool;

import java.io.BufferedReader;
import java.io.File;
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
public class Configuration {

	// init method declaration
	public String DB_URL;
	public Integer DB_MAX_CONNECTIONS;

	// for work with MySQL instead of derby will need follow
	// public static String DB_USER_NAME;
	// public static String DB_PASSWORD;
	// public static String DB_DRIVER;

	
	// Singleton Declaration
	private static Configuration istance = new Configuration();

	private Configuration() {
		init();
	}

	public static Configuration getInstance() {
		return istance;
	}

	/**
	 * Read Database URL from External file : "files/db.txt"
	 * 
	 * @return
	 * @throws CouponSystemException
	 */
	private String readDbUrlFromFile() throws CouponSystemException {
		// source file
		File file = new File("files/db.txt");

		String dbUrl = null;
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			// reading from file
			dbUrl = in.readLine();
		} catch (IOException e) {
			throw new CouponSystemException("Cant find DB_URL", e);
		}

		return dbUrl;

	}

	/**
	 * Init function , perform all Database Configurations ( Create URL , Config nam
	 * of Connections to DB )
	 */
	private void init() {

		try {
			DB_URL = readDbUrlFromFile();
			DB_MAX_CONNECTIONS = 10;

			// For MYSQL
			// DB_USER_NAME = "root";
			// DB_PASSWORD = "root";
			// DB_DRIVER = "com.mysql.jdbc.Driver";
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}

	}

}
