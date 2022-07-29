package item.equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import battlecore.DamageType;
import battlecore.action.Action;
import battlecore.action.ActionFactory;
import battlecore.action.ResolveTime;
import creature.CreatureTag;
import item.ItemFactory;
import item.Rarity;
import logic.ConditionFactory;
import logic.ProfileCondition;
import profile.character.CharacterStat;
import stat.ChangeDuration;
import stat.StatsContainer;

import static profile.character.CharacterStat.AGILITY;
import static profile.character.CharacterStat.INTELLIGENCE;
import static profile.character.CharacterStat.STRENGTH;
import static profile.character.CharacterStat.WISDOM;
import static utils.RandomController.randomElementOf;
import static utils.RandomController.randomInt;

public class CharacterEquipmentFactory {

    public static CharacterEquipment woodenClub() {
        return EquipmentTemplate.empty().withName("Wooden club").withRarity(Rarity.COMMON)
                .withDamageChange(DamageType.PHYSICAL, 1)
                .withAttackDamageType(DamageType.PHYSICAL)
                .withType(EquipmentType.ONE_HANDED)
                .withCondition(ConditionFactory.statCondition(STRENGTH, 2))
                .withValue(5)
                .build();
    }

    public static CharacterEquipment leatherCloak() {
        return EquipmentTemplate.empty().withName("Leather cloak").withRarity(Rarity.COMMON)
                .withArmorChange(DamageType.PHYSICAL, 1)
                .withType(EquipmentType.ARMOR)
                .withCondition(ConditionFactory.statCondition(STRENGTH, 3))
                .withValue(6)
                .build();
    }

    public static CharacterEquipment oldBoots() {
        return EquipmentTemplate.empty().withName("Old boots").withRarity(Rarity.COMMON)
                .withStatChange(AGILITY, 1)
                .withBarrierChange(DamageType.PHYSICAL, 2)
                .withType(EquipmentType.BOOTS)
                .withValue(7)
                .build();
    }

    public static CharacterEquipment leatherGloves() {
        return EquipmentTemplate.empty().withName("Leather gloves").withRarity(Rarity.COMMON)
                .withStatChange(STRENGTH, 1)
                .withBarrierChange(DamageType.PHYSICAL, 1)
                .withType(EquipmentType.GLOVES)
                .withValue(8)
                .build();
    }

    public static CharacterEquipment ironKnife() {
        return EquipmentTemplate.empty().withName("Iron knife").withRarity(Rarity.COMMON)
                .withTagChange(CreatureTag.CRIT_CHANCE, 5)
                .withDamageChange(DamageType.PHYSICAL, 2)
                .withAttackDamageType(DamageType.PHYSICAL)
                .withType(EquipmentType.ONE_HANDED)
                .withValue(9)
                .build();
    }

    public static CharacterEquipment woodenShield() {
        return EquipmentTemplate.empty().withName("Wooden shield").withRarity(Rarity.COMMON)
                .withArmorChange(DamageType.PHYSICAL, 1)
                .withBarrierChange(DamageType.PHYSICAL, 3)
                .withType(EquipmentType.ONE_HANDED)
                .withValue(9)
                .build();
    }

    public static CharacterEquipment greenSlimeStaff() {
        return EquipmentTemplate.empty().withName("Green slime staff").withRarity(Rarity.RARE)
                .withDamageChange(DamageType.POISON, 2)
                .withStatChange(INTELLIGENCE, 1)
                .withAttackDamageType(DamageType.POISON)
                .withType(EquipmentType.ONE_HANDED)
                .withCondition(ConditionFactory.statCondition(INTELLIGENCE, 3))
                .withValue(7)
                .build();
    }

    public static CharacterEquipment greenSlimeShield() {
        return EquipmentTemplate.empty().withName("Green slime shield").withRarity(Rarity.RARE)
                .withBarrierChange(DamageType.POISON, randomInt(2, 4))
                .withBarrierChange(DamageType.PHYSICAL, randomInt(3, 5))
                .withDamageChange(DamageType.POISON, 1)
                .withType(EquipmentType.ONE_HANDED)
                .withCondition(ConditionFactory.statCondition(STRENGTH, 3))
                .withValue(8)
                .build();
    }

    public static CharacterEquipment greenSlimeRing() {
        return EquipmentTemplate.empty().withName("Green slime ring").withRarity(Rarity.RARE)
                .withBarrierChange(DamageType.POISON, 2)
                .withStatChange(WISDOM, 1)
                .withType(EquipmentType.RING)
                .withValue(9)
                .build();
    }

    public static ConsumableEquipment greenSlimeCube() {
        return EquipmentTemplate.empty().withName("Green slime cube").withRarity(Rarity.COMMON)
                .withType(EquipmentType.CONSUMABLE)
                .withBarrierChange(DamageType.PHYSICAL, 2)
                .withValue(10)
                .buildConsumable(10, ResolveTime.ON_BATTLE_END);
    }

    // YELLOW SLIME

    public static ConsumableEquipment yellowSlimeCube() {
        return EquipmentTemplate.empty().withName("Yellow slime cube").withRarity(Rarity.COMMON)
                .withType(EquipmentType.CONSUMABLE)
                .withDamageChange(DamageType.PHYSICAL, 1)
                .withValue(11)
                .buildConsumable(10, ResolveTime.ON_BATTLE_END);
    }

    public static CharacterEquipment yellowSlimeRing() {
        return EquipmentTemplate.empty().withName("Yellow slime ring").withRarity(Rarity.RARE)
                .withBarrierChange(DamageType.PHYSICAL, 2)
                .withStatChange(INTELLIGENCE, 1)
                .withType(EquipmentType.RING)
                .withValue(9)
                .build();
    }

    public static CharacterEquipment yellowJellyHelmet() {
        Action action = ActionFactory.dealDamageAction(
                ResolveTime.AFTER_ATTACKED,
                DamageType.POISON,
                50,
                5,
                "spits with yellow jelly helmet to"
        );
        return EquipmentTemplate.empty().withName("Yellow jelly helmet").withRarity(Rarity.RARE)
                .addDescription(action.getDescription())
                .withArmorChange(DamageType.PHYSICAL, 1)
                .withArmorChange(DamageType.POISON, 1)
                .withDamageChange(DamageType.POISON, 1)
                .withType(EquipmentType.HELMET)
                .withValue(10)
                .buildWithAction(
                        (EquipAction) character -> character.addAction(action),
                        (EquipAction) character -> character.removeAction(action));
    }

    public static CharacterEquipment yellowSlimeSpear() {
        Action action = ActionFactory.dealDamageAction(
                ResolveTime.AFTER_ATTACK,
                DamageType.PHYSICAL,
                30,
                10,
                "attacks again with yellow slime spear "
        );
        return EquipmentTemplate.empty().withName("Yellow slime spear").withRarity(Rarity.RARE)
                .addDescription(action.getDescription())
                .withDamageChange(DamageType.PHYSICAL, 6)
                .withStatChange(STRENGTH, 2)
                .withAttackDamageType(DamageType.PHYSICAL)
                .withType(EquipmentType.TWO_HANDED)
                .withValue(11)
                .build(action);
    }

    // --------------------------------------------------------------------------------------------------------------------------
    // MINING
    // --------------------------------------------------------------------------------------------------------------------------
    private static final Action PICKAXE_HIT = ActionFactory.trigger(ResolveTime.AFTER_ATTACK, ResolveTime.AFTER_PICKAXE_HIT);


    public static CharacterEquipment woodenPickaxe() {
        return EquipmentTemplate.empty().withName("Wooden pickaxe").withRarity(Rarity.COMMON)
                .withDamageChange(DamageType.MINING, 1)
                .withAttackDamageType(DamageType.MINING)
                .withType(EquipmentType.TWO_HANDED)
                .withValue(10)
                .build(PICKAXE_HIT);
    }

    public static CharacterEquipment cooperPickaxe() {
        Action additionalCooper = ActionFactory.giveItem(ResolveTime.AFTER_PICKAXE_HIT, ItemFactory.COOPER_ORE, 1, 10);
        return EquipmentTemplate.empty().withName("Cooper pickaxe").withRarity(Rarity.COMMON)
                .addDescription(additionalCooper.getDescription())
                .withDamageChange(DamageType.MINING, 2)
                .withAttackDamageType(DamageType.MINING)
                .withType(EquipmentType.TWO_HANDED)
                .withValue(12)
                .build(additionalCooper, PICKAXE_HIT);
    }

    // --------------------------------------------------------------------------------------------------------------------------
    //
    // --------------------------------------------------------------------------------------------------------------------------


    private static class EquipmentTemplate implements Serializable {
        private String name;
        private Rarity rarity;
        private String descriptionTemplate;
        private EquipmentType equipmentType;
        private final List<ProfileCondition> equipProfileConditions;
        private final List<Integer> values;
        private final List<StatsContainer.Change<CharacterStat>> statChanges;
        private final List<StatsContainer.Change<CreatureTag>> tagChanges;
        private final List<StatsContainer.Change<DamageType>> armorChanges;
        private final List<StatsContainer.Change<DamageType>> minDamageChanges;
        private final List<StatsContainer.Change<DamageType>> maxDamageChanges;
        private final List<StatsContainer.Change<DamageType>> damageChanges;
        private final List<StatsContainer.Change<DamageType>> barrierChanges;
        private final Set<DamageType> attackDamageTypes;
        private int value;

        public EquipmentTemplate(
                String name,
                Rarity rarity,
                String descriptionTemplate,
                EquipmentType equipmentType,
                List<ProfileCondition> equipProfileConditions,
                List<Integer> values,
                List<StatsContainer.Change<CharacterStat>> statChanges,
                List<StatsContainer.Change<CreatureTag>> tagChanges,
                List<StatsContainer.Change<DamageType>> armorChanges,
                List<StatsContainer.Change<DamageType>> minDamageChanges,
                List<StatsContainer.Change<DamageType>> maxDamageChanges,
                List<StatsContainer.Change<DamageType>> damageChanges,
                List<StatsContainer.Change<DamageType>> barrierChanges,
                Set<DamageType> attackDamageTypes,
                int value)
        {
            this.name = name;
            this.rarity = rarity;
            this.descriptionTemplate = descriptionTemplate;
            this.equipmentType = equipmentType;
            this.equipProfileConditions = equipProfileConditions;
            this.values = values;
            this.statChanges = statChanges;
            this.tagChanges = tagChanges;
            this.armorChanges = armorChanges;
            this.minDamageChanges = minDamageChanges;
            this.maxDamageChanges = maxDamageChanges;
            this.damageChanges = damageChanges;
            this.barrierChanges = barrierChanges;
            this.attackDamageTypes = attackDamageTypes;
            this.value = value;
        }

        public static EquipmentTemplate empty() {
            return new EquipmentTemplate("", Rarity.UNDEFINED, "", EquipmentType.UNDEFINED, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashSet<>(), 0);
        }

        public EquipmentTemplate withName(String name) {
            this.name = name;
            return this;
        }

        public EquipmentTemplate withDescription(String description) {
            this.descriptionTemplate = description;
            return this;
        }

        public EquipmentTemplate addValue(int value) {
            this.values.add(value);
            return this;
        }

        public EquipmentTemplate withRarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public EquipmentTemplate withStatChange(CharacterStat stat, int amount) {
            return withStatChange(stat, amount, false);
        }

        public EquipmentTemplate withStatChange(CharacterStat stat, int amount, boolean isPercentage) {
            if (amount != 0) {
                statChanges.add(new StatsContainer.Change<>(stat, ChangeDuration.PERMANENT, amount, isPercentage));
            }
            return this;
        }

        public EquipmentTemplate withTagChange(CreatureTag tag, int amount) {
            return withTagChange(tag, amount, false);
        }

        public EquipmentTemplate withTagChange(CreatureTag tag, int amount, boolean isPercentage) {
            if (amount != 0) {
                tagChanges.add(new StatsContainer.Change<>(tag, ChangeDuration.PERMANENT, amount, isPercentage));
            }
            return this;
        }

        public EquipmentTemplate withArmorChange(DamageType damageType, int amount, boolean isPercentage) {
            if (amount != 0) {
                armorChanges.add(new StatsContainer.Change<>(damageType, ChangeDuration.PERMANENT, amount, isPercentage));
            }
            return this;
        }

        public EquipmentTemplate withArmorChange(DamageType damageType, int amount) {
            return withArmorChange(damageType, amount, false);
        }

        public EquipmentTemplate withDamageChange(DamageType damageType, int min, int max, boolean isPercentage) {
            if (min != 0) {
                minDamageChanges.add(new StatsContainer.Change<>(damageType, ChangeDuration.PERMANENT, min, isPercentage));
            }
            if (max != 0) {
                maxDamageChanges.add(new StatsContainer.Change<>(damageType, ChangeDuration.PERMANENT, max, isPercentage));
            }
            return this;
        }

        public EquipmentTemplate withDamageChange(DamageType damageType, int min, int max) {
            withDamageChange(damageType, min, max, false);
            return this;
        }

        public EquipmentTemplate withDamageChange(DamageType damageType, int amount, boolean isPercentage) {
            if (amount != 0) {
                damageChanges.add(new StatsContainer.Change<>(damageType, ChangeDuration.PERMANENT, amount, isPercentage));
            }
            return this;
        }

        public EquipmentTemplate withDamageChange(DamageType damageType, int amount) {
            return withDamageChange(damageType, amount, false);
        }

        public EquipmentTemplate withBarrierChange(DamageType damageType, int amount, boolean isPercentage) {
            if (amount != 0) {
                barrierChanges.add(new StatsContainer.Change<>(damageType, ChangeDuration.PERMANENT, amount, isPercentage));
            }
            return this;
        }

        public EquipmentTemplate withBarrierChange(DamageType damageType, int amount) {
            return withBarrierChange(damageType, amount, false);
        }

        public EquipmentTemplate withAttackDamageType(DamageType damageType) {
            this.attackDamageTypes.add(damageType);
            return this;
        }

        public EquipmentTemplate withCondition(ProfileCondition profileCondition) {
            this.equipProfileConditions.add(profileCondition);
            return this;
        }

        public EquipmentTemplate withType(EquipmentType equipmentType) {
            this.equipmentType = equipmentType;
            return this;
        }

        public EquipmentTemplate withStatChange(CharacterStat stat, int from, int to, boolean isPercentage) {
            return withStatChange(stat, randomInt(from, to, true), isPercentage);
        }

        public EquipmentTemplate withValue(int value) {
            this.value = value;
            return this;
        }

        public EquipmentTemplate withRandomStatChange(int from, int to, boolean isPercentage, CharacterStat... stats) {
            if (stats.length == 0) {
                return this;
            }
            return withStatChange(randomElementOf(stats), from, to, isPercentage);
        }

        public EquipmentTemplate withRandomStatChange(int value) {
            Map<CharacterStat, Double> buffs = new HashMap<>();
            while (value > 0) {
                int rawAmount = randomInt(1, value, true);
                CharacterStat stat = CharacterStat.getRandom();
                double currentAmount = buffs.getOrDefault(stat, 0.0);
                buffs.put(stat, currentAmount + (double) rawAmount);
                value -= rawAmount;
            }
            for (CharacterStat stat : buffs.keySet()) {
                withStatChange(stat, buffs.get(stat).intValue(), false);
            }
            return this;
        }

        public EquipmentTemplate addDescription(String additionalDescription) {
            String beginDescriptionLine = descriptionTemplate.length() > 0 ? "$ " : "";
            descriptionTemplate += (beginDescriptionLine + additionalDescription);
            return this;
        }

        private String descriptionChange(String name, double amount, boolean isPercentage) {
            String symbol = amount > 0 ? "+" : "";
            String percent = isPercentage ? "%%" : "";
            return symbol + amount + percent + " " + name + ";";
        }

        public CharacterEquipment build(Action... actions) {
            return buildWithAction(character -> {
                for (Action action : actions) {
                    character.addAction(action);
                }
            }, character -> {
                for (Action action : actions) {
                    character.removeAction(action);
                }
            });
        }

        public CharacterEquipment build() {
            return buildWithAction(EquipAction.empty(), EquipAction.empty());
        }

        public CharacterEquipment buildWithAction(EquipAction equipAction, EquipAction unequipAction) {

            List<StatsContainer.Change<CharacterStat>> recalculatedChanges = recalculateStatChanges();
            List<StatsContainer.Change<CreatureTag>> recalculatedTagChanges = recalculateTagChanges();
            List<StatsContainer.Change<DamageType>> recalculatedArmorChanges = recalculateDamageTypeChanges(armorChanges, "armor");
            List<StatsContainer.Change<DamageType>> recalculatedMinDamageChanges = recalculateDamageTypeChanges(minDamageChanges, "damage min");
            List<StatsContainer.Change<DamageType>> recalculatedMaxDamageChanges = recalculateDamageTypeChanges(maxDamageChanges, "damage max");
            List<StatsContainer.Change<DamageType>> recalculatedDamageChanges = recalculateDamageTypeChanges(damageChanges, "damage");
            List<StatsContainer.Change<DamageType>> recalculatedBarrierChanges = recalculateDamageTypeChanges(barrierChanges, "barrier");

            addDescription(equipmentType.getName() + ";");
            for (DamageType damageType : attackDamageTypes) {
                addDescription("Damage type: " + damageType.getName() + ";");
            }
            StringBuilder conditionsString = new StringBuilder();
            for (ProfileCondition profileCondition : equipProfileConditions) {
                if (conditionsString.length() > 0) {
                    conditionsString.append("; ");
                }
                conditionsString.append(profileCondition.getDescription());
            }
            if (conditionsString.length() > 0 ) {
                addDescription( "{" + conditionsString + "}");
            }

            return CharacterEquipment.newEquipment(
                    this.name,
                    String.format(this.descriptionTemplate, values.toArray()),
                    rarity,
                    value,
                    equipProfileConditions,
                    equipmentType,
                    character -> {
                        equipAction.resolve(character);
                        for (StatsContainer.Change<CharacterStat> change : recalculatedChanges) {
                            character.applyStatChange(change);
                        }
                        for (StatsContainer.Change<CreatureTag> change : recalculatedTagChanges) {
                            character.applyTagChange(change);
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedArmorChanges) {
                            character.getArmor().addStatChange(change);
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedMinDamageChanges) {
                            character.getDamageMin().addStatChange(change);
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedMaxDamageChanges) {
                            character.getDamageMax().addStatChange(change);
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedDamageChanges) {
                            character.getDamageMin().addStatChange(change);
                            character.getDamageMax().addStatChange(change);
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedBarrierChanges) {
                            character.getBarrier().addStatChange(change);
                        }
                        for (DamageType damageType : attackDamageTypes) {
                            character.addAttackDamageType(damageType);
                        }
                    },
                    character -> {
                        unequipAction.resolve(character);
                        for (StatsContainer.Change<CharacterStat> change : recalculatedChanges) {
                            character.applyStatChange(change.getReversed());
                        }
                        for (StatsContainer.Change<CreatureTag> change : recalculatedTagChanges) {
                            character.applyTagChange(change.getReversed());
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedArmorChanges) {
                            character.getArmor().addStatChange(change.getReversed());
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedMinDamageChanges) {
                            character.getDamageMin().addStatChange(change.getReversed());
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedMaxDamageChanges) {
                            character.getDamageMax().addStatChange(change.getReversed());
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedDamageChanges) {
                            character.getDamageMin().addStatChange(change.getReversed());
                            character.getDamageMax().addStatChange(change.getReversed());
                        }
                        for (StatsContainer.Change<DamageType> change : recalculatedBarrierChanges) {
                            character.getBarrier().addStatChange(change.getReversed());
                        }
                        for (DamageType damageType : attackDamageTypes) {
                            character.removeAttackDamageType(damageType);
                        }
                    });
        }

        public ConsumableEquipment buildConsumable(double consumeChance, ResolveTime consumeTime) {
            ConsumableEquipment consumableEquipment = ConsumableEquipment.from(this.build(), consumeChance, consumeTime);
            consumableEquipment.addDescription("$ " + consumeChance + "% chance to consume " + consumeTime.name + "!");
            return consumableEquipment;
        }

        public ConsumableEquipment buildConsumableWithAction(EquipAction equipAction, EquipAction unequipAction, double consumeChance, ResolveTime consumeTime) {
            ConsumableEquipment consumableEquipment = ConsumableEquipment.from(this.buildWithAction(equipAction, unequipAction), consumeChance, consumeTime);
            consumableEquipment.addDescription(consumeChance + "%% chance to consume " + consumeTime.name + "!");
            return consumableEquipment;
        }


        private List<StatsContainer.Change<CharacterStat>> recalculateStatChanges() {
            Map<CharacterStat, Double> floatChanges = new TreeMap<>();
            Map<CharacterStat, Double> percentageChanges = new TreeMap<>();
            List<StatsContainer.Change<CharacterStat>> recalculatedChanges = new ArrayList<>();
            for (StatsContainer.Change<CharacterStat> change : statChanges) {
                CharacterStat creatureStat = change.getTag();
                if (change.isPercentage()) {
                    double current = percentageChanges.getOrDefault(creatureStat, 0.0);
                    percentageChanges.put(creatureStat, current + change.getAmount());
                } else {
                    double current = floatChanges.getOrDefault(creatureStat, 0.0);
                    floatChanges.put(creatureStat, current + change.getAmount());
                }
            }

            for (Map.Entry<CharacterStat, Double> change : floatChanges.entrySet()) {
                recalculatedChanges.add(
                        new StatsContainer.Change<>(change.getKey(), ChangeDuration.PERMANENT, change.getValue(), false)
                );
                addDescription(descriptionChange(change.getKey().getName(), change.getValue(), false));
            }
            for (Map.Entry<CharacterStat, Double> change : percentageChanges.entrySet()) {
                recalculatedChanges.add(
                        new StatsContainer.Change<>(change.getKey(), ChangeDuration.PERMANENT, change.getValue(), true)
                );
                addDescription(descriptionChange(change.getKey().getName(), change.getValue(), true));
            }
            return recalculatedChanges;
        }

        private List<StatsContainer.Change<CreatureTag>> recalculateTagChanges() {
            Map<CreatureTag, Double> floatChanges = new TreeMap<>();
            Map<CreatureTag, Double> percentageChanges = new TreeMap<>();
            List<StatsContainer.Change<CreatureTag>> recalculatedChanges = new ArrayList<>();
            for (StatsContainer.Change<CreatureTag> change : tagChanges) {
                CreatureTag creatureStat = change.getTag();
                if (change.isPercentage()) {
                    double current = percentageChanges.getOrDefault(creatureStat, 0.0);
                    percentageChanges.put(creatureStat, current + change.getAmount());
                } else {
                    double current = floatChanges.getOrDefault(creatureStat, 0.0);
                    floatChanges.put(creatureStat, current + change.getAmount());
                }
            }

            for (Map.Entry<CreatureTag, Double> change : floatChanges.entrySet()) {
                recalculatedChanges.add(
                        new StatsContainer.Change<>(change.getKey(), ChangeDuration.PERMANENT, change.getValue(), false)
                );
                addDescription(descriptionChange(change.getKey().getName(), change.getValue(), false));
            }
            for (Map.Entry<CreatureTag, Double> change : percentageChanges.entrySet()) {
                recalculatedChanges.add(
                        new StatsContainer.Change<>(change.getKey(), ChangeDuration.PERMANENT, change.getValue(), true)
                );
                addDescription(descriptionChange(change.getKey().getName(), change.getValue(), true));
            }
            return recalculatedChanges;
        }

        private List<StatsContainer.Change<DamageType>> recalculateDamageTypeChanges(List<StatsContainer.Change<DamageType>> changes, String name) {
            Map<DamageType, Double> floatChanges = new TreeMap<>();
            Map<DamageType, Double> percentageChanges = new TreeMap<>();
            List<StatsContainer.Change<DamageType>> recalculatedChanges = new ArrayList<>();
            for (StatsContainer.Change<DamageType> change : changes) {
                DamageType damageType = change.getTag();
                if (change.isPercentage()) {
                    double current = percentageChanges.getOrDefault(damageType, 0.0);
                    percentageChanges.put(damageType, current + change.getAmount());
                } else {
                    double current = floatChanges.getOrDefault(damageType, 0.0);
                    floatChanges.put(damageType, current + change.getAmount());
                }
            }

            for (Map.Entry<DamageType, Double> change : floatChanges.entrySet()) {
                recalculatedChanges.add(
                        new StatsContainer.Change<>(change.getKey(), ChangeDuration.PERMANENT, change.getValue(), false)
                );
                addDescription(descriptionChange(change.getKey().getName() + " " + name, change.getValue(), false));
            }
            for (Map.Entry<DamageType, Double> change : percentageChanges.entrySet()) {
                recalculatedChanges.add(
                        new StatsContainer.Change<>(change.getKey(), ChangeDuration.PERMANENT, change.getValue(), true)
                );
                addDescription(descriptionChange(change.getKey().getName() + " " + name, change.getValue(), true));
            }
            return recalculatedChanges;
        }
    }
}
