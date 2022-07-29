package shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import item.Item;
import profile.Profile;
import profile.ProfileTagEnum;
import utils.ControllerState;
import utils.HasName;
import utils.MessageController;
import utils.Selector;
import viewers.ShopViewer;

public class ShopController implements HasName {
    protected Profile player;
    private final ProfileTagEnum shopTag;
    private List<List<ShopItem>> pages;
    private int currentPage;

    public ShopController(Profile player, ProfileTagEnum shopTag) {
        this.player = player;
        this.shopTag = shopTag;
    }

    public void shopProcessing() {
        ControllerState state = ControllerState.PROCESSING;
        int selectedNumber;
        while (state != ControllerState.EXIT) {
            Shop shop = ShopFactory.shopFromTag(player, shopTag);
            regeneratePages(shop.getAllItems());
            boolean hasPrevPage = currentPage > 0;
            boolean hasNextPage = currentPage + 1 < pages.size();
            MessageController.clearConsole();
            List<ShopItem> items = (currentPage < pages.size() && currentPage >= 0) ? pages.get(currentPage) : new ArrayList<>();
            if (items.size() == 0) {
                currentPage--;
                continue;
            }
            MessageController.print(ShopViewer.getPageView(
                    shop,
                    player.getInventory(),
                    items,
                    hasPrevPage,
                    hasNextPage
            ));

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
                    continue;
                }
                if (hasPrevPage && selectedNumber == 6) {
                    currentPage--;
                    continue;
                }
                ShopItem chosenItem = items.get(selectedNumber - 1);
                AtomicBoolean canBuy = new AtomicBoolean(true);

                Map<Item, Integer> cost = chosenItem.getCosts();
                cost.forEach((item, amount) -> {
                    if (!player.getInventory().has(item, amount)) {
                        canBuy.set(false);
                        MessageController.print("You don't have enough " + item + "! ("
                                + player.getInventory().get(item) + " / " + amount + ")");
                    }
                });

                if (canBuy.get()) {
                    buy(chosenItem);
                }
            } else {
                state = ControllerState.EXIT;
            }
        }
        player.saveProfile();
    }

    public void buy(ShopItem shopItem) {
        shopItem.getBuyAction().resolve(this, shopItem);
    }

    private void regeneratePages(List<ShopItem> shopItems) {
        this.pages = generatePages(shopItems);
    }

    private List<List<ShopItem>> generatePages(List<ShopItem> allItems) {
        allItems.sort((o1, o2) -> o2.getValue() - o1.getValue());
        List<List<ShopItem>> pages = new ArrayList<>();
        int itemsCounter = 0;
        while (itemsCounter < allItems.size()) {
            int pageItemsCounter = 0;
            List<ShopItem> page = new ArrayList<>();
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

    public Profile getPlayer() {
        return player;
    }

    @Override
    public String getName() {
        return shopTag.getName();
    }
}
