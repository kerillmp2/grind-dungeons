package shop;

import java.io.Serializable;
import java.util.Map;

import item.Item;
import utils.MessageController;

public interface BuyAction extends Serializable {
    void resolve(ShopController shopController, ShopItem shopItem);

    static BuyAction defaultAction() {
        return (shopController, shopItem) -> {
            Map<Item, Integer> cost = shopItem.getCosts();
            cost.forEach((key, value) -> shopController.getPlayer().getInventory().pull(key, value));
            shopItem.getItem().onPickUp(shopController.getPlayer());
            MessageController.print("You bought " + shopItem.getItem().getName() + "!");
        };
    }
}