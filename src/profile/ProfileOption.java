package profile;

import utils.Tag;

public enum ProfileOption implements Tag {
    DUNGEONS("Dungeons"),
    CHALLENGES("Challenges"),
    SHOPS("Shops"),
    INVENTORY("Inventory"),
    EQUIPMENT("Equipment"),
    CONVERTERS("Converters"),
    LIBRARY("Library"),
    STATS("Stats"),
    ;

    private final String name;

    ProfileOption(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
