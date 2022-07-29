package creature.ore;

import java.util.List;

import battlecore.DamageType;
import creature.CreatureFactory;
import dungeon.DungeonCreature;
import dungeon.LootItem;
import dungeon.LootTableFactory;

public class OreFactory extends CreatureFactory {

    public static DungeonCreature cooperOre() {
        Ore cooperOre = buildOreFromTemplate(
                CreatureTemplate.empty().withName("Cooper ore").withAdditionalHP(8).withFloatArmor(DamageType.MINING, 3)
        );
        List<LootItem> lootTable = LootTableFactory.forCooperOre();
        return dungeonCreatureFrom(cooperOre, lootTable, 0);
    }

    private static Ore buildOreFromTemplate(CreatureTemplate creatureTemplate) {
        return creatureTemplate.buildOre();
    }
}
