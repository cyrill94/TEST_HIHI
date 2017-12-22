package cards;

import java.io.Serializable;

/** @author Cyrill  **/

public class Card implements Serializable{

	private int intCost;
	private String strName;
	private String imagePath;
	protected EnumCardType cardType;
	
	public Card() {
				
	}
	
	public Card(int intCost, String strName) {
		this.intCost = intCost;
		this.strName = strName;
		this.imagePath = "/images/"+this.strName+".jpg";
	}
	
	public String getName() {
		return this.strName;
	}

	public int getIntCost() {
		return intCost;
	}

	public void setIntCost(int intCost) {
		this.intCost = intCost;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public EnumCardType getCardType() {
		return this.cardType;
	}	
	
	@Override
	public String toString() {
		return strName;
	}
	
	
		
}
