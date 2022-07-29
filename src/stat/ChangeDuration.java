package stat;

import java.io.Serializable;

import battlecore.action.ResolveTime;

public enum ChangeDuration implements Serializable {
    PERMANENT("Permanent"),
    UNTIL_NEXT_TURN("until next turn"),
    UNTIL_BATTLE_END("until battle ends"),
    NONE("none");

    private final String name;

    ChangeDuration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ChangeDuration fromResolveTime(ResolveTime resolveTime) {
        switch (resolveTime) {
            case ON_START_TURN -> {
                return UNTIL_NEXT_TURN;
            }
            case ON_BATTLE_END -> {
                return UNTIL_BATTLE_END;
            }
            default -> {
                return NONE;
            }
        }
    }
}
