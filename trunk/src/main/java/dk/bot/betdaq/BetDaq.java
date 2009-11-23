package dk.bot.betdaq;

import java.util.Date;
import java.util.List;

import dk.bot.betdaq.model.BDAccountBalance;
import dk.bot.betdaq.model.BDAccountPostingItem;
import dk.bot.betdaq.model.BDBootstrapOrders;
import dk.bot.betdaq.model.BDEvent;
import dk.bot.betdaq.model.BDMarket;
import dk.bot.betdaq.model.BDMarketWithPrices;
import dk.bot.betdaq.model.BDOrder;

/**
 * BetDaq adapter.
 * 
 * @author korzekwad
 * 
 */
public interface BetDaq {

	/** Initialise BetDaq adapter. */
	public void init(BetDaqConfig config);

	/**
	 * List bootstrap orders that have a sequence number greater than the sequence number specified.
	 * 
	 * @param sequenceNumber
	 * @return
	 */
	public BDBootstrapOrders listBootstrapOrders(long sequenceNumber);

	/**
	 * Returns a list of orders (for the currently logged in user) that have changed since a given sequence number.
	 * 
	 * @param sequenceNumber
	 */
	public List<BDOrder> listOrdersChangedSince(long sequenceNumber);

	public List<BDEvent> listTopLevelEvents();

	/**
	 * @param eventIds
	 *            List of event identifiers that events/markets are returned for.
	 * @param wantDirectDescendentsOnly
	 * @return
	 */
	public List<BDEvent> getEventSubTreeNoSelections(List<Long> eventIds, boolean wantDirectDescendentsOnly);

	/**
	 * Returns detailed information about a given markets.
	 * 
	 * @param marketIds
	 * @return
	 */
	public List<BDMarket> getMarketInformation(List<Long> marketIds);

	/**
	 * 
	 * @param marketIds
	 *            List of market identifiers that the markets with prices are returned for.
	 */
	public List<BDMarketWithPrices> getMarketsWithPrices(List<Long> marketIds, double thresholdAmount,
			int numberForPricesRequired, int numberAgainstPricesRequired, boolean wantVirtualSelections);

	/**
	 * Places one order on the exchange.
	 * 
	 * @return betId
	 */
	public long placeOrdersNoReceipt(long selectionId, double stake, double price, short polarity,
			short expectedWithdrawalSequenceNumber);

	/**
	 * Cancel orders on a betting exchange
	 * 
	 * @param orderIds
	 *            list of order ids to cancel
	 */
	public void cancelOrders(List<Long> orderIds);

	/**
	 * Obtain the current odds ladder.
	 * 
	 * @return List of available prices that bets may be placed on. (in a decimal format)
	 */
	public List<Double> getOddsLadder();

	/** Returns a summary of current balances. */
	public BDAccountBalance getAccountBalances();

	/**
	 * Returns more detailed information about the account, including account transactions between two given date and
	 * times.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<BDAccountPostingItem> listAccountPostings(Date startDate, Date endDate);

}
