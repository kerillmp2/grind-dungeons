package viewers;

import item.book.Book;
import profile.Profile;
import utils.Constants;
import utils.HasInventoryView;

public class ItemViewer extends Viewer {

    public static <T extends HasInventoryView> String getItemView(T item, int amount) {
        final int LENGTH = Constants.ITEM_VIEW_LENGTH.value;
        final int HEIGHT = Constants.ITEM_VIEW_HEIGHT.value;
        final int OFFSET = Constants.ITEM_VIEW_OFFSET.value;

        Window window = new Window(LENGTH, OFFSET);
        window.lineWithAngles();
        window.line(item.getHeader());
        window.lineWithAngles();

        String[] nameParts = item.getDescription().split(" ");
        StringBuilder line = new StringBuilder();
        int curLength = 0;
        for (int i = 0; i < nameParts.length; i++) {
            int curHeight = window.getCurrentHeight();
            String currentPart = nameParts[i];
            if (curHeight >= HEIGHT - 1) {
                break;
            }
            if (currentPart.length() > LENGTH) {
                continue;
            }
            if (curLength > 0) {
                if (currentPart.length() + curLength < LENGTH - 2 - OFFSET * 2) {
                    line.append(" ").append(currentPart);
                    curLength += currentPart.length() + 1;
                } else {
                    window.line(line.toString());
                    line = new StringBuilder();
                    curLength = 0;
                    i--;
                    continue;
                }
            } else {
                line.append(currentPart);
                curLength += currentPart.length();
            }
            if (currentPart.endsWith("$")) {
                window.line(line.toString());
                line = new StringBuilder();
                curLength = 0;
            }
        }
        window.line(line.toString());
        int currentHeight = window.getCurrentHeight();
        int rowsRemains = HEIGHT - currentHeight;
        while (rowsRemains > 3) {
            window.emptyLine();
            rowsRemains--;
        }
        window.lineWithAngles();
        if (item.getFooter().length() > 0) {
            window.line(item.getFooter());
        }
        if (amount > 0) {
            window.line("X" + amount);
        }
        window.lineWithAngles();
        return window.getView();
    }

    public static String getBookView(Book book, Profile profile) {
        final int LENGTH = Constants.BOOK_VIEW_LENGTH.value;
        final int HEIGHT = Constants.BOOK_VIEW_HEIGHT.value;
        final int OFFSET = Constants.BOOK_VIEW_OFFSET.value;

        Window window = new Window(LENGTH, OFFSET);
        window.lineWithAngles();
        window.line(book.getNameWithLevel());
        window.lineWithAngles();

        String[] nameParts = book.getDescription().split(" ");
        StringBuilder line = new StringBuilder();
        int curLength = 0;
        for (int i = 0; i < nameParts.length; i++) {
            int curHeight = window.getCurrentHeight();
            String currentPart = nameParts[i];
            if (curHeight >= HEIGHT - 1) {
                break;
            }
            if (currentPart.length() > LENGTH) {
                continue;
            }
            if (curLength > 0) {
                if (currentPart.length() + curLength < LENGTH - 2 - OFFSET * 2) {
                    line.append(" ").append(currentPart);
                    curLength += currentPart.length() + 1;
                } else {
                    window.line(line.toString());
                    line = new StringBuilder();
                    curLength = 0;
                    i--;
                    continue;
                }
            } else {
                line.append(currentPart);
                curLength += currentPart.length();
            }
            if (currentPart.endsWith("$")) {
                window.line(line.toString());
                line = new StringBuilder();
                curLength = 0;
            }
        }
        window.line(line.toString());
        int currentHeight = window.getCurrentHeight();
        int rowsRemains = HEIGHT - currentHeight;
        while (rowsRemains > 4) {
            window.emptyLine();
            rowsRemains--;
        }
        String additionalDescription = book.getAdditionalDescriptionFor(profile);
        if (book.isEquiped()) {
            window.line("Equiped");
        } else {
            window.emptyLine();
        }
        if (additionalDescription.length() > 0) {
            window.lineWithAngles();
            window.line(additionalDescription);
        } else {
            window.emptyLine();
            window.emptyLine();
        }
        window.lineWithAngles();
        return window.getView();
    }
}
