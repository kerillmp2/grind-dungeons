package viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import item.Item;
import profile.inventory.Inventory;
import shop.Shop;
import shop.ShopItem;
import utils.Constants;

public class ShopViewer extends Viewer {

    private static final int ITEM_OFFSET = Constants.BASIC_OFFSET.value;
    private static final int ITEM_HEIGHT = Constants.ITEM_VIEW_HEIGHT.value;

    public static String getPageHeader(String name, int level, boolean hasPrevPage, boolean hasNextPage) {
        Window header = new Window();
        header.lineWithAngles();
        String left = "";
        String middle = name + " [Level " + level + "]";
        String right = "";
        if (hasPrevPage) {
            left = "<-- [6]";
        }
        if (hasNextPage) {
            right = "[7] -->";
        }
        header.leftMiddleRight(left, middle, right);
        header.lineWithAngles();
        return header.getView();
    }

    public static String getPageView(Shop shop, Inventory playerInventory, List<ShopItem> items, boolean hasPrevPage, boolean hasNextPage) {
        Window window = new Window();
        window.add(getPageHeader(shop.getName(), shop.getShopLevel(), hasPrevPage, hasNextPage));

        List<List<String>> splittedItemViews = new ArrayList<>();

        for (ShopItem item : items) {
            splittedItemViews.add(Arrays.stream(ItemViewer.getItemView(item, 0).split("\n")).collect(Collectors.toList()));
        }

        for (int i = 0; i < ITEM_HEIGHT; i++) {
            StringBuilder inline = new StringBuilder();
            for (List<String> itemView : splittedItemViews) {
                inline.append(itemView.get(i));
                inline.append(" ".repeat(ITEM_OFFSET));
            }
            window.line(inline.toString());
        }
        window.emptyLine();
        window.lineWithAngles();
        window.line("0. Exit");
        window.list(items.stream().map(ShopItem::getBasicView).collect(Collectors.toList()),
                true, 1, false, false);
        window.add(getFooter(shop, playerInventory));
        return window.getView();
    }

    public static String getFooter(Shop shop, Inventory playerInventory) {
        Window footer = new Window();
        StringBuilder playerCurrenciesView = new StringBuilder();
        Set<Item> currencies = shop.getCurrencies();
        for (Item currency : currencies) {
            if (playerInventory.has(currency)) {
                playerCurrenciesView.append("(").append(currency.getName()).append(" X").append(playerInventory.get(currency)).append("); ");
            }
        }
        if (playerCurrenciesView.length() > 0) {
            footer.lineWithAngles();
            footer.line("You have " + playerCurrenciesView);
        }
        footer.lineWithAngles();
        return footer.getView();
    }
}
