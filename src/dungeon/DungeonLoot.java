package dungeon;

import item.Item;

public class DungeonLoot {
    private final Item item;

    public DungeonLoot(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
