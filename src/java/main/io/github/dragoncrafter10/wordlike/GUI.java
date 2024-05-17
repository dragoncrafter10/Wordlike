package io.github.dragoncrafter10.wordlike;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {
	JLabel winloss, hiddenWord, enemyModifierText;
	JLabel[][] letters = new JLabel[7][5];
	JLabel[] relicsText = new JLabel[100];
	JPanel[] relicsPanels = new JPanel[20];
	JLabel[] information = new JLabel[100];
	JFrame frame, infoFrame;
	JPanel guessRow, newG, enemyStuff, container, consumablesRow, infoContainer;
	JPanel[] letterRows = new JPanel[7];
	JPanel[] infoRows = new JPanel[100];
	JTextField wordBox;
	JButton check, newGame, showInfo, hideInfo;
	String guess = "aaaaa";
	int[] hints = {0, 0, 0, 0, 0};
	int[] realHints = {0, 0, 0, 0, 0};
	int[] zeros = {0, 0, 0, 0, 0};
	int round = 2;
	int floor = 1;
	Runner runner = new Runner();
	Enemy enemy = new Enemy(runner.getWord(), (int)(Math.random()*6+1));
	Consumable[] slots = new Consumable[20];
	JButton[] conButtons = new JButton[20];
	JPanel[] conRows = new JPanel[4];
	Relic[] relics = new Relic[100];
	boolean cheatGuess = false;
	boolean extraGuess = false;
	int numRelics = 0;
	Dimension hide = new Dimension(0, 0);
	Dimension show = new Dimension(80, 30);
	
	JPanel rewardsTextPanel, rewardsPanel, rewardsReplacePanel, skipPanel, rewardsContainer, confirmTextPanel, confirmButtonPanel, infoPanel, confirmContainer;
	JLabel rewardsText, confirmText;
	JFrame rewardsFrame, confirmFrame;
	JButton reward1, reward2, reward3, skipReward, confirmButton, backButton, infoRewardsB;
	JButton[] swaps = new JButton[20];
	Relic relicOption1 = new Relic((int)(Math.random()*4));
	Relic relicOption2 = new Relic((int)(Math.random()*4));
	Relic relicOption3 = new Relic((int)(Math.random()*4));
	Consumable consumableOption1 = new Consumable("tempo", (int)(Math.random()*3+1));
	Consumable consumableOption2 = new Consumable("tempo", (int)(Math.random()*3+1));
	Consumable consumableOption3 = new Consumable("tempo", (int)(Math.random()*3+1));
	Consumable con = new Consumable();
	int conEf = 0;
	boolean pass = false;
	
	public void runGUI() {
		
		frame = new JFrame("Wordle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		guessRow = new JPanel();
		guessRow.setLayout(new FlowLayout());
		newG = new JPanel();
		newG.setLayout(new FlowLayout());
		enemyStuff = new JPanel();
		enemyStuff.setLayout(new FlowLayout());
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		consumablesRow = new JPanel();
		consumablesRow.setLayout(new FlowLayout());
		for(int x = 0; x < 20; x++) {
			relicsPanels[x] = new JPanel();
			relicsPanels[x].setLayout(new FlowLayout());
		}
		
		for(int x = 0; x < 7; x++) {
			letterRows[x] = new JPanel();
			letterRows[x].setLayout(new FlowLayout());
			for(int y = 0; y < 5; y++) {
				letters[x][y] = new JLabel("");
				letters[x][y].setFont(new Font(letters[x][y].getFont().getName(), letters[x][y].getFont().getStyle(), 30));
				letters[x][y].setPreferredSize(new Dimension(30, 30));
				letters[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				letters[x][y].setVerticalAlignment(SwingConstants.CENTER);
				letterRows[x].add(letters[x][y]);
			}
			container.add(letterRows[x]);
		}
		
		for(int x = 0; x < 100; x++) {
			relicsText[x] = new JLabel("");
			relics[x] = new Relic();
		}
		relics[0].swapRelic((int)(Math.random()*4));
		relics[0].setWord(runner.getWord());
		relicsText[0].setText(relics[0].runStartRelics());
		numRelics = 1;
		relicsPanels[0].add(relicsText[0]);
		for(int x = 0; x < 100; x++) {
			if(relics[x].getRelicName().equals("Expansion Pass")) {
				pass = true;
				round = 1;
			}
		}
		
		wordBox = new JTextField(10);
		guessRow.add(wordBox);
		check = new JButton("Check");
		check.setActionCommand("Check");
		check.addActionListener(this);
		guessRow.add(check);
		winloss = new JLabel("Tries left: " + (8-round));
		guessRow.add(winloss);
		hiddenWord = new JLabel();
		guessRow.add(hiddenWord);
		newGame = new JButton("New Game");
		newGame.setActionCommand("New Game");
		newGame.addActionListener(this);
		newG.add(newGame);
		enemyModifierText = new JLabel(enemy.getModText());
		enemyModifierText.setFont(new Font(enemyModifierText.getFont().getName(), enemyModifierText.getFont().getStyle(), 16));
		enemyStuff.add(enemyModifierText);
		for(int x = 0; x < 4; x++) {
			conRows[x] = new JPanel();
			conRows[x].setLayout(new FlowLayout());
		}
		for(int x = 0; x < 20; x++) {
			slots[x] = new Consumable();
			conButtons[x] = new JButton("");
			conButtons[x].setActionCommand("Con " + x);
			conButtons[x].addActionListener(this);
			conButtons[x].setPreferredSize(hide);
			conRows[x/5].add(conButtons[x]);
		}
		slots[0].newConsumable(runner.getWord(), (int)(Math.random()*3+1));
		conButtons[0].setPreferredSize(show);
		conButtons[0].setText(slots[0].getDisplayText());
		showInfo = new JButton("Show information");
		showInfo.setActionCommand("Show Info");
		showInfo.addActionListener(this);
		newG.add(showInfo);
		
		
		container.add(guessRow);
		container.add(enemyStuff);
		container.add(conRows[0]);
		container.add(newG);
		container.add(relicsPanels[0]);
		frame.add(container);
		frame.setSize(500, 500);
		frame.setVisible(true);
		
		infoFrame = new JFrame("Wordlike Info");
		infoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		infoContainer = new JPanel();
		infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.Y_AXIS));
		information[0] = new JLabel("Consumables: Single use items that help solve the hidden word. Only five can be held at a time");
		information[1] = new JLabel("Hint: Consumable that reveals one letter in the hidden word, but not the location of it");
		information[2] = new JLabel("Reveal: Consumable that reveals one letter and the location of it in the hidden word");
		information[3] = new JLabel("Cheat: Consumable that allows one guess consisting of ANY 5 letters");
		information[4] = new JLabel("");
		information[5] = new JLabel("Relics: permanent upgrades that help guess the hidden word.");
		information[6] = new JLabel("Red Card: Relic that shows one letter at the start of each fight that IS NOT IN the hidden word");
		information[7] = new JLabel("Yellow Card: Relic that shows one letter at the start of each fight that IS IN the hidden word");
		information[8] = new JLabel("SOS Signal: Relic that reveals a letter and its position in the hidden word the first time a guess that is COMPLETELY wrong is entered");
		information[9] = new JLabel("Expansion Pass: Relic that allows one extra guess");
		information[10] = new JLabel("");
		information[11] = new JLabel("Enemy Modifiers: Conditions that make guessing the hidden word more difficult");
		information[12] = new JLabel("No matter the modifier, if the hidden word is guessed completely correctly, it will work");
		information[13] = new JLabel("Enemy is Faking Letters: The enemy picks three shown letters that will always show as green when guessed");
		information[14] = new JLabel("Enemy is Hiding Letters: The enemy picks three shown letters that will always show as white when guessed");
		information[15] = new JLabel("Enemy is Preventing letters From being Guessed: Guesses containing the shown letter will not be allowed");
		information[16] = new JLabel("Enemy May Display False Information: The enemy will randomly modify the guess to sometimes show false information");
		information[17] = new JLabel("Enemy is Preventing Repeat Letters: Any letters used in the previous guess may not be guessed in the next guess");
		information[18] = new JLabel("Enemy Swaps Guessed Letters: The enemy picks two random letters and swaps them after the guess is made, updating their colors appropriately");
		information[19] = new JLabel("");
		for(int x = 0; x < 19; x++) {
			infoRows[x] = new JPanel();
			infoRows[x].setLayout(new FlowLayout());
			infoRows[x].add(information[x]);
			infoContainer.add(infoRows[x]);
		}
		hideInfo = new JButton("Hide information");
		hideInfo.setActionCommand("Hide Info");
		hideInfo.addActionListener(this);
		infoContainer.add(hideInfo);
		infoFrame.add(infoContainer);
		infoFrame.setSize(1000, 800);
		infoFrame.setVisible(false);
		
		confirmFrame = new JFrame("Confirm");
		confirmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		confirmTextPanel = new JPanel();
		confirmTextPanel.setLayout(new FlowLayout());
		confirmButtonPanel = new JPanel();
		confirmButtonPanel.setLayout(new FlowLayout());
		confirmContainer = new JPanel();
		confirmContainer.setLayout(new BoxLayout(confirmContainer, BoxLayout.Y_AXIS));
		
		confirmText = new JLabel("Are you sure you want to reset?");
		confirmTextPanel.add(confirmText);
		confirmButton = new JButton("Yes");
		confirmButton.setActionCommand("reset");
		confirmButton.addActionListener(this);
		confirmButtonPanel.add(confirmButton);
		backButton = new JButton("No");
		backButton.setActionCommand("Back");
		backButton.addActionListener(this);
		confirmButtonPanel.add(backButton);
		confirmContainer.add(confirmTextPanel);
		confirmContainer.add(confirmButtonPanel);
		confirmFrame.add(confirmContainer);
		confirmFrame.setSize(300, 100);
		confirmFrame.setVisible(false);
		
		rewardsScreen();
	}
	
	public void displayHints() {
		guess = guess.toLowerCase();
		if(runner.checkWord(guess, cheatGuess) && enemy.runGuessModifiers(guess)) {
			for(int x = 0; x < 10; x++) {
				relicsText[x].setText(relics[x].runGuessRelics(guess));
			}
			realHints = runner.compareWords(guess);
			hints = enemy.compareWords(guess);
			if(round > 0 && round < 8) {
				updateLabels(round, hints, enemy.getGuess());
				round++;
				if(checkWin(realHints)) {
					updateLabels(round-1, realHints, enemy.getGuess());
					round = -1;
				}
				if(cheatGuess) {
					cheatGuess = false;
					for(int x = 0; x < 20; x++) {
						if(slots[x].getHint() == -1) {
							slots[x].deleteConsumable();
							conButtons[x].setText("");
							conButtons[x].setBackground(null);
							conButtons[x].setPreferredSize(hide);
						}
					}
				}
				winloss.setText("Tries left:" + (8-round));
			} 
			
			if(round == -1) {
				winloss.setText("You win!");
				hiddenWord.setText("The word was: " + runner.getWord());
				newGame.setText("Continue");
			}
			if(round == 8) {
				winloss.setText("You lose!");
				hiddenWord.setText("The word was: " + runner.getWord());
				newGame.setText("New Game");
			}
			wordBox.setText("");
			
		} else {
			if(round > 0 && round < 8) {
				winloss.setText("Invalid input!");
			}
			wordBox.setText("");
		}
		
	}
	
	public void continueGame() {
		floor++;
		newGame.setText("New Game");
		round = 2;
		updateLabels(1, zeros, "     ");
		updateLabels(2, zeros, "     ");
		updateLabels(3, zeros, "     ");
		updateLabels(4, zeros, "     ");
		updateLabels(5, zeros, "     ");
		updateLabels(6, zeros, "     ");
		updateLabels(7, zeros, "     ");
		wordBox.setText("");
		hiddenWord.setText("");
		runner.generateWord();
		for(int x = 0; x < 20; x++) {
			if(!slots[x].isReal()) {
				slots[x].deleteConsumable();
				conButtons[x].setText("");
				conButtons[x].setBackground(null);
				conButtons[x].setPreferredSize(hide);
			} else {
				slots[x].updateWord(runner.getWord());
				conButtons[x].setText(slots[x].getDisplayText());
				conButtons[x].setPreferredSize(show);
				conButtons[x].setBackground(null);
			}
		}
		
		enemy.makeNewEnemy(runner.getWord(), (int)(Math.random()*6+1));
		enemyModifierText.setText(enemy.getModText());
		numRelics = 1;
		for(int x = 0; x < 100; x++) {
			relics[x].setWord(runner.getWord());
			if(!relics[x].getRelicName().equals("")) {
				relicsText[x].setText(relics[x].runStartRelics());
				relicsPanels[(x/5)].add(relicsText[x]);
				if(x%5 == 0) {
					container.add(relicsPanels[x/5]);
				}
				numRelics++;
			}
			
			if(relics[x].getRelicName().equals("Expansion Pass")) {
				round = 1;
			}
		}
		
		winloss.setText("Tries left: " + (8-round));
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		if(eventName.equals("Check")) {
			guess = wordBox.getText();
			displayHints();
		}
		if(eventName.equals("New Game")) {
			if(newGame.getText().equals("New Game")) {
				frame.setVisible(false);
				confirmFrame.setVisible(true);
			} else {
				generateRewardsScreen();
				frame.setVisible(false);
			}
			
		}
		if(eventName.equals("Back")) {
			frame.setVisible(true);
			confirmFrame.setVisible(false);
		}
		if(eventName.equals("reset")) {
			frame.setVisible(true);
			confirmFrame.setVisible(false);
			floor = 1;
			//newGame.setText("New Game");
			round = 2;
			updateLabels(1, zeros, "     ");
			updateLabels(2, zeros, "     ");
			updateLabels(3, zeros, "     ");
			updateLabels(4, zeros, "     ");
			updateLabels(5, zeros, "     ");
			updateLabels(6, zeros, "     ");
			updateLabels(7, zeros, "     ");
			wordBox.setText("");
			hiddenWord.setText("");
			runner.generateWord();
			for(int x = 0; x < 20; x++) {
				slots[x].deleteConsumable();
				conButtons[x].setText("");
				conButtons[x].setPreferredSize(hide);
				conButtons[x].setBackground(null);
			}
			slots[0] = new Consumable(runner.getWord(), (int)(Math.random()*3+1));
			conButtons[0].setText(slots[0].getDisplayText());
			conButtons[0].setPreferredSize(show);
			conButtons[0].setBackground(null);
			
			enemy.makeNewEnemy(runner.getWord(), (int)(Math.random()*6+1));
			enemyModifierText.setText(enemy.getModText());
			for(int x = 0; x < 100; x++) {
				relics[x].deleteRelic();
				relicsText[x].setText("");
				relicsPanels[(x/5)].remove(relicsText[x]);
				container.remove(relicsPanels[(x/5)]);
			}
			relics[0].swapRelic((int)(Math.random()*4));
			relics[0].setWord(runner.getWord());
			relicsText[0].setText(relics[0].runStartRelics());
			relicsPanels[0].add(relicsText[0]);
			container.add(relicsPanels[0]);
			for(int x = 0; x < 10; x++) {
				if(relics[x].getRelicName().equals("Expansion Pass")) {
					round = 1;
				}
			}
			numRelics = 1;
			winloss.setText("Tries left: " + (8-round));
		} 
		if(eventName.equals("Con " + 0)) {
			conButtons[0].setText(slots[0].useWordConsumable());
			if(slots[0].getHint() == 1) {
				conButtons[0].setBackground(Color.yellow);
			}
			if(slots[0].getHint() == 2) {
				conButtons[0].setBackground(Color.green);
			}
			if(slots[0].getHint() == -1) {
				cheatGuess = true;
				conButtons[0].setBackground(Color.green);
			}
		}
		if(eventName.equals("Con " + 1)) {
			conButtons[1].setText(slots[1].useWordConsumable());
			if(slots[1].getHint() == 1) {
				conButtons[1].setBackground(Color.yellow);
			}
			if(slots[1].getHint() == 2) {
				conButtons[1].setBackground(Color.green);
			}
			if(slots[1].getHint() == -1) {
				cheatGuess = true;
				conButtons[1].setBackground(Color.green);
			}
		}
		if(eventName.equals("Con " + 2)) {
			conButtons[2].setText(slots[2].useWordConsumable());
			if(slots[2].getHint() == 1) {
				conButtons[2].setBackground(Color.yellow);
			}
			if(slots[2].getHint() == 2) {
				conButtons[2].setBackground(Color.green);
			}
			if(slots[2].getHint() == -1) {
				cheatGuess = true;
				conButtons[2].setBackground(Color.green);
			}
		}
		if(eventName.equals("Con " + 3)) {
			conButtons[3].setText(slots[3].useWordConsumable());
			if(slots[3].getHint() == 1) {
				conButtons[3].setBackground(Color.yellow);
			}
			if(slots[3].getHint() == 2) {
				conButtons[3].setBackground(Color.green);
			}
			if(slots[3].getHint() == -1) {
				cheatGuess = true;
				conButtons[3].setBackground(Color.green);
			}
		}
		if(eventName.equals("Con " + 4)) {
			conButtons[4].setText(slots[4].useWordConsumable());
			if(slots[4].getHint() == 1) {
				conButtons[4].setBackground(Color.yellow);
			}
			if(slots[4].getHint() == 2) {
				conButtons[4].setBackground(Color.green);
			}
			if(slots[4].getHint() == -1) {
				cheatGuess = true;
				conButtons[4].setBackground(Color.green);
			}
		}
		if(eventName.equals("Show Info")) {
			infoFrame.setVisible(true);
		}
		if(eventName.equals("Hide Info")) {
			infoFrame.setVisible(false);
		}
		if(eventName.equals("claimRelic1")) {
			addRelic(relicOption1.getRelicValue());
			rewardsFrame.setVisible(false);
			continueGame();
		}
		if(eventName.equals("claimRelic2")) {
			addRelic(relicOption2.getRelicValue());
			rewardsFrame.setVisible(false);
			continueGame();
		}
		if(eventName.equals("claimRelic3")) {
			addRelic(relicOption3.getRelicValue());
			rewardsFrame.setVisible(false);
			continueGame();
		}
		if(eventName.equals("claimComsumable1")) {
			con = getOp1();
			conEf = con.getEffect();
		}
		if(eventName.equals("claimComsumable2")) {
			con = getOp2();
			conEf = con.getEffect();
		}
		if(eventName.equals("claimComsumable3")) {
			con = getOp3();
			conEf = con.getEffect();
		}
		if(eventName.equals("skipReward")) {
			rewardsFrame.setVisible(false);
			continueGame();
		}
		if(eventName.equals("swap " + 0) && conEf != 0) {
			slots[0].newConsumable(runner.getWord(), conEf);
			rewardsFrame.setVisible(false);
			conButtons[0].setBackground(null);
			conButtons[0].setText(slots[0].getDisplayText());
			conButtons[0].setPreferredSize(show);
			conEf = 0;
			continueGame();
		}
		if(eventName.equals("swap " + 1) && conEf != 0) {
			slots[1].newConsumable(runner.getWord(), conEf);
			rewardsFrame.setVisible(false);
			conButtons[1].setBackground(null);
			conButtons[1].setText(slots[1].getDisplayText());
			conButtons[1].setPreferredSize(show);
			conEf = 0;
			continueGame();
		}
		if(eventName.equals("swap " + 2) && conEf != 0) {
			slots[2].newConsumable(runner.getWord(), conEf);
			rewardsFrame.setVisible(false);
			conButtons[2].setBackground(null);
			conButtons[2].setText(slots[2].getDisplayText());
			conButtons[2].setPreferredSize(show);
			conEf = 0;
			continueGame();
		}
		if(eventName.equals("swap " + 3) && conEf != 0) {
			slots[3].newConsumable(runner.getWord(), conEf);
			rewardsFrame.setVisible(false);
			conButtons[3].setBackground(null);
			conButtons[3].setText(slots[3].getDisplayText());
			conButtons[3].setPreferredSize(show);
			conEf = 0;
			continueGame();
		}
		if(eventName.equals("swap " + 4) && conEf != 0) {
			slots[4].newConsumable(runner.getWord(), conEf);
			rewardsFrame.setVisible(false);
			conButtons[4].setBackground(null);
			conButtons[4].setText(slots[4].getDisplayText());
			conButtons[4].setPreferredSize(show);
			conEf = 0;
			continueGame();
		}
	}
	
	public void updateLabels(int round, int[] hint, String g) {
		for(int x  = 0; x < 5; x++) {
			letters[round-1][x].setOpaque(true);
			letters[round-1][x].setText(g.substring(x, x+1));
			if(hint[x] == 0) {
				letters[round-1][x].setBackground(Color.white);
			}
			if(hint[x] == 1) {
				letters[round-1][x].setBackground(Color.yellow);
			}
			if(hint[x] == 2 || g.equals(hiddenWord)) {
				letters[round-1][x].setBackground(Color.green);
			}
			if(g.substring(x, x+1).equals(" ")) {
				letters[round-1][x].setText("");
				letters[round-1][x].setBackground(null);
			}
		}
	}
	
	public String getUserWord() {
		return wordBox.getText();
	}
	
	public boolean checkWin(int[] h) {
		for(int x = 0; x < 5; x++) {
			if(h[x] != 2) {
				return false;
			}
		}
		return true;
	}
	
	public void addRelic(int relic) {
		boolean stop = false;
		for(int x = 0; x < 10; x++) {
			if(relics[x].getRelicName().equals("") && !stop) {
				relics[x].swapRelic(relic);
				relicsText[x].setText(relics[x].getRelicName());
				//System.out.println(relics[x].getRelicName());
				stop = true;
			}
		}
	}
	
	
	public String getSlotName(int slot) {
		if(slots[slot].isReal()) {
			return slots[slot].getDisplayText();
		}
		return "empty";
	}
	
	
	
	
	public void rewardsScreen() {
		rewardsFrame = new JFrame("Rewards");
		rewardsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rewardsFrame.setVisible(false);
		rewardsContainer = new JPanel();
		rewardsContainer.setLayout(new BoxLayout(rewardsContainer, BoxLayout.Y_AXIS));
		
		rewardsTextPanel = new JPanel();
		rewardsTextPanel.setLayout(new FlowLayout());
		skipPanel = new JPanel();
		skipPanel.setLayout(new FlowLayout());
		rewardsText = new JLabel("Choose a reward!", SwingConstants.CENTER);
		rewardsText.setFont(new Font(rewardsText.getFont().getName(), rewardsText.getFont().getStyle(), 30));
		rewardsText.setAlignmentX(Component.CENTER_ALIGNMENT);
		//rewardsText.setHorizontalAlignment(SwingConstants.CENTER);
		//rewardsText.setVerticalAlignment(SwingConstants.CENTER);
		rewardsTextPanel.add(rewardsText);
		rewardsPanel = new JPanel();
		rewardsPanel.setLayout(new FlowLayout());
		
		reward1 = new JButton("");
		reward1.setActionCommand("claimRelic1");
		reward1.addActionListener(this);
		rewardsPanel.add(reward1);
		reward2 = new JButton("");
		reward2.setActionCommand("claimRelic2");
		reward2.addActionListener(this);
		rewardsPanel.add(reward2);
		reward3 = new JButton("");
		reward3.setActionCommand("claimRelic3");
		reward3.addActionListener(this);
		rewardsPanel.add(reward3);
		
		rewardsReplacePanel = new JPanel();
		rewardsReplacePanel.setLayout(new FlowLayout());
		for(int x = 0; x < 5; x ++) {
			swaps[x] = new JButton("");
			swaps[x].setActionCommand("swap " + x);
			swaps[x].addActionListener(this);
			rewardsReplacePanel.add(swaps[x]);
		}
		skipReward = new JButton("Skip Rewards");
		skipReward.setActionCommand("skipReward");
		skipReward.addActionListener(this);
		skipPanel.add(skipReward);
		infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout());
		infoRewardsB = new JButton("Show information");
		infoRewardsB.setActionCommand("Show Info");
		infoRewardsB.addActionListener(this);
		infoPanel.add(infoRewardsB);
		
		rewardsContainer.add(rewardsText);
		rewardsContainer.add(rewardsPanel);
		rewardsContainer.add(rewardsReplacePanel);
		rewardsContainer.add(skipPanel);
		rewardsContainer.add(infoPanel);
		rewardsFrame.add(rewardsContainer);
		rewardsFrame.setSize(400, 400);
	}
	
	public void generateRewardsScreen() {
		conEf = 0;
		con.deleteConsumable();
		pass = false;
		for(int x = 0; x < 10; x++) {
			if(relics[x].getText().equals("Expansion Pass")) {
				pass = true;
			}
		}
		if((int)(Math.random()*5) == 4) {
			if(pass = true) {
				relicOption1.swapRelic((int)(Math.random()*3+1));
			} else {
				relicOption1.swapRelic((int)(Math.random()*4));
			}
			reward1.setText(relicOption1.getRelicName());
			reward1.setActionCommand("claimRelic1");
		} else {
			consumableOption1.newConsumable("tempo", (int)(Math.random()*3+1));
			reward1.setText(consumableOption1.getDisplayText());
			reward1.setActionCommand("claimComsumable1");
		}
		if((int)(Math.random()*5) == 4) {
			if(pass = true) {
				relicOption2.swapRelic((int)(Math.random()*3+1));
			} else {
				relicOption2.swapRelic((int)(Math.random()*4));
			}
			reward2.setText(relicOption2.getRelicName());
			reward2.setActionCommand("claimRelic2");
		} else {
			consumableOption2.newConsumable("tempo", (int)(Math.random()*3+1));
			reward2.setText(consumableOption2.getDisplayText());
			reward2.setActionCommand("claimComsumable2");
		}
		if((int)(Math.random()*5) == 4) {
			if(pass = true) {
				relicOption2.swapRelic((int)(Math.random()*3+1));
			} else {
				relicOption2.swapRelic((int)(Math.random()*4));
			}
			reward3.setText(relicOption3.getRelicName());
			reward3.setActionCommand("claimRelic3");
		} else {
			consumableOption3.newConsumable("tempo", (int)(Math.random()*3+1));
			reward3.setText(consumableOption3.getDisplayText());
			reward3.setActionCommand("claimComsumable3");
		}
		swaps[0].setText(getSlotName(0));
		swaps[1].setText(getSlotName(1));
		swaps[2].setText(getSlotName(2));
		swaps[3].setText(getSlotName(3));
		swaps[4].setText(getSlotName(4));
		/*swaps[0].setPreferredSize(hide);
		swaps[1].setPreferredSize(hide);
		swaps[2].setPreferredSize(hide);
		swaps[3].setPreferredSize(hide);
		swaps[4].setPreferredSize(hide);*/
		rewardsFrame.setVisible(true);
	}
	
	public Consumable getOp1() {
		return consumableOption1;
	}
	public Consumable getOp2() {
		return consumableOption2;
	}
	public Consumable getOp3() {
		return consumableOption3;
	}
	public void setConSlot(int slot, Consumable c) {
		slots[slot] = c;
	}
}