package io.github.dragoncrafter10.wordlike;

import java.util.Objects;

public class Hint {
    private Word word;
    private String guess;

    // NOTE - probably not worth serialization honestly, but whatever. - S.
    private transient String lastWord;
    private transient HintValue[] hints;
    private transient boolean compared;

    /**
     * Construct a {@link Hint} object with a given correct word and guess.
     * 
     * @param word The correct word. As this is a word object, it will stay valid for as long as the object is valid.
     * @param guess A guess. This guess will not update with future guesses.
     * 
     * @throws NullPointerException If any parameters are {@code null}.
     * @throws IllegalArgumentException If {@code word} and {@code guess} are different lengths.
     */
    public Hint(Word word, String guess) {
        this.word = Objects.requireNonNull(word, "Parameter `word` cannot be value `null`");
        this.guess = Objects.requireNonNull(guess, "Parameter `guess` cannot be value `null`").toLowerCase();

        if(word.length() != guess.length()){
            throw new IllegalArgumentException("Parameters `word` and `guess` must have the same length (lengths are: " + word.length() + ", " + guess.length() + ").");
        }

        hints = new HintValue[word.length()];
        lastWord = word.getCurrentWord();
        compared = false;
    }

    /**
     * Get the value of a specific location
     * 
     * @param i index
     * @return A {@link HintValue} object representing how correct that location was.
     */
    public HintValue get(int i){
        ensureCompared();
        return hints[i];
    }

    /**
     * Ensures that this hint has properly compared the hint with the current word
     * 
     * @return
     */
    private boolean ensureCompared(){
        if(!word.getCurrentWord().equals(lastWord)){
            compared = false;
            lastWord = word.getCurrentWord();
        }

        if(compared) return false;

        synchronized (this){
            for(int i = 0; i < hints.length; i++){
                if(word.get(i) == guess.charAt(i))
                    hints[i] = HintValue.CORRECT;
                else if(word.contains(word.get(i)))
                    hints[i] = HintValue.LOCATION;
                else 
                    hints[i] = HintValue.INCORRECT;
            }
        }
        compared = true;
        return true;
    }

    public enum HintValue {
        CORRECT, // green
        LOCATION, // yellow
        INCORRECT, // red/gray
        ;
    }
}
