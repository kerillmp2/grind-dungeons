package dungeon;

import java.util.ArrayList;
import java.util.List;

import creature.CreatureFactory;
import creature.ore.OreFactory;
import profile.Profile;
import profile.ProfileTagEnum;

public class DungeonControllerFactory {

    public static DungeonController slimeDungeon(Profile profile) {
        int dungeonLevel = (int) profile.getProfileTags().get(ProfileTagEnum.SLIME_DUNGEON_LEVEL);
        Dungeon slimeDungeon = new Dungeon("Slime dungeon", generateSlimeDungeonCreatures(dungeonLevel), 1 + (int) Math.pow(2, dungeonLevel));
        return new DungeonController(slimeDungeon, profile);
    }

    public static DungeonController mines(Profile profile) {
        int dungeonLevel = (int) profile.getProfileTags().get(ProfileTagEnum.MINE_LEVEL);
        Dungeon mine = new Dungeon("Mine", generateMineCreatures(dungeonLevel), 1 + (int) Math.pow(2, dungeonLevel));
        return new DungeonController(mine, profile);
    }

    private static List<DungeonCreature> generateSlimeDungeonCreatures(int dungeonLevel) {
        List<DungeonCreature> dungeonCreatures = new ArrayList<>();
        if (dungeonLevel == 1) {
            dungeonCreatures.add(CreatureFactory.greenSlime().withSpawnChance(100.0));
        }
        if (dungeonLevel == 2) {
            dungeonCreatures.add(CreatureFactory.yellowSlime().withSpawnChance(35.0));
            dungeonCreatures.add(CreatureFactory.greenSlime().withSpawnChance(50.0));
            dungeonCreatures.add(CreatureFactory.greenSlime().withSpawnChance(50.0));
            dungeonCreatures.add(CreatureFactory.greenSlime().withSpawnChance(10.0));
        }
        if (dungeonLevel == 3) {
            dungeonCreatures.add(CreatureFactory.pinky().withSpawnChance(1.0));
            dungeonCreatures.add(CreatureFactory.redSlime().withSpawnChance(25.0));
            dungeonCreatures.add(CreatureFactory.yellowSlime().withSpawnChance(80.0));
            dungeonCreatures.add(CreatureFactory.yellowSlime().withSpawnChance(50.0));
            dungeonCreatures.add(CreatureFactory.greenSlime().withSpawnChance(100.0));
            dungeonCreatures.add(CreatureFactory.greenSlime().withSpawnChance(95.0));
            dungeonCreatures.add(CreatureFactory.greenSlime().withSpawnChance(90.0));
        }
        return dungeonCreatures;
    }

    private static List<DungeonCreature> generateMineCreatures(int dungeonLevel) {
        List<DungeonCreature> dungeonCreatures = new ArrayList<>();
        if (dungeonLevel == 1) {
            dungeonCreatures.add(OreFactory.cooperOre().withSpawnChance(100.0));
        }
        return dungeonCreatures;
    }
}
