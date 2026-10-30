package fangdjupet.item;
import fangdjupet.contract.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

    class ArmorTest {

        @Test
        void shouldReturnCorrectValues() {


            Armor armor = new Armor("Järnrustning", 3, 5);


            assertEquals("Järnrustning", armor.getName());
            assertEquals(3, armor.getLevel());
            assertEquals(5, armor.getDefenseBonus());
            assertEquals(0, armor.getAttackBonus());
        }

        @Test
        void shouldThrowExceptionWhenDefenseBonusIsNegative() {


            assertThrows(IllegalArgumentException.class, () ->
                    new Armor("Järnrustning", 3, -1)
            );
        }
    }

