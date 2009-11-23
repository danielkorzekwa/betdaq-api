package dk.bot.betdaq;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Creates BetDaq object.
 * 
 * @author daniel
 * 
 */
public class BetDaqFactoryBean implements FactoryBean {

	private BetDaq betDaq;
	private String userName;
	private String password;
	private String version;
	private String language;

	public BetDaqFactoryBean() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-betdaq.xml" });
		betDaq = (BetDaq) context.getBean("betDaq");
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setLanguage(String language) {
		this.language = language;	
	}
	
	public void init() {
		BetDaqConfig config = new BetDaqConfig(userName,password,version,language);
		betDaq.init(config);
	}
	
	public Object getObject() throws Exception {
		return betDaq;
	}

	public Class getObjectType() {
		return BetDaq.class;
	}

	public boolean isSingleton() {
		return true;
	}
}