package coupon.sys.core.test;

import coupon.sys.core.connectionPool.ConnectionPool;

public class ConPoolTestWithRunnableDbDao {

	public static void main(String[] args) {
		ConnectionPool pool = ConnectionPool.getInstance();
		
		CompanyDbDaoRunnable c1 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c2 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c3 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c4 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c5 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c6 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c7 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c8 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c9 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c10 = new CompanyDbDaoRunnable(pool);
		CompanyDbDaoRunnable c11 = new CompanyDbDaoRunnable(pool);

		Thread t1 = new Thread(c1, "c1");
		Thread t2 = new Thread(c2, "c2");
		Thread t3 = new Thread(c3, "c3");
		Thread t4 = new Thread(c4, "c4");
		Thread t5 = new Thread(c5, "c5");
		Thread t6 = new Thread(c6, "c6");
		Thread t7 = new Thread(c7, "c6");
		Thread t8 = new Thread(c8, "c6");
		Thread t9 = new Thread(c9, "c6");
		Thread t10 = new Thread(c10, "c6");
		Thread t11 = new Thread(c11, "c6");

		try {
			t1.start();
			Thread.sleep(100);
		t2.start();
		Thread.sleep(100);
		t3.start();
		Thread.sleep(100);
		t4.start();
		Thread.sleep(100);
		t5.start();
		Thread.sleep(100);
		t6.start();
		Thread.sleep(100);
		t7.start();
		Thread.sleep(100);
		t8.start();
		Thread.sleep(100);
		t9.start();
		Thread.sleep(100);
		t10.start();
		Thread.sleep(100);
		t11.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
