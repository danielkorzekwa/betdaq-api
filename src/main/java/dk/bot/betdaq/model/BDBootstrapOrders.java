package dk.bot.betdaq.model;

import java.io.Serializable;
import java.util.List;

/**
 * Exchange bets obtained on the bootstrap phase.
 * 
 * @author daniel
 * 
 */
public class BDBootstrapOrders implements Serializable{

	private final List<BDOrder> orders;
	
	/**Max sequence number of order available from BetDaq.*/
	private final long maxSequenceNumber;
	
	public BDBootstrapOrders(List<BDOrder> orders,long maxSequenceNumber) {
		this.orders = orders;
		this.maxSequenceNumber = maxSequenceNumber;
	}

	public List<BDOrder> getOrders() {
		return orders;
	}

	public long getMaxSequenceNumber() {
		return maxSequenceNumber;
	}	
}
