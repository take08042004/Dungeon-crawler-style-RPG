package com.example.rpg.game;

import com.example.rpg.character.Player;
import com.example.rpg.character.Enemy;
import com.example.rpg.exception.InvalidInputException; // 例外の使用 [cite: 12]

import java.util.InputMismatchException; // 例外の使用 [cite: 12]
import java.util.Scanner;

public class Game {
    private Player player;
    private Dungeon dungeon;
    private Scanner scanner;
    private GameState state;

    public Game(Scanner scanner) {
        this.scanner = scanner;
        this.state = GameState.PLAYER_CREATION;
    }

    public void start() {
        System.out.println("--- ダンジョン探索RPG ---");
        System.out.println("既存のプレイヤーで開始しますか？ (y/n)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("y")) {
            player = Player.loadPlayer().orElse(null);
            if (player != null) {
                System.out.println(player.getName() + "でゲームを再開します。");
            } else {
                System.out.println("プレイヤーデータが見つかりませんでした。新しいプレイヤーを作成します。");
                createPlayer();
            }
        } else {
            createPlayer();
        }

        dungeon = new Dungeon();
        state = GameState.EXPLORING_DUNGEON;
        runGameLoop();
    }

    private void createPlayer() {
        System.out.print("プレイヤーの名前を入力してください: ");
        String name = scanner.nextLine();
        player = new Player(name, 100, 10); // 初期HPと攻撃力
        System.out.println(player.getName() + "が誕生しました！");
    }

    private void runGameLoop() {
        while (state != GameState.GAME_OVER && state != GameState.GAME_CLEAR) {
            switch (state) {
                case EXPLORING_DUNGEON:
                    exploreDungeon();
                    break;
                case BATTLING_ENEMY:
                    break;
            }
        }

        if (state == GameState.GAME_CLEAR) {
            System.out.println("--------------------");
            System.out.println("ダンジョンをクリアしました！おめでとうございます！"); // ゲームクリア [cite: 23]
            System.out.println("--------------------");
        } else if (state == GameState.GAME_OVER) {
            System.out.println("--------------------");
            System.out.println("HPが0になりました。ゲームオーバーです..."); // ゲームオーバー [cite: 22]
            System.out.println("--------------------");
        }
        player.savePlayer();
    }

    private void exploreDungeon() {
        if (dungeon.getEnemies().isEmpty()) {
            System.out.println("ダンジョンの奥深くに進んでいます...");
            System.out.println("ついにボスの部屋に到達しました！");
            state = GameState.BATTLING_BOSS;
            battle(dungeon.getBoss());
        } else {
            System.out.println("ダンジョンを進みます...");
            Enemy currentEnemy = dungeon.getEnemies().remove(0);
            System.out.println(currentEnemy.getName() + "と遭遇しました！"); // 敵との遭遇 [cite: 20]
            state = GameState.BATTLING_ENEMY;
            battle(currentEnemy);
        }
    }

    private void battle(Enemy enemy) {
        System.out.println("--- 戦闘開始: " + enemy.getName() + " ---");
        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n" + player.getName() + " HP: " + player.getHp() + " | " + enemy.getName() + " HP: " + enemy.getHp());
            System.out.println("行動を選択してください:");
            System.out.println("1. 攻撃");
            System.out.println("2. 逃げる (成功率低)");

            try {
                int choice = getUserChoice();
                switch (choice) {
                    case 1:
                        enemy.takeDamage(player.attack());
                        break;
                    case 2:
                        if (Math.random() < 0.3) { // 逃走成功率30%
                            System.out.println("うまく逃げ切れた！");
                            state = GameState.EXPLORING_DUNGEON;
                            return;
                        } else {
                            System.out.println("逃げられなかった！");
                        }
                        break;
                    default:
                        throw new InvalidInputException("無効な選択です。1か2を入力してください。"); // 例外の宣言と使用 [cite: 12]
                }
            } catch (InvalidInputException | InputMismatchException e) {
                System.out.println("エラー: " + e.getMessage());
                scanner.nextLine(); // 無効な入力をクリア
                continue;
            }

            if (enemy.isAlive()) {
                player.takeDamage(enemy.attack());
            }
        }

        if (!player.isAlive()) {
            state = GameState.GAME_OVER; // ゲームオーバー条件 [cite: 22]
        } else if (!enemy.isAlive() && enemy == dungeon.getBoss()) {
            state = GameState.GAME_CLEAR; // ボス撃破でゲームクリア [cite: 23]
        } else if (!enemy.isAlive()) {
            System.out.println(enemy.getName() + "を倒した！");
            state = GameState.EXPLORING_DUNGEON;
        }
    }

    private int getUserChoice() throws InvalidInputException { // 例外の宣言と使用 [cite: 12]
        System.out.print("選択: ");
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine(); // 改行を消費
            return choice;
        } else {
            scanner.nextLine(); // 無効な入力をクリア
            throw new InvalidInputException("数値で入力してください。"); // 例外の宣言と使用 [cite: 12]
        }
    }
}
