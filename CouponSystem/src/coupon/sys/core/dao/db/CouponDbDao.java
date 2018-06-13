package coupon.sys.core.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import coupon.sys.core.beans.Company;
import coupon.sys.core.beans.Coupon;
import coupon.sys.core.beans.CouponType;
import coupon.sys.core.connectionPool.ConnectionPool;
import coupon.sys.core.dao.CouponDao;
import coupon.sys.core.exceptions.CouponSystemException;

public class CouponDbDao implements CouponDao {
	private ConnectionPool pool;

	public CouponDbDao() {
		super();
		this.pool = ConnectionPool.getInstance();
	}

	@Override
	public void create(Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();
		String sql = "INSERT INTO coupon VALUES(" + coupon.getId() + ",'" + coupon.getTitle() + "','"
				+ coupon.getStartDate() + "','" + coupon.getEndDate() + "'," + coupon.getAmount() + ",'"
				+ coupon.getType() + "','" + coupon.getMessage() + "'," + coupon.getPrice() + ",'" + coupon.getImage()
				+ "')";

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public Coupon read(long id) throws CouponSystemException {
		Connection connection = pool.getConnection();
		Coupon coupon = new Coupon();

		String sql = "SELECT * FROM coupon WHERE ID=" + id;

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}
		return coupon;
	}

	@Override
	public void update(Coupon coupon) throws CouponSystemException {
		Connection connection = pool.getConnection();

		String sql = "UPDATE coupon SET TITLE ='" + coupon.getTitle() + "',START_DATE='" + coupon.getStartDate()
				+ "',END_DATE='" + coupon.getEndDate() + "',AMOUNT=" + coupon.getAmount() + ",TYPE='" + coupon.getType()
				+ "',MESSAGE='" + coupon.getMessage() + "',PRICE=" + coupon.getPrice() + ",IMAGE='" + coupon.getImage()
				+ "' WHERE id=" + coupon.getId();

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public void delete(Coupon coupon) throws CouponSystemException {

		Connection connection = pool.getConnection();
		String sql = "DELETE FROM coupon WHERE ID=" + coupon.getId();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

	}

	@Override
	public Collection<Coupon> getAllCoupon() throws CouponSystemException {
		Connection connection = pool.getConnection();
		Collection<Coupon> coupons = new ArrayList<>();

		String sql = "SELECT * FROM coupon";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong("ID"));
				coupon.setTitle(rs.getString("TITLE"));
				coupon.setStartDate(rs.getDate("START_DATE"));
				coupon.setEndDate(rs.getDate("END_DATE"));
				coupon.setAmount(rs.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rs.getString("TYPE")));
				coupon.setMessage(rs.getString("MESSAGE"));
				coupon.setPrice(rs.getDouble("PRICE"));
				coupon.setImage(rs.getString("IMAGE"));

				coupons.add(coupon);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pool.returnConnection(connection);
		}

		return coupons;

	}

	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) {
		// TODO Auto-generated method stub
		return null;
	}

}
