package io.github.dragoncrafter10.wordlike.meta;

import java.io.Serial;
import java.util.HashSet;

/**
 * The {@link Dictionary} is the player's main way of tracking meta-progression.
 * <p>
 * This is <string><em>distinctly different</em></strong> from a {@link java.util.Dictionary} or {@link java.util.Map}.
 * This class does <strong>not</strong> map keys to values, but stores packets of player-earned information in a tidy
 * list.
 */
public class Dictionary extends HashSet<DictionaryEntry> { // idk what to do with this yet
    // Increment this on every commit and pr that changes this file
    @Serial
    private static final long serialVersionUID = 2l;
    
}
