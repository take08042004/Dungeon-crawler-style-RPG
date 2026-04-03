package com.example.rpg.character;

public interface Character {
    String getName();

    int getHp();

    void takeDamage(int damage);

    boolean isAlive();

    int attack();

}
