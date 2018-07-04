package coupon.sys.core.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.exceptions.CouponSystemException;

public class TruncateAllTables {
	
	private ConnectionPool pool;

	
	
	public TruncateAllTables() {
		super();
		this.pool = ConnectionPool.getInstance();
	}


	
	public void trancateAll() throws CouponSystemException {
		
		
		Connection connection = pool.getConnection();

		try {
			String sql_table1 = "TRUNCATE TABLE COMPANY";          
			String sql_table2 ="TRUNCATE TABLE COMPANY_COUPON"  ;      
			String sql_table3 ="TRUNCATE TABLE COUPON" ;                    
			String sql_table4 ="TRUNCATE TABLE CUSTOMER";                  
			String sql_table5 ="TRUNCATE TABLE CUSTOMER_COUPON" ;  
			
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql_table1);
			stmt.executeUpdate(sql_table2);
			stmt.executeUpdate(sql_table3);
			stmt.executeUpdate(sql_table4);
			stmt.executeUpdate(sql_table5);

		} catch (SQLException e) {
			throw new CouponSystemException("Truncate fail", e);
		} finally {
			pool.returnConnection(connection);
		}

	}

}
