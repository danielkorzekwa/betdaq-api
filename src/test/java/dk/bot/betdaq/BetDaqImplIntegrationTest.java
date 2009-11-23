package dk.bot.betdaq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dk.bot.betdaq.model.BDAccountBalance;
import dk.bot.betdaq.model.BDAccountPostingItem;
import dk.bot.betdaq.model.BDBootstrapOrders;
import dk.bot.betdaq.model.BDEvent;
import dk.bot.betdaq.model.BDMarket;
import dk.bot.betdaq.model.BDMarketHelper;
import dk.bot.betdaq.model.BDMarketWithPrices;
import dk.bot.betdaq.model.BDOrder;
import dk.bot.betdaq.model.BDMarket.BDSelection;
import dk.bot.betdaq.model.BDMarketWithPrices.BDSelectionWithPrices;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-betdaq-test.xml" })
public class BetDaqImplIntegrationTest {

	@Resource
	BetDaq betDaq;

	@Test
	public void testListBootstrapOrders() {
		BDBootstrapOrders listBootstrapOrders = betDaq.listBootstrapOrders(-1);

		assertNotNull(listBootstrapOrders);
	}

	@Test
	public void testListOrdersChangedSince() {
		List<BDOrder> listOrdersChangedSince = betDaq.listOrdersChangedSince(Long.MAX_VALUE);

		assertNotNull(listOrdersChangedSince);
	}

	@Test
	public void testListTopLevelEvents() {
		List<BDEvent> topLevelEvents = betDaq.listTopLevelEvents();
		assertTrue("No events.", topLevelEvents.size() > 0);
	}

	@Test
	public void testGetEventSubTreeNoSelections() {

		List<Long> eventIds = Arrays.asList(100004l, 100003l, 100005l);
		List<BDEvent> events = betDaq.getEventSubTreeNoSelections(eventIds, false);
	}

	@Test
	public void testGetEventSubTreeNoSelectionsDirectDescendentsOnly() {
		List<Long> eventIds = Arrays.asList(100004l, 100003l, 100005l);

		List<BDEvent> events = betDaq.getEventSubTreeNoSelections(eventIds, true);

		for (BDEvent parentEvent : events) {
			for (BDEvent event : parentEvent.getChildEvents()) {
				assertTrue("Children for event > 0: " + event.getEventName(), event.getChildEvents().size() == 0);
			}
		}
	}

	@Test
	public void testGetMarketInformation() {
		List<Long> eventIds = Arrays.asList(100004l, 100003l, 100005l);
		List<BDEvent> events = betDaq.getEventSubTreeNoSelections(eventIds, false);

		List<BDMarket> markets = new ArrayList<BDMarket>();
		for (BDEvent event : events) {
			markets.addAll(BDMarketHelper.getAllMarkets(event));
		}

		assertTrue("Can't run test - no markets", markets.size() > 0);

		List<Long> marketIds = Arrays.asList(markets.get(0).getId());
		List<BDMarket> marketInformation = betDaq.getMarketInformation(marketIds);

		assertEquals(1, marketInformation.size());
		assertNotNull(marketInformation.get(0).getSelections());
		assertTrue("No selections for market", marketInformation.get(0).getSelections().size() > 0);

	}

	@Test
	public void testGetMarketsWithPricesHR() {
		List<BDEvent> events = betDaq.getEventSubTreeNoSelections(Arrays.asList(100004l), false);

		List<BDMarket> markets = new ArrayList<BDMarket>();
		for (BDEvent event : events) {
			markets.addAll(BDMarketHelper.getAllMarkets(event));
		}

		List<Long> marketIds = new ArrayList<Long>();
		for (int i = 0; i < 50 && i < markets.size(); i++) {
			marketIds.add(markets.get(i).getId());
		}

		List<BDMarketWithPrices> marketsWithPrices = betDaq.getMarketsWithPrices(marketIds, 0, 3, 3, false);

		assertTrue("No markets returned.", marketsWithPrices.size() > 0);

		for (BDMarketWithPrices market : marketsWithPrices) {
			assertNotNull("Selections are null", market.getSelections());

			for (BDSelectionWithPrices selection : market.getSelections()) {
				assertNotNull("Prices are null", selection.getPrices());
			}
		}

	}

	@Test
	public void testGetMarketsWithPricesSoccerInPlay() {
		List<BDEvent> events = betDaq.getEventSubTreeNoSelections(Arrays.asList(100003l), false);

		List<BDMarket> markets = new ArrayList<BDMarket>();
		for (BDEvent event : events) {
			markets.addAll(BDMarketHelper.getAllMarkets(event));
		}

		List<Long> marketIds = new ArrayList<Long>();
		int marketIndex = 0;
		while (marketIds.size() < 50 && marketIndex < markets.size()) {
			BDMarket market = markets.get(marketIndex);
			if (market.getStartTime().getTime() < System.currentTimeMillis()) {
				marketIds.add(market.getId());
			}
			marketIndex++;
		}

		List<BDMarketWithPrices> marketsWithPrices = betDaq.getMarketsWithPrices(marketIds, 0, 3, 3, false);

		assertTrue("No markets returned.", marketsWithPrices.size() > 0);

		for (BDMarketWithPrices market : marketsWithPrices) {
			if (market.isCurrentlyInRunning()) {
				System.out.println("Market in play: " + market);
			}
		}

	}

	@Test
	public void testGetMarketsWithPricesTennis() {
		List<BDEvent> events = betDaq.getEventSubTreeNoSelections(Arrays.asList(100005l), false);

		List<BDMarket> markets = new ArrayList<BDMarket>();
		for (BDEvent event : events) {
			markets.addAll(BDMarketHelper.getAllMarkets(event));
		}

		List<Long> marketIds = new ArrayList<Long>();
		int marketIndex = 0;
		while (marketIds.size() < 50 && marketIndex < markets.size()) {
			BDMarket market = markets.get(marketIndex);
			marketIds.add(market.getId());
			marketIndex++;
		}

		List<BDMarketWithPrices> marketsWithPrices = betDaq.getMarketsWithPrices(marketIds, 0, 3, 3, false);

		assertTrue("No markets returned.", marketsWithPrices.size() > 0);

		for (BDMarketWithPrices market : marketsWithPrices) {
			System.out.println(market);
		}

	}

	@Test
	public void testPlaceOrdersNoReceipt() {
		List<Long> eventIds = Arrays.asList(100004l);
		List<BDEvent> events = betDaq.getEventSubTreeNoSelections(eventIds, false);

		List<BDMarket> markets = new ArrayList<BDMarket>();
		for (BDEvent event : events) {
			markets.addAll(BDMarketHelper.getAllMarkets(event));
		}
		assertTrue("Can't run test - no markets", markets.size() > 0);

		List<Long> marketIds = Arrays.asList(markets.get(0).getId());
		BDMarket marketInformation = betDaq.getMarketInformation(marketIds).get(0);
		BDSelection selection = marketInformation.getSelections().get(0);

		/** Place lay bet. */
		long betId = betDaq.placeOrdersNoReceipt(selection.getId(), 2d, 1.01, (short) 2, marketInformation
				.getWithdrawalSequenceNumber());

		betDaq.cancelOrders(Arrays.asList(betId));
	}

	@Test
	public void testGetOddsLadder() {
		List<Double> oddsLadder = betDaq.getOddsLadder();

		assertTrue("OddsLadder is empty", oddsLadder.size() > 0);
	}

	@Test
	public void testGetAccountBalances() {
		BDAccountBalance accountBalances = betDaq.getAccountBalances();

		assertNotNull(accountBalances);

	}

	@Test
	public void testListAccountPostings() {
		List<BDAccountPostingItem> items = betDaq.listAccountPostings(new Date(System.currentTimeMillis() - 1000 * 3600
				* 24 * 14), new Date(System.currentTimeMillis()));

		assertNotNull(items);
	}
}
