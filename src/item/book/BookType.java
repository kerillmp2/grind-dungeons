package item.book;

import java.io.Serializable;

public record BookType(String name) implements Serializable {
    public static final BookType UNDEFINED = new BookType("UNDEFINED");
    public static final BookType GREEN_YAMMY_BOOK = new BookType("GREEN_YAMMY_BOOK");
    public static final BookType POISON_SPIT_BOOK = new BookType("POISON_SPIT_BOOK");
    public static final BookType PINKY_DANCE_BOOK = new BookType("PINKY_DANCE_BOOK");
    // ITEM_FARMER

    // MINER
    public static final BookType CASUAL_COOPER_MINER = new BookType("CASUAL_COOPER_MINER");

    public static BookType farmer(String itemName) {
        return new BookType(itemName.toUpperCase() + " FARMER BOOK");
    }
}
