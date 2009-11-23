package dk.bot.betdaq.model;

import java.util.Date;

/**Represents exchange bet
 * 
 * @author daniel
 *
 */
public class BDOrder {

	    private long id;
	    private long marketId;
	    private long selectionId;
	    private long sequenceNumber;
	    private Date issuedAt;
	    private short polarity;
	    private double unmatchedStake;
	    private double requestedPrice;
	    private double matchedPrice;
	    private double matchedStake;
	    private double matchedAgainstStake;
	    private Short status;
	    private Boolean restrictOrderToBroker;
	    private Long punterReferenceNumber;
	    private boolean cancelOnInRunning;
	    private boolean cancelIfSelectionReset;
	    private boolean isCurrentlyInRunning;
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public long getMarketId() {
			return marketId;
		}
		public void setMarketId(long marketId) {
			this.marketId = marketId;
		}
		public long getSelectionId() {
			return selectionId;
		}
		public void setSelectionId(long selectionId) {
			this.selectionId = selectionId;
		}
		public long getSequenceNumber() {
			return sequenceNumber;
		}
		public void setSequenceNumber(long sequenceNumber) {
			this.sequenceNumber = sequenceNumber;
		}
		public Date getIssuedAt() {
			return issuedAt;
		}
		public void setIssuedAt(Date issuedAt) {
			this.issuedAt = issuedAt;
		}
		public short getPolarity() {
			return polarity;
		}
		public void setPolarity(short polarity) {
			this.polarity = polarity;
		}
		public double getUnmatchedStake() {
			return unmatchedStake;
		}
		public void setUnmatchedStake(double unmatchedStake) {
			this.unmatchedStake = unmatchedStake;
		}
		public double getRequestedPrice() {
			return requestedPrice;
		}
		public void setRequestedPrice(double requestedPrice) {
			this.requestedPrice = requestedPrice;
		}
		public double getMatchedPrice() {
			return matchedPrice;
		}
		public void setMatchedPrice(double matchedPrice) {
			this.matchedPrice = matchedPrice;
		}
		public double getMatchedStake() {
			return matchedStake;
		}
		public void setMatchedStake(double matchedStake) {
			this.matchedStake = matchedStake;
		}
		public double getMatchedAgainstStake() {
			return matchedAgainstStake;
		}
		public void setMatchedAgainstStake(double matchedAgainstStake) {
			this.matchedAgainstStake = matchedAgainstStake;
		}
		public Short getStatus() {
			return status;
		}
		public void setStatus(Short status) {
			this.status = status;
		}
		public Boolean getRestrictOrderToBroker() {
			return restrictOrderToBroker;
		}
		public void setRestrictOrderToBroker(Boolean restrictOrderToBroker) {
			this.restrictOrderToBroker = restrictOrderToBroker;
		}
		public Long getPunterReferenceNumber() {
			return punterReferenceNumber;
		}
		public void setPunterReferenceNumber(Long punterReferenceNumber) {
			this.punterReferenceNumber = punterReferenceNumber;
		}
		public boolean isCancelOnInRunning() {
			return cancelOnInRunning;
		}
		public void setCancelOnInRunning(boolean cancelOnInRunning) {
			this.cancelOnInRunning = cancelOnInRunning;
		}
		public boolean isCancelIfSelectionReset() {
			return cancelIfSelectionReset;
		}
		public void setCancelIfSelectionReset(boolean cancelIfSelectionReset) {
			this.cancelIfSelectionReset = cancelIfSelectionReset;
		}
		public boolean isCurrentlyInRunning() {
			return isCurrentlyInRunning;
		}
		public void setCurrentlyInRunning(boolean isCurrentlyInRunning) {
			this.isCurrentlyInRunning = isCurrentlyInRunning;
		}	    
}
