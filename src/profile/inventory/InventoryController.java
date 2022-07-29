package profile.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import item.Item;
import item.equipment.CharacterEquipment;
import item.usable.UsableItem;
import logic.ItemCondition;
import profile.Profile;
import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.InventoryViewer;

public class InventoryController {

    Profile profile;
    List<List<Item>> pages;
    List<ItemCondition> filters;
    int currentPage;

    private InventoryController(Profile profile, List<List<Item>> pages, int currentPage, List<ItemCondition> filters) {
        this.profile = profile;
        this.pages = pages;
        this.currentPage = currentPage;
        this.filters = filters;
    }

    public static InventoryController defaultController(Profile profile) {
        return new InventoryController(profile, new ArrayList<>(), 0, new ArrayList<>());
    }

    public static InventoryController withFilters(Profile profile, ItemCondition... itemConditions) {
        List<ItemCondition> itemConditionList = Arrays.stream(itemConditions).toList();
        return new InventoryController(profile, new ArrayList<>(), 0, itemConditionList);
    }

    public void openInventoryProcessing() {
        regeneratePages();
        ControllerState controllerState = ControllerState.PROCESSING;
        int selectedNumber;
        while (controllerState != ControllerState.EXIT) {
            boolean hasPrevPage = currentPage > 0;
            boolean hasNextPage = currentPage + 1 < pages.size();
            MessageController.clearConsole();
            List<Item> items = (currentPage < pages.size() && currentPage >= 0) ? pages.get(currentPage) : new ArrayList<>();
            MessageController.print(InventoryViewer.getPageView(
                    items,
                    profile.getInventory().getItems(),
                    profile.getName(),
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
                        Item chosenItem = items.get(selectedNumber - 1);
                        if (tryToEquip(chosenItem)) {
                            regeneratePages();
                        } else {
                            if (tryToUse(chosenItem)) {
                                regeneratePages();
                            }
                        }
                    }
                }
            } else {
                controllerState = ControllerState.EXIT;
            }
        }
    }

    public boolean tryToEquip(Item item) {
        if (item instanceof CharacterEquipment equipment) {
            String equipErrorMessages = equipment.canBeEquippedBy(profile);
            if (equipErrorMessages.length() == 0) {
                profile.getCharacter().equip(equipment, profile.getInventory());
                return true;
            } else {
                MessageController.print(equipErrorMessages);
            }
        }
        return false;
    }

    public boolean tryToUse(Item item) {
        if (item instanceof UsableItem usableItem) {
            String useErrorMessages = usableItem.canBeUsedBy(profile);
            if (useErrorMessages.length() == 0) {
                profile.useItem(usableItem);
                profile.getInventory().put(usableItem);
                return true;
            } else {
                MessageController.print(useErrorMessages);
            }
        }
        return false;
    }

    private void regeneratePages() {
        this.pages = generatePages(profile.getInventory());
    }

    private List<List<Item>> generatePages(Inventory inventory) {
        List<Item> allItems = inventory.getItems().keySet().stream()
                .filter(this::checkFilters)
                .sorted().collect(Collectors.toList());
        List<List<Item>> pages = new ArrayList<>();
        int itemsCounter = 0;
        while (itemsCounter < allItems.size()) {
            int pageItemsCounter = 0;
            List<Item> page = new ArrayList<>();
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

    private boolean checkFilters(Item item) {
        for (ItemCondition filter : this.filters) {
            if (!filter.check(item)) {
                return false;
            }
        }
        return true;
    }
}
