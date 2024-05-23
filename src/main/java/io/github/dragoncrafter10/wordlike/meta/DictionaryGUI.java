package io.github.dragoncrafter10.wordlike.meta;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DictionaryGUI extends JFrame {

    private JPanel enemyEntries;
    private JPanel miscEntries;
    // TODO: relics and stuff
    
    Map<Class<? extends DictionaryEntry>, Container> rows;

    public DictionaryGUI() {
        super("Dictionary");
        setContentPane(new JPanel(null, false));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        enemyEntries = new JPanel(false);

        add(enemyEntries);

        rows = new HashMap<>();
        rows.put(EnemyEntry.class, enemyEntries);
    }

    public synchronized void updateKnowledge(Dictionary data){
        invalidate();
        for (DictionaryEntry entry : data) {
            Class<? extends DictionaryEntry> cls = entry.getClass();

            EntryPanel entryPanel = new EntryPanel(entry);
            
            Container con = rows.getOrDefault(cls, miscEntries);

            con.remove(entryPanel); // prevent duplicates
            con.add(entryPanel); // does NOT remove elements that are missing from the given data
        }
        validate();
    }
}
