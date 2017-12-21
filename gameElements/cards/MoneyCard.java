package cards;

/** @author Cyrill **/

public class MoneyCard extends Card {
	
	private int intValue;

	public MoneyCard(int intCost, int intValue, String strName) {
		super(intCost, strName);
		this.intValue = intValue;
		this.cardType = EnumCardType.MoneyCard;
	}
	
	public void setIntValue(int value) {
		this.intValue = value;
	}
	
	public int getIntValue() {
		return this.intValue;
	}
}
