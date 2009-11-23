package dk.bot.betdaq.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**Represents betdaq transaction item for settled bets.
 * 
 * @author daniel
 *
 */
public class BDAccountPostingItem implements Serializable{

	    private Date postedAt;
	    private String description;
	    private BigDecimal amount;
	    private BigDecimal resultingBalance;
	    private Short postingCategory;
	    private Long orderId;
	    private Long marketId;
		public Date getPostedAt() {
			return postedAt;
		}
		public void setPostedAt(Date postedAt) {
			this.postedAt = postedAt;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public BigDecimal getResultingBalance() {
			return resultingBalance;
		}
		public void setResultingBalance(BigDecimal resultingBalance) {
			this.resultingBalance = resultingBalance;
		}
		public Short getPostingCategory() {
			return postingCategory;
		}
		public void setPostingCategory(Short postingCategory) {
			this.postingCategory = postingCategory;
		}
		public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		public Long getMarketId() {
			return marketId;
		}
		public void setMarketId(Long marketId) {
			this.marketId = marketId;
		}
}
