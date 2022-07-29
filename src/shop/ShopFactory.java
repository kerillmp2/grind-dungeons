package shop;

import java.util.ArrayList;
import java.util.List;

import item.Item;
import item.ItemFactory;
import item.Rarity;
import item.equipment.CharacterEquipmentFactory;
import profile.Profile;
import profile.ProfileTagEnum;
import profile.character.CharacterStat;
import utils.Pair;

public class ShopFactory {

    public static Shop slimeShop(Profile profile) {
        int level = (int) profile.getProfileTags().get(ProfileTagEnum.SLIME_SHOP_LEVEL);
        List<ShopItem> items = getItemsForSlimeShop(profile, level);
        List<ShopItem> additionals = getAdditionalOptionsForSlimeShop(profile, level);
        return Shop.withParams("Slime shop", items, additionals, level);
    }

    public static Shop oreShop(Profile profile) {
        int level = (int) profile.getProfileTags().get(ProfileTagEnum.ORE_SHOP_LEVEL);
        List<ShopItem> items = getItemsForOreShop(profile, level);
        List<ShopItem> additionals = getAdditionalOptionsForOreShop(profile, level);
        return Shop.withParams("Ore shop", items, additionals, level);
    }

    private static List<ShopItem> getItemsForSlimeShop(Profile profile, int level) {
        List<ShopItem> items = new ArrayList<>();
        if (level >= 1) {
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.greenSlimeCube(), 5, ItemFactory.GREEN_SLIME));
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.woodenClub(), 10, ItemFactory.GREEN_SLIME));
            if (profile.getProfileTags().get(ProfileTagEnum.GREEN_MOUSSE_STR_BOUGHT) < 1) {
                items.add(ShopItem.fromItem(ItemFactory.greenMousse(CharacterStat.STRENGTH, 1), 5, ItemFactory.GREEN_SLIME));
            }
        }
        if (level >= 2) {
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.leatherCloak(), 30, ItemFactory.GREEN_SLIME));
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.oldBoots(), 35, ItemFactory.GREEN_SLIME));
            if (profile.getProfileTags().get(ProfileTagEnum.GREEN_MOUSSE_AGI_BOUGHT) < 1) {
                items.add(ShopItem.fromItem(ItemFactory.greenMousse(CharacterStat.AGILITY, 1), 10, ItemFactory.GREEN_SLIME));
            }
            if (!profile.getLibrary().hasFarmerBook(ItemFactory.GREEN_SLIME)) {
                items.add(ShopItem.fromItem(ItemFactory.farmerBook(ItemFactory.GREEN_SLIME), 40, ItemFactory.GREEN_SLIME));
            }
        }
        if (level >= 3) {
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.leatherGloves(), 40, ItemFactory.GREEN_SLIME));
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.ironKnife(), 50, ItemFactory.GREEN_SLIME));
            if (profile.getProfileTags().get(ProfileTagEnum.GREEN_MOUSSE_INT_BOUGHT) < 1) {
                items.add(ShopItem.fromItem(ItemFactory.greenMousse(CharacterStat.INTELLIGENCE, 1), 20, ItemFactory.GREEN_SLIME));
            }
            if (profile.getProfileTags().get(ProfileTagEnum.GREEN_MOUSSE_WIS_BOUGHT) < 1) {
                items.add(ShopItem.fromItem(ItemFactory.greenMousse(CharacterStat.WISDOM, 1), 20, ItemFactory.GREEN_SLIME));
            }

        }
        if (level >= 4) {
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.woodenShield(), 50, ItemFactory.GREEN_SLIME));
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.woodenPickaxe(), 60, ItemFactory.GREEN_SLIME));
            if (profile.getProfileTags().get(ProfileTagEnum.GREEN_MOUSSE_LUCK_BOUGHT) < 1) {
                items.add(ShopItem.fromItem(ItemFactory.greenMousse(CharacterStat.LUCK, 1), 30, ItemFactory.GREEN_SLIME));
            }
            if (profile.getProfileTags().get(ProfileTagEnum.GREEN_MOUSSE_CONS_BOUGHT) < 1) {
                items.add(ShopItem.fromItem(ItemFactory.greenMousse(CharacterStat.CONSTITUTION, 1), 40, ItemFactory.GREEN_SLIME));
            }
        }
        if (level >= 5) {
            if (profile.getProfileTags().get(ProfileTagEnum.SLIME_CONVERTER_BOUGHT) < 1) {
                items.add(ShopItem.fromItem(ItemFactory.slimeConverter(), 20, ItemFactory.YELLOW_SLIME));
            }
            if (profile.getProfileTags().get(ProfileTagEnum.GREEN_MOUSSE_BOUGHT) < 1) {
                items.add(ShopItem.fromItem(ItemFactory.ultimateGreenMousse(), 100, ItemFactory.GREEN_SLIME));
            }
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.yellowSlimeCube(), 10, ItemFactory.YELLOW_SLIME));
        }
        return items;
    }

    private static List<ShopItem> getItemsForOreShop(Profile profile, int level) {
        List<ShopItem> items = new ArrayList<>();
        if (level >= 1) {
            items.add(ShopItem.fromItem(CharacterEquipmentFactory.cooperPickaxe(), 20, ItemFactory.COOPER_ORE));
        }
        return items;
    }

    private static List<ShopItem> getAdditionalOptionsForSlimeShop(Profile profile, int level) {
        List<ShopItem> items = new ArrayList<>();
        switch ((int) profile.getProfileTags().get(ProfileTagEnum.SLIME_DUNGEON_LEVEL)) {
            case 1 -> items.add(new ShopItem(
                    Item.noPickup("Level up dungeon!", "(1 -> 2)$ New dangerous enemy!", Rarity.UNDEFINED, 9998),
                    40,
                    ItemFactory.GREEN_SLIME,
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.SLIME_DUNGEON_LEVEL, 1);
                    })
            );
            case 2 -> items.add(new ShopItem(
                    Item.noPickup("Level up dungeon!", "(2 -> 3)$ New dangerous enemy!", Rarity.UNDEFINED, 9998),
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.SLIME_DUNGEON_LEVEL, 1);
                    },
                    new Pair<>(ItemFactory.GREEN_SLIME, 50),
                    new Pair<>(ItemFactory.YELLOW_SLIME, 10)
            ));
            case 3 -> items.add(new ShopItem(
                    Item.noPickup("Level up dungeon!", "(3 -> 4)$ New dangerous enemy!", Rarity.UNDEFINED, 9998),
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.SLIME_DUNGEON_LEVEL, 1);
                    },
                    new Pair<>(ItemFactory.GREEN_SLIME, 100),
                    new Pair<>(ItemFactory.YELLOW_SLIME, 30),
                    new Pair<>(ItemFactory.RED_SLIME, 15)
            ));
        }
        switch (level) {
            case 1 -> items.add(new ShopItem(
                    Item.noPickup("Level up shop!", "(1 -> 2)$ New items!", Rarity.UNDEFINED, 9999),
                    10,
                    ItemFactory.GREEN_SLIME,
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.SLIME_SHOP_LEVEL, 1);
                    }));
            case 2 -> items.add(new ShopItem(
                    Item.noPickup("Level up shop!", "(2 -> 3)$ New items!", Rarity.UNDEFINED, 9999),
                    30,
                    ItemFactory.GREEN_SLIME,
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.SLIME_SHOP_LEVEL, 1);
                    }));
            case 3 -> items.add(new ShopItem(
                    Item.noPickup("Level up shop!", "(3 -> 4)$ New items!", Rarity.UNDEFINED, 9999),
                    80,
                    ItemFactory.GREEN_SLIME,
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.SLIME_SHOP_LEVEL, 1);
                    }));
            case 4 -> items.add(new ShopItem(
                    Item.noPickup("Level up shop!", "(4 -> 5)$ New items and converter!", Rarity.UNDEFINED, 9999),
                    100,
                    ItemFactory.GREEN_SLIME,
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.SLIME_SHOP_LEVEL, 1);
                    }));
        }
        return items;
    }

    private static List<ShopItem> getAdditionalOptionsForOreShop(Profile profile, int level) {
        List<ShopItem> items = new ArrayList<>();
        switch ((int) profile.getProfileTags().get(ProfileTagEnum.SLIME_DUNGEON_LEVEL)) {
            case 1 -> items.add(new ShopItem(
                    Item.noPickup("Level up mine!", "(1 -> 2)$ New ore in mines!", Rarity.UNDEFINED, 9998),
                    40,
                    ItemFactory.COOPER_ORE,
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.MINE_LEVEL, 1);
                    })
            );
            case 2 -> items.add(new ShopItem(
                    Item.noPickup("Level up mine!", "(2 -> 3)$ New ore in mines!", Rarity.UNDEFINED, 9998),
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.MINE_LEVEL, 1);
                    },
                    new Pair<>(ItemFactory.COOPER_ORE, 50)
            ));
            case 3 -> items.add(new ShopItem(
                    Item.noPickup("Level up mine!", "(3 -> 4)$ New ore in mines!", Rarity.UNDEFINED, 9998),
                    (shopController, shopItem) -> {
                        BuyAction.defaultAction().resolve(shopController, shopItem);
                        shopController.getPlayer().getProfileTags().add(ProfileTagEnum.MINE_LEVEL, 1);
                    },
                    new Pair<>(ItemFactory.COOPER_ORE, 100)
            ));
        }
        int levelUpCost = (int) (10 + (Math.pow(1.4, level) + 5) * level + Math.pow(1.05, level));
        items.add(levelUpShopItem(ProfileTagEnum.MINE_LEVEL, level, new Pair<>(ItemFactory.COOPER_ORE, levelUpCost)));
        return items;
    }


    public static Shop shopFromTag(Profile profile, ProfileTagEnum profileTag) {
        switch (profileTag) {
            case SLIME_SHOP_LEVEL -> {
                return slimeShop(profile);
            }
            case ORE_SHOP_LEVEL -> {
                return oreShop(profile);
            }
            default -> {
                return Shop.withParams("Dummy shop", new ArrayList<>(), new ArrayList<>(), 0);
            }
        }
    }

    @SafeVarargs
    public static ShopItem levelUpShopItem(ProfileTagEnum shopTag, int curLevel, Pair<Item, Integer>... costs) {
        return new ShopItem(
                Item.noPickup("Level up shop!", "(" + curLevel + " -> " + (curLevel + 1) + ")$ New items!", Rarity.UNDEFINED, 9999),
                (shopController, shopItem) -> {
                    BuyAction.defaultAction().resolve(shopController, shopItem);
                    shopController.getPlayer().getProfileTags().add(shopTag, 1);
                },
                costs);
    }
}
