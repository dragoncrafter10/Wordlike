package io.github.dragoncrafter10.wordlike;

public class Enemy {
	String word, modText, prevGuess, swappedWord, cGuess;
	String[] letterList = {"", "", "", "", ""};
	int modifier;
	StringBuilder sb = new StringBuilder();
	
	public Enemy() {
		
	}
	
	public Enemy(String w, int m) {
		word = w;
		modifier = m;
		setupModifiers();
	}
	
	public void makeNewEnemy(String w, int m) {
		word = w;
		modifier = m;
		letterList[0] = "";
		letterList[1] = "";
		letterList[2] = "";
		letterList[3] = "";
		letterList[4] = "";
		setupModifiers();
	}
	
	public String getModText() {
		return modText;
	}
	
	public void setupModifiers() {
		switch(modifier) {
		case 1:
			while(letterList[0].equals(letterList[1]) || letterList[0].equals(letterList[2]) || letterList[1].equals(letterList[2])) {
				letterList[0] = getRandomLetter();
				letterList[1] = getRandomLetter();
				letterList[2] = getRandomLetter();
			}
			modText = "Enemy is faking letters " + letterList[0] + ", " + letterList[1] + ", "  + letterList[2];
			break;
		case 2:
			while(letterList[0].equals(letterList[1]) || letterList[0].equals(letterList[2]) || letterList[1].equals(letterList[2])) {
				letterList[0] = getRandomLetter();
				letterList[1] = getRandomLetter();
				letterList[2] = getRandomLetter();
			}
			modText = "Enemy is hiding letters " + letterList[0] + ", " + letterList[1] + ", "  + letterList[2];
			break;
		case 3:
			letterList[0] = getRandomLetter();
			modText = "Enemy is preventing " + letterList[0] + " from being guessed";
			break;
		case 4:
			modText = "Enemy may display false information";
			break;
		case 5:
			prevGuess = "";
			modText = "Enemy is preventing repeat letters";
			break;
		case 6:
			swappedWord = "";
			modText = "Enemy swaps guessed letters";
			break;
		}
	}
	
	public int[] runWordModifier(String guess, int[] results) {
		cGuess = guess;
		switch(modifier) {
		case -6:
			modifier = 6;
			cGuess = swappedWord;
			return compareWords(swappedWord);
		case 1:
			for(int x = 0; x < 5; x++) {
				for(int y = 0; y < 3; y++) {
					if(guess.substring(x, x+1).equals(letterList[y])) {
						results[x] = 2;
					}
				}
			}
			break;
		case 2:
			for(int x = 0; x < 5; x++) {
				for(int y = 0; y < 3; y++) {
					if(guess.substring(x, x+1).equals(letterList[y])) {
						results[x] = 0;
					}
				}
			}
			break;
		case 4:
			for(int x = 0; x < 5; x++) {
				if((int)(Math.random()*6) == 1) {
					results[x] = (int)(Math.random()*3);
				}
			}
			break;
		}
		return results;
	}
	
	public boolean runGuessModifiers(String guess) {
		cGuess = guess;
		if(!guess.equals(word)) {
			switch(modifier) {
			case 3:
				for(int x = 0; x < 5; x++) {
					if(guess.substring(x,x+1).equals(letterList[0])) {
						return false;
					}
				}
				break;
			case 5:
				if(prevGuess.equals("")) {
					prevGuess = guess;
				} else {
					for(int x = 0; x < 5; x++) {
						for(int y = 0; y < 5; y++) {
							if(prevGuess.substring(x, x+1).equals(guess.substring(y, y+1))) {
								return false;
							}
						}
					}
					prevGuess = guess;
				}
				break;
			case 6:
				swappedWord = "";
				int swap1 = 0;
				int swap2 = 0;
				while(swap1 == swap2) {
					swap1 = (int)(Math.random()*5);
					swap2 = (int)(Math.random()*5);
				}
				for(int x = 0; x < 5; x++) {
					if(x != swap1 && x != swap2) {
						swappedWord += guess.substring(x, x+1);
					}
					if(x == swap2) {
						swappedWord += guess.substring(swap1, swap1+1);
					}
					if(x == swap1) {
						swappedWord += guess.substring(swap2, swap2+1);
					}
				}
				modifier = -6;
				return true;
			}
		}
		return true;
	}
	
	public int[] compareWords(String uWord) {
		int ucounter = 1;
		int ccounter = 0;
		int diff = 0;
		int[] results = new int[5];
		for(int x = 0; x < 5; x++) {
			results[x] = 0;
		}
		
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
			if(ucounter > ccounter) {
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
		results = runWordModifier(uWord, results);
		return results;
	}
	
	public String getRandomLetter() {
		String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		return letters[(int)(Math.random()*24)];
	}
	
	public String getGuess() {
		return cGuess;
	}
}