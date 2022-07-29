package battlecore;

import profile.character.CharacterStat;
import stat.StatsContainer;
import utils.Tag;

public enum DamageType implements Tag {
    PHYSICAL("Physical",
            "Phys",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.STRENGTH) * 0.8 + statsContainer.get(CharacterStat.AGILITY) * 0.2) * Math.min(0.6 + statsContainer.get(CharacterStat.WISDOM) / 300.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.STRENGTH) * 0.8 + statsContainer.get(CharacterStat.AGILITY) * 0.2)
    ),
    FIRE("Fire",
            "Fire",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE) * 0.7 + statsContainer.get(CharacterStat.AGILITY) * 0.2 + statsContainer.get(CharacterStat.STRENGTH) * 0.2) * Math.min(0.5 + statsContainer.get(CharacterStat.WISDOM) / 300.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.INTELLIGENCE) * 0.7 + statsContainer.get(CharacterStat.AGILITY) * 0.2 + statsContainer.get(CharacterStat.STRENGTH) * 0.2)
    ),
    WATER("Water",
            "Water",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE) * 0.8 + statsContainer.get(CharacterStat.AGILITY) * 0.1) * Math.min(0.7 + statsContainer.get(CharacterStat.WISDOM) / 200.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.INTELLIGENCE) * 0.8 + statsContainer.get(CharacterStat.AGILITY) * 0.1)
    ),
    EARTH("Earth",
            "Earth",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE) * 0.5 + statsContainer.get(CharacterStat.STRENGTH) * 0.5) * Math.min(0.6 + statsContainer.get(CharacterStat.WISDOM) / 300.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.INTELLIGENCE) * 0.5 + statsContainer.get(CharacterStat.STRENGTH) * 0.5)
    ),
    AIR("Air",
            "Air",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE) * 0.7 + statsContainer.get(CharacterStat.AGILITY) * 0.4) * Math.min(0.5 + statsContainer.get(CharacterStat.WISDOM) / 300.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.INTELLIGENCE) * 0.7 + statsContainer.get(CharacterStat.AGILITY) * 0.4)
    ),
    POISON("Poison",
            "Poison",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE)) * Math.min(0.8 + statsContainer.get(CharacterStat.WISDOM) / 300.0, 1)),
            statsContainer -> 1 + statsContainer.get(CharacterStat.INTELLIGENCE)
    ),
    INSECT("Insect",
            "Insect",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE) * 0.6 + statsContainer.get(CharacterStat.AGILITY) * 0.6) * Math.min(0.4 + statsContainer.get(CharacterStat.WISDOM) / 300.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.INTELLIGENCE) * 0.6 + statsContainer.get(CharacterStat.AGILITY) * 0.6)
    ),
    LIGHTNING("Lightning",
            "Lightning",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE) * 0.45 + statsContainer.get(CharacterStat.AGILITY) * 0.5) * Math.min(0.8 + statsContainer.get(CharacterStat.WISDOM) / 200.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.INTELLIGENCE) * 0.45 + statsContainer.get(CharacterStat.AGILITY) * 0.5)
    ),
    DARK("Dark",
            "Dark",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE) * 0.25 + statsContainer.get(CharacterStat.STRENGTH) * 0.55 + statsContainer.get(CharacterStat.AGILITY) * 0.25) * Math.min(0.7 + statsContainer.get(CharacterStat.WISDOM) / 300.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.INTELLIGENCE) * 0.25 + statsContainer.get(CharacterStat.STRENGTH) * 0.55 + statsContainer.get(CharacterStat.AGILITY) * 0.25)
    ),
    LIGHT("Light",
            "Light",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.INTELLIGENCE) * 0.34 + statsContainer.get(CharacterStat.STRENGTH) * 0.33 + statsContainer.get(CharacterStat.AGILITY) * 0.33) * Math.min(0.7 + statsContainer.get(CharacterStat.WISDOM) / 200.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.INTELLIGENCE) * 0.34 + statsContainer.get(CharacterStat.STRENGTH) * 0.33 + statsContainer.get(CharacterStat.AGILITY) * 0.33)
    ),
    MINING("Mining",
            "Mining",
            statsContainer -> 1 + (int) ((statsContainer.get(CharacterStat.STRENGTH) * 0.8 + statsContainer.get(CharacterStat.AGILITY) * 0.3) * Math.min(0.6 + statsContainer.get(CharacterStat.WISDOM) / 100.0, 1)),
            statsContainer -> 1 + (int) (statsContainer.get(CharacterStat.STRENGTH) * 0.8 + statsContainer.get(CharacterStat.AGILITY) * 0.3),
            true
    ),
    ;

    private final String name;
    private final String shortName;
    private final Formula minDamageFormula;
    private final Formula maxDamageFormula;
    private final boolean isNonCreatureType;

    DamageType(String name, String shortName, Formula minDamageFormula, Formula maxDamageFormula) {
        this.name = name;
        this.shortName = shortName;
        this.minDamageFormula = minDamageFormula;
        this.maxDamageFormula = maxDamageFormula;
        this.isNonCreatureType = false;
    }

    DamageType(String name, String shortName, Formula minDamageFormula, Formula maxDamageFormula, boolean isNonCreatureType) {
        this.name = name;
        this.shortName = shortName;
        this.minDamageFormula = minDamageFormula;
        this.maxDamageFormula = maxDamageFormula;
        this.isNonCreatureType = isNonCreatureType;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int calculateMinDamage(StatsContainer<CharacterStat, ? extends Tag> statsContainer) {
        return minDamageFormula.calculate(statsContainer);
    }

    public int calculateMaxDamage(StatsContainer<CharacterStat, ? extends Tag> statsContainer) {
        return maxDamageFormula.calculate(statsContainer);
    }

    public boolean isNonCreatureType() {
        return isNonCreatureType;
    }

    public interface Formula {
        int calculate(StatsContainer<CharacterStat, ? extends Tag> statsContainer);
    }
}
