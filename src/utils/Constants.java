package utils;

public enum Constants {
    UNDEFINED( 0, 0),

    //View константы
    BATTLE_VIEW_LENGTH(1, 30),
    BATTLE_VIEW_HEIGHT( 2, 8),
    BATTLE_VIEW_OFFSET( 3, 1),
    ITEM_VIEW_LENGTH(4, 27),
    ITEM_VIEW_HEIGHT(5, 13),
    ITEM_VIEW_OFFSET(6, 1),

    BOOK_VIEW_LENGTH(7, 27),
    BOOK_VIEW_HEIGHT(8, 13),
    BOOK_VIEW_OFFSET(9, 1),
    BATTLEFIELD_VIEW_SIZE(10, 155),
    WINDOW_SIZE(11, 155),
    BASIC_OFFSET(12, 3),

    // Shop Item View
    SHOP_ITEM_NAME_LEN(15, 25),
    SHOP_ITEM_COST_LEN(17, 90),
    SHOP_ITEM_RARITY_LEN(18, 11),

    // Creature Stats View
    STATS_NAME_LEN(30, 30),
    STATS_DAMAGE_LEN(31, 30),
    STATS_ARMOR_LEN(32, 30),
    STATS_BARRIER_LEN(33, 30),

    //PRINT константы
    PRINT_MESSAGES_IN_CONTROLLER(100, 1),
    PRINT_ENG_MESSAGES(101, 1),
    PRINT_RU_MESSAGES(102, 0),
    PRINT_CREATURE_STATS_IN_BATTLE(103, 0),

    // ANIMATION константы
    SKIP_ALL_ANIMATIONS(150, 1),

    //Другие игровые константы
    BATTLE_TURN_LIMIT(200, 100)
    ;

    public int id;
    public int value;

    Constants(int id, int value) {
        this.id = id;
        this.value = value;
    }

    public static String getSavePath() {
        return "./profile_data.arph";
    }
}
