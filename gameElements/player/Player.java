package player;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import server.ServerModel;

/**@author Cyrill**/

public class Player implements Serializable{
	
	private String name;
	private String password;
	private EnumCardSet cardset;
	private EnumLanguage language;

	public Player(String dummy) {
		if (dummy.equals("dummy")) {
			this.name = "";
			this.password = "";
			this.cardset = EnumCardSet.Standard;
			this.language = EnumLanguage.English;
		}
		
	}
	
	public Player(String name, String password){
			this.name = name;
			this.password = password;
			this.cardset = ServerModel.getCardSet(this.name);
			this.language = ServerModel.getLanguage(this.name);
		
		}
	
	public Player(String name, String password, EnumCardSet cardset, EnumLanguage language){
		writeInDatabase(name, password, cardset, language);
		this.name = name;
		this.password = password;
		this.cardset = cardset;
		this.language = language;
		}
	
	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}
		
	public EnumCardSet getCardSet() {
		return this.cardset;		
	}
	
	public EnumLanguage getLanguage() {
		return this.language;		
	}
	
	public void setCardSet(EnumCardSet cardset) {
		this.cardset = cardset;
	}
	
	public void setLanguage(EnumLanguage language) {
		this.language = language;
	}
	
	public void writeInDatabase(String name, String password, EnumCardSet enumcardset, EnumLanguage enumlanguage) {
		File database = new File("serverApp\\resources\\database.txt");
		
		String cardSet = null;
		String language = null;
		
		if (enumcardset == EnumCardSet.Standard){
			cardSet = "Standard";
		}
		
		if (enumcardset == EnumCardSet.BigMoney){
			cardSet = "BigMoney";
		}

		if (enumcardset == EnumCardSet.InChange){
			cardSet = "InChange";
		}
		
		if (enumcardset == EnumCardSet.VillageSquare){
			cardSet = "VillageSquare";
		}
		
		if (enumlanguage == EnumLanguage.German) {
			language = "German";
		}
		
		if (enumlanguage == EnumLanguage.English) {
			language = "English";
		}
		
		try {
			FileWriter writer = new FileWriter(database,true);
			writer.write("\n");
			writer.write(name+";"+password+";"+cardSet+";"+language);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		String s = "Name: "+this.name+", Password: "+this.password+", Cardset: "+this.cardset+", Language:"+this.language;
		return s;
	}

}
