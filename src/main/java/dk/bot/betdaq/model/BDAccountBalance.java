package dk.bot.betdaq.model;

import java.io.Serializable;
import java.math.BigDecimal;

/** Data model for betdaq account balance.
 * 
 * @author daniel
 *
 */
public class BDAccountBalance implements Serializable{

	    private String currency;
	    private BigDecimal balance;
	    private BigDecimal exposure;
	    private BigDecimal availableFunds;
	    private BigDecimal credit;
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public BigDecimal getBalance() {
			return balance;
		}
		public void setBalance(BigDecimal balance) {
			this.balance = balance;
		}
		public BigDecimal getExposure() {
			return exposure;
		}
		public void setExposure(BigDecimal exposure) {
			this.exposure = exposure;
		}
		public BigDecimal getAvailableFunds() {
			return availableFunds;
		}
		public void setAvailableFunds(BigDecimal availableFunds) {
			this.availableFunds = availableFunds;
		}
		public BigDecimal getCredit() {
			return credit;
		}
		public void setCredit(BigDecimal credit) {
			this.credit = credit;
		}
}
