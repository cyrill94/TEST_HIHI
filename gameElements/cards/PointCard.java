package cards;

/** @author Cyrill **/

public class PointCard extends Card {
	
	private int intValue;

	public PointCard(int intCost, int intValue, String strName) {
		super(intCost, strName);
		this.intValue = intValue;
		this.cardType = EnumCardType.PointCard;
	}
	
	public void setIntValue (int value) {
		this.intValue = value;
	}
	
	public int getValue () {
		return this.intValue;
	}
	
}
