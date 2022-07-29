package profile.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import battlecore.DamageType;
import battlecore.action.Action;
import creature.CreatureTag;
import item.equipment.CharacterEquipment;
import item.equipment.ConsumableEquipment;
import profile.Profile;
import profile.inventory.Inventory;
import stat.ChangeDuration;
import stat.StatsContainer;
import utils.MessageController;

public class Character implements Serializable {
    private final StatsContainer<CharacterStat, CreatureTag> stats;
    private final StatsContainer<DamageType, CreatureTag> damageMin;
    private final StatsContainer<DamageType, CreatureTag> damageMax;
    private final StatsContainer<DamageType, CreatureTag> armor;
    private final StatsContainer<DamageType, CreatureTag> barrier;
    private final Map<BodyPart, CharacterEquipment> equipment;
    private final List<Action> actions;
    private final Set<DamageType> attackDamageTypes;

    public Character(
            StatsContainer<CharacterStat, CreatureTag> stats,
            StatsContainer<DamageType, CreatureTag> damageMin,
            StatsContainer<DamageType, CreatureTag> damageMax,
            StatsContainer<DamageType, CreatureTag> armor,
            StatsContainer<DamageType, CreatureTag> barrier,
            Map<BodyPart, CharacterEquipment> equipment,
            List<Action> actions,
            Set<DamageType> attackDamageTypes)
    {
        this.stats = stats;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.armor = armor;
        this.barrier = barrier;
        this.equipment = equipment;
        this.actions = actions;
        this.attackDamageTypes = attackDamageTypes;
    }

    public static Character empty() {
        StatsContainer<CharacterStat, CreatureTag> stats = new StatsContainer<>();
        Map<BodyPart, CharacterEquipment> equipment = new HashMap<>();
        for (CharacterStat stat : CharacterStat.values()) {
            stats.addStatChange(stat, ChangeDuration.PERMANENT, 0, false);
        }
        return new Character(stats, new StatsContainer<>(), new StatsContainer<>(), new StatsContainer<>(), new StatsContainer<>(), equipment, new ArrayList<>(), new HashSet<>());
    }

    public StatsContainer<CharacterStat, CreatureTag> getStats() {
        return stats;
    }

    public void equip(CharacterEquipment newEquipment, Inventory inventory) {
        BodyPart bodyPart = newEquipment.getBodyPart();
        inventory.pull(newEquipment);

        if (bodyPart == BodyPart.ONE_HAND) {
            if (!hasEquipmentOn(BodyPart.RIGHT_HAND)) {
                equipment.put(BodyPart.RIGHT_HAND, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            if (!hasEquipmentOn(BodyPart.LEFT_HAND)) {
                equipment.put(BodyPart.LEFT_HAND, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            unequip(BodyPart.RIGHT_HAND, inventory);
            equipment.put(BodyPart.RIGHT_HAND, newEquipment);
            newEquipment.onEquip(this);
            return;
        }

        if (bodyPart == BodyPart.TWO_HANDS) {
            unequip(BodyPart.TWO_HANDS, inventory);
            equipment.put(BodyPart.LEFT_HAND, newEquipment);
            equipment.put(BodyPart.RIGHT_HAND, newEquipment);
            newEquipment.onEquip(this);
            return;
        }

        if (bodyPart == BodyPart.ANY_RING) {
            if (!hasEquipmentOn(BodyPart.RING_1)) {
                equipment.put(BodyPart.RING_1, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            if (!hasEquipmentOn(BodyPart.RING_2)) {
                equipment.put(BodyPart.RING_2, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            if (!hasEquipmentOn(BodyPart.RING_3)) {
                equipment.put(BodyPart.RING_3, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            unequip(BodyPart.RING_1, inventory);
            equipment.put(BodyPart.RING_1, newEquipment);
            newEquipment.onEquip(this);
            return;
        }

        if (bodyPart == BodyPart.ANY_CONSUMABLE) {
            if (!hasEquipmentOn(BodyPart.CONSUMABLE_1)) {
                equipment.put(BodyPart.CONSUMABLE_1, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            if (!hasEquipmentOn(BodyPart.CONSUMABLE_2)) {
                equipment.put(BodyPart.CONSUMABLE_2, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            if (!hasEquipmentOn(BodyPart.CONSUMABLE_3)) {
                equipment.put(BodyPart.CONSUMABLE_3, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            if (!hasEquipmentOn(BodyPart.CONSUMABLE_4)) {
                equipment.put(BodyPart.CONSUMABLE_4, newEquipment);
                newEquipment.onEquip(this);
                return;
            }
            unequip(BodyPart.CONSUMABLE_1, inventory);
            equipment.put(BodyPart.CONSUMABLE_1, newEquipment);
            newEquipment.onEquip(this);
            return;
        }

        unequip(bodyPart, inventory);
        equipment.put(bodyPart, newEquipment);
        newEquipment.onEquip(this);
    }

    public void unequip(BodyPart bodyPart, Inventory inventory) {
        if (bodyPart == BodyPart.LEFT_HAND || bodyPart == BodyPart.RIGHT_HAND) {
            if (hasEquipmentOn(BodyPart.LEFT_HAND)) {
                CharacterEquipment leftHandEquipment = getEquipmentOn(BodyPart.LEFT_HAND);
                CharacterEquipment rightHandEquipment = getEquipmentOn(BodyPart.RIGHT_HAND);
                if (leftHandEquipment.getBodyPart() == BodyPart.TWO_HANDS || rightHandEquipment.getBodyPart() == BodyPart.TWO_HANDS) {
                    CharacterEquipment previousEquipment = getEquipmentOn(BodyPart.LEFT_HAND);
                    equipment.put(BodyPart.LEFT_HAND, CharacterEquipment.empty(BodyPart.LEFT_HAND));
                    equipment.put(BodyPart.RIGHT_HAND, CharacterEquipment.empty(BodyPart.RIGHT_HAND));
                    previousEquipment.onUnequip(this);
                    inventory.put(previousEquipment);
                }
            }
            return;
        }
        if (bodyPart == BodyPart.TWO_HANDS) {
            if (hasEquipmentOn(BodyPart.LEFT_HAND)) {
                CharacterEquipment previousEquipment = getEquipmentOn(BodyPart.LEFT_HAND);
                equipment.put(BodyPart.LEFT_HAND, CharacterEquipment.empty(BodyPart.LEFT_HAND));
                previousEquipment.onUnequip(this);
                inventory.put(previousEquipment);
            }
            return;
        }
        if (hasEquipmentOn(bodyPart)) {
            CharacterEquipment previousEquipment = getEquipmentOn(bodyPart);
            equipment.remove(bodyPart);
            previousEquipment.onUnequip(this);
            inventory.put(previousEquipment);
        }
    }

    public CharacterEquipment getEquipmentOn(BodyPart bodyPart) {
        return equipment.getOrDefault(bodyPart, CharacterEquipment.empty(bodyPart));
    }

    public boolean hasEquipmentOn(BodyPart bodyPart) {
        return equipment.containsKey(bodyPart);
    }

    public void applyStatChange(StatsContainer.Change<CharacterStat> change) {
        stats.addStatChange(change);
    }

    public void apply(CharacterStat characterStat, ChangeDuration duration, int amount, boolean isPercentage) {
        stats.addStatChange(characterStat, duration, amount, isPercentage);
    }

    public void applyTagChange(StatsContainer.Change<CreatureTag> change) {
        stats.addTagChange(change);
    }

    public void apply(CreatureTag characterTag, ChangeDuration duration, int amount, boolean isPercentage) {
        stats.addTagChange(characterTag, duration, amount, isPercentage);
    }

    public void addAttackDamageType(DamageType damageType) {
        this.attackDamageTypes.add(damageType);
    }

    public void removeAttackDamageType(DamageType damageType) {
        this.attackDamageTypes.remove(damageType);
    }

    public String getStatsView() {
        StringBuilder statsView = new StringBuilder();
        for (CharacterStat stat : CharacterStat.values()) {
            statsView.append(stat.getName()).append(": ").append(stats.get(stat)).append("; ");
        }
        return statsView.toString();
    }

    public List<ConsumableEquipment> getConsumables() {
        List<ConsumableEquipment> consumables = new ArrayList<>();
        for (BodyPart bodyPart : BodyPart.getConsumables()) {
            CharacterEquipment equipment = getEquipmentOn(bodyPart);
            if (equipment instanceof ConsumableEquipment consumable) {
                consumables.add(consumable);
            }
        }
        return consumables;
    }

    public void consume(ConsumableEquipment consumableEquipment, Inventory inventory) {
        for (BodyPart bodyPart : BodyPart.getConsumables()) {
            if (consumableEquipment == getEquipmentOn(bodyPart)) {
                unequip(bodyPart, inventory);
                inventory.pull(consumableEquipment);
                return;
            }
        }
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public void removeAction(Action action) {
        this.actions.remove(action);
    }

    public List<Action> getActions() {
        return actions;
    }

    public List<String> getDescriptiveStatsView() {
        List<String> descriptiveStatsView = new ArrayList<>();
        descriptiveStatsView.add(getStatsView());
        return descriptiveStatsView;
    }

    public void onBattleEnd(Profile profile) {
        for (BodyPart bodyPart : BodyPart.getMainBodyParts()) {
            if (hasEquipmentOn(bodyPart)) {
                CharacterEquipment equipment = getEquipmentOn(bodyPart);
                String errorMessage = equipment.canBeEquippedBy(profile);
                if (errorMessage.length() > 0) {
                    MessageController.print(errorMessage);
                    this.unequip(bodyPart, profile.getInventory());
                }
            }
        }
    }

    public StatsContainer<DamageType, CreatureTag> getDamageMin() {
        return damageMin;
    }

    public StatsContainer<DamageType, CreatureTag> getDamageMax() {
        return damageMax;
    }

    public StatsContainer<DamageType, CreatureTag> getArmor() {
        return armor;
    }

    public StatsContainer<DamageType, CreatureTag> getBarrier() {
        return barrier;
    }

    public Map<BodyPart, CharacterEquipment> getEquipment() {
        return equipment;
    }

    public Set<DamageType> getAttackDamageTypes() {
        return attackDamageTypes;
    }
}
