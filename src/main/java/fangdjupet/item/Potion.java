package fangdjupet.item;

import fangdjupet.contract.ItemType;


public class Potion extends AbstractItem {
    private final int healAmount;

    public Potion(String name, int level, int healAmount) {
        super(name, level);
        this.healAmount = requireNotNegative(healAmount, "healAmount");
    }

    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }

    public int getHealAmount() {
        return healAmount;
    }

}
