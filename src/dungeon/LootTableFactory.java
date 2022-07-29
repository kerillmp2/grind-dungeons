package dungeon;

import java.util.ArrayList;
import java.util.List;

import item.ItemFactory;
import item.equipment.CharacterEquipmentFactory;

public class LootTableFactory {

    public static List<LootItem> forGreenSlime() {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(ItemFactory.GREEN_SLIME, 30));
        lootItems.add(new LootItem(ItemFactory.GREEN_SLIME, 30));
        lootItems.add(new LootItem(ItemFactory.GREEN_SLIME, 20));
        lootItems.add(new LootItem(ItemFactory.GREEN_SLIME, 20));
        lootItems.add(new LootItem(ItemFactory.greenYammyBook(), 1));
        lootItems.add(new LootItem(CharacterEquipmentFactory.greenSlimeRing(), 1));
        lootItems.add(new LootItem(CharacterEquipmentFactory.greenSlimeShield(), 1));
        lootItems.add(new LootItem(CharacterEquipmentFactory.greenSlimeStaff(), 1));
        lootItems.add(new LootItem(ItemFactory.poisonSpitBook(), 0.1));
        return lootItems;
    }

    public static List<LootItem> forYellowSlime() {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(ItemFactory.GREEN_SLIME, 34));
        lootItems.add(new LootItem(ItemFactory.GREEN_SLIME, 34));
        lootItems.add(new LootItem(ItemFactory.GREEN_SLIME, 30));
        lootItems.add(new LootItem(ItemFactory.YELLOW_SLIME, 25));
        lootItems.add(new LootItem(ItemFactory.YELLOW_SLIME, 25));
        lootItems.add(new LootItem(ItemFactory.YELLOW_SLIME, 10));
        lootItems.add(new LootItem(ItemFactory.YELLOW_SLIME, 10));
        lootItems.add(new LootItem(ItemFactory.poisonSpitBook(), 0.3));
        lootItems.add(new LootItem(CharacterEquipmentFactory.yellowSlimeRing(), 1));
        lootItems.add(new LootItem(CharacterEquipmentFactory.yellowJellyHelmet(), 0.4));
        lootItems.add(new LootItem(CharacterEquipmentFactory.yellowSlimeSpear(), 0.5));
        return lootItems;
    }

    public static List<LootItem> forRedSlime() {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(ItemFactory.YELLOW_SLIME, 25));
        lootItems.add(new LootItem(ItemFactory.YELLOW_SLIME, 25));
        lootItems.add(new LootItem(ItemFactory.YELLOW_SLIME, 20));
        lootItems.add(new LootItem(ItemFactory.YELLOW_SLIME, 20));
        lootItems.add(new LootItem(ItemFactory.RED_SLIME, 20));
        lootItems.add(new LootItem(ItemFactory.RED_SLIME, 20));
        lootItems.add(new LootItem(ItemFactory.RED_SLIME, 13));
        lootItems.add(new LootItem(ItemFactory.RED_SLIME, 13));
        lootItems.add(new LootItem(ItemFactory.poisonSpitBook(), 0.8));
        return lootItems;
    }

    public static List<LootItem> forPinky() {
        List<LootItem> lootItems = new ArrayList<>();
        for (int i = 10; i >= 0; i--) {
            lootItems.add(new LootItem(ItemFactory.PINK_SLIME, 100 - i * 10));
        }
        lootItems.add(new LootItem(ItemFactory.poisonSpitBook(), 2));
        lootItems.add(new LootItem(ItemFactory.pinkyDance(), 1));
        return lootItems;
    }

    public static List<LootItem> forCooperOre() {
        List<LootItem> lootItems = new ArrayList<>();
        lootItems.add(new LootItem(ItemFactory.COOPER_ORE, 100));
        lootItems.add(new LootItem(ItemFactory.COOPER_ORE, 33));
        lootItems.add(new LootItem(ItemFactory.COOPER_ORE, 20));
        lootItems.add(new LootItem(ItemFactory.COOPER_ORE, 10));
        lootItems.add(new LootItem(ItemFactory.COOPER_ORE, 5));
        lootItems.add(new LootItem(ItemFactory.casualCooperMinerBook(), 0.1));
        return lootItems;
    }
}
