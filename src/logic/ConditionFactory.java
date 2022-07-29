package logic;

import item.equipment.CharacterEquipment;
import profile.Profile;
import profile.ProfileTagEnum;
import profile.character.BodyPart;
import profile.character.CharacterStat;

public class ConditionFactory {

    public static ProfileCondition statCondition(CharacterStat stat, int amount) {
        return new ProfileCondition() {
            @Override
            public boolean check(Profile profile) {
                return profile.getCharacter().getStats().get(stat) >= amount;
            }

            @Override
            public String getDescription() {
                return stat.getShortName() + " >= " + amount;
            }

            @Override
            public String getErrorMessage() {
                return "You have less than " + amount + " " + stat.getShortName() + "!";
            }
        };
    }

    public static ProfileCondition usageLimitCondition(ProfileTagEnum tag, int maxUsages) {
        return new ProfileCondition() {
            @Override
            public boolean check(Profile profile) {
                return profile.getProfileTags().get(tag) < maxUsages;
            }

            @Override
            public String getDescription() {
                if (maxUsages == 1) {
                    return "Can be used only " + maxUsages + " time!";
                } else {
                    return "Can be used only " + maxUsages + " times!";
                }
            }

            @Override
            public String getErrorMessage() {
                return "You have already used this item " + maxUsages + " times!";
            }
        };
    }

    public static ItemCondition forBodyPart(BodyPart bodyPart) {
        return item -> {
            if (item instanceof CharacterEquipment characterEquipment) {
                if (bodyPart == BodyPart.RIGHT_HAND || bodyPart == BodyPart.LEFT_HAND) {
                    return characterEquipment.getBodyPart() == BodyPart.ONE_HAND || characterEquipment.getBodyPart() == BodyPart.TWO_HANDS;
                }
                if (bodyPart == BodyPart.RING_1 || bodyPart == BodyPart.RING_2 || bodyPart == BodyPart.RING_3) {
                    return characterEquipment.getBodyPart() == BodyPart.ANY_RING;
                }
                if (bodyPart == BodyPart.CONSUMABLE_1 || bodyPart == BodyPart.CONSUMABLE_2 || bodyPart == BodyPart.CONSUMABLE_3 || bodyPart == BodyPart.CONSUMABLE_4) {
                    return characterEquipment.getBodyPart() == BodyPart.ANY_CONSUMABLE;
                }
                return characterEquipment.getBodyPart() == bodyPart;
            }
            return false;
        };
    }
}
