package dk.bot.betdaq.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.globalbettingexchange.externalapi.SelectionType;

/**
 * Data model for BetDaq market
 * 
 * @author korzekwad
 * 
 */
public class BDMarket implements Serializable{

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
	
	private Long eventClassifierId;
	private String eventPath;
	private String eventNamesPath;
	
	private BigDecimal placePayout;
	
	private List<BDSelection> selections;
	
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

	public Long getEventClassifierId() {
		return eventClassifierId;
	}

	public void setEventClassifierId(Long eventClassifierId) {
		this.eventClassifierId = eventClassifierId;
	}

	public BigDecimal getPlacePayout() {
		return placePayout;
	}

	public void setPlacePayout(BigDecimal placePayout) {
		this.placePayout = placePayout;
	}

	public String getEventPath() {
		return eventPath;
	}

	public void setEventPath(String eventPath) {
		this.eventPath = eventPath;
	}

	public String getEventNamesPath() {
		return eventNamesPath;
	}

	public void setEventNamesPath(String eventNamesPath) {
		this.eventNamesPath = eventNamesPath;
	}

	public List<BDSelection> getSelections() {
		return selections;
	}

	public void setSelections(List<BDSelection> selections) {
		this.selections = selections;
	}
	
	/**
	 * Data model for BetDaq market selection
	 * 
	 * @author korzekwad
	 * 
	 */
	public static class BDSelection implements Serializable{

		private long id;
		private String name;
		private short status;
		private short resetCount;
		private BigDecimal deductionFactor;
		private int displayOrder;
		private Boolean isFlipped;
		private Long shadowSelectionId;

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

		public int getDisplayOrder() {
			return displayOrder;
		}

		public void setDisplayOrder(int displayOrder) {
			this.displayOrder = displayOrder;
		}

		public Boolean getIsFlipped() {
			return isFlipped;
		}

		public void setIsFlipped(Boolean isFlipped) {
			this.isFlipped = isFlipped;
		}

		public Long getShadowSelectionId() {
			return shadowSelectionId;
		}

		public void setShadowSelectionId(Long shadowSelectionId) {
			this.shadowSelectionId = shadowSelectionId;
		}
		
	}
	
}
