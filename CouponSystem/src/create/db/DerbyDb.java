package create.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.derby.impl.tools.sysinfo.Main;

import coupon.sys.core.exceptions.CouponSystemException;

public class DerbyDb {
	public static void main(String[] args) {
		try {
			createDb();
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createDb() throws CouponSystemException {
		String url = getDerbyDbURL();
		try (Connection con = DriverManager.getConnection(url);) {
			System.out.println("connected"); // Remove latter ;

//			String companyTable = "CREATE TABLE Company(ID BIGINT PRIMARY KEY,COMP_NAME VARCHAR(30),PASSWORD VARCHAR(30),EMAIL VARCHAR(30))";
//			String customerTable = "CREATE TABLE Customer(ID BIGINT PRIMARY KEY,CUST_NAME VARCHAR(30),PASSWORD VARCHAR(30))";
//			String couponTable = "CREATE TABLE Coupon(ID BIGINT PRIMARY KEY,TITLE VARCHAR(254),START_DATE DATE,END_DATE DATE,AMOUNT INT,TYPE VARCHAR(254),MESSAGE VARCHAR(254),PRICE DOUBLE,IMAGE VARCHAR(254))";
//			String customerCoupone = "CREATE TABLE Customer_Coupon( CUST_ID BIGINT, COUPON_ID BIGINT , PRIMARY KEY(CUST_ID,COUPON_ID))";
//			String companyCoupone = "CREATE TABLE Company_Coupon(COMP_ID BIGINT ,COUPON_ID BIGINT , PRIMARY KEY( COMP_ID,COUPON_ID))";
			String lastId = "CREATE TABLE Last_Id(TYPE VARCHAR(30) PRIMARY KEY, ID BIGINT)";

			Statement stmt = con.createStatement();

//			stmt.executeUpdate(companyTable);
//			stmt.executeUpdate(customerTable);
//			stmt.executeUpdate(couponTable);
//			stmt.executeUpdate(customerCoupone);
//			stmt.executeUpdate(companyCoupone);
//			stmt.executeUpdate(lastId);
			
			initLastIdTable();

		} catch (SQLException e) {
			throw new CouponSystemException("Database creations fail", e);
			// e.printStackTrace();
		}

	}
	
	public static void initLastIdTable() throws CouponSystemException {
		try (Connection con = DriverManager.getConnection(getDerbyDbURL());) {
		String initCompany = "INSERT INTO Last_Id VALUES('Company',0)";
		String initCustomer = "INSERT INTO Last_Id VALUES('Customer',0)";

		Statement stmt = con.createStatement();
		stmt.executeUpdate(initCompany);
		stmt.executeUpdate(initCustomer);

		} catch (SQLException e) {
			throw new CouponSystemException("Database creations fail", e);
			// e.printStackTrace();
		}
	}

	public static String getDerbyDbURL() throws CouponSystemException {
		File file = new File("files/db.txt");

		try (BufferedReader in = new BufferedReader(new FileReader(file));) {
			return in.readLine() + ";create=true";
		} catch (IOException e) {
			throw new CouponSystemException("Cant read Database URL", e);
		}

	}

}