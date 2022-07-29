package viewers;

import java.util.ArrayList;
import java.util.List;

import item.Item;
import item.book.Book;
import item.book.BookFactory;
import item.book.BookType;
import utils.MessageController;

public class BookTableViewer extends Viewer {

    public static void printBookTableForBook(BookType bookType) {
        List<String> descriptionsList = new ArrayList<>();
        for (int level = 1; level <= 100; level++) {
            Book book = BookFactory.getBook(bookType, level);
            descriptionsList.add(book.getBookDescription().getDescription() + " " + book.getUpgradeCostsString());
        }
        Window window = new Window();
        window.lineWithAngles().centeredText(bookType.name()).lineWithAngles().list(descriptionsList).lineWithAngles();
        MessageController.print(window.getView());
    }

    public static void printBookTableForFarmerBook(Item item) {
        List<String> descriptionsList = new ArrayList<>();
        for (int level = 1; level <= 100; level++) {
            Book book = BookFactory.getFramerBook(item, level);
            descriptionsList.add(book.getBookDescription().getDescription() + " " + book.getUpgradeCostsString());
        }
        Window window = new Window();
        window.lineWithAngles().centeredText(item.getName() + " farmer book").lineWithAngles().list(descriptionsList).lineWithAngles();
        MessageController.print(window.getView());
    }
}
