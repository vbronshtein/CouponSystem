package coupon.sys.core.dao.db;

public class SQL_QUERY {

	//CompanyDbDao
	public static String SQL_Create_Company = "INSERT INTO company VALUES(%s , '%s', '%s', '%s')";
	public static String SQL_Read_Company = "SELECT * FROM company WHERE ID= %s";
	public static String SQL_Update_Company = "UPDATE company SET COMP_NAME='%s',PASSWORD='%s',EMAIL='%s' WHERE COMP_NAME='%s'";
	public static String SQL_Delete_Company = "DELETE FROM company WHERE COMP_NAME='%s'";
	public static String SQL_Get_All_Companies = "SELECT * FROM company ";
	public static String SQL_Get_Company_By_Name = "SELECT * FROM company WHERE COMP_NAME= '%s'";
	public static String SQL_Login_As_Company = "SELECT PASSWORD FROM company WHERE COMP_NAME='%s'";
	
	//CustomerDbDao
	public static String SQL_Create_Customer = "INSERT INTO CUSTOMER VALUES(%s,'%s','%s')";
	public static String SQL_Read_Customer =  "SELECT * FROM customer WHERE ID= %s";
	public static String SQL_Update_Customer = "UPDATE customer SET CUST_NAME='%s',PASSWORD='%s' WHERE id= %s";
	public static String SQL_Delete_Customer = "DELETE FROM customer WHERE CUST_NAME='%s'";
	public static String SQL_Get_All_Customer = "SELECT * FROM customer";
	public static String SQL_Get_Customer_By_Name = "SELECT * FROM customer WHERE CUST_NAME= '%s'";
	public static String SQL_Login_As_Customer = "SELECT PASSWORD FROM customer WHERE CUST_NAME='%s'";

	
	//CompanyCouponDbDao
	public static String SQL_Create_CompCoupon_Coupon = "INSERT INTO coupon VALUES(%s,'%s','%s','%s',%s,'%s','%s',%s,'%s')";
	public static String SQL_Create_CompCoupon_CompCoupon = "INSERT INTO company_coupon VALUES(%s, %s)";
	public static String SQL_Update_CompCoupon = "UPDATE coupon SET TITLE ='%s',START_DATE='%s',END_DATE='%s',AMOUNT= %s,TYPE='%s',MESSAGE='%s',PRICE=%s,IMAGE='%s' WHERE id=%s";
	public static String SQL_Delete_CompCoupon_Coupon =  "DELETE FROM Coupon WHERE ID IN (SELECT COUPON_ID FROM Coupon INNER JOIN Company_Coupon ON Coupon.Id =Company_Coupon.COUPON_Id WHERE COMP_Id = %s and COUPON_Id= %s )";
	public static String SQL_Delete_CompCoupon_CustCoupon = "DELETE FROM Customer_coupon WHERE coupon_id IN (SELECT Customer_coupon.COUPON_ID FROM Customer_coupon INNER JOIN Company_Coupon ON Customer_Coupon.COUPON_Id =Company_Coupon.COUPON_Id WHERE COMP_Id = %s and Company_Coupon.COUPON_Id= %s )";
	public static String SQL_Delete_CompCoupon_CompCoupon = "DELETE FROM company_coupon WHERE COMP_ID= %s and COUPON_Id= %s ";
	public static String SQL_Read_CompCoupon = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id= %s and c.id = %s";
	public static String SQL_Get_All_CompCoupon = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id= %s";
	public static String SQL_Delete_All_CompCoupons_Coupon = "DELETE FROM Coupon WHERE ID IN (SELECT COUPON_ID FROM Coupon INNER JOIN Company_Coupon ON Coupon.Id =Company_Coupon.COUPON_Id WHERE COMP_Id = %s)";
	public static String SQL_Delete_All_CompCoupons_CustCoupon = "DELETE FROM Customer_coupon WHERE coupon_id IN (SELECT Customer_coupon.COUPON_ID FROM Customer_coupon INNER JOIN Company_Coupon ON Customer_Coupon.COUPON_Id =Company_Coupon.COUPON_Id WHERE COMP_Id = %s)";
	public static String SQL_Delete_All_CompCoupons_CompCoupon = "DELETE FROM company_coupon WHERE COMP_ID= %s ";
	public static String SQL_Get_CompCoupon_By_Type = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id=%s and c.type='%s'";
	public static String SQL_Get_CompCoupon_By_Price = "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id= %s and c.price< %s";
	public static String SQL_Get_CompanyCoupon_Upto_Date =  "SELECT c.* FROM coupon c INNER JOIN company_coupon cc ON c.id=cc.coupon_id WHERE cc.comp_id= %s and c.end_date<'%s'";
	public static String SQL_Is_Coupon_Title_Exist = "SELECT * FROM coupon WHERE TITLE = '%s'";


	//CustomerCouponDbDao
	public static String SQL_Purchase_Customer_Coupon_Create = "INSERT INTO CUSTOMER_COUPON VALUES(%s,%s)";
	public static String SQL_Purchase_Customer_Coupon_GetAmount = "SELECT AMOUNT FROM coupon WHERE TITLE='%s'";
	public static String SQL_Purchase_Customer_Coupon_UpdateAmount = "UPDATE coupon SET AMOUNT=(%s - 1)  WHERE TITLE='%s'";
	public static String SQL_Delete_Customer_Coupon = "DELETE FROM CUSTOMER_COUPON WHERE COMP_ID=%s and COUPON_ID=%s";
	public static String SQL_Read_Customer_Coupon = "SELECT c.* FROM coupon c INNER JOIN customer_coupon cc ON c.id=cc.coupon_id WHERE cc.cust_id= %s and cc.coupon_id=%s";
	public static String SQL_Delete_All_Customer_Coupons = "DELETE FROM Coupon WHERE ID IN (SELECT COUPON_ID FROM Coupon INNER JOIN Customer_Coupon ON Coupon.Id =Customer_Coupon.COUPON_Id WHERE Cust_Id = %s)";
	public static String SQL_Delete_All_Customer_Coupons_CompCoupons = "DELETE FROM Company_coupon WHERE coupon_id IN (SELECT Company_coupon.COUPON_ID FROM Company_coupon INNER JOIN Customer_Coupon ON Company_Coupon.COUPON_Id =Customer_Coupon.COUPON_Id WHERE cust_Id = %s)";
	public static String SQL_Delete_All_Customer_Coupons_CustCoupons = "DELETE FROM customer_coupon WHERE CUST_ID= %s";
	public static String SQL_Delete_Customer_from_CustCoupon = "DELETE FROM CUSTOMER_COUPON WHERE CUST_ID=%s";
	public static String SQL_Get_All_Cust_Coupons = "SELECT c.* FROM coupon c INNER JOIN customer_coupon cc ON c.id=cc.coupon_id WHERE cc.cust_id=%s";
	public static String SQL_Get_Cust_Coupons_By_Type = "SELECT c.* FROM coupon c INNER JOIN customer_coupon cc ON c.id=cc.coupon_id WHERE cc.cust_id=%s and c.type='%s'";
	public static String SQL_Get_Cust_Coupons_Upto_price =  "SELECT c.* FROM coupon c INNER JOIN customer_coupon cc ON c.id=cc.coupon_id WHERE cc.cust_id=%s and c.price< %s";
	public static String SQL_Get_Coupon_Id = "SELECT ID FROM coupon WHERE TITLE='%s'"; 


	//GlobalCouponDbDao
	public static String SQL_Get_Coupon_Amount = "SELECT AMOUNT FROM coupon WHERE TITLE='%s'";
	public static String SQL_is_Coupon_Expired = "SELECT END_DATE FROM coupon WHERE TITLE='%s'";
	public static String SQL_Del_All_Expiried_Coupons_Comp = "DELETE FROM company_coupon WHERE COUPON_ID IN (select ID from coupon where end_date <='%s')";
	public static String SQL_Del_All_Expiried_Coupons_Cust = "DELETE FROM customer_coupon WHERE COUPON_ID IN (select ID from coupon where end_date <='%s')";
	public static String SQL_Del_All_Expiried_Coupons = "DELETE FROM coupon WHERE END_DATE<='%s'";
	public static String SQL_Get_Coupon_By_Title = "SELECT * FROM coupon WHERE TITLE='%s'";

	
	//Database last ID Helper 
	public static String SQL_Get_Last_Available_id = "SELECT id FROM last_id WHERE type='%s'";
	public static String SQL_Get_Last_Available_id_Update = "UPDATE last_id SET id=%s WHERE Type='%s'";
	public static String SQL_Get_Last_Available_id_Init = "INSERT INTO last_id VALUES('%s',1)";
}
