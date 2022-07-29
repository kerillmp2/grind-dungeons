package dungeon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import creature.CreatureFactory;
import utils.HasName;
import utils.RandomController;

public class Dungeon implements HasName {
    private final String name;
    private final List<DungeonCreature> spawnChances;
    private final int powerLimit;

    public Dungeon(String name, List<DungeonCreature> spawnChances, int powerLimit) {
        this.name = name;
        this.spawnChances = spawnChances;
        this.powerLimit = powerLimit;
    }

    public DungeonCreatureGenerator getGenerator() {
        return () -> {
            int currentPower = 0;
            List<DungeonCreature> spawnedCreatures = new ArrayList<>();
            for (DungeonCreature creature : spawnChances) {
                if (currentPower + creature.getPowerLevel() <= powerLimit) {
                    double spawnChance = creature.getSpawnChance();
                    boolean spawned = RandomController.roll(spawnChance);
                    if (spawned) {
                        spawnedCreatures.add(CreatureFactory.dungeonCreatureFrom(creature, creature.getLootTable(), spawnChance));
                        currentPower += creature.getPowerLevel();
                    }
                }
            }
            if (spawnedCreatures.size() == 0) {
                Optional<DungeonCreature> mostChances = spawnChances.stream().max(Comparator.comparingDouble(DungeonCreature::getSpawnChance));
                if (mostChances.isPresent()) {
                    DungeonCreature creature = mostChances.get();
                    spawnedCreatures.add(CreatureFactory.dungeonCreatureFrom(creature));
                } else {
                    spawnedCreatures.add(CreatureFactory.dungeonCreatureFrom(CreatureFactory.dummy()));
                }
            }
            return spawnedCreatures;
        };
    }

    @Override
    public String getName() {
        return name;
    }
}
