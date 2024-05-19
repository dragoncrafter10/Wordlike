package io.github.dragoncrafter10.wordlike.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Spliterator;

public class WordBox extends Container implements Iterable<LetterBox> {

    private transient int width;

    private transient LetterBox head;

    public WordBox(int width){
        super();
        
        if(width <= 0){
            throw new IllegalArgumentException("Parameter `width` cannot be less than 1; was `" + width + "`");
        }

        this.width = width;

        this.setLayout(new GridLayout(0, width));

        head = new LetterBox();
        
		LetterBox[] lbs = new LetterBox[width];
		lbs[0] = head;
        this.add(head);
		for(int i = 1; i < lbs.length; i++){
			lbs[i] = new LetterBox(null, lbs[i - 1]);
			lbs[i - 1].setNext(lbs[i]);
            this.add(lbs[i]);
		}
    }

    public String getText() {
        StringBuilder sb = new StringBuilder(width);

        LetterBox lb = head;
        while(lb != null){
            sb.append(lb.getText());
            lb = lb.next();
        }

        return sb.toString();
    }

    public void clear() {
        LetterBox lb = head;
        while(lb != null){
            lb.setText("");
            lb = lb.next();
        }
    }

    @Override
    public Iterator<LetterBox> iterator() {
        return new Iterator<LetterBox>() {
            LetterBox lb = head;

            @Override
            public boolean hasNext() {
                return lb != null;
            }

            @Override
            public LetterBox next() {
                final LetterBox ret = lb;
                lb = lb.next();
                return ret;
            }
            
        };
    }

    @Override
    public Spliterator<LetterBox> spliterator() {
        // TODO: potential for better impl
        return Iterable.super.spliterator();
    }

    
}
