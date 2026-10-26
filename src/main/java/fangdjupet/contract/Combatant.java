package fangdjupet.contract;

/**
 * Något som kan delta i en strid: hjälte eller fiende.
 */
public interface Combatant {
    String getName();
    int getHealth();
    int getMaxHealth();
    int getAttack();
    int getDefense();

    /** Minskar hälsan med angiven mängd, dock aldrig under 0. */
    void takeDamage(int amount);

    /** Anfaller målet och returnerar skadan som gjordes. */
    int attack(Combatant target);

    default boolean isAlive() {
        return getHealth() > 0;
    }
}
