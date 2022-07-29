package profile.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import item.Item;
import item.book.Book;
import profile.Profile;
import profile.inventory.Inventory;
import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.LibraryViewer;

public class LibraryController {
    Profile profile;
    List<List<Book>> pages;
    int currentPage;

    private LibraryController(Profile profile, List<List<Book>> pages, int currentPage) {
        this.profile = profile;
        this.pages = pages;
        this.currentPage = currentPage;
    }

    public static LibraryController defaultController(Profile profile) {
        return new LibraryController(profile, new ArrayList<>(), 0);
    }

    public void openLibraryProcessing() {
        regeneratePages();
        ControllerState controllerState = ControllerState.PROCESSING;
        int selectedNumber;
        boolean hasPrevPage = currentPage > 0;
        boolean hasNextPage = currentPage + 1 < pages.size();
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();
            List<Book> books = (currentPage < pages.size() && currentPage >= 0) ? pages.get(currentPage) : new ArrayList<>();
            MessageController.print(LibraryViewer.getPageView(
                    books,
                    profile,
                    hasPrevPage,
                    hasNextPage)
            );
            if (hasPrevPage) {
                if (hasNextPage) {
                    selectedNumber = Selector.itemSelect(pages.get(currentPage), 6, 7);
                } else {
                    selectedNumber = Selector.itemSelect(pages.get(currentPage), 6);
                }
            } else {
                if (hasNextPage) {
                    selectedNumber = Selector.itemSelect(pages.get(currentPage), 7);
                } else {
                    selectedNumber = Selector.itemSelect(pages.get(currentPage));
                }
            }
            if (selectedNumber != 0) {
                if (hasNextPage && selectedNumber == 7) {
                    currentPage++;
                } else {
                    if (hasPrevPage && selectedNumber == 6) {
                        currentPage--;
                    } else {
                        Book chosenBook = books.get(selectedNumber - 1);
                        processBookChoice(chosenBook);
                        regeneratePages();
                    }
                }
            } else {
                controllerState = ControllerState.EXIT;
            }
        }
        profile.saveProfile();
    }

    public void processBookChoice(Book book) {
        ControllerState controllerState = ControllerState.PROCESSING;
        int selectedNumber;
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();
            MessageController.print(LibraryViewer.getBookView(profile, book));
            selectedNumber = Selector.itemSelect(new ArrayList<>(), 0, 1, 2);
            if (selectedNumber == 1) {
                if (book.isEquiped()) {
                    unequipBook(book);
                } else {
                    equipBook(book);
                }
            }
            if (selectedNumber == 2) {
                if (tryToUpgrade(book)) {
                    controllerState = ControllerState.EXIT;
                }
            }
            if (selectedNumber == 0) {
                controllerState = ControllerState.EXIT;
            }
        }
    }

    public boolean tryToUpgrade(Book book) {
        Map<Item, Integer> costs = book.getUpgradeCosts();
        Inventory inventory = profile.getInventory();
        StringBuilder notEnoughResourcesMsg = new StringBuilder();
        boolean enoughResources = true;
        for (Map.Entry<Item, Integer> cost : costs.entrySet()) {
            int inInventory = inventory.get(cost.getKey());
            if (inInventory < cost.getValue()) {
                enoughResources = false;
                notEnoughResourcesMsg.append("You don't have enough ").append(cost.getKey().getName()).append("! (").append(inInventory).append(" / ").append(cost.getValue()).append(")");
            }
        }
        if (enoughResources) {
            upgradeBook(book);
            return true;
        } else {
            MessageController.print(notEnoughResourcesMsg.toString());
            return false;
        }
    }

    public void equipBook(Book book) {
        profile.getCharacter().addAction(book.getAction());
        book.setEquiped(true);
        regeneratePages();
    }

    public void unequipBook(Book book) {
        profile.getCharacter().removeAction(book.getAction());
        book.setEquiped(false);
        regeneratePages();
    }

    public void upgradeBook(Book book) {
        unequipBook(book);
        Book newBook = book.upgraded();
        profile.getLibrary().getAvailableBooks().remove(book);
        profile.getLibrary().getAvailableBooks().add(newBook);
        equipBook(newBook);
        Inventory inventory = profile.getInventory();
        for (Map.Entry<Item, Integer> cost : book.getUpgradeCosts().entrySet()) {
            inventory.pull(cost.getKey(), cost.getValue());
        }
    }

    private void regeneratePages() {
        this.pages = generatePages(profile.getLibrary());
    }

    private List<List<Book>> generatePages(Library library) {
        List<Book> allItems = library.getAvailableBooks();
        List<List<Book>> pages = new ArrayList<>();
        int itemsCounter = 0;
        while (itemsCounter < allItems.size()) {
            int pageItemsCounter = 0;
            List<Book> page = new ArrayList<>();
            while (pageItemsCounter < 5) {
                if (itemsCounter + pageItemsCounter >= allItems.size()) {
                    break;
                }
                page.add(allItems.get(itemsCounter + pageItemsCounter));
                pageItemsCounter++;
            }
            pages.add(page);
            itemsCounter += pageItemsCounter;
        }
        if (pages.size() == 0) {
            pages.add(new ArrayList<>());
        }
        return pages;
    }
}
