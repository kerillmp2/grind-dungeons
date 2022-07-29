package profile;

import item.Item;
import item.ItemFactory;
import profile.character.CharacterStat;
import utils.Tag;

public enum ProfileTagEnum implements Tag {
    UNDEFINED("UNDEFINED"),

 //------------------------------------------------------------------------------------------------------------------
 // SHOP LEVELS
 //------------------------------------------------------------------------------------------------------------------
    SLIME_SHOP_LEVEL("Slime shop"),
    ORE_SHOP_LEVEL("Ore shop"),
 //------------------------------------------------------------------------------------------------------------------

 //------------------------------------------------------------------------------------------------------------------
 // DUNGEON LEVELS
 //------------------------------------------------------------------------------------------------------------------
    SLIME_DUNGEON_LEVEL("Slime dungeon"),
    MINE_LEVEL("Mine"),
 //------------------------------------------------------------------------------------------------------------------

 //------------------------------------------------------------------------------------------------------------------
 // SHOP ITEMS
 //------------------------------------------------------------------------------------------------------------------
    GREEN_MOUSSE_BOUGHT("Green mousses bought"),
    GREEN_MOUSSE_STR_BOUGHT("Green mousses bought"),
    GREEN_MOUSSE_AGI_BOUGHT("Green mousses bought"),
    GREEN_MOUSSE_WIS_BOUGHT("Green mousses bought"),
    GREEN_MOUSSE_INT_BOUGHT("Green mousses bought"),
    GREEN_MOUSSE_CONS_BOUGHT("Green mousses bought"),
    GREEN_MOUSSE_LUCK_BOUGHT("Green mousses bought"),
 //------------------------------------------------------------------------------------------------------------------


 //------------------------------------------------------------------------------------------------------------------
 // CONVERTERS
 //------------------------------------------------------------------------------------------------------------------
 // SLIME CONVERTER
    SLIME_CONVERTER_BOUGHT("Slime converter bought"),
    GREEN_SLIME_VALUE("Green slime value"),
    YELLOW_SLIME_VALUE("Yellow slime value"),
    RED_SLIME_VALUE("Red slime value"),
    PINK_SLIME_VALUE("Pink slime value"),
 //------------------------------------------------------------------------------------------------------------------

 //------------------------------------------------------------------------------------------------------------------
 // BOOKS
 //------------------------------------------------------------------------------------------------------------------
    HAS_GREEN_YAMMY_BOOK("Green yammy book"),
    HAS_POISON_SPIT_BOOK("Poison spit book"),
    HAS_PINKY_DANCE_BOOK("Pinky dance book"),
    HAS_CASUAL_COOPER_MINER_BOOK("Casual cooper miner book"),
 //------------------------------------------------------------------------------------------------------------------
 // FARM BOOKS
    HAS_GREEN_SLIME_FRAMER("Green slime farmer book"),
 //------------------------------------------------------------------------------------------------------------------

 //------------------------------------------------------------------------------------------------------------------
 // CHALLENGES
 //------------------------------------------------------------------------------------------------------------------
    CHALLENGE_THE_HORDE("The Horde", true),
    CHALLENGE_ONE_PUNCH_MAN("One Punch Man", true),
    CHALLENGE_TANK("Tank", true),
 //------------------------------------------------------------------------------------------------------------------


    ;

    private final String name;
    private final boolean challengeTag;

    ProfileTagEnum(String name) {
        this.name = name;
        this.challengeTag = false;
    }

    ProfileTagEnum(String name, boolean challengeTag) {
        this.name = name;
        this.challengeTag = challengeTag;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isChallengeTag() {
        return challengeTag;
    }

    public static ProfileTagEnum withStat(ProfileTagEnum tag, CharacterStat stat) {
        if (tag == GREEN_MOUSSE_BOUGHT) {
            switch (stat) {
                case STRENGTH -> {
                    return GREEN_MOUSSE_STR_BOUGHT;
                }
                case LUCK -> {
                    return GREEN_MOUSSE_LUCK_BOUGHT;
                }
                case WISDOM -> {
                    return GREEN_MOUSSE_WIS_BOUGHT;
                }
                case INTELLIGENCE -> {
                    return GREEN_MOUSSE_INT_BOUGHT;
                }
                case AGILITY -> {
                    return GREEN_MOUSSE_AGI_BOUGHT;
                }
                case CONSTITUTION -> {
                    return GREEN_MOUSSE_CONS_BOUGHT;
                }
            }
        }
        return UNDEFINED;
    }

    public static ProfileTagEnum getValueTagForItem(Item item) {
        if (item.getName().equals(ItemFactory.GREEN_SLIME.getName())) {
            return GREEN_SLIME_VALUE;
        }
        if (item.getName().equals(ItemFactory.YELLOW_SLIME.getName())) {
            return YELLOW_SLIME_VALUE;
        }
        if (item.getName().equals(ItemFactory.RED_SLIME.getName())) {
            return RED_SLIME_VALUE;
        }
        if (item.getName().equals(ItemFactory.PINK_SLIME.getName())) {
            return PINK_SLIME_VALUE;
        }
        return UNDEFINED;
    }
}
