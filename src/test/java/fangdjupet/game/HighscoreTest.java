package fangdjupet.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HighscoreTest {
    @TempDir
    Path tempDir;

    private Path file;
    private Highscore highscore;

    @BeforeEach
    void setUp() {
        file = tempDir.resolve("fangdjupet.highscore");
        highscore = new Highscore(file);
    }

    @Test
    void missingFileGivesEmptyList() throws IOException {
        assertTrue(highscore.load().isEmpty());
    }

    @Test
    void addedEntryCanBeLoadedAgain() throws IOException {
        highscore.add(new HighscoreEntry("Peter", 3));

        assertEquals(List.of(new HighscoreEntry("Peter", 3)), highscore.load());
    }

    @Test
    void entriesSurviveANewHighscoreObject() throws IOException {
        highscore.add(new HighscoreEntry("Peter", 3));

        Highscore reopened = new Highscore(file);

        assertEquals(1, reopened.load().size());
    }

    @Test
    void topIsSortedWithMostEnemiesFirst() throws IOException {
        highscore.add(new HighscoreEntry("Jalena", 1));
        highscore.add(new HighscoreEntry("Peter", 3));
        highscore.add(new HighscoreEntry("Stefan", 2));

        List<HighscoreEntry> top = highscore.top(3);

        assertEquals(List.of("Peter", "Stefan", "Jalena"),
                top.stream().map(HighscoreEntry::heroName).toList());
    }

    @Test
    void topReturnsAtMostRequestedCount() throws IOException {
        highscore.add(new HighscoreEntry("A", 1));
        highscore.add(new HighscoreEntry("B", 2));
        highscore.add(new HighscoreEntry("C", 3));

        assertEquals(2, highscore.top(2).size());
    }

    @Test
    void equalScoresKeepTheOldestFirst() throws IOException {
        highscore.add(new HighscoreEntry("Först", 2));
        highscore.add(new HighscoreEntry("Sen", 2));

        assertEquals("Först", highscore.top(2).get(0).heroName());
    }

    @Test
    void corruptLinesAreSkipped() throws IOException {
        Files.writeString(file, "Peter;3\ninte en rad\nStefan;många\n;2\nJalena;1\n");

        assertEquals(2, highscore.load().size());
    }

    @Test
    void semicolonInNameDoesNotBreakTheFile() throws IOException {
        highscore.add(new HighscoreEntry("Pe;ter", 3));

        assertEquals("Pe,ter", highscore.load().get(0).heroName());
    }

    @Test
    void negativeCountThrows() {
        assertThrows(IllegalArgumentException.class, () -> highscore.top(-1));
    }

    @Test
    void entryRejectsBlankNameAndNegativeScore() {
        assertThrows(IllegalArgumentException.class, () -> new HighscoreEntry(" ", 1));
        assertThrows(IllegalArgumentException.class, () -> new HighscoreEntry("Peter", -1));
    }
}
