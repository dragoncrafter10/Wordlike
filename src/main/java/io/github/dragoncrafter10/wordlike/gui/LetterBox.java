package io.github.dragoncrafter10.wordlike.gui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class LetterBox extends JTextField {
    private LetterBox next;
    private LetterBox prev;

    // Look Customization
    private Color oofColor = Color.LIGHT_GRAY; // out-of focus color
    private Color infColor = Color.WHITE; // in focus color

    public LetterBox(){
        this(null, null);
    }

    public LetterBox(LetterBox next, LetterBox prev){
        super(1);
        this.next = next;
        this.prev = prev;
        addKeyListener(new TabOnTypeListener());
        addFocusListener(new FocusColorListener());
        setBackground(oofColor);
        setCaretColor(new Color(0, true)); // To make the carat invisible
        setText(" ");
        setCaretPosition(1);
        setHorizontalAlignment(JTextField.CENTER);
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
    
    public LetterBox next(){
        return next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public Color getOutOfFocusColor(){
        return oofColor;
    }

    public Color getInFocusColor(){
        return infColor;
    }

    public void setOutOfFocusColor(Color col){
        infColor = col;
        setCaretColor(infColor); // To make the carat invisible
    }

    public void setInFocusColor(Color col){
        oofColor = col;
    }

    @Override
    public String getText(){
        return super.getText().trim();
    }

    @Override
    public String getText(int offs, int len) throws BadLocationException {
        return super.getText(offs, len).trim();
    }

    /**
     * This listener is designed to give a more natural feel for typing in a series of letter boxes.
     * <p>
     * Upon typing any key, the input focus will be attempted to shift to the next letter box, if
     * it is present. If the key typed is a backspace, however, the *previous* letter box will instead
     * request focus.
     */
    class TabOnTypeListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // TODO: have enter activate the check function
            if(e.isConsumed()) return;

            if(e.getKeyChar() == '\b'){
                setText("");
                
                if(prev != null)
                    prev.requestFocusInWindow();
            } else {
                if(!Character.isLetter(e.getKeyChar())){
                    e.consume();
                    return;
                }

                if(next != null && !e.isShiftDown()) {
                    next.requestFocusInWindow();
                }

                // clear the letterbox so that the letter is replaced
                if(!getText().isEmpty()){
                    setText("");
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Consuming prevents windows from making the 'invalid key-bind' noise when
            // backspacing nothing
            e.consume();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // ignore
        }
    }

    class FocusColorListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            setBackground(infColor);
        }

        @Override
        public void focusLost(FocusEvent e) {
            setBackground(oofColor);
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
