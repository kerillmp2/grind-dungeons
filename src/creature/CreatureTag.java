package creature;

import utils.Tag;

public enum CreatureTag implements Tag {
    UNDEFINED("UNDEFINED"),

    // Basic
    TURN_PRIORITY("Turn priority"),
    CRIT_CHANCE("Crit chance"),
    CRIT_DAMAGE_MULTIPLIER("Crit damage");

    private final String name;

    CreatureTag(String name) {
        this.name = name;
    }

    public static CreatureTag byName(String name) {
        for (CreatureTag status : CreatureTag.values()) {
            if (status.name.equals(name)) {
                return status;
            }
        }
        return UNDEFINED;
    }

    @Override
    public String getName() {
        return name;
    }
}
