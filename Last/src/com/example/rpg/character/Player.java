package com.example.rpg.character;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class Player implements Character{
    private String name;
    private int hp;
    private int attackPower;
    private static final String PLAYER_DATA_FILE = "resources/player_data.txt";

    public Player(String name, int hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.attackPower = attackPower;
    }

    public static Optional<Player> loadPlayer() { // ファイル読み込みとStream [cite: 14, 15]
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(PLAYER_DATA_FILE))) {
            return reader.lines() // Streamを使用 [cite: 15]
                    .findFirst()
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            return new Player(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        }
                        return null;
                    });
        } catch (IOException e) {
            System.out.println("プレイヤーデータの読み込みに失敗しました: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void savePlayer() { // ファイル保存 [cite: 14]
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PLAYER_DATA_FILE))) {
            writer.write(this.name + "," + this.hp + "," + this.attackPower);
            System.out.println("プレイヤーデータが保存されました。");
        } catch (IOException e) {
            System.out.println("プレイヤーデータの保存に失敗しました: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(name + "は" + damage + "のダメージを受けました。残りHP: " + hp);
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public int attack() {
        int damage = (int) (Math.random() * attackPower) + attackPower / 2;
        System.out.println(name + "の攻撃！" + damage + "のダメージを与えました。");
        return damage;
    }
}

