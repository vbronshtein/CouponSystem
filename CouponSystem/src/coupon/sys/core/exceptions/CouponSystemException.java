package coupon.sys.core.exceptions;

/**
 * Use CouponSystemException for convert system exceptions to coupon system
 * exception format
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
	 * Basic Exception Constructor params
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
	 * Basic Exception Constructor params
	 * 
	 * @param message
	 * @param cause
	 */
	public CouponSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Basic Exception Constructor params
	 * 
	 * @param message
	 */
	public CouponSystemException(String message) {
		super(message);
	}

	/**
	 * Basic Exception Constructor params
	 * 
	 * @param cause
	 */
	public CouponSystemException(Throwable cause) {
		super(cause);
	}

}
