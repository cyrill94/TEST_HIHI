/**@author Sven Cueni**/
package templates;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class TemplateView {
	
	private TemplateModel model;
	private VBox root;
	Stage primaryStage;
	
	public TemplateView(TemplateModel model, Stage primaryStage){
		this.model = model;
		this.primaryStage = primaryStage;
		
		Image icon = new Image(getClass().getResourceAsStream("/templates/Icon.png"));
		
		Scene scene = this.getScene();
		scene.getStylesheets().add(
				getClass().getResource("/stylesheets/style.css").toExternalForm());
		primaryStage.setTitle("Dominion");
		primaryStage.centerOnScreen();
		//primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(icon);
	}
	
	//gets scene from extending class
	public abstract Scene getScene();
	
	public void start(){
		primaryStage.show();
	}
	
	public void stop(){
		primaryStage.hide();
		primaryStage = null;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void hide() {
		primaryStage.hide();
	}

	
}
