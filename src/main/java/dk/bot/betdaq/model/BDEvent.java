package dk.bot.betdaq.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**Data model for BetDaq Event
 * 
 * @author korzekwad
 *
 */
public class BDEvent implements Serializable{

	private final long eventId;
	private final String eventName;
	private final short displayOrder;
	private final boolean enabledForMultiples;
	private final long parentEventId;
	private String eventPath;
	private String eventNamesPath;
	
	private final List<BDEvent> childEvents;
	private final List<BDMarket> bdMarkets;
	
	public BDEvent(long eventId, String eventName, short displayOrder,boolean enabledForMultiples, long parentEventId, String eventPath,String eventNamesPath,List<BDEvent> childEvents,List<BDMarket> bdMarkets) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.displayOrder = displayOrder;
		this.enabledForMultiples = enabledForMultiples;
		this.parentEventId = parentEventId;
		this.eventPath = eventPath;
		this.eventNamesPath = eventNamesPath;
		this.childEvents = childEvents;
		this.bdMarkets = bdMarkets;
	}

	public long getEventId() {
		return eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public short getDisplayOrder() {
		return displayOrder;
	}

	public boolean isEnabledForMultiples() {
		return enabledForMultiples;
	}

	public long getParentEventId() {
		return parentEventId;
	}

	public List<BDEvent> getChildEvents() {
		return childEvents;
	}

	public List<BDMarket> getBdMarkets() {
		return bdMarkets;
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
