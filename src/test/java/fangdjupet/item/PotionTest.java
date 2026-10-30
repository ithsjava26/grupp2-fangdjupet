package fangdjupet.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class  PotionTest{

@Test

    void ShouldReturnCorrectValues(){

    Potion potion = new Potion("Mjölk",3, 10);

    assertEquals("Mjölk", potion.getName());
    assertEquals(3, potion.getLevel());
    assertEquals(10, potion.getHealAmount());
}

    void ShouldThrowExceptionWhenHealAmountIsNegative(){

    assertThrows(IllegalArgumentException.class, () ->
        new Potion("Mjölk", 5, -2)
    );
    }


}
