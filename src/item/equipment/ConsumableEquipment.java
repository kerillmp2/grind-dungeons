package item.equipment;

import java.util.List;

import battlecore.action.ResolveTime;
import item.Rarity;
import logic.ProfileCondition;
import profile.character.BodyPart;
import utils.RandomController;

public class ConsumableEquipment extends CharacterEquipment {

    private final double consumeChance;
    private final ResolveTime consumeTime;

    public ConsumableEquipment(
            String name,
            String description,
            Rarity rarity,
            int value,
            List<ProfileCondition> equipProfileConditions,
            EquipmentType equipmentType,
            EquipAction equipAction,
            EquipAction unequipAction,
            double consumeChance,
            ResolveTime resolveTime)
    {
        super(name, description, rarity, value, equipProfileConditions, equipmentType, equipAction, unequipAction);
        this.consumeChance = consumeChance;
        this.consumeTime = resolveTime;
    }

    public static ConsumableEquipment from(CharacterEquipment c, double consumeChance, ResolveTime resolveTime)
    {
        return new ConsumableEquipment(c.getName(), c.getDescription(), c.getRarity(), c.getValue(), c.getEquipProfileConditions(), c.getEquipmentType(), c.getEquipAction(), c.getUnequipAction(), consumeChance, resolveTime);
    }

    public static ConsumableEquipment empty(BodyPart bodyPart) {
        return ConsumableEquipment.from(CharacterEquipment.empty(bodyPart), 0, ResolveTime.UNDEFINED);
    }

    public boolean isConsumed() {
        return RandomController.roll(consumeChance);
    }

    public double getConsumeChance() {
        return consumeChance;
    }

    public ResolveTime getConsumeTime() {
        return consumeTime;
    }
}
