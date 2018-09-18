package coupon.sys.core.beans;

import java.util.Date;

/**
 * Coupon Bean ( Template of Coupon Object ).
 * 
 * @author vbronshtein
 *
 */
public class Coupon {
	private long id;
	private String title;
	private Date startDate; // check what date format needed util or sql
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;

	/**
	 * Default Constructor
	 */
	public Coupon() {
		super();
	}

	/**
	 * Full Constructor , contain all coupon parameters for fast creation new Coupon
	 * object
	 * 
	 * @param title
	 *            Coupon title.
	 * @param startDate
	 *            Coupon Start date.
	 * @param endDate
	 *            Coupon end date.
	 * @param amount
	 *            Total coupon amount ( can be updated ).
	 * @param type
	 *            Coupon type : Food , Health , Travel .....
	 * @param message
	 *            coupon info .
	 * @param price
	 *            Price of coupon.
	 * @param image
	 *            Link to coupon image.
	 */
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price,
			String image) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}

	/**
	 * Get Coupon ID
	 * 
	 * @return id - Coupon id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set Coupon ID
	 * 
	 * @param id
	 *            Set coupon id .
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get Coupon Title
	 * 
	 * @return title - Coupon title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set Coupon title
	 * 
	 * @param title
	 *            coupon title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get Coupon start date
	 * 
	 * @return startDate - Coupon start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Set Coupon start date
	 * 
	 * @param startDate
	 *            Coupon start date.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get Coupon end Date
	 * 
	 * @return endDate - Coupon end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Set Coupon end date
	 * 
	 * @param endDate
	 *            Coupon end date .
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get Coupon amount
	 * 
	 * @return amount - Get coupon amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Set Coupon amount
	 * 
	 * @param amount
	 *            Amount of coupon.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Get Coupon type
	 * 
	 * @return type - Get coupon type
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * Set Coupon type
	 * 
	 * @param type
	 *            Coupon type : Food , Health , Travel ...
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * Get Coupon message
	 * 
	 * @return message - Coupon message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set Coupon message
	 * 
	 * @param message
	 *            Coupon info .
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get Coupon price
	 * 
	 * @return price - Coupon price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Set Coupon price
	 * 
	 * @param price
	 *            Coupon price.
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Get Coupon Image URL
	 * 
	 * @return image - Image URL
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Set Coupon image URL
	 * 
	 * @param image
	 *            Link to Coupon image.
	 */
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}

}
