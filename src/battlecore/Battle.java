package battlecore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import creature.CharacterCreature;
import creature.Creature;
import battlecore.controllers.BattleController;
import dungeon.DungeonCreature;
import dungeon.DungeonCreatureGenerator;
import item.Item;
import profile.Profile;
import utils.Constants;
import utils.MessageController;
import utils.threads.ExitThread;

public class Battle {

    public static boolean processBattleSeries(Profile profile, DungeonCreatureGenerator enemiesGenerator, BattleCondition loseCondition, ExitThread exitThread) {
        boolean wonByCondition = false;
        CharacterCreature character = CharacterCreature.fromProfile(profile);
        BattleStatus battleStatus = BattleStatus.FIRST_PLAYER_WIN;
        List<DungeonCreature> defeatedEnemies = new ArrayList<>();
        int battleCounter = 0;
        exitThread.start();
        while (battleStatus == BattleStatus.FIRST_PLAYER_WIN && battleCounter < 100 && !exitThread.isFinished()) {
            List<Creature> characterCreature = List.of(character);
            List<DungeonCreature> enemies = enemiesGenerator.generateEnemies();
            Battler battler = new Battler(profile.getName(), characterCreature);
            Battler enemy = new Battler("Dungeon creatures", enemies);
            battleStatus = BattleController.processBattleWithLoseCondition(battler, enemy, loseCondition, exitThread);
            MessageController.printBattleStatus(battleStatus, profile.getName());
            if (battleStatus == BattleStatus.FIRST_PLAYER_WIN) {
                defeatedEnemies.addAll(enemies);
            }

            battleCounter++;
            if (Constants.SKIP_ALL_ANIMATIONS.value == 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2000);
                } catch (InterruptedException ignored) {

                }
            }
        }
        processLooting(profile, character, defeatedEnemies);
        exitThread.finish();
        profile.onBattleEnd();
        profile.saveProfile();
        return wonByCondition;
    }

    public static boolean processSingleBattleWithLoseCondition(Profile profile, List<DungeonCreature> enemies, BattleCondition loseCondition, ExitThread exitThread) {
        CharacterCreature character = CharacterCreature.fromProfile(profile);
        exitThread.start();

        List<Creature> characterCreature = List.of(character);
        Battler battler = new Battler(character.getName(), characterCreature);
        Battler enemy = new Battler("Dungeon creatures", enemies);

        BattleStatus battleStatus = BattleController.processBattleWithLoseCondition(battler, enemy, loseCondition, exitThread);
        MessageController.printBattleStatus(battleStatus, character.getName());

        if (battleStatus == BattleStatus.FIRST_PLAYER_WIN) {
            processLooting(profile, character, enemies);
        }

        if (Constants.SKIP_ALL_ANIMATIONS.value == 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(800);
            } catch (InterruptedException ignored) {

            }
        }

        exitThread.finish();
        profile.onBattleEnd();
        profile.saveProfile();
        return battleStatus == BattleStatus.FIRST_PLAYER_WIN;
    }

    public static boolean processSingleBattle(Profile profile, List<DungeonCreature> enemies, ExitThread exitThread) {
        return processSingleBattleWithLoseCondition(profile, enemies, c -> false, exitThread);
    }

    private static void processLooting(Profile profile, CharacterCreature character, List<DungeonCreature> killedCreatures) {
        Map<Item, Integer> lootMap = new HashMap<>();
        for (DungeonCreature creature : killedCreatures) {
            List<Item> lootList = creature.getLoot();
            for (Item loot : lootList)  {
                lootMap.put(loot, lootMap.getOrDefault(loot, 0) + 1);
            }
        }

        for (Item item : character.getAdditionalLoot()) {
            item.onPickUp(profile);
            lootMap.put(item, lootMap.getOrDefault(item, 0) + 1);
        }
        character.clearAdditionalLoot();

        for (Map.Entry<Item, Integer> loot : lootMap.entrySet()) {
            for (int i = 0; i < loot.getValue(); i++) {
                loot.getKey().onPickUp(profile);
            }
        }

        MessageController.printLoot(lootMap, "Battle rewards");
    }
}
