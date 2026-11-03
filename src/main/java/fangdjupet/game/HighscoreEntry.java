package fangdjupet.game;

import java.util.Objects;

/**
 * Ett resultat i topplistan: hjältens namn och hur många fiender som besegrades.
 */
public record HighscoreEntry(String heroName, int enemiesDefeated) {
    public HighscoreEntry {
        Objects.requireNonNull(heroName, "heroName får inte vara null");
        if (heroName.isBlank()) {
            throw new IllegalArgumentException("heroName får inte vara tomt");
        }
        if (enemiesDefeated < 0) {
            throw new IllegalArgumentException("enemiesDefeated får inte vara negativt");
        }
    }
}
