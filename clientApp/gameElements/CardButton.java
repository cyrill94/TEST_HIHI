/**@author Sven**/
package gameElements;


import javax.swing.ToolTipManager;

import cards.Card;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardButton extends Button{
	
	private String Name;
	private Card card;
	private boolean selected;
	
	//Constructor
	
	public CardButton(String name, Card card){
		this.Name = name;
		this.card = card;
		boolean selected;
		selected = false;
		this.selected = selected;
		this.getStyleClass().add("Cards");
	}

	public CardButton(String name){
		this.Name = name;
		boolean selected;
		selected = false;
		this.selected = selected;
		this.getStyleClass().add("Cards");
	}
	
	
	//adds Tooltip to Cardbutton to have a closer look at the card
	public void addToolTip(){
		Image image = new Image("/stylesheets/"+this.getName()+".jpg");
		ImageView imageView = new ImageView(image);
		Tooltip tt = new Tooltip();
		//tt.getStyleableParent().getStyleClass().clear(); --> sorgt für Darstellungsfehler
		tt.getStyleableParent().getStyleClass().add("Tooltip");
		tt.setGraphic(imageView);
		tt.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		this.setTooltip(tt);
		ToolTipManager.sharedInstance().setDismissDelay(10000);
	}
	
	//removes Tooltip from CardButton
	public void removeToolTip(){
		this.setTooltip(null);
	}
	
	
	//marks the cardButton as selected or deselected
	public void selectCard(){
		if(this.selected==false){
			this.selected = true;
			this.getStyleClass().clear();
			this.getStyleClass().add("selectedCardButton");
		}
		else{
			this.selected = false;
			this.getStyleClass().clear();
			this.getStyleClass().add("Cards");
		}
	}
	
	//marks the cardButton as selected or deselected --> only for stealing cards
		public void selectCardToSteal(){
			if(this.selected==false){
				this.selected = true;
				this.getStyleClass().clear();
				this.getStyleClass().add("selectedCardToSteal");
			}
			else{
				this.selected = false;
				this.getStyleClass().clear();
				this.getStyleClass().add("buyCards");
			}
		}
	
	public boolean isSelected(){
		return this.selected;
	}
	
	//Getter and Setter
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	


}
