/**
 * Project: Gis
 * File: ApplicationException.java
 * Date: Oct 18, 2014
 * Time: 1:59:25 PM
 */

package a00698160.gis;

/**
 * @author Jason Chan, A00698160
 */
@SuppressWarnings("serial")
public class ApplicationException extends Exception {

	/**
	 * Construct an ApplicationException
	 * 
	 * @param message
	 *            the exception message.
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * Construct an ApplicationException
	 * 
	 * @param throwable
	 *            a Throwable.
	 */
	public ApplicationException(Throwable throwable) {
		super(throwable);
	}

}
