package io.github.dragoncrafter10.wordlike;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Runner {
	
	String word;
	String userWord = "";
	int[] comp = new int[5];
	Word wordGen = new Word();
	Scanner scan = new Scanner(System.in);
	int[] results = {0, 0, 0, 0, 0};
	
	public Runner() {
		word = wordGen.getCurrentWord();
	}
	
	public String getWord() {
		return word.substring(0, 5);
	}
	
	public int[] getHints() {
		return results;
	}
	
	public void generateWord() {
		word = wordGen.getRandomWord();
	}
	
	public int[] compareWords(String uWord) {
		int ucounter = 1;
		int ccounter = 0;
		int diff = 0;
		for(int x = 0; x < 5; x++) {
			results[x] = 0;
		}
		word = wordGen.getCurrentWord();
		
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 5; y++) {
				if(uWord.substring(x, x+1).equals(word.substring(y, y+1))) {
					results[x] = 1;
				}
			}
		}
		
		for(int z = 0; z < 5; z++) {
			if(uWord.substring(z, z+1).equals(word.substring(z, z+1))) {
				results[z] = 2;
			}
		}
		
		for(int x = 0; x < 5; x++) {
			for(int y = 0; y < 5; y++) {
				if(uWord.substring(x, x+1).equals(uWord.substring(y, y+1)) && y != x) {
					ucounter++;
				}
				if(uWord.substring(x, x+1).equals(word.substring(y, y+1))) {
					ccounter++;
				}
			}
			//System.out.println(ucounter + " " + ccounter + " " + uWord.substring(x, x+1));
			if(ucounter > ccounter) {
				//System.out.println(ucounter + " " + ccounter + " " + uWord.substring(x, x+1) + " " + word.substring(x, x+1));
				diff = ucounter-ccounter;
				if(results[x] != 0) {
					for(int y = 4; y > -1; y--) {
						if(uWord.substring(y, y+1).equals(uWord.substring(x, x+1)) && y != x && results[y] != 2 && diff > 0) {
							results[y] = 0;
							diff--;
						}
					}
				}
			}
			ucounter = 1;
			ccounter = 0;
		}
		
		
		return results;
	}
	
	public boolean checkWord(String input, boolean cheating) {
		if(input.length() == 5 && (wordGen.contains(input) || cheating)) {
			return Pattern.matches("[a-zA-Z]+", input);
		}
		//System.out.println(cheating);
		return false;
	}
	
	public void runGame() {
		
	}
	
}