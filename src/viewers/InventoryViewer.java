package viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import item.Item;
import utils.Constants;

public class InventoryViewer extends Viewer {

    private static final int ITEM_OFFSET = Constants.BASIC_OFFSET.value;
    private static final int ITEM_HEIGHT = Constants.ITEM_VIEW_HEIGHT.value;

    public static String getPageHeader(String name, boolean hasPrevPage, boolean hasNextPage) {
        Window header = new Window();
        header.lineWithAngles();
        String left = "";
        String middle = name + "'s inventory";
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

    public static String getPageView(List<Item> items, Map<Item, Integer> amounts, String name, boolean hasPrevPage, boolean hasNextPage) {
        Window window = new Window();
        window.add(getPageHeader(name, hasPrevPage, hasNextPage));

        List<List<String>> splittedItemViews = new ArrayList<>();

        for (Item item : items) {
            if (amounts != null) {
                splittedItemViews.add(Arrays.stream(ItemViewer.getItemView(item, amounts.get(item)).split("\n")).collect(Collectors.toList()));
            } else {
                splittedItemViews.add(Arrays.stream(ItemViewer.getItemView(item, 0).split("\n")).collect(Collectors.toList()));
            }
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
        window.list(items.stream().map(Item::getBasicView).collect(Collectors.toList()),
                true, 1, false, false);

        window.lineWithAngles();
        return window.getView();
    }
}
