package fangdjupet.combat;

import fangdjupet.contract.GameEvent;
import fangdjupet.contract.GameEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BattleTest {
    private final MeleeAttack melee = new MeleeAttack();
    private List<GameEvent> events;
    private Battle battle;

    @BeforeEach
    void setUp() {
        events = new ArrayList<>();
        battle = new Battle(events::add);
    }

    @Test
    void heroStrikesFirstThenEnemyStrikesBack() {
        Hero hero = new Hero("Hjälten", 30, 10, 2, melee);
        Enemy troll = new Enemy("Trollet", 1, 20, 5, 3, melee);

        battle.fightRound(hero, troll);

        assertEquals(13, troll.getHealth());
        assertEquals(27, hero.getHealth());
        assertEquals(2, events.size());
        assertTrue(events.get(0).message().startsWith("Hjälten"));
    }

    @Test
    void defeatedEnemyDoesNotStrikeBack() {
        Hero hero = new Hero("Hjälten", 30, 10, 2, melee);
        Enemy rat = new Enemy("Råttan", 1, 5, 5, 0, melee);

        battle.fightRound(hero, rat);

        assertEquals(0, rat.getHealth());
        assertEquals(30, hero.getHealth());
        assertEquals(1, events.size());
    }

    @Test
    void heroDefeatedEventIsPublishedWhenHeroFalls() {
        Hero hero = new Hero("Hjälten", 1, 1, 0, melee);
        Enemy dragon = new Enemy("Draken", 5, 50, 10, 0, melee);

        battle.fightRound(hero, dragon);

        assertEquals(0, hero.getHealth());
        assertEquals(GameEventType.HERO_DEFEATED, events.get(events.size() - 1).type());
    }
}
