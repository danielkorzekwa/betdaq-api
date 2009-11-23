package dk.bot.betdaq.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Data model for BetDaq market with prices
 * 
 * @author korzekwad
 * 
 */
public class BDMarketWithPrices {

	private long id;
	private String name;
	private short type;
	private short status;
	private short numberOfWinningSelections;
	private Date startTime;
	private short withdrawalSequenceNumber;
	private short displayOrder;
	private boolean isEnabledForMultiples;
	private boolean isInRunningAllowed;
	private boolean isManagedWhenInRunning;
	private boolean isCurrentlyInRunning;
	private int inRunningDelaySeconds;
	private Integer returnCode;
	private BigDecimal placePayout;
	private BigDecimal totalMatchedAmount;

	private List<BDSelectionWithPrices> selections;

	public List<BDSelectionWithPrices> getSelections() {
		return selections;
	}

	public void setSelections(List<BDSelectionWithPrices> selections) {
		this.selections = selections;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTotalMatchedAmount() {
		return totalMatchedAmount;
	}

	public void setTotalMatchedAmount(BigDecimal totalMatchedAmount) {
		this.totalMatchedAmount = totalMatchedAmount;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public short getNumberOfWinningSelections() {
		return numberOfWinningSelections;
	}

	public void setNumberOfWinningSelections(short numberOfWinningSelections) {
		this.numberOfWinningSelections = numberOfWinningSelections;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public short getWithdrawalSequenceNumber() {
		return withdrawalSequenceNumber;
	}

	public void setWithdrawalSequenceNumber(short withdrawalSequenceNumber) {
		this.withdrawalSequenceNumber = withdrawalSequenceNumber;
	}

	public short getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(short displayOrder) {
		this.displayOrder = displayOrder;
	}

	public boolean isEnabledForMultiples() {
		return isEnabledForMultiples;
	}

	public void setEnabledForMultiples(boolean isEnabledForMultiples) {
		this.isEnabledForMultiples = isEnabledForMultiples;
	}

	public boolean isInRunningAllowed() {
		return isInRunningAllowed;
	}

	public void setInRunningAllowed(boolean isInRunningAllowed) {
		this.isInRunningAllowed = isInRunningAllowed;
	}

	public boolean isManagedWhenInRunning() {
		return isManagedWhenInRunning;
	}

	public void setManagedWhenInRunning(boolean isManagedWhenInRunning) {
		this.isManagedWhenInRunning = isManagedWhenInRunning;
	}

	public boolean isCurrentlyInRunning() {
		return isCurrentlyInRunning;
	}

	public void setCurrentlyInRunning(boolean isCurrentlyInRunning) {
		this.isCurrentlyInRunning = isCurrentlyInRunning;
	}

	public int getInRunningDelaySeconds() {
		return inRunningDelaySeconds;
	}

	public void setInRunningDelaySeconds(int inRunningDelaySeconds) {
		this.inRunningDelaySeconds = inRunningDelaySeconds;
	}

	public Integer getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	public BigDecimal getPlacePayout() {
		return placePayout;
	}

	public void setPlacePayout(BigDecimal placePayout) {
		this.placePayout = placePayout;
	}

	/**
	 * Data model for betdaq selection with prices.
	 * 
	 * @author daniel
	 * 
	 */
	public static class BDSelectionWithPrices {

		private long id;
		private String name;
		private short status;
		private short resetCount;
		private BigDecimal deductionFactor;

		private List<BDPrice> prices;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public short getStatus() {
			return status;
		}

		public void setStatus(short status) {
			this.status = status;
		}

		public short getResetCount() {
			return resetCount;
		}

		public void setResetCount(short resetCount) {
			this.resetCount = resetCount;
		}

		public BigDecimal getDeductionFactor() {
			return deductionFactor;
		}

		public void setDeductionFactor(BigDecimal deductionFactor) {
			this.deductionFactor = deductionFactor;
		}

		public List<BDPrice> getPrices() {
			return prices;
		}

		public void setPrices(List<BDPrice> prices) {
			this.prices = prices;
		}

		/**
		 * Data model for BetDaq price
		 * 
		 * @author daniel
		 * 
		 */
		public static class BDPrice {

			private final double price;
			private final double stake;
			private final BDPRiceTypeEnum type;

			public BDPrice(double price, double stake, BDPRiceTypeEnum type) {
				this.price = price;
				this.stake = stake;
				this.type = type;
			}

			public double getPrice() {
				return price;
			}

			public double getStake() {
				return stake;
			}

			public BDPRiceTypeEnum getType() {
				return type;
			}

			public static enum BDPRiceTypeEnum {
				ForSidePrices, AgainstSidePrices;
			}

			@Override
			public String toString() {
				return ToStringBuilder.reflectionToString(this).toString();
			}
		}

	}

	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.SHORT_PREFIX_STYLE).append("name", name).append("type",type).append("status",status).append("startTime",startTime).append("isCurrentlyInRunning",isCurrentlyInRunning).append("inRunningDelaySeconds",inRunningDelaySeconds).toString();
	}
}
