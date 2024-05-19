package io.github.dragoncrafter10.wordlike;

public class Relic {
	int relic = 1;
	String word = "";
	String text = "";
	String name = "";
	boolean used = false;
	
	public Relic() {
		
	}
	
	public void setWord(String w) {
		word = w;
		used = false;
	}
	
	public String getText() {
		return text;
	}
	
	public String getRelicName() {
		return name;
	}
	
	public int getRelicValue() {
		return relic;
	}
	
	public Relic(int r) {
		relic = r;
		if(relic == 1) {
			name = "Red Card";
		}
		if(relic == 2) {
			name = "Yellow Card";
		}
		if(relic == 3) {
			used = false;
			name = "SOS Signal";
		}
		if(relic == 0) {
			name = "Expansion Pass";
		}
	}
	
	public void swapRelic(int r) {
		relic = r;
		if(relic == 1) {
			name = "Red Card";
		}
		if(relic == 2) {
			name = "Yellow Card";
		}
		if(relic == 3) {
			used = false;
			name = "SOS Signal";
		}
		if(relic == 0) {
			name = "Expansion Pass";
		}
	}
	
	public String runStartRelics() {
		if(relic == 1) {
			String letter = getRandomLetter();
			while(word.contains(letter)) {
				letter = getRandomLetter();
			}
			text = name + ": " + letter.toUpperCase();
		} 
		if(relic == 2) {
			String letter = getRandomLetter();
			while(!word.contains(letter)) {
				letter = getRandomLetter();
			}
			text = name + ": " + letter.toUpperCase();
		}
		if(relic == 3) {
			text = name;
		}
		if(relic == 0) {
			text = "Expansion Pass";
		}
		return text;
	}
	
	public String runGuessRelics(String guess) {
		if(relic == 3) {
			if(!used) {
				int[] hints = compareWords(guess);
				for(int x = 0; x < 5; x++) {
					if(hints[x] != 0) {
						text = name;
						return text;
					}
				}
				String letter = getRandomLetter();
				while(!word.contains(letter)) {
					letter = getRandomLetter();
				}
				text = name + ": " + letter.toUpperCase() + " at " + (word.indexOf(letter)+1);
				used = true;
			} else {
			}
		}
		return text;
	}
	
	public String getRandomLetter() {
		// String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		// return letters[(int)(Math.random()*24)];
		// You don't need an array for everything
		return String.valueOf(getRandomChar());
	}

	public char getRandomChar() {
		return (char)(Math.random() * ('z' - 'a') + 'a');
	}
	
	public void deleteRelic() {
		relic = -1;
		text = "";
		used = false;
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
		return results;
	}
}