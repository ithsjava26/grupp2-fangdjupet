package fangdjupet.combat;

import fangdjupet.contract.AttackStrategy;
import fangdjupet.contract.Equippable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Hero extends BaseCharacter {
    private final List<Equippable> equipment = new ArrayList<>();

    public Hero(String name, int maxHealth, int baseAttack, int baseDefense, AttackStrategy attackStrategy) {
        super(name, maxHealth, baseAttack, baseDefense, attackStrategy);
    }

    public void equip(Equippable item) {
        equipment.add(Objects.requireNonNull(item, "item får inte vara null"));
    }

    public void unequip(Equippable item) {
        equipment.remove(item);
    }

    public List<Equippable> getEquipment() {
        return Collections.unmodifiableList(equipment);
    }

    @Override
    public int getAttack() {
        return super.getAttack() + equipment.stream().mapToInt(Equippable::getAttackBonus).sum();
    }

    @Override
    public int getDefense() {
        return super.getDefense() + equipment.stream().mapToInt(Equippable::getDefenseBonus).sum();
    }
}
