package dk.bot.betdaq;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.globalbettingexchange.externalapi.CancelOrdersRequest;
import com.globalbettingexchange.externalapi.CancelOrdersResponse2;
import com.globalbettingexchange.externalapi.EventClassifierType;
import com.globalbettingexchange.externalapi.ExternalApiHeader;
import com.globalbettingexchange.externalapi.GetAccountBalancesRequest;
import com.globalbettingexchange.externalapi.GetAccountBalancesResponse2;
import com.globalbettingexchange.externalapi.GetEventSubTreeNoSelectionsRequest;
import com.globalbettingexchange.externalapi.GetEventSubTreeNoSelectionsResponse2;
import com.globalbettingexchange.externalapi.GetMarketInformationRequest;
import com.globalbettingexchange.externalapi.GetMarketInformationResponse2;
import com.globalbettingexchange.externalapi.GetOddsLadderRequest;
import com.globalbettingexchange.externalapi.GetOddsLadderResponse2;
import com.globalbettingexchange.externalapi.GetOddsLadderResponseItem;
import com.globalbettingexchange.externalapi.GetPricesRequest;
import com.globalbettingexchange.externalapi.GetPricesResponse2;
import com.globalbettingexchange.externalapi.ListAccountPostingsRequest;
import com.globalbettingexchange.externalapi.ListAccountPostingsResponse2;
import com.globalbettingexchange.externalapi.ListAccountPostingsResponseItem;
import com.globalbettingexchange.externalapi.ListBootstrapOrdersRequest;
import com.globalbettingexchange.externalapi.ListBootstrapOrdersResponse2;
import com.globalbettingexchange.externalapi.ListOrdersChangedSinceRequest;
import com.globalbettingexchange.externalapi.ListOrdersChangedSinceResponse2;
import com.globalbettingexchange.externalapi.ListTopLevelEventsRequest;
import com.globalbettingexchange.externalapi.ListTopLevelEventsResponse2;
import com.globalbettingexchange.externalapi.MarketType;
import com.globalbettingexchange.externalapi.MarketTypeWithPrices;
import com.globalbettingexchange.externalapi.Order;
import com.globalbettingexchange.externalapi.PlaceOrdersNoReceiptRequest;
import com.globalbettingexchange.externalapi.PlaceOrdersNoReceiptResponse2;
import com.globalbettingexchange.externalapi.PricesType;
import com.globalbettingexchange.externalapi.ReadOnlyService;
import com.globalbettingexchange.externalapi.ReturnStatus;
import com.globalbettingexchange.externalapi.SecureService;
import com.globalbettingexchange.externalapi.SelectionType;
import com.globalbettingexchange.externalapi.SelectionTypeWithPrices;
import com.globalbettingexchange.externalapi.SimpleOrderRequest;
import com.globalbettingexchange.externalapi.PlaceOrdersNoReceiptRequest.Orders;

import dk.bot.betdaq.model.BDAccountBalance;
import dk.bot.betdaq.model.BDAccountPostingItem;
import dk.bot.betdaq.model.BDBootstrapOrders;
import dk.bot.betdaq.model.BDEvent;
import dk.bot.betdaq.model.BDMarket;
import dk.bot.betdaq.model.BDMarketWithPrices;
import dk.bot.betdaq.model.BDOrder;
import dk.bot.betdaq.model.BDMarket.BDSelection;
import dk.bot.betdaq.model.BDMarketWithPrices.BDSelectionWithPrices;
import dk.bot.betdaq.model.BDMarketWithPrices.BDSelectionWithPrices.BDPrice;
import dk.bot.betdaq.model.BDMarketWithPrices.BDSelectionWithPrices.BDPrice.BDPRiceTypeEnum;

public class BetDaqImpl implements BetDaq {

	private final Log log = LogFactory.getLog(BetDaqImpl.class.getSimpleName());
	
	private final ReadOnlyService readOnlyService;
	private ExternalApiHeader header;
	private final SecureService secureService;

	public BetDaqImpl(ReadOnlyService readOnlyService, SecureService secureService) {
		this.readOnlyService = readOnlyService;
		this.secureService = secureService;
	}

	public void init(BetDaqConfig config) {
		header = new ExternalApiHeader();
		header.setUsername(config.getUserName());
		header.setPassword(config.getPassword());
		header.setVersion(new BigDecimal(config.getVersion()));
		header.setLanguageCode(config.getLanguage());
	}

	public List<BDAccountPostingItem> listAccountPostings(Date startDate, Date endDate) {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		GregorianCalendar calFrom = (GregorianCalendar) GregorianCalendar.getInstance();
		calFrom.setTime(startDate);
		GregorianCalendar calTo = (GregorianCalendar) GregorianCalendar.getInstance();
		calTo.setTime(endDate);

		XMLGregorianCalendar xmlCalFrom;
		XMLGregorianCalendar xmlCalTo;
		try {
			xmlCalFrom = DatatypeFactory.newInstance().newXMLGregorianCalendar(calFrom);
			xmlCalTo = DatatypeFactory.newInstance().newXMLGregorianCalendar(calTo);

		} catch (DatatypeConfigurationException e) {
			throw new BetDaqException(e);
		}

		ListAccountPostingsRequest request = new ListAccountPostingsRequest();
		request.setStartTime(xmlCalFrom);
		request.setEndTime(xmlCalTo);

		ListAccountPostingsResponse2 response = secureService.listAccountPostings(request, header);
		validateReturnCode(response.getReturnStatus());

		List<BDAccountPostingItem> bdItems = new ArrayList<BDAccountPostingItem>();
		for (ListAccountPostingsResponseItem item : response.getOrders().getOrder()) {
			BDAccountPostingItem bdItem = new BDAccountPostingItem();
			bdItem.setPostedAt(item.getPostedAt().toGregorianCalendar().getTime());
			bdItem.setDescription(item.getDescription());
			bdItem.setAmount(item.getAmount());
			bdItem.setResultingBalance(item.getResultingBalance());
			bdItem.setPostingCategory(item.getPostingCategory());
			bdItem.setOrderId(item.getOrderId());
			bdItem.setMarketId(item.getMarketId());
			
			bdItems.add(bdItem);

		}
		return bdItems;
	}

	public BDAccountBalance getAccountBalances() {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		GetAccountBalancesRequest request = new GetAccountBalancesRequest();
		GetAccountBalancesResponse2 response = secureService.getAccountBalances(request, header);
		validateReturnCode(response.getReturnStatus());

		BDAccountBalance accountBalance = new BDAccountBalance();
		accountBalance.setCurrency(response.getCurrency());
		accountBalance.setBalance(response.getBalance());
		accountBalance.setExposure(response.getExposure());
		accountBalance.setAvailableFunds(response.getAvailableFunds());
		accountBalance.setCredit(response.getCredit());

		return accountBalance;

	}

	public List<Double> getOddsLadder() {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		GetOddsLadderRequest request = new GetOddsLadderRequest();
		request.setPriceFormat((short) 1);

		GetOddsLadderResponse2 response = readOnlyService.getOddsLadder(request, header);
		validateReturnCode(response.getReturnStatus());

		List<Double> priceLadder = new ArrayList<Double>();
		for (GetOddsLadderResponseItem item : response.getLadder()) {
			priceLadder.add(item.getPrice().doubleValue());
		}

		return priceLadder;

	}

	public List<BDOrder> listOrdersChangedSince(long sequenceNumber) {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		ListOrdersChangedSinceRequest request = new ListOrdersChangedSinceRequest();
		request.setSequenceNumber(sequenceNumber);

		ListOrdersChangedSinceResponse2 response = secureService.listOrdersChangedSince(request, header);
		validateReturnCode(response.getReturnStatus());

		List<BDOrder> bdOrders = new ArrayList<BDOrder>();
		if (response.getOrders() != null) {
			for (Order order : response.getOrders().getOrder()) {
				BDOrder bdOrder = convertOrder(order);
				bdOrders.add(bdOrder);
			}
		}

		return bdOrders;
	}

	public BDBootstrapOrders listBootstrapOrders(long sequenceNumber) {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		ListBootstrapOrdersRequest request = new ListBootstrapOrdersRequest();
		request.setSequenceNumber(sequenceNumber);

		ListBootstrapOrdersResponse2 response = secureService.listBootstrapOrders(request, header);
		validateReturnCode(response.getReturnStatus());

		List<BDOrder> bdOrders = new ArrayList<BDOrder>();
		if (response.getOrders() != null) {
			for (Order order : response.getOrders().getOrder()) {
				BDOrder bdOrder = convertOrder(order);
				bdOrders.add(bdOrder);
			}
		}
		return new BDBootstrapOrders(bdOrders, response.getMaximumSequenceNumber());
	}

	public List<BDEvent> listTopLevelEvents() {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		ListTopLevelEventsRequest request = new ListTopLevelEventsRequest();

		ListTopLevelEventsResponse2 response = readOnlyService.listTopLevelEvents(request, header);
		validateReturnCode(response.getReturnStatus());

		List<BDEvent> events = new ArrayList<BDEvent>(response.getEventClassifiers().size());
		for (EventClassifierType event : response.getEventClassifiers()) {
			events.add(convertEvent(event, null, null));
		}
		return events;
	}

	public List<BDMarket> getMarketInformation(List<Long> marketIds) {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		GetMarketInformationRequest request = new GetMarketInformationRequest();

		for (Long marketId : marketIds) {
			request.getMarketIds().add(marketId);
		}

		GetMarketInformationResponse2 response = readOnlyService.getMarketInformation(request, header);
		validateReturnCode(response.getReturnStatus());

		List<MarketType> markets = response.getMarkets();

		List<BDMarket> bdMarkets = new ArrayList<BDMarket>();
		for (MarketType marketType : markets) {
			bdMarkets.add(convertMarket(marketType));
		}
		return bdMarkets;
	}

	public List<BDEvent> getEventSubTreeNoSelections(List<Long> eventIds, boolean wantDirectDescendentsOnly) {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		GetEventSubTreeNoSelectionsRequest request = new GetEventSubTreeNoSelectionsRequest();
		request.setWantDirectDescendentsOnly(wantDirectDescendentsOnly);
		for (Long eventId : eventIds) {
			request.getEventClassifierIds().add(eventId);
		}

		GetEventSubTreeNoSelectionsResponse2 response = readOnlyService.getEventSubTreeNoSelections(request, header);
		validateReturnCode(response.getReturnStatus());

		List<BDEvent> events = new ArrayList<BDEvent>(response.getEventClassifiers().size());
		for (EventClassifierType event : response.getEventClassifiers()) {
			events.add(convertEvent(event, null, null));
		}
		return events;
	}

	public List<BDMarketWithPrices> getMarketsWithPrices(List<Long> marketIds, double thresholdAmount,
			int numberForPricesRequired, int numberAgainstPricesRequired, boolean wantVirtualSelections) {
		if (marketIds.size() > 50) {
			throw new IllegalArgumentException("Max amount of markets to get prices for is 50");
		}

		/** Wait to not be black-listed. */
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		GetPricesRequest request = new GetPricesRequest();
		request.getMarketIds().addAll(marketIds);
		request.setThresholdAmount(new BigDecimal(thresholdAmount));
		request.setNumberForPricesRequired(numberForPricesRequired);
		request.setNumberAgainstPricesRequired(numberAgainstPricesRequired);
		request.setWantVirtualSelections(wantVirtualSelections);

		GetPricesResponse2 response = readOnlyService.getPrices(request, header);
		validateReturnCode(response.getReturnStatus());

		List<BDMarketWithPrices> markets = new ArrayList<BDMarketWithPrices>();
		for (MarketTypeWithPrices market : response.getMarketPrices()) {
			if (market.getStartTime() != null) {
				BDMarketWithPrices bdMarket = new BDMarketWithPrices();
				bdMarket.setId(market.getId());
				bdMarket.setName(market.getName());
				bdMarket.setType(market.getType());
				bdMarket.setStatus(market.getStatus());
				bdMarket.setNumberOfWinningSelections(market.getNumberOfWinningSelections());
				bdMarket.setStartTime(market.getStartTime().toGregorianCalendar().getTime());
				bdMarket.setWithdrawalSequenceNumber(market.getWithdrawalSequenceNumber());
				bdMarket.setDisplayOrder(market.getDisplayOrder());
				bdMarket.setEnabledForMultiples(market.isIsEnabledForMultiples());
				bdMarket.setInRunningAllowed(market.isIsInRunningAllowed());
				bdMarket.setManagedWhenInRunning(market.isIsManagedWhenInRunning());
				bdMarket.setCurrentlyInRunning(market.isIsCurrentlyInRunning());
				bdMarket.setInRunningDelaySeconds(market.getInRunningDelaySeconds());
				bdMarket.setReturnCode(market.getReturnCode());
				bdMarket.setTotalMatchedAmount(market.getTotalMatchedAmount());
				bdMarket.setPlacePayout(market.getPlacePayout());

				/** Set market selections with prices */
				List<BDSelectionWithPrices> selections = new ArrayList<BDSelectionWithPrices>();
				for (SelectionTypeWithPrices selection : market.getSelections()) {

					BDSelectionWithPrices bdSelection = new BDSelectionWithPrices();
					bdSelection.setId(selection.getId());
					bdSelection.setName(selection.getName());
					bdSelection.setStatus(selection.getStatus());
					bdSelection.setResetCount(selection.getResetCount());
					bdSelection.setDeductionFactor(selection.getDeductionFactor());

					/** Set selection prices. */
					List<BDPrice> bdPrices = new ArrayList<BDPrice>();
					List<JAXBElement<PricesType>> forSidePricesAndAgainstSidePrices = selection
							.getForSidePricesAndAgainstSidePrices();
					for (JAXBElement<PricesType> price : forSidePricesAndAgainstSidePrices) {
						BDPRiceTypeEnum priceType;
						if (price.getName().getLocalPart().equals("ForSidePrices")) {
							priceType = BDPRiceTypeEnum.ForSidePrices;
						} else if (price.getName().getLocalPart().equals("AgainstSidePrices")) {
							priceType = BDPRiceTypeEnum.AgainstSidePrices;
						} else {
							throw new UnsupportedOperationException("Price type is not supported: "
									+ price.getName().getLocalPart());
						}
						BDPrice bdPrice = new BDPrice(price.getValue().getPrice().doubleValue(), price.getValue()
								.getStake().doubleValue(), priceType);
						bdPrices.add(bdPrice);
					}
					bdSelection.setPrices(bdPrices);

					selections.add(bdSelection);

				}
				bdMarket.setSelections(selections);

				markets.add(bdMarket);
			}
		}
		return markets;
	}

	public long placeOrdersNoReceipt(long selectionId, double stake, double price, short polarity,
			short expectedWithdrawalSequenceNumber) {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		PlaceOrdersNoReceiptRequest request = new PlaceOrdersNoReceiptRequest();
		Orders orders = new PlaceOrdersNoReceiptRequest.Orders();
		request.setOrders(orders);

		SimpleOrderRequest order = new SimpleOrderRequest();
		order.setSelectionId(selectionId);
		order.setStake(new BigDecimal(stake));
		BigDecimal roundPrice = new BigDecimal(Double.toString(price)).setScale(2, BigDecimal.ROUND_HALF_UP);
		order.setPrice(roundPrice);
		order.setPolarity(polarity);

		order.setExpectedWithdrawalSequenceNumber(expectedWithdrawalSequenceNumber);

		order.setCancelIfSelectionReset(true);
		order.setCancelOnInRunning(true);
		order.setWithdrawlRepriceOption((short) 2); // cancel on rule 4

		orders.getOrder().add(order);

		PlaceOrdersNoReceiptResponse2 response = secureService.placeOrdersNoReceipt(request, header);
		try {
		validateReturnCode(response.getReturnStatus());
		}
		catch(BetDaqException e) {
			log.error("PlaceOrdersNoReceipts error. Stake: " +  stake + ", size: " + roundPrice + ", polarity: " + polarity);
			throw e;
		}
		return response.getOrderHandles().getOrderHandle().get(0);
	}

	public void cancelOrders(List<Long> orderIds) {
		/** Wait to not be black-listed. */
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (header == null) {
			throw new IllegalStateException("Service not initialized.");
		}

		CancelOrdersRequest request = new CancelOrdersRequest();

		for (Long orderId : orderIds) {
			request.getOrderHandle().add(orderId);
		}

		CancelOrdersResponse2 response = secureService.cancelOrders(request, header);
		validateReturnCode(response.getReturnStatus());

	}

	private BDOrder convertOrder(Order order) {
		BDOrder bdOrder = new BDOrder();

		bdOrder.setId(order.getId());
		bdOrder.setMarketId(order.getMarketId());
		bdOrder.setSelectionId(order.getSelectionId());
		bdOrder.setSequenceNumber(order.getSequenceNumber());
		bdOrder.setIssuedAt(order.getIssuedAt().toGregorianCalendar().getTime());
		bdOrder.setPolarity(order.getPolarity());
		bdOrder.setUnmatchedStake(order.getUnmatchedStake().doubleValue());
		bdOrder.setRequestedPrice(order.getRequestedPrice().doubleValue());
		bdOrder.setMatchedPrice(order.getMatchedPrice().doubleValue());
		bdOrder.setMatchedStake(order.getMatchedStake().doubleValue());
		bdOrder.setMatchedAgainstStake(order.getMatchedAgainstStake().doubleValue());
		bdOrder.setStatus(order.getStatus());
		bdOrder.setRestrictOrderToBroker(order.isRestrictOrderToBroker());
		bdOrder.setPunterReferenceNumber(order.getPunterReferenceNumber());
		bdOrder.setCancelOnInRunning(order.isCancelOnInRunning());
		bdOrder.setCancelIfSelectionReset(order.isCancelIfSelectionReset());
		bdOrder.setCurrentlyInRunning(order.isIsCurrentlyInRunning());

		return bdOrder;
	}

	private BDEvent convertEvent(EventClassifierType event, String eventPath, String eventNamesPath) {
		if (eventPath == null) {
			eventPath = "";
		}
		if (eventNamesPath == null) {
			eventNamesPath = "";
		}

		eventPath = eventPath + "/" + event.getId();
		eventNamesPath = eventNamesPath + "\\" + event.getName();

		List<BDEvent> bdChildEvents = new ArrayList<BDEvent>();
		for (EventClassifierType childEvent : event.getEventClassifiers()) {
			bdChildEvents.add(convertEvent(childEvent, eventPath, eventNamesPath));
		}

		List<BDMarket> bdMarkets = new ArrayList<BDMarket>();
		for (MarketType market : event.getMarkets()) {
			BDMarket bdMarket = convertMarket(market);
			bdMarket.setEventPath(eventPath);
			bdMarket.setEventNamesPath(eventNamesPath);
			bdMarkets.add(bdMarket);
		}

		BDEvent bdEvent = new BDEvent(event.getId(), event.getName(), event.getDisplayOrder(), event
				.isIsEnabledForMultiples(), event.getParentId(), eventPath, eventNamesPath, bdChildEvents, bdMarkets);

		return bdEvent;
	}

	private BDMarket convertMarket(MarketType market) {

		BDMarket bdMarket = new BDMarket();
		bdMarket.setId(market.getId());
		bdMarket.setName(market.getName());
		bdMarket.setType(market.getType());
		bdMarket.setStatus(market.getStatus());
		bdMarket.setNumberOfWinningSelections(market.getNumberOfWinningSelections());
		bdMarket.setStartTime(market.getStartTime().toGregorianCalendar().getTime());
		bdMarket.setWithdrawalSequenceNumber(market.getWithdrawalSequenceNumber());
		bdMarket.setDisplayOrder(market.getDisplayOrder());
		bdMarket.setEnabledForMultiples(market.isIsEnabledForMultiples());
		bdMarket.setInRunningAllowed(market.isIsInRunningAllowed());
		bdMarket.setManagedWhenInRunning(market.isIsManagedWhenInRunning());
		bdMarket.setCurrentlyInRunning(market.isIsCurrentlyInRunning());
		bdMarket.setEventClassifierId(market.getEventClassifierId());
		bdMarket.setPlacePayout(market.getPlacePayout());

		if (market.getSelections() != null && market.getSelections().size() > 0) {
			List<BDSelection> selections = new ArrayList<BDSelection>();
			for (SelectionType selectionType : market.getSelections()) {
				BDSelection selection = new BDSelection();
				selection.setId(selectionType.getId());
				selection.setName(selectionType.getName());
				selection.setStatus(selectionType.getStatus());
				selection.setResetCount(selectionType.getResetCount());
				selection.setDeductionFactor(selection.getDeductionFactor());
				selection.setDisplayOrder(selectionType.getDisplayOrder());
				selection.setIsFlipped(selectionType.isIsFlipped());
				selection.setShadowSelectionId(selectionType.getShadowSelectionId());

				selections.add(selection);
			}
			bdMarket.setSelections(selections);
		}
		return bdMarket;

	}

	/**
	 * Throws BetDaqException if return status code is not 0 (success)
	 * 
	 */
	private void validateReturnCode(ReturnStatus status) {
		if (status.getCode() != 0) {
			throw new BetDaqException("Status code: " + status.getCode() + ". " + status.getDescription() + ". "
					+ status.getExtraInformation());
		}
	}
}
