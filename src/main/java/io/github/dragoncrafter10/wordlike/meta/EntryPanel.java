package io.github.dragoncrafter10.wordlike.meta;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EntryPanel extends JPanel {
    public EntryPanel(DictionaryEntry entry) {
        super(null, false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(entry.getName());
        JLabel desc = new JLabel(entry.getDesc());

        this.add(name);
        this.add(desc);
    }
}
