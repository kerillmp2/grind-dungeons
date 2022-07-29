package battlecore.action;

import utils.Tag;

public enum ActionTag implements Tag {
    UNDEFINED("UNDEFINED"),

    // TARGETS
    TARGET_RANDOM_ALIVE_ENEMY("TARGET_RANDOM_ALIVE_ENEMY"),
    TARGET_SELF("TARGET_SELF"),
    TARGET_CHARACTER_CREATURE("TARGET_CHARACTER_CREATURE"),

    // HEALING
    HEAL_FLOAT("HEAL_FLOAT"),
    HEAL_PERCENTAGE_OF_MAX("HEAL_PERCENTAGE_OF_MAX"),
    HEAL_PERCENTAGE_OF_MISSING("HEAL_PERCENTAGE_OF_MISSING"),
    ;

    private String name;

    ActionTag(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
