package fangdjupet.contract;

/**
 * Ett föremål som hjälten kan bära och som påverkar stridsvärdena.
 * Kopplingen mellan föremålsmodulen och stridsmodulen.
 */
public interface Equippable extends fangdjupet.contract.Item {
    int getAttackBonus();
    int getDefenseBonus();
}
