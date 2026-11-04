package fangdjupet.combat;

import fangdjupet.contract.Combatant;
import fangdjupet.contract.GameEvent;
import fangdjupet.contract.GameEventListener;
import fangdjupet.contract.GameEventType;

import java.util.Objects;

/**
 * Turordning i strid: hjälten slår först, sedan fienden om den lever.
 */
public class Battle {
    private final GameEventListener events;

    public Battle(GameEventListener events) {
        this.events = Objects.requireNonNull(events, "events får inte vara null");
    }

    public void fightRound(Combatant hero, Combatant enemy) {
        strike(hero, enemy);
        if (!enemy.isAlive()) {
            return;
        }
        strike(enemy, hero);
        if (!hero.isAlive()) {
            events.onEvent(new GameEvent(GameEventType.HERO_DEFEATED,
                    hero.getName() + " har fallit i Fångdjupet."));
        }
    }

    private void strike(Combatant attacker, Combatant defender) {
        int damage = attacker.attack(defender);
        events.onEvent(new GameEvent(GameEventType.ATTACK,
                attacker.getName() + " gör " + damage + " i skada på " + defender.getName()
                        + " (hälsa " + defender.getHealth() + "/" + defender.getMaxHealth() + ")"));
    }
}
