package battlecore.action;

import java.io.Serializable;

public enum ResolveTime implements Serializable {

    UNDEFINED("UNDEFINED"),
    ON_START_TURN("on turn start"),
    BEFORE_MAIN_PHASE("before main phase"),
    ON_MAIN_PHASE("on main phase"),
    AFTER_MAIN_PHASE("after main phase"),
    ON_END_TURN("on turn end"),
    BEFORE_TAKING_DAMAGE("before taking damage"),
    ON_TAKING_DAMAGE("on taking damage"),
    AFTER_TAKING_DAMAGE("after taking damage"),
    BEFORE_DEALING_DAMAGE("before dealing damage"),
    ON_DEALING_DAMAGE("on dealing damage"),
    AFTER_DEALING_DAMAGE("after dealing damage"),
    BEFORE_ATTACK("before attack"),
    AFTER_ATTACK("after attack"),
    AFTER_ATTACKED("after attacked"),
    ON_BATTLE_START("on battle start"),
    ON_BATTLE_END("on battle end"),
    AFTER_DODGE("after doge"),
    AFTER_HEALING("after healing"),
    AFTER_PICKAXE_HIT("after pickaxe hit"),
    AFTER_MONSTER_KILL("after monster kill");

    public String name;

    ResolveTime(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}