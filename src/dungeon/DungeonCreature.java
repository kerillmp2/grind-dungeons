package dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import battlecore.DamageType;
import battlecore.action.Action;
import battlecore.action.ResolveTime;
import battlecore.battlefield.Battlefield;
import battlecore.battlefield.BattlefieldSide;
import creature.Creature;
import battlecore.battlefield.ObjectStatus;
import creature.CreatureTag;
import item.Item;
import profile.character.CharacterStat;
import stat.StatsContainer;
import utils.RandomController;

public class DungeonCreature extends Creature {
    private final List<LootItem> lootTable;
    private double spawnChance;

    public DungeonCreature(
            Set<ObjectStatus> statusSet,
            Map<ResolveTime, List<Action>> actions,
            Battlefield battlefield,
            BattlefieldSide battlefieldSide,
            StatsContainer<CharacterStat, CreatureTag> statsContainer,
            StatsContainer<DamageType, CreatureTag> damageMin,
            StatsContainer<DamageType, CreatureTag> damageMax,
            StatsContainer<DamageType, CreatureTag> armor,
            StatsContainer<DamageType, CreatureTag> barrier,
            StatsContainer<DamageType, CreatureTag> resist,
            List<DamageType> attackDamageType,
            String name,
            int currentHP,
            int maxHP,
            int powerLevel,
            double spawnChance,
            List<LootItem> lootTable) {
        super(statusSet, actions, battlefield, battlefieldSide, statsContainer, damageMin, damageMax, armor, barrier, resist, attackDamageType, name, currentHP, maxHP, powerLevel);
        this.lootTable = lootTable;
        this.spawnChance = spawnChance;
    }

    public List<Item> getLoot() {
        List<Item> loot = new ArrayList<>();
        for (LootItem lootItem : lootTable) {
            double spawnChance = lootItem.getChance();
            boolean spawned = RandomController.roll(spawnChance);
            if (spawned) {
                loot.add(lootItem.getItem());
            }
        }
        return loot;
    }

    public DungeonCreature withSpawnChance(double spawnChance) {
        this.spawnChance = spawnChance;
        return this;
    }

    public List<LootItem> getLootTable() {
        return lootTable;
    }

    public double getSpawnChance() {
        return spawnChance;
    }
}
