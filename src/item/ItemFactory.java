package item;

import java.io.Serializable;

import battlecore.DamageType;
import item.book.BookFactory;
import item.book.BookType;
import profile.ProfileTagEnum;
import profile.character.CharacterStat;
import stat.ChangeDuration;
import utils.MessageController;

public class ItemFactory implements Serializable {

    public static Item GREEN_SLIME = greenSlime();
    public static Item YELLOW_SLIME = yellowSlime();
    public static Item RED_SLIME = redSlime();

    public static Item PINK_SLIME = pinkSlime();
    public static Item COOPER_ORE = cooperOre();

    private static Item greenSlime() {
        return Item.newItem("Green slime", "Eww! Sticky!", Rarity.COMMON, 1);
    }

    private static Item yellowSlime() {
        return Item.newItem("Yellow slime", "Now in yellow color!", Rarity.COMMON, 2);
    }

    private static Item redSlime() {
        return Item.newItem("Red slime", "And whats next? Purple?", Rarity.COMMON, 3);
    }

    private static Item pinkSlime() {
        return Item.newItem("Pink slime", "Super rare pink slime!", Rarity.UNCOMMON, 6);
    }

    private static Item cooperOre() {
        return Item.newItem("Cooper ore", "Terraria vibes", Rarity.COMMON, 1);
    }

    public static Item statBuff(CharacterStat stat, double amount, boolean isPercentage) {
        String percent = "";
        if (isPercentage) {
            percent = "%%";
        }
        return new Item(stat.getShortName() + " buff", "+" + amount + percent + " " + stat.getName() + " forever!", Rarity.UNIQUE, 25,
                (profile, item) -> profile.getCharacter().getStats().addStatChange(stat, ChangeDuration.PERMANENT, amount, false));
    }

    public static Item damageBuff(DamageType damageType, double amount, boolean isPercentage) {
        String percent = "";
        if (isPercentage) {
            percent = "%%";
        }
        return new Item(damageType.getShortName() + " damage buff", "+" + amount + percent + " " + damageType.getName() + " damage forever!", Rarity.UNIQUE, 25,
                (profile, item) -> {
                    profile.getCharacter().getDamageMin().addStatChange(damageType, ChangeDuration.PERMANENT, amount, false);
                    profile.getCharacter().getDamageMin().addStatChange(damageType, ChangeDuration.PERMANENT, amount, false);
                });
    }

    public static Item greenMousse(CharacterStat stat, int amount) {
        return new Item("Green mousse " + stat.getShortName(), "+" + amount + " " + stat.getName() + " forever!", Rarity.UNIQUE, 25, (profile, item) -> {
            profile.getProfileTags().add(ProfileTagEnum.withStat(ProfileTagEnum.GREEN_MOUSSE_BOUGHT, stat), 1);
            profile.getCharacter().getStats().addStatChange(stat, ChangeDuration.PERMANENT, amount, false);
        });
    }

    public static Item mousse(CharacterStat stat, int amount, Item item) {
        return new Item(
                item.getName().split(" ")[0] + " mousse " + stat.getShortName(),
                "+" + amount + " " + stat.getName() + " forever!",
                Rarity.UNIQUE,
                item.value * 5,
                (profile, i) -> {
                    profile.getProfileTags().add(ProfileTagEnum.withStat(ProfileTagEnum.GREEN_MOUSSE_BOUGHT, stat), 1);
                    profile.getCharacter().getStats().addStatChange(stat, ChangeDuration.PERMANENT, amount, false);
                });
    }

    public static Item slimeConverter() {
        return new Item("Slime converter", "Allowes you to convert green slime into yellow and backwards!", Rarity.UNIQUE, 100, ((profile, item) -> {
            profile.getProfileTags().add(ProfileTagEnum.SLIME_CONVERTER_BOUGHT, 1);
            profile.getProfileTags().add(ProfileTagEnum.GREEN_SLIME_VALUE, 5);
            profile.getProfileTags().add(ProfileTagEnum.YELLOW_SLIME_VALUE, 22);
            profile.getProfileTags().add(ProfileTagEnum.RED_SLIME_VALUE, 100);
            profile.getProfileTags().add(ProfileTagEnum.PINK_SLIME_VALUE, 300);
        }));
    }

    public static Item ultimateGreenMousse() {
        return new Item("Ultimate green mousse", "+1 for all 6 stats forever!", Rarity.UNIQUE, 30, (profile, item) -> {
            profile.getProfileTags().add(ProfileTagEnum.GREEN_MOUSSE_BOUGHT, 1);
            for (CharacterStat characterStat : CharacterStat.values()) {
                profile.getCharacter().getStats().addStatChange(characterStat, ChangeDuration.PERMANENT, 1, false);
            }
        });
    }

    public static Item greenYammyBook() {
        return new Item("Green yammy book", "Learn how to use skill \"Green yammy!\"", Rarity.UNIQUE, 10, (profile, item) -> {
            if (!profile.getProfileTags().has(ProfileTagEnum.HAS_GREEN_YAMMY_BOOK)) {
                profile.getProfileTags().add(ProfileTagEnum.HAS_GREEN_YAMMY_BOOK, 1);
                profile.getLibrary().getAvailableBooks().add(BookFactory.greenYammyBook(1));
            } else {
                int amount = 30;
                Item replacement = GREEN_SLIME;
                MessageController.print("You already have \"Green yammy book\", so I will give you " + amount + " " + replacement.getName() + " instead!");
                profile.getInventory().put(replacement, amount);
            }
        });
    }

    public static Item poisonSpitBook() {
        return new Item("Poison spit book", "Learn how to use skill \"Poison spit\"!", Rarity.UNIQUE, 10, (profile, item) -> {
            if (!profile.getProfileTags().has(ProfileTagEnum.HAS_POISON_SPIT_BOOK)) {
                profile.getProfileTags().add(ProfileTagEnum.HAS_POISON_SPIT_BOOK, 1);
                profile.getLibrary().getAvailableBooks().add(BookFactory.poisonSpit(1));
            } else {
                int amount = 50;
                Item replacement = GREEN_SLIME;
                MessageController.print("You already have \"Poison spit book\", so I will give you " + amount + " " + replacement.getName() + " instead!");
                profile.getInventory().put(replacement, amount);
            }
        });
    }

    public static Item pinkyDance() {
        return new Item("Pinky dance book", "Learn how to use skill \"Pinky dance\"!", Rarity.UNIQUE, 10, (profile, item) -> {
            if (!profile.getProfileTags().has(ProfileTagEnum.HAS_PINKY_DANCE_BOOK)) {
                profile.getProfileTags().add(ProfileTagEnum.HAS_PINKY_DANCE_BOOK, 1);
                profile.getLibrary().getAvailableBooks().add(BookFactory.pinkyDance(1));
            } else {
                int amount = 30;
                Item replacement = PINK_SLIME;
                MessageController.print("You already have \"Pinky Dance\", so I will give you " + amount + " " + replacement.getName() + " instead!");
                profile.getInventory().put(replacement, amount);
            }
        });
    }

    public static Item casualCooperMinerBook() {
        return new Item("Casual cooper miner book", "Learn how to use skill \"Casual cooper miner\"!", Rarity.UNIQUE, 10, (profile, item) -> {
            if (!profile.getProfileTags().has(ProfileTagEnum.HAS_CASUAL_COOPER_MINER_BOOK)) {
                profile.getProfileTags().add(ProfileTagEnum.HAS_CASUAL_COOPER_MINER_BOOK, 1);
                profile.getLibrary().getAvailableBooks().add(BookFactory.casualCooperMiner(1));
            } else {
                int amount = 50;
                Item replacement = COOPER_ORE;
                MessageController.print("You already have \"Casual cooper miner\", so I will give you " + amount + " " + replacement.getName() + " instead!");
                profile.getInventory().put(replacement, amount);
            }
        });
    }

    public static Item farmerBook(Item item) {
        String name = item.getName() + " farmer";
        return new Item(name + " book", "Learn how to farm more " + item.getName() + " with skill \"" + name + "\"!", Rarity.UNIQUE, 10, ((profile, item1) -> {
            if (profile.getLibrary().getAvailableBooks().stream().noneMatch(book -> book.getBookType().equals(BookType.farmer(item.getName())))) {
                profile.getLibrary().getAvailableBooks().add(BookFactory.resourceFarmerBook(item, 1));
            }
        }));
    }

    public static Item converterFromItem() {
        return Item.newItem("From", "", Rarity.UNDEFINED, 0);
    }

    public static Item converterToItem() {
        return Item.newItem("To", "", Rarity.UNDEFINED, 0);
    }
}
