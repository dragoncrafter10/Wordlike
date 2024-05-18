package io.github.dragoncrafter10.wordlike.items;

import io.github.dragoncrafter10.wordlike.Hint;
import io.github.dragoncrafter10.wordlike.Word;

public interface Relic {

    /**
     * Triggers when this relic is given to the player.
     */
    public void onPickup();

    /**
     * Triggers when the player stops having this relic.
     * <p>
     * This is important for relics that have passive effects, so
     * that the effect is properly removed once the player no longer
     * has the relic.
     */
    public void onPutdown();

    /**
     * Triggers on the player guessing a word.
     * <p>
     * A relic may have no effect on guesses, but should still be able to return
     * a hint.
     * 
     * @param guess The player's guess
     * @return A {@link Hint} result of the guess.
     */
    public Hint onGuess(String guess);

    /**
     * Triggers on the start of a round
     * 
     * @param word The word of this round. Will remain valid into future rounds.
     */
    public void onRoundStart(Word word);

    /**
     * Gets the name of this relic.
     * 
     * @return The name of this relic.
     */
    public String getName();

    /**
     * Gets a short description of this relic. The description should be friendly.
     * 
     * @return A player-friendly description of this relic's effects.
     */
    public String getDesc();
}
