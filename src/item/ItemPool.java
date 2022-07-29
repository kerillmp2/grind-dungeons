package item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemPool<T extends Item> {
    Map<Rarity, List<T>> pool;

    public ItemPool(Map<Rarity, List<T>> pool) {
        this.pool = pool;
    }

    public static <T extends Item> ItemPool<T> empty() {
        Map<Rarity, List<T>> pool = new HashMap<>();
        for (Rarity rarity : Rarity.values()) {
            if (rarity != Rarity.UNDEFINED) {
                pool.put(rarity, new ArrayList<>());
            }
        }
        return new ItemPool<>(pool);
    }

    public void add(T item) {
        pool.get(item.getRarity()).add(item);
    }

    public void remove(T item) {
        pool.get(item.getRarity()).remove(item);
    }

    public T pullItemForRarity(Rarity rarity) {
        List<T> items = pool.get(rarity);
        Collections.shuffle(items);
        T item = items.size() > 0 ? items.get(0) : null;
        if (item != null) {
            items.remove(item);
        }
        return item;
    }
}
