package fangdjupet.contract;

/**
 * Strategy-mönstret: olika sätt att anfalla (närstrid, magi, avstånd ...).
 */
public interface AttackStrategy {
    String getName();

    /** Beräknar skadan, men tillämpar den inte. Returnerar aldrig ett negativt värde. */
    int calculateDamage(fangdjupet.contract.Combatant attacker, fangdjupet.contract.Combatant defender);
}
