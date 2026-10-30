package fangdjupet.item;

import fangdjupet.contract.Item;
import fangdjupet.contract.ItemType;

import java.util.List;
import java.util.Objects;


public final class ItemFactory {
    private static final List<String> WEAPON_NAMES =
            List.of("Rostig dolk", "Järnsvärd", "Stålsvärd", "Runsvärd");
    private static final List<String> ARMOR_NAMES =
            List.of("Läderväst", "Ringbrynja", "Stålrustning", "Drakfjällsrustning");
    private static final List<String> POTION_NAMES =
            List.of("Minimjölk", "Mellanmjölk", "Standardmjölk");

    private ItemFactory() {
    }

    public static Item create(ItemType type, int level) {
        Objects.requireNonNull(type, "type får inte vara null");
        return switch (type) {
            case WEAPON -> createWeapon(level);
            case ARMOR -> createArmor(level);
            case POTION -> createPotion(level);
        };
    }

    public static Weapon createWeapon(int level) {
        return new Weapon(nameForLevel(WEAPON_NAMES, level), level, 2 * level);
    }

    public static Armor createArmor(int level) {
        return new Armor(nameForLevel(ARMOR_NAMES, level), level, level);
    }

    public static Potion createPotion(int level) {
        return new Potion(nameForLevel(POTION_NAMES, level), level, 10 * level);
    }


    private static String nameForLevel(List<String> names, int level) {
        if (level < 1) {
            throw new IllegalArgumentException("level måste vara minst 1");
        }
        return names.get(Math.min(level, names.size()) - 1);
    }
}
