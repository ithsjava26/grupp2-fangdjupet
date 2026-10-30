package fangdjupet.combat;

import fangdjupet.contract.AttackStrategy;

public class Enemy extends BaseCharacter {
    private final int level;

    public Enemy(String name, int level, int maxHealth, int baseAttack, int baseDefense,
                 AttackStrategy attackStrategy) {
        super(name, maxHealth, baseAttack, baseDefense, attackStrategy);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
