package coupon.sys.core.exceptions;

/**
 * Use CouponSystemException for convert system exceptoions to coupon system exception format
 * 
 * @author vbronshtein
 *
 */
public class CouponSystemException extends Exception {

	private static final long serialVersionUID = 1L;

	public CouponSystemException() {
		super();
	}
	/**
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CouponSystemException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public CouponSystemException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 
	 * @param message
	 */
	public CouponSystemException(String message) {
		super(message);
	}
	
	/**
	 * 
	 * @param cause
	 */
	public CouponSystemException(Throwable cause) {
		super(cause);
	}

}
