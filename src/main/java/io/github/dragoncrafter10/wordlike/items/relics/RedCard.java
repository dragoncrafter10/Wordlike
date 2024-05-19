package io.github.dragoncrafter10.wordlike.items.relics;

import java.text.MessageFormat;

import io.github.dragoncrafter10.wordlike.Hint;
import io.github.dragoncrafter10.wordlike.Word;
import io.github.dragoncrafter10.wordlike.items.Relic;

public final class RedCard implements Relic {
    private transient Word word;

    private char given;

    public RedCard(Word word){
        this.word = word;
    }

    @Override
    public Hint onGuess(String guess) {
        return new Hint(word, guess);
    }

    @Override
    public void onRoundStart(Word word) {
        this.word = word;
        given = word.get((int)(Math.random() * word.length()));
    }

    @Override
    public void onPickup() {
        given = 'A'; // default to avoid confusion
    }

    @Override
    public void onPutdown() {
        // This relic doesn't do anything that it needs to undo, so nothing happens here.
    }

    @Override
    public String getName() {
        return "Red Card";
    }

    @Override
    public String getDesc() {
        return MessageFormat.format("Gives a letter that is present: {0}", given);
    }
    
}
