package challenges;

import java.util.HashMap;
import java.util.Map;

import item.Item;
import item.ItemFactory;
import profile.ProfileTagEnum;
import utils.MessageController;

public class ChallengeRewardFactory {

    private static ChallengeReward theHordeReward(int level) {
        Map<Item, Integer> rewards = new HashMap<>();
        switch (level) {
            case 1 -> rewards.put(ItemFactory.GREEN_SLIME, 7);
            case 2 -> rewards.put(ItemFactory.GREEN_SLIME, 20);
            case 3 -> rewards.put(ItemFactory.GREEN_SLIME, 30);
        }
        return giveItems(rewards);
    }

    private static ChallengeReward onePunchManReward(int level) {
        Map<Item, Integer> rewards = new HashMap<>();
        switch (level) {
            case 1 -> rewards.put(ItemFactory.GREEN_SLIME, 7);
            case 2 -> rewards.put(ItemFactory.GREEN_SLIME, 80);
        }
        return giveItems(rewards);
    }

    private static ChallengeReward tankReward(int level) {
        Map<Item, Integer> rewards = new HashMap<>();
        switch (level) {
            case 1 -> rewards.put(ItemFactory.GREEN_SLIME, 7);
            case 2 -> rewards.put(ItemFactory.GREEN_SLIME, 90);
        }
        return giveItems(rewards);
    }

    public static ChallengeReward getReward(ProfileTagEnum profileTag, int level) {
        switch (profileTag) {
            case CHALLENGE_THE_HORDE -> {
                return theHordeReward(level);
            }
            case CHALLENGE_ONE_PUNCH_MAN -> {
                return onePunchManReward(level);
            }
            case CHALLENGE_TANK -> {
                return tankReward(level);
            }
            default -> {
                return profile -> {};
            }
        }
    }

    public static ChallengeReward giveItems(Map<Item, Integer> items) {
        return profile -> {
            for (Map.Entry<Item, Integer> loot : items.entrySet()) {
                for (int i = 0; i < loot.getValue(); i++) {
                    loot.getKey().onPickUp(profile);
                }
            }
            MessageController.printLoot(items, "Challenge rewards");
        };
    }
}
