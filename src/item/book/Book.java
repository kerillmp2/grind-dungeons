package item.book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import battlecore.action.Action;
import item.Item;
import item.PickUpAction;
import item.Rarity;
import profile.Profile;

public class Book extends Item implements Serializable {
    private final Map<Item, Integer> upgradeCosts;
    private final Action action;
    protected final int level;

    BookDescription bookDescription;
    BookType bookType;
    private boolean equiped;


    public Book(String name, String description, Rarity rarity, int value, Map<Item, Integer> upgradeCosts, BookDescription bookDescription, BookType bookType, int level, boolean equiped, Action action) {
        this(name, description, rarity, value, upgradeCosts, bookDescription, bookType, level, equiped, action, PickUpAction.defaultAction());
    }

    public Book(String name, String description, Rarity rarity, int value, Map<Item, Integer> upgradeCosts, BookDescription bookDescription, BookType bookType, int level, boolean equiped, Action action, PickUpAction pickUpAction) {
        super(name, description, rarity, value, pickUpAction);
        this.upgradeCosts = upgradeCosts;
        this.bookDescription = bookDescription;
        this.bookType = bookType;
        this.level = level;
        this.equiped = equiped;
        this.action = action;
    }

    public Book upgraded() {
        return BookFactory.getBook(bookType, level + 1);
    }

    public String getNameWithLevel() {
        return this.name + " [" + this.level + "]";
    }

    public Map<Item, Integer> getUpgradeCosts() {
        return upgradeCosts;
    }

    public String getUpgradeCostsString() {
        StringBuilder upgradeCost = new StringBuilder("Cost: ");
        for (Map.Entry<Item, Integer> cost : upgradeCosts.entrySet()) {
            upgradeCost.append(cost.getKey().getName()).append(" X").append(cost.getValue()).append(";");
        }
        return upgradeCost.toString();
    }

    public int getLevel() {
        return level;
    }

    public boolean isEquiped() {
        return equiped;
    }

    public String getLevelUpDescription() {
        return this.bookDescription.compareWith(this.upgraded().getBookDescription());
    }

    public void setEquiped(boolean equiped) {
        this.equiped = equiped;
    }

    public BookType getBookType() {
        return bookType;
    }

    public Action getAction() {
        return action;
    }

    public BookDescription getBookDescription() {
        return bookDescription;
    }

    public String getAdditionalDescriptionFor(Profile profile) {
        return bookDescription.getAdditionalDescriptionFor(profile);
    }

    public static class BookDescription implements Serializable {
        private final String template;
        private final List<String> values;
        private final AdditionalDescriptionGenerator generator;

        public static final BookDescription EMPTY = new BookDescription("", new ArrayList<>(), profile -> "");

        public BookDescription(String template, List<String> values, AdditionalDescriptionGenerator generator) {
            this.template = template;
            this.values = values;
            this.generator = generator;
        }

        public String getDescription() {
            return String.format(template, values.toArray());
        }

        public String getDescription(List<String> values) {
            return String.format(template, values.toArray());
        }

        public String getAdditionalDescriptionFor(Profile profile) {
            return generator.generateFor(profile);
        }

        public String compareWith(BookDescription bookDescription) {
            List<String> newValues = new ArrayList<>();
            for (int i = 0; i < values.size(); i++) {
                if (i < bookDescription.values.size()) {
                    newValues.add("{" + values.get(i) + " -> " + bookDescription.values.get(i) + "}");
                }
            }
            for (int i = values.size(); i < bookDescription.values.size(); i++) {
                newValues.add(bookDescription.values.get(i));
            }
            return bookDescription.getDescription(newValues);
        }

        public List<String> getValues() {
            return values;
        }
    }
}
