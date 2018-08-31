package coupon.sys.core.facade;

/**
 * Login interface for Facades
 * 
 * @author vbronshtein
 *
 */
public interface CouponClientFacade {
	CouponClientFacade login(String name, String password, String clientType);
}
