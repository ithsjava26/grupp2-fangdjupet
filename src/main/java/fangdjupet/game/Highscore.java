package fangdjupet.game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Topplistan, sparad som en textfil med en rad per resultat: namn;besegrade fiender.
 * Trasiga rader hoppas över, så att en skadad fil aldrig stoppar spelet.
 */
public class Highscore {
    private static final String SEPARATOR = ";";

    private final Path file;

    public Highscore(Path file) {
        this.file = Objects.requireNonNull(file, "file får inte vara null");
    }

    /** Läser alla resultat i den ordning de lades till. En fil som inte finns ger en tom lista. */
    public List<HighscoreEntry> load() throws IOException {
        if (Files.notExists(file)) {
            return List.of();
        }
        List<HighscoreEntry> entries = new ArrayList<>();
        for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
            parse(line).ifPresent(entries::add);
        }
        return entries;
    }

    /** Lägger till ett resultat och sparar hela listan. */
    public void add(HighscoreEntry entry) throws IOException {
        Objects.requireNonNull(entry, "entry får inte vara null");
        List<HighscoreEntry> entries = new ArrayList<>(load());
        entries.add(entry);

        List<String> lines = entries.stream()
                .map(e -> clean(e.heroName()) + SEPARATOR + e.enemiesDefeated())
                .toList();
        Files.write(file, lines, StandardCharsets.UTF_8);
    }

    /** De bästa resultaten, flest besegrade fiender först. Vid lika står det äldsta resultatet först. */
    public List<HighscoreEntry> top(int count) throws IOException {
        if (count < 0) {
            throw new IllegalArgumentException("count får inte vara negativt");
        }
        return load().stream()
                .sorted(Comparator.comparingInt(HighscoreEntry::enemiesDefeated).reversed())
                .limit(count)
                .toList();
    }

    private static Optional<HighscoreEntry> parse(String line) {
        String[] parts = line.split(SEPARATOR, -1);
        if (parts.length != 2) {
            return Optional.empty();
        }
        try {
            return Optional.of(new HighscoreEntry(parts[0], Integer.parseInt(parts[1].trim())));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /** Semikolon i ett namn skulle förstöra radformatet. */
    private static String clean(String name) {
        return name.replace(SEPARATOR, ",");
    }
}
