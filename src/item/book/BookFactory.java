package item.book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import battlecore.DamageType;
import battlecore.action.Action;
import battlecore.action.ActionFactory;
import battlecore.action.ResolveTime;
import item.Item;
import item.ItemFactory;
import item.Rarity;
import stat.ChangeDuration;
import utils.Pair;

import static item.book.BookType.*;

public class BookFactory implements Serializable {

    public static List<Book> allBooks = new ArrayList<>();

    public static Book greenYammyBook(int level) {
        int amount = (int) (level + level / 5.0 + level / 10.0 + Math.pow(1.08, level));
        double chance = (int) (Math.min(6 + level / 3.0 + level / 5.0 + Math.pow(1.033, level), 100) * 100) / 100.0;
        int incr = Math.floorDiv(level, 3) + Math.floorDiv(level, 5) + Math.floorDiv(level, 7);
        int cost = (int) (level * 7 + incr * level + Math.pow(1.16, level));
        return BookTemplate.empty().withName("Green yammy!").withRarity(Rarity.COMMON)
                .withType(GREEN_YAMMY_BOOK)
                .withCost(ItemFactory.GREEN_SLIME, cost)
                .withDescription("You have %s%% chance to heal %s HP after each battle")
                .withValue(chance).withValue(amount)
                .withAction(ActionFactory.healAction(ResolveTime.ON_BATTLE_END, amount, chance))
                .withLevel(level)
                .buildFramerBook();
    }

    public static Book poisonSpit(int level) {
        double chance = (int) ((Math.min(4 + level / 4.0 + level / 6.0 + Math.pow(1.03, level), 100)) * 100) / 100.0;
        double baseDamage = (int) ((level + level / 2.0 + level / 5.0 + 5 * Math.pow(1.04, level)) * 100) / 100.0;
        double damageCoeff = (int) ((level / 2.0 + level / 10.0 + level / 15.0 + Math.pow(1.02, level)) * 100) / 100.0;
        List<Pair<Item, Integer>> costs = new ArrayList<>();

        int costGreen = (int) (level * 10 + Math.floorDiv(level, 3) * level + Math.pow(1.09, level));
        costs.add(new Pair<>(ItemFactory.GREEN_SLIME, costGreen));
        if (level >= 9) {
            int costYellow = (int) (level * 6 + Math.floorDiv(level, 5) * (level - 9) + Math.pow(1.07, level));
            costs.add(new Pair<>(ItemFactory.YELLOW_SLIME, costYellow));
        }
        if (level >= 20) {
            int costRed = (int) (level * 3 + Math.floorDiv(level, 9) * (level - 19) + Math.pow(1.04, level));
            costs.add(new Pair<>(ItemFactory.RED_SLIME, costRed));
        }
        BookTemplate bookTemplate = BookTemplate.empty().withName("Poison spit").withRarity(Rarity.COMMON)
            .withType(BookType.POISON_SPIT_BOOK)
            .withDescription("You have %s%% chance to deal %s + %s%% of poison damage after attack")
            .withValue(chance).withValue(baseDamage).withValue(damageCoeff)
            .withAction(ActionFactory.dealDamageAction(ResolveTime.AFTER_ATTACK, DamageType.POISON, damageCoeff, chance, "spits poison on"))
            .withLevel(level)
            .withAdditionalDescriptionGenerator(profile -> "Damage "
                    + (baseDamage + (int) (damageCoeff / 100.0 * (profile.getCharacter().getDamageMin().get(DamageType.POISON) + DamageType.POISON.calculateMinDamage(profile.getCharacter().getStats()))))
                    + "-"
                    + (baseDamage + (int) (damageCoeff / 100.0 * (profile.getCharacter().getDamageMax().get(DamageType.POISON) + DamageType.POISON.calculateMaxDamage(profile.getCharacter().getStats())))));

        for (Pair<Item, Integer> cost : costs) {
            bookTemplate.withCost(cost.first, cost.second);
        }
        return bookTemplate.buildFramerBook();
    }

    public static Book pinkyDance(int level) {
        double chance = (int) (Math.min(6 + level / 3.25 + level / 4.6 + Math.pow(1.026, level), 100) * 100) / 100.0;
        int poisonArmor = (int) (1 + level / 2.0 + level / 4.0 + Math.pow(1.04, level));
        List<Pair<Item, Integer>> costs = new ArrayList<>();

        int costGreen = (int) (level * 12 + Math.floorDiv(level, 3) * level + Math.pow(1.08, level));
        costs.add(new Pair<>(ItemFactory.GREEN_SLIME, costGreen));
        if (level >= 5) {
            int costYellow = (int) (level * 6 + Math.floorDiv(level, 5) * (level - 5) + Math.pow(1.06, level));
            costs.add(new Pair<>(ItemFactory.YELLOW_SLIME, costYellow));
        }
        if (level >= 7) {
            int costRed = (int) (level * 3 + Math.floorDiv(level, 7) * (level - 7) + Math.pow(1.04, level));
            costs.add(new Pair<>(ItemFactory.RED_SLIME, costRed));
        }
        if (level >= 15) {
            int costPink = (int) (level + Math.floorDiv(level, 12) * (level - 15) + Math.pow(1.01, level));
            costs.add(new Pair<>(ItemFactory.PINK_SLIME, costPink));
        }
        BookTemplate bookTemplate = BookTemplate.empty().withName("Pinky dance").withRarity(Rarity.COMMON)
                .withType(BookType.PINKY_DANCE_BOOK)
                .withDescription("You have %s%% chance to gain %s poison armor at the end of your turn until your next turn")
                .withValue(chance).withValue(poisonArmor)
                .withAction(ActionFactory.gainArmor(
                        ResolveTime.ON_END_TURN,
                        DamageType.POISON,
                        ChangeDuration.UNTIL_NEXT_TURN,
                        poisonArmor,
                        false,
                        chance,
                        "dances like Pinky")
                )
                .withLevel(level);

        for (Pair<Item, Integer> cost : costs) {
            bookTemplate.withCost(cost.first, cost.second);
        }
        return bookTemplate.buildFramerBook();
    }

    public static FarmerBook resourceFarmerBook(Item item, int level) {
        double value = Math.log(Math.E + item.getValue());
        int amount = 1 + (int) ((Math.pow(1.04, level) + level) / value) + level / 4 + level / 10;
        double chance = Math.min((int) ((1 + Math.pow(1.04, level) / value + level / value / Math.pow(1.015, level) + level / 5) * 100.0) / 100.0, 100);
        int cost = (int) ((amount * (chance / 100.0) * (level + value) * Math.pow(1.03, level)) + level * (5 + item.getValue() + value)) + (int) (level / 5 * Math.pow(1.06, level) * value);
        String bookName = item.getName() + " farmer";
        String description = "You have %s%% chance to loot %s " + item.getName() + " after each kill!";
        return BookTemplate.empty().withName(bookName).withRarity(Rarity.UNIQUE)
                .withType(new BookType(bookName.toUpperCase() + " BOOK"))
                .withDescription(description)
                .withCost(item, cost)
                .withValue(chance).withValue(amount)
                .withLevel(level)
                .withAction(ActionFactory.farmer(item, amount, chance))
                .buildFramerBook(item);
    }

    public static Book casualCooperMiner(int level) {
        int amount = (int) (Math.pow(1.012, level) + level / 12.0);
        double chance = (int) ((amount + Math.pow(1.04, 1.5 * level) + level / 4.0) / amount * 100) / 100.0;
        int cost = (int) ((Math.pow(1.01, level) * 10 + amount) * level + Math.pow(1.132, level));
        return BookTemplate.empty().withName("Casual cooper miner").withRarity(Rarity.COMMON)
                .withType(BookType.CASUAL_COOPER_MINER)
                .withDescription("You have %s%% chance to mine %s cooper after each pickaxe hit!")
                .withCost(ItemFactory.COOPER_ORE, cost)
                .withValue(chance).withValue(amount)
                .withLevel(level)
                .withAction(ActionFactory.farmer(ItemFactory.GREEN_SLIME, amount, chance))
                .buildFramerBook();
    }

    public static Book getBook(BookType bookType, int level) {
        if (GREEN_YAMMY_BOOK.equals(bookType)) {
            return greenYammyBook(level);
        } else if (POISON_SPIT_BOOK.equals(bookType)) {
            return poisonSpit(level);
        } else if (CASUAL_COOPER_MINER.equals(bookType)) {
            return casualCooperMiner(level);
        } else if (PINKY_DANCE_BOOK.equals(bookType)) {
            return pinkyDance(level);
        }
        return BookTemplate.empty().buildFramerBook();
    }

    public static FarmerBook getFramerBook(Item item, int level) {
        return resourceFarmerBook(item, level);
    }

    public static class BookTemplate {
        private final Map<Item, Integer> upgradeCosts;
        private final List<String> values;
        private BookType bookType;
        private String name;
        private Rarity rarity;
        private String descriptionTemplate;
        private Action action;
        private AdditionalDescriptionGenerator additionalDescriptionGenerator;
        private int level;

        public BookTemplate(Map<Item, Integer> upgradeCosts, List<String> values, BookType bookType, String name, Rarity rarity, String descriptionTemplate, Action action, AdditionalDescriptionGenerator additionalDescriptionGenerator, int level) {
            this.upgradeCosts = upgradeCosts;
            this.values = values;
            this.bookType = bookType;
            this.name = name;
            this.rarity = rarity;
            this.descriptionTemplate = descriptionTemplate;
            this.action = action;
            this.additionalDescriptionGenerator = additionalDescriptionGenerator;
            this.level = level;
        }

        public static BookTemplate empty() {
            return new BookTemplate(new HashMap<>(), new ArrayList<>(), BookType.UNDEFINED, "", Rarity.UNDEFINED, "", Action.empty(), AdditionalDescriptionGenerator.empty(), 0);
        }

        public BookTemplate withCost(Item item, int amount) {
            this.upgradeCosts.put(item, amount);
            return this;
        }

        public BookTemplate withName(String name) {
            this.name = name;
            return this;
        }

        public BookTemplate withDescription(String description) {
            this.descriptionTemplate = description;
            return this;
        }

        public BookTemplate withValue(double value) {
            this.values.add(String.valueOf(value));
            return this;
        }

        public BookTemplate withRarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public BookTemplate withType(BookType bookType) {
            this.bookType = bookType;
            return this;
        }

        public BookTemplate addDescription(String additionalDescription) {
            String beginDescriptionLine = descriptionTemplate.length() > 0 ? "$ " : "";
            descriptionTemplate += (beginDescriptionLine + additionalDescription);
            return this;
        }

        public BookTemplate withAction(Action action) {
            this.action = action;
            return this;
        }

        public BookTemplate withLevel(int level) {
            this.level = level;
            return this;
        }

        public BookTemplate withAdditionalDescriptionGenerator(AdditionalDescriptionGenerator additionalDescriptionGenerator) {
            this.additionalDescriptionGenerator = additionalDescriptionGenerator;
            return this;
        }

        public Book buildFramerBook() {
            return new Book(
                    this.name,
                    String.format(this.descriptionTemplate, values.toArray()),
                    this.rarity,
                    rarity.getValue() * 100 + level * 2,
                    upgradeCosts,
                    new Book.BookDescription(descriptionTemplate, values, additionalDescriptionGenerator),
                    bookType,
                    level,
                    false,
                    action);

        }

        public FarmerBook buildFramerBook(Item item) {
            return new FarmerBook(this.name,
                    String.format(this.descriptionTemplate, values.toArray()),
                    this.rarity,
                    rarity.getValue() * 100 + level * 2,
                    upgradeCosts,
                    new Book.BookDescription(descriptionTemplate, values, additionalDescriptionGenerator),
                    bookType,
                    level,
                    false,
                    action,
                    item);
        }
    }
}
