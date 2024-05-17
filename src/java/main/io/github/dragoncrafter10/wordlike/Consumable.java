package io.github.dragoncrafter10.wordlike;

public class Consumable {
	String word, letter, displayText;
	int random, hint;
	int effect = 0;
	boolean exists = false;
	
	public Consumable() {
		displayText = "";
	}
	
	public Consumable(String w, int num) {
		newConsumable(w, num);
	}
	
	public int getHint() {
		return hint;
	}
	
	public int getEffect() {
		return effect;
	}
	
	public String getDisplayText() {
		if(!exists) {
			return "";
		}
		return displayText;
	}
	
	public void updateWord(String w) {
		word = w;
		if(effect != 0) {
			newConsumable(word, effect);
		}
	}
	
	public void newConsumable(String w, int num) {
		word = w;
		effect = num;
		exists = true;
		switch(num) {
		case 1:
			hint = 1;
			random = (int)(Math.random()*5);
			letter = word.substring(random, random+1);
			displayText = "hint";
			break;
		case 2:
			hint = 2;
			random = (int)(Math.random()*5);
			letter = word.substring(random, random+1) + " " + (random+1);
			displayText = "reveal";
			break;
		case 3:
			hint = 2;
			displayText = "cheat";
			break;
		}
	}
	
	public void deleteConsumable() {
		word = "";
		exists = false;
		displayText = "";
		effect = 0;
		
	}
	
	public String useWordConsumable() {
		exists = false;
		switch(effect) {
		case 1:
			return letter;
		case 2:
			return letter;
		case 3:
			hint = -1;
			return "Active";
		}
		return "";
	}
	
	public boolean isReal() {
		return exists;
	}
}