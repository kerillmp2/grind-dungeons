package profile.character;

import utils.RandomController;
import utils.Tag;

public enum CharacterStat implements Tag {
    STRENGTH("Strength", "STR"),
    AGILITY("Agility", "AGI"),
    CONSTITUTION("Constitution", "CON"),
    INTELLIGENCE("Intelligence", "INT"),
    WISDOM("Wisdom", "WIS"),
    LUCK("Luck", "LUC");

    private final String name;
    private final String shortName;

    CharacterStat(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public static CharacterStat getRandom() {
        return RandomController.randomElementOf(CharacterStat.values());
    }
}
