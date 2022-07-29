package profile.inventory;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import item.Item;

public class Inventory implements Serializable {
    private final Map<Item, Integer> items;

    public Inventory(Map<Item, Integer> items) {
        this.items = items;
    }

    public static Inventory empty() {
        return new Inventory(new HashMap<>());
    }

    public int get(Item item) {
        return items.getOrDefault(item, 0);
    }

    public void put(Item item, int amount) {
        items.put(item, get(item) + amount);
    }

    public void put(Item item) {
        put(item, 1);
    }

    public boolean pull(Item item, int amount) {
        if (has(item, amount)) {
            items.put(item, get(item) - amount);
            if (items.get(item) <= 0) {
                items.remove(item);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean pull(Item item) {
        return pull(item, 1);
    }

    public boolean has(Item item, int amount) {
        return get(item) >= amount;
    }

    public boolean has(Item item) {
        return has(item, 1);
    }

    public Map<Item, Integer> getItems() {
        return items;
    }
}
