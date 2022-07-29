package profile.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import item.Item;
import item.book.Book;
import item.book.FarmerBook;

public class Library implements Serializable {
    private final List<Book> availableBooks;

    private Library(List<Book> availableBooks) {
        this.availableBooks = availableBooks;
    }

    public static Library empty() {
        return new Library(new ArrayList<>());
    }

    public List<Book> getAvailableBooks() {
        return availableBooks;
    }

    public boolean hasFarmerBook(Item item) {
        for (Book book : availableBooks) {
            if (book instanceof FarmerBook farmerBook) {
                if (farmerBook.getFarmItem().getName().equalsIgnoreCase(item.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
