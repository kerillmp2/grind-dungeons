package dungeon;

import item.Item;

public class LootItem {
    private final Item item;
    private final double chance;

    public LootItem(Item item, double chance) {
        this.item = item;
        this.chance = chance;
    }

    public Item getItem() {
        return item;
    }

    public double getChance() {
        return chance;
    }
}
