package cards;

/** @author Cyrill **/

public class SpellCard extends Card {

	public SpellCard(int intCost, String strName) {
		super(intCost, strName);
		this.cardType = EnumCardType.SpellCard;

	}
	
}
