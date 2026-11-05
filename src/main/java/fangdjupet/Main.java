package fangdjupet;

import fangdjupet.game.EventLog;
import fangdjupet.game.Game;
import fangdjupet.game.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.addListener(new EventLog());

        try (Scanner scanner = new Scanner(System.in)) {
            Menu menu = new Menu(scanner, game);
            menu.run();
        }
    }
}
