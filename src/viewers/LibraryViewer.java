package viewers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import item.Item;
import item.book.Book;
import profile.Profile;
import utils.Constants;

public class LibraryViewer extends Viewer {

    private static final int BOOK_OFFSET = Constants.BOOK_VIEW_OFFSET.value;
    private static final int BOOK_HEIGHT = Constants.BOOK_VIEW_HEIGHT.value;

    public static String getPageHeader(String name, boolean hasPrevPage, boolean hasNextPage) {
        Window header = new Window();
        header.lineWithAngles();
        String left = "";
        String middle = name + "'s library";
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

    public static String getPageView(List<Book> items, Profile profile, boolean hasPrevPage, boolean hasNextPage) {
        Window window = new Window();
        window.add(getPageHeader(profile.getName(), hasPrevPage, hasNextPage));

        List<List<String>> splittedItemViews = new ArrayList<>();

        for (Book book : items) {
            splittedItemViews.add(Arrays.stream(ItemViewer.getBookView(book, profile).split("\n")).collect(Collectors.toList()));
        }

        for (int i = 0; i < BOOK_HEIGHT; i++) {
            StringBuilder inline = new StringBuilder();
            for (List<String> itemView : splittedItemViews) {
                inline.append(itemView.get(i));
                inline.append(" ".repeat(BOOK_OFFSET));
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

    public static String getBookView(Profile profile, Book book) {
        Window window = new Window();
        window.add(getPageHeader(profile.getName(), false, false));

        List<List<String>> splittedItemViews = new ArrayList<>();

        splittedItemViews.add(Arrays.stream(ItemViewer.getBookView(book, profile).split("\n")).collect(Collectors.toList()));

        for (int i = 0; i < BOOK_HEIGHT; i++) {
            StringBuilder inline = new StringBuilder();
            for (List<String> itemView : splittedItemViews) {
                inline.append(itemView.get(i));
                inline.append(" ".repeat(BOOK_OFFSET));
            }
            window.line(inline.toString());
        }

        window.emptyLine();
        window.lineWithAngles();
        window.line("0. Back");
        if (book.isEquiped()) {
            window.line("1. Unequip");
        } else {
            window.line("1. Equip");
        }
        window.line("2. Upgrade");
        Map<Item, Integer> upgradeCosts = book.getUpgradeCosts();
        StringBuilder upgradeCost = new StringBuilder("Cost: ");
        for (Map.Entry<Item, Integer> cost : upgradeCosts.entrySet()) {
            upgradeCost.append(cost.getKey().getName()).append(" X").append(cost.getValue()).append(";");
        }
        window.line(upgradeCost.toString());
        window.line(book.getLevelUpDescription());
        window.lineWithAngles();
        return window.getView();
    }
}
