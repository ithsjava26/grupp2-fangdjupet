package fangdjupet.item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AbstractItemLevelTest{

@Test
void shouldThrowExceptionWhenNameIsNull() {


    assertThrows(NullPointerException.class, () ->
            new Weapon(null, 3, 5)
    );
}

@Test
void shouldReturnFormattedText() {


    Weapon weapon = new Weapon("Svärd", 3, 5);


    assertEquals("Svärd (nivå 3)", weapon.toString());
}
}















