package dk.bot.betdaq;

/**Authorisation configuration for BetDaq.
 * 
 * @author korzekwad
 *
 */
public class BetDaqConfig {

	private final String userName;
	private final String password;
	private final String version;
	private final String language;

	public BetDaqConfig(String userName, String password, String version, String language) {
		this.userName = userName;
		this.password = password;
		this.version = version;
		this.language = language;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getVersion() {
		return version;
	}

	public String getLanguage() {
		return language;
	}
	
}
