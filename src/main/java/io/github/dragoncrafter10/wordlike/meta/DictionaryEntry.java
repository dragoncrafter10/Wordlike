package io.github.dragoncrafter10.wordlike.meta;

import java.io.Serializable;

public interface DictionaryEntry extends Serializable {
    /**
     * Checks whether this dictionary entry has been discovered
     * 
     * @return
     */
    boolean isDiscovered();

    /**
     * Gets the name of whatever this entry is of.
     * 
     * @return The name of this entry's subject.
     */
    String getName();

    /**
     * Gets a short description of whatever this entry is of.
     * 
     * @return The description of this entry's subject.
     */
    String getDesc();
}
