package io.github.dragoncrafter10.wordlike.gui;

import java.awt.Container;

public class WordBox extends Container {

    private transient int width;

    private transient LetterBox head;

    public WordBox(int width){
        super();
        
        if(width <= 0){
            throw new IllegalArgumentException("Parameter `width` cannot be less than 1; was `" + width + "`");
        }

        this.width = width;

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

    public void addAllTo(Container comp){
        LetterBox lb = head;
        while(lb != null){
            comp.add(lb);
            lb = lb.next();
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

    
}
