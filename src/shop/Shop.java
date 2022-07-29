package shop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import item.Item;
import utils.HasName;

public class Shop implements HasName {

    private final List<ShopItem> itemPool;
    private final List<ShopItem> additionalOptions;
    private final Set<Item> currencies;
    private final String name;
    private int shopLevel;

    private Shop(List<ShopItem> itemPool, List<ShopItem> additionalOptions, Set<Item> currencies, String name, int shopLevel) {
        this.itemPool = itemPool;
        this.additionalOptions = additionalOptions;
        this.currencies = currencies;
        this.name = name;
        this.shopLevel = shopLevel;
    }

    public static Shop withParams(String name, List<ShopItem> itemPool, List<ShopItem> additionalOptions, int shopLevel) {
        return new Shop(itemPool, additionalOptions, generateCurrencies(itemPool, additionalOptions), name, shopLevel);
    }

    public void remove(ShopItem shopItem) {
        itemPool.remove(shopItem);
        additionalOptions.remove(shopItem);
    }

    public List<ShopItem> getAdditionalOptions() {
        return additionalOptions;
    }

    public String getName() {
        return name;
    }

    public int getShopLevel() {
        return shopLevel;
    }

    public List<ShopItem> getItemPool() {
        return itemPool;
    }

    public List<ShopItem> getAllItems() {
        List<ShopItem> allItems = new ArrayList<>(itemPool);
        allItems.addAll(additionalOptions);
        return allItems;
    }

    public void incrementShopLevel() {
        shopLevel++;
    }

    private static Set<Item> generateCurrencies(List<ShopItem> itemPool, List<ShopItem> additionalOptions) {
        Set<Item> currencies = new HashSet<>();
        for (ShopItem shopItem : itemPool) {
            currencies.addAll(shopItem.getCosts().keySet());
        }
        for (ShopItem shopItem : additionalOptions) {
            currencies.addAll(shopItem.getCosts().keySet());
        }
        return currencies;
    }

    public Set<Item> getCurrencies() {
        return currencies;
    }
}
