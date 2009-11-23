package dk.bot.betdaq;

/**General exception class
 * 
 * @author korzekwad
 *
 */
public class BetDaqException extends RuntimeException{

	public BetDaqException(String message) {
		super(message);
	}
	
	public BetDaqException(String message, Throwable cause) {
		super(message,cause);
	}
	
	public BetDaqException(Throwable cause) {
		super(cause);
	}
}
