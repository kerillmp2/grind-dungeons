package challenges;

import java.util.ArrayList;
import java.util.List;

import battlecore.Battle;
import battlecore.BattleCondition;
import creature.Creature;
import creature.CreatureFactory;
import dungeon.DungeonCreature;
import profile.ProfileTagEnum;
import utils.threads.ExitThread;

public class ChallengeProcessFactory {

    public static ChallengeProcess theHordeChallenge(int level) {
        List<DungeonCreature> enemies = new ArrayList<>();
        switch (level) {
            case 1 -> {
                for (int i = 0; i < 5; i++) {
                    enemies.add(CreatureFactory.greenSlime());
                }
            }
            case 2 -> {
                for (int i = 0; i < 10; i++) {
                    enemies.add(CreatureFactory.greenSlime());
                }
            }
            default -> enemies.add(CreatureFactory.undying());
        }
        return ChallengeProcessFactory.singleBattleProcess(enemies);
    }

    public static ChallengeProcess onePunchManChallenge(int level) {
        List<DungeonCreature> enemy = new ArrayList<>();
        Creature dummy;
        switch (level) {
            case 1 -> dummy = CreatureFactory.punchingDummy(5);
            case 2 -> dummy = CreatureFactory.punchingDummy(10);
            case 3 -> dummy = CreatureFactory.punchingDummy(25);
            default -> dummy = CreatureFactory.undying();
        }
        enemy.add(CreatureFactory.dungeonCreatureFrom(dummy));
        return ChallengeProcessFactory.singleBattleWithLoseCondition(enemy, battleController -> battleController.getTurnController().getTurnCounter() >= 1);
    }

    public static ChallengeProcess tankChallenge(int level) {
        List<DungeonCreature> enemy = new ArrayList<>();
        Creature dummy;
        switch (level) {
            case 1 -> dummy = CreatureFactory.fightingDummy(15);
            case 2 -> dummy = CreatureFactory.fightingDummy(25);
            case 3 -> dummy = CreatureFactory.fightingDummy(40);
            default -> dummy = CreatureFactory.undying();
        }
        enemy.add(CreatureFactory.dungeonCreatureFrom(dummy));
        return ChallengeProcessFactory.singleBattleProcess(enemy);
    }

    public static ChallengeProcess getChallenge(ProfileTagEnum profileTag, int level) {
        switch (profileTag) {
            case CHALLENGE_THE_HORDE -> {
                return theHordeChallenge(level);
            }
            case CHALLENGE_ONE_PUNCH_MAN -> {
                return onePunchManChallenge(level);
            }
            case CHALLENGE_TANK -> {
                return tankChallenge(level);
            }
            default -> {
                return profile -> false;
            }
        }
    }

    public static ChallengeProcess singleBattleProcess(List<DungeonCreature> enemies) {
        return profile -> Battle.processSingleBattle(profile, enemies, new ExitThread());
    }

    public static ChallengeProcess singleBattleWithLoseCondition(List<DungeonCreature> enemies, BattleCondition condition) {
        return profile -> Battle.processSingleBattleWithLoseCondition(profile, enemies, condition, new ExitThread());
    }
}
