package com.example.rpg.character;

public class Enemy implements Character{
    protected String name;
    protected int hp;
    protected int attackPower;

    public Enemy(String name, int hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.attackPower = attackPower;
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
