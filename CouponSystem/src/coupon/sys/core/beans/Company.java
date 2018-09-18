package coupon.sys.core.beans;

import java.util.Collection;

/**
 * Company bean ( Template of Company Object ).
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

	/**
	 * Default Constructor
	 */
	public Company() {
		super();
	}

	/**
	 * Full Constructor , Contain all Company attributes
	 * 
	 * @param name
	 *            Company name.
	 * @param password
	 *            Password for login.
	 * @param email
	 *            Company Email.
	 */
	public Company(String name, String password, String email) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
	}

	/**
	 * getId - return company id
	 * 
	 * @return id - Company Id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set ID to Company Object
	 * 
	 * @param id
	 *            Set Company ID ( ID Auto generated on DataBase ).
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Return Company name
	 * 
	 * @return name - Company name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Name to Company Object
	 * 
	 * @param name
	 *            Company Name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return Company Password
	 * 
	 * @return password - Company Password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set Password to Company Object
	 * 
	 * @param password
	 *            Company password for connect to DataBase.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Return Company Email
	 * 
	 * @return email - Company Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set Email to Company Object
	 * 
	 * @param email
	 *            Company Email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Return List of All Company coupons
	 * 
	 * @return coupons - Collection of all company Coupons
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * Set Collection of Coupons to company Object
	 * 
	 * @param coupons
	 *            Company Coupon list.
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
