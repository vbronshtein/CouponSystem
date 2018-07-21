package coupon.sys.core.beans;

import java.util.Collection;

/**
 * Customer bean
 * 
 * @author vbronshtein
 *
 */
public class Customer {
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	public Customer() {
		super();
	}

	/**
	 * 
	 * @param id
	 * @param custName
	 * @param password
	 */
	public Customer(String custName, String password) {
		super();
		this.custName = custName;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	/**
	 * 
	 * @param custName
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * 
	 * @param coupons
	 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
		return result;
	}

	public boolean isNameEqualTo(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (custName == null) {
			if (other.custName != null)
				return false;
		} else if (!custName.equals(other.custName))
			return false;
		return true;
	}

}
