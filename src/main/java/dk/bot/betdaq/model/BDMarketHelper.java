package dk.bot.betdaq.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author korzekwad
 * 
 */
public class BDMarketHelper {

	/** Traverse BDEvent recursively and return all markets. */
	public static List<BDMarket> getAllMarkets(BDEvent event) {
		List<BDMarket> markets = new ArrayList<BDMarket>();

		if (event.getBdMarkets() != null) {
			markets.addAll(event.getBdMarkets());
		}

		for (BDEvent childEvent : event.getChildEvents()) {
			markets.addAll(getAllMarkets(childEvent));
		}

		return markets;
	}
}
