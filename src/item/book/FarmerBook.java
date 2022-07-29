package item.book;

import java.util.Map;

import battlecore.action.Action;
import item.Item;
import item.Rarity;

public class FarmerBook extends Book {
    private final Item farmItem;

    public FarmerBook(String name, String description, Rarity rarity, int value, Map<Item, Integer> upgradeCosts, BookDescription bookDescription, BookType bookType, int level, boolean equiped, Action action, Item farmItem) {
        super(name, description, rarity, value, upgradeCosts, bookDescription, bookType, level, equiped, action);
        this.farmItem = farmItem;
    }

    @Override
    public Book upgraded() {
        return BookFactory.getFramerBook(farmItem, level + 1);
    }

    public Item getFarmItem() {
        return farmItem;
    }
}
