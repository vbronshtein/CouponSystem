package coupon.sys.core.beans;

import java.util.Collection;

/**
 * Company bean
 * 
 * @author vbronshtein
 *
 */
public class Company {

	private long id;
	private String name;
	private String password;
	private String email;
	private Collection<Coupon> coupons; // its look like need something else here

	public Company() {
		super();
	}
	

	/**
	 * 
	 * @param id
	 * @param name
	 * @param password
	 * @param email
	 */
	public Company( String name, String password, String email) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
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

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
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

	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
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
		return "Company [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", coupons="
				+ coupons + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	
	
	
	public boolean isNameEqualTo(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
