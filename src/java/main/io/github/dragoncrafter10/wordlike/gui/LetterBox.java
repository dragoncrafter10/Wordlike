package io.github.dragoncrafter10.wordlike.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class LetterBox extends JTextField {
    private LetterBox next;
    private LetterBox prev;

    public LetterBox(){
        this(null, null);
    }

    public LetterBox(LetterBox next, LetterBox prev){
        super(1);
        this.next = next;
        this.prev = prev;
        addKeyListener(new TabOnTypeListener(this));
    }

    public LetterBox next(){
        return next;
    }

    public LetterBox prev(){
        return prev;
    }
    
    public void setNext(LetterBox next){
        this.next = next;
    }

    public void setPrev(LetterBox prev){
        this.prev = prev;
    }

    @Override
    protected Document createDefaultModel(){
        return new SingleLetterDocument();
    }

    /**
     * This listener is designed to give a more natural feel for typing in a series of letter boxes.
     * <p>
     * Upon typing any key, the input focus will be attempted to shift to the next letter box, if
     * it is present. If the key typed is a backspace, however, the *previous* letter box will instead
     * request focus.
     */
    static class TabOnTypeListener implements KeyListener {

        private LetterBox lb;

        public TabOnTypeListener(LetterBox lb){
            this.lb = Objects.requireNonNull(lb);
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO: have enter activate the check function
            if(e.isConsumed()) return;

            if(e.getKeyChar() == '\b'){
                if(lb.prev != null)
                lb.prev.requestFocusInWindow();
            } else {
                if(!Character.isLetter(e.getKeyChar())){
                    e.consume();
                    return;
                }

                if(lb.next != null && !e.isShiftDown()) {
                    lb.next.requestFocusInWindow();
                }

                // clear the letterbox so that the letter is replaced
                if(!lb.getText().isEmpty()){
                    lb.setText("");
                }
            } 
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // ignore
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // ignore
        }

        

    }

    static class SingleLetterDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if(str == null) return;

            super.insertString(offs, str.substring(0, 1).toUpperCase(), a);
        }
    }
}
