package io.github.dragoncrafter10.wordlike.meta;

import java.io.Serial;

import io.github.dragoncrafter10.wordlike.Enemy;

public class EnemyEntry implements DictionaryEntry {

    @Serial
    private static final long serialVersionUID = 1l;

    private Enemy enemy;

    public EnemyEntry(Enemy enemy){
        this.enemy = enemy;
    }

    @Override
    public boolean isDiscovered() {
        return enemy != null;
    }

    @Override
    public String getName() {
        return enemy != null ? enemy.getName() : "Locked";
    }

    @Override
    public String getDesc() {
        return enemy != null ? enemy.getModText() : "Defeat this enemy to discover it.";
    }
    
}
