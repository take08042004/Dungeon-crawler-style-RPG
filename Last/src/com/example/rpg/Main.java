package com.example.rpg;

import com.example.rpg.game.Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        game.start();
        scanner.close();
    }
}
