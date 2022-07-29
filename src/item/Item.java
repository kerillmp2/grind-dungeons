package item;

import java.io.Serializable;
import java.util.Objects;

import profile.Profile;
import shop.HasBasicView;
import utils.Constants;
import utils.HasInventoryView;

public class Item implements HasBasicView, HasInventoryView, Comparable<Item>, Serializable {
    protected String name;
    protected String description;
    protected Rarity rarity;
    protected PickUpAction pickUpAction;
    protected int value;

    public Item(String name, String description, Rarity rarity, int value, PickUpAction pickUpAction) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.value = value;
        this.pickUpAction = pickUpAction;
    }

    public static Item newItem(String name, String description, Rarity rarity, int value) {
        return new Item(name, description, rarity, value, PickUpAction.defaultAction());
    }

    public static Item noPickup(String name, String description, Rarity rarity, int value) {
         return new Item(name, description, rarity, value, ((profile, item) -> {}));
    }

    public String getName() {
        return name;
    }

    public String getNameWithRarity() {
        if (rarity == Rarity.UNDEFINED || rarity == Rarity.UNIQUE) {
            return name;
        } else {
            return name + " [" + rarity.getShortName() + "]";
        }
    }

    @Override
    public String getHeader() {
        return getNameWithRarity();
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getFooter() {
        return "";
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String getBasicView() {
        StringBuilder view = new StringBuilder();
        String nameString = name;
        String rarityString;
        String descriptionString;
        if (rarity != Rarity.UNDEFINED) {
            rarityString = "{" + rarity + "}";
        } else {
            rarityString = "";
        }
        if (description.length() > 0) {
            descriptionString = "[" + description + "]";
        } else {
            descriptionString = "";
        }
        view.append(nameString)
                .append(" ".repeat(Constants.SHOP_ITEM_NAME_LEN.value - nameString.length())).append(" ");
        view.append(rarityString)
                .append(" ".repeat(Constants.SHOP_ITEM_RARITY_LEN.value - rarityString.length())).append(" ");
        view.append(descriptionString);
        return view.toString();
    }

    public void onPickUp(Profile profile) {
        pickUpAction.resolve(profile, this);
    }

    public void addDescription(String description) {
        this.description = this.description + " " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return value == item.value && Objects.equals(name, item.name) && Objects.equals(description, item.description) && rarity == item.rarity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, rarity, value);
    }

    @Override
    public int compareTo(Item o) {
        return value - o.value;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                "value='" + value + '\'' +
                '}';
    }
}
