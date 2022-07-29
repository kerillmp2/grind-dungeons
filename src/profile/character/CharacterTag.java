package profile.character;

import utils.RandomController;
import utils.Tag;

public enum CharacterTag implements Tag {
    CRIT_CHANCE("Super power")
    ;

    private final String name;

    CharacterTag(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static CharacterStat getRandom() {
        return RandomController.randomElementOf(CharacterStat.values());
    }
}
