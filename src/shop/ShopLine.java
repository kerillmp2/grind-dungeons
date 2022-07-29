package shop;

import java.util.ArrayList;
import java.util.List;

public class ShopLine {
    public List<ShopItem> items;

    public ShopLine(List<ShopItem> items) {
        this.items = items;
    }

    public boolean isEmpty() {
        for (ShopItem item : items) {
            if (!item.getItem().getName().equals("Empty")
                    && !item.getItem().getName().equals("Пусто")
                    && !item.getItem().getName().equals("Продано")
                    && !item.getItem().getName().equals("Sold")) {
                return false;
            }
        }
        return true;
    }
}
