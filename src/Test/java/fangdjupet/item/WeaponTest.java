package fangdjupet.item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeaponTest {
    @Test
    void getLevelReturnsCorrectLevel() {

        Weapon weapon = new Weapon("Svärd", 3, 5);

        int level = weapon.getLevel();

        assertEquals(3, level);
    }

    @Test

    void getNameReturnsCorrectName(){
        Weapon weapon = new Weapon("Svärd", 3, 5);

        String name = weapon.getName();

        assertEquals("Svärd", name);

    }
}
