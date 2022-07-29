package shop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import item.Item;
import item.Rarity;
import utils.Constants;
import utils.HasInventoryView;
import utils.Pair;

public class ShopItem implements HasBasicView, HasInventoryView {
    private final Item item;
    private final Map<Item, Integer> costs;
    private BuyAction buyAction;

    public ShopItem(Item item, Map<Item, Integer> costs, BuyAction buyAction) {
        this.item = item;
        this.costs = costs;
        this.buyAction = buyAction;
    }

    public ShopItem(Item item, int cost, Item currency, BuyAction buyAction) {
        this.item = item;
        this.costs = new HashMap<>();
        this.costs.put(currency, cost);
        this.buyAction = buyAction;
    }

    @SafeVarargs
    public ShopItem(Item item, BuyAction buyAction, Pair<Item, Integer>... costs) {
        this.item = item;
        this.costs = new HashMap<>();
        Arrays.stream(costs).forEach(cost -> this.costs.put(cost.first, cost.second));
        this.buyAction = buyAction;
    }

    public static ShopItem fromItem(Item item, int cost, Item currency) {
        return new ShopItem(item, cost, currency, BuyAction.defaultAction());
    }

    @SafeVarargs
    public static ShopItem fromItem(Item item, Pair<Item, Integer>... costs) {
        return new ShopItem(item, BuyAction.defaultAction(), costs);
    }

    public ShopItem wrapBuyAction(BuyAction action) {
        BuyAction lastBuyAction = this.buyAction;
        this.buyAction = (shopController, shopItem) -> {
            lastBuyAction.resolve(shopController, shopItem);
            action.resolve(shopController, shopItem);
        };
        return this;
    }

    public Map<Item, Integer> getCosts() {
        return costs;
    }

    public Item getItem() {
        return item;
    }

    public int getValue() {
        return item.getValue();
    }

    public BuyAction getBuyAction() {
        return buyAction;
    }

    @Override
    public String getBasicView() {
        StringBuilder view = new StringBuilder();
        String nameString = item.getName();
        String rarityString;
        String descriptionString;

        if (item.getRarity() != Rarity.UNDEFINED) {
            rarityString = "{" + item.getRarity() + "}";
        } else {
            rarityString = "";
        }
        if (item.getDescription().length() > 0) {
            descriptionString = "[" + item.getDescription() + "]";
        } else {
            descriptionString = "";
        }
        String costString = "(" + getCostString() + ")";
        view.append(item.getName())
                .append(" ".repeat(Constants.SHOP_ITEM_NAME_LEN.value - nameString.length())).append(" ");
        view.append(rarityString)
                .append(" ".repeat(Constants.SHOP_ITEM_RARITY_LEN.value - rarityString.length())).append(" ");
        view.append(costString)
                .append(" ".repeat(Constants.SHOP_ITEM_COST_LEN.value - costString.length())).append(" ");
        return view.toString();
    }
    
    @Override
    public String getName() {
        return item.getName();
    }

    @Override
    public String getHeader() {
        return item.getNameWithRarity();
    }

    @Override
    public String getDescription() {
        return item.getDescription();
    }

    @Override
    public String getFooter() {
        return getCostString();
    }

    public String getCostString() {
        StringBuilder upgradeCost = new StringBuilder();
        for (Map.Entry<Item, Integer> cost : costs.entrySet()) {
            if (upgradeCost.length() > 0) {
                upgradeCost.append("; ");
            }
            upgradeCost.append(cost.getKey().getName()).append(" X").append(cost.getValue());
        }
        return upgradeCost.toString();
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "item=" + item +
                '}';
    }
}
