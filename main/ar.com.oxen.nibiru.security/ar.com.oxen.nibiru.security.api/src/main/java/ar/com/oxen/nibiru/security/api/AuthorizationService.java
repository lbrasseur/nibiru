package ar.com.oxen.nibiru.security.api;

/**
 * Service for authorizing actions and users.
 * 
 */
public interface AuthorizationService {
	/**
	 * Checks if the logged user has a given role.
	 * 
	 * @param role
	 *            The role name.
	 * @return True if the user has the role
	 */
	boolean isCallerInRole(String role);
}