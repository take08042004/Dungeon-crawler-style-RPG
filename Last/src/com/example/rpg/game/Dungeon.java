package com.example.rpg.game;

import com.example.rpg.character.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    private List<Enemy> enemies;
    private Enemy boss;

    public Dungeon() {
        enemies = new ArrayList<>();
        // ザコ敵の生成
        enemies.add(new Enemy("ゴブリン", 30, 5));
        enemies.add(new Enemy("スライム", 20, 3));
        enemies.add(new Enemy("オーク", 40, 7));
        // ボスの生成 [cite: 21]
        boss = new Enemy("ドラゴン", 100, 15);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Enemy getBoss() {
        return boss;
    }
}
