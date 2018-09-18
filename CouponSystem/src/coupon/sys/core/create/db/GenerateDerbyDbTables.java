package coupon.sys.core.create.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import coupon.sys.core.exceptions.CouponSystemException;

/**
 * Class Generate Data Base Tables for Coupon system program. Helper method only
 * for first data base creation
 * 
 * @author vbronshtein
 *
 */
public class GenerateDerbyDbTables {

	// Main method init table generation
	public static void main(String[] args) {
		try {
			createDb();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		}
	}

	// create DB
	public static void createDb() throws CouponSystemException {
		String url = getDerbyDbURL();
		try (Connection con = DriverManager.getConnection(url);) {

			String companyTable = "CREATE TABLE Company(ID BIGINT PRIMARY KEY,COMP_NAME VARCHAR(30),PASSWORD VARCHAR(30),EMAIL VARCHAR(30))";
			String customerTable = "CREATE TABLE Customer(ID BIGINT PRIMARY KEY,CUST_NAME VARCHAR(30),PASSWORD VARCHAR(30))";
			String couponTable = "CREATE TABLE Coupon(ID BIGINT PRIMARY KEY,TITLE VARCHAR(254),START_DATE DATE,END_DATE DATE,AMOUNT INT,TYPE VARCHAR(254),MESSAGE VARCHAR(254),PRICE DOUBLE,IMAGE VARCHAR(254))";
			String customerCoupone = "CREATE TABLE Customer_Coupon( CUST_ID BIGINT, COUPON_ID BIGINT , PRIMARY KEY(CUST_ID,COUPON_ID))";
			String companyCoupone = "CREATE TABLE Company_Coupon(COMP_ID BIGINT ,COUPON_ID BIGINT , PRIMARY KEY( COMP_ID,COUPON_ID))";
			String lastId = "CREATE TABLE Last_Id(TYPE VARCHAR(30) PRIMARY KEY, ID BIGINT)";

			Statement stmt = con.createStatement();

			stmt.executeUpdate(companyTable);
			stmt.executeUpdate(customerTable);
			stmt.executeUpdate(couponTable);
			stmt.executeUpdate(customerCoupone);
			stmt.executeUpdate(companyCoupone);
			stmt.executeUpdate(lastId);

		} catch (SQLException e) {
			throw new CouponSystemException("Database creations fail", e);
			// e.printStackTrace();
		}

	}

	// read DB URL
	public static String getDerbyDbURL() throws CouponSystemException {
		File file = new File("files/db.txt");

		try (BufferedReader in = new BufferedReader(new FileReader(file));) {
			return in.readLine() + ";create=true";
		} catch (IOException e) {
			throw new CouponSystemException("Fail to read Database URL", e);
		}

	}

}