package creature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import battlecore.DamageType;
import battlecore.action.Action;
import battlecore.action.ActionFactory;
import battlecore.action.ResolveTime;
import battlecore.battlefield.ObjectStatus;
import creature.ore.Ore;
import dungeon.DungeonCreature;
import dungeon.LootItem;
import dungeon.LootTableFactory;
import profile.Profile;
import profile.character.Character;
import profile.character.CharacterStat;
import stat.ChangeDuration;
import stat.StatsContainer;
import utils.Calculator;

public class CreatureFactory {

    public static Creature fromProfile(Profile profile) {
        Character character = profile.getCharacter();
        CreatureTemplate template = CreatureTemplate.empty().withName(profile.getName());
        for (CharacterStat characterStat : CharacterStat.values()) {
            template.withStatChange(characterStat, character.getStats().getFloat(characterStat), false);
            template.withStatChange(characterStat, character.getStats().getPercentage(characterStat), true);
        }
        for (CreatureTag tag : character.getStats().getFloatTagChanges().keySet()) {
            template.withTagChange(tag, character.getStats().getFloatTag(tag), false);
        }
        for (CreatureTag tag : character.getStats().getPercentageTagChanges().keySet()) {
            template.withTagChange(tag, character.getStats().getPercentageTag(tag), true);
        }
        for (DamageType damageType : DamageType.values()) {
            template.withPercentageArmor(damageType, character.getArmor().getPercentage(damageType));
            template.withFloatArmor(damageType, character.getArmor().getFloat(damageType));

            template.withPercentageDamage(damageType, character.getDamageMin().getPercentage(damageType), character.getDamageMax().getPercentage(damageType));
            template.withFloatDamage(damageType, character.getDamageMin().getFloat(damageType), character.getDamageMax().getFloat(damageType));

            template.withPercentageBarrier(damageType, character.getBarrier().getPercentage(damageType));
            template.withFloatBarrier(damageType, character.getBarrier().getFloat(damageType));
        }
        for (Action action : character.getActions()) {
            template.wrapAction(action);
        }
        for (DamageType damageType : character.getAttackDamageTypes()) {
            template.withAttackDamageType(damageType);
        }
        return template.build();
    }

    public static DungeonCreature greenSlime() {
        Creature creature = templateWithStats("Green slime", 2, 0, 1, 1, 0, 0, 2)
                .wrapAction(ResolveTime.ON_MAIN_PHASE, ActionFactory.dealDamageAction(ResolveTime.AFTER_ATTACK, DamageType.POISON, 15.0, "spits deadly poison on"))
                .withFloatDamage(DamageType.PHYSICAL, 0, 1)
                .build();
        List<LootItem> lootTable = LootTableFactory.forGreenSlime();
        return dungeonCreatureFrom(creature, lootTable, 0);
    }

    public static DungeonCreature yellowSlime() {
        Creature creature = templateWithStats("Yellow slime", 4, 2, 2, 1, 1, 2, 4)
                .wrapAction(ResolveTime.ON_MAIN_PHASE, ActionFactory.dealDamageAction(ResolveTime.AFTER_ATTACK, DamageType.POISON, 25.0, "spits deadly poison on"))
                .withFloatArmor(DamageType.POISON, 2)
                .build();
        List<LootItem> lootTable = LootTableFactory.forYellowSlime();
        return dungeonCreatureFrom(creature, lootTable, 0);
    }

    public static DungeonCreature redSlime() {
        Creature creature = templateWithStats("Red slime", 8, 5, 3, 3, 3, 5, 9)
                .wrapAction(ResolveTime.ON_MAIN_PHASE, ActionFactory.dealDamageAction(ResolveTime.AFTER_ATTACK, DamageType.POISON, 40.0, "spits deadly poison on"))
                .withFloatArmor(DamageType.POISON, 3)
                .withFloatArmor(DamageType.PHYSICAL, 1)
                .build();
        List<LootItem> lootTable = LootTableFactory.forRedSlime();
        return dungeonCreatureFrom(creature, lootTable, 0);
    }

    public static DungeonCreature pinky() {
        Creature creature = templateWithStats("Pinky", 13, 10, 10, 10, 10, 10, 9)
                .wrapAction(ResolveTime.ON_MAIN_PHASE, ActionFactory.dealDamageAction(ResolveTime.AFTER_ATTACK, DamageType.POISON, 55.0, "spits deadly poison on"))
                .wrapAction(ResolveTime.AFTER_ATTACK, ActionFactory.gainArmor(
                        ResolveTime.ON_END_TURN,
                        DamageType.POISON,
                        ChangeDuration.UNTIL_NEXT_TURN,
                        5,
                        false,
                        50,
                        "dances like Pinky"))
                .withFloatArmor(DamageType.POISON, 6)
                .withFloatArmor(DamageType.PHYSICAL, 3)
                .withAdditionalHP(10)
                .build();
        List<LootItem> lootTable = LootTableFactory.forPinky();
        return dungeonCreatureFrom(creature, lootTable, 0);
    }

    public static Creature punchingDummy(int additionalHp) {
        return templateWithStats("Punching dummy", 1, 1, 1, 1, 1, 1, 1)
                .withAdditionalHP(additionalHp)
                .build();
    }

    public static Creature fightingDummy(int attackPower) {
        return templateWithStats("Fighting dummy", 1, 1, 1, 1, 999, 1, 1)
                .withAttackDamageType(DamageType.PHYSICAL)
                .withFloatDamage(DamageType.PHYSICAL, attackPower, attackPower)
                .withTagChange(CreatureTag.TURN_PRIORITY, 10, false)
                .build();
    }

    public static Creature dummy() {
        return withStats("Dummy", 1, 1, 1, 1, 1, 1, 1);
    }

    public static DungeonCreature undying() {
        return dungeonCreatureFrom(withStats("Undying", 99999, 99999,99999,99999,99999,99999, 99999), new ArrayList<>(), 0);
    }

    public static Creature withStats(String name, int strength, int agility, int constitution, int intelligence, int wisdom, int luck, int powerLevel) {
        Creature creature = templateWithStats(name, strength, agility, constitution, intelligence, wisdom, luck, powerLevel).build();
        creature.addAction(ActionFactory.basicAttackAction(DamageType.PHYSICAL));
        return creature;
    }

    private static CreatureTemplate templateWithStats(String name, int strength, int agility, int constitution, int intelligence, int wisdom, int luck, int powerLevel) {
        return CreatureTemplate.empty().withName(name)
                .withStatChange(CharacterStat.STRENGTH, strength, false)
                .withStatChange(CharacterStat.AGILITY, agility, false)
                .withStatChange(CharacterStat.CONSTITUTION, constitution, false)
                .withStatChange(CharacterStat.INTELLIGENCE, intelligence, false)
                .withStatChange(CharacterStat.WISDOM, wisdom, false)
                .withStatChange(CharacterStat.LUCK, luck, false)
                .withPowerLevel(powerLevel);
    }

    public static DungeonCreature dungeonCreatureFrom(Creature creature, List<LootItem> lootTable, double spawnChance) {
        Set<ObjectStatus> objectStatuses = new HashSet<>(creature.getStatusSet());
        Map<ResolveTime, List<Action>> actions = new HashMap<>();
        for (Map.Entry<ResolveTime, List<Action>> entry : creature.getActions().entrySet()) {
            ResolveTime resolveTime = entry.getKey();
            List<Action> actionList = new ArrayList<>();
            for (Action action : entry.getValue()) {
                Action copy = Action.copyOf(action);
                actionList.add(copy);
            }
            actions.put(resolveTime, actionList);
        }
        return new DungeonCreature(
                objectStatuses,
                actions,
                null,
                null,
                StatsContainer.copyOf(creature.getStatsContainer()),
                StatsContainer.copyOf(creature.getDamageMin()),
                StatsContainer.copyOf(creature.getDamageMax()),
                StatsContainer.copyOf(creature.getArmor()),
                StatsContainer.copyOf(creature.getBarrier()),
                StatsContainer.copyOf(creature.getResist()),
                creature.getAttackDamageTypes(),
                creature.getName(),
                creature.getMaxHP(),
                creature.getMaxHp(),
                creature.getPowerLevel(),
                spawnChance,
                lootTable
        );
    }

    public static DungeonCreature dungeonCreatureFrom(Creature creature) {
        return dungeonCreatureFrom(creature, new ArrayList<>(), 0);
    }

    protected static class CreatureTemplate {
        private final Set<ObjectStatus> statusSet;
        private final Map<ResolveTime, List<Action>> actions;
        private final StatsContainer<CharacterStat, CreatureTag> statsContainer;
        private final StatsContainer<DamageType, CreatureTag> damageMin;
        private final StatsContainer<DamageType, CreatureTag> damageMax;
        private final StatsContainer<DamageType, CreatureTag> armor;
        private final StatsContainer<DamageType, CreatureTag> barrier;
        private final StatsContainer<DamageType, CreatureTag> resist;
        private final Set<DamageType> attackDamageTypes;
        private String name;
        private int powerLevel;
        private int additionalHP;

        public CreatureTemplate(
                Set<ObjectStatus> statusSet,
                Map<ResolveTime, List<Action>> actions,
                StatsContainer<CharacterStat, CreatureTag> statsContainer,
                StatsContainer<DamageType, CreatureTag> damageMin,
                StatsContainer<DamageType, CreatureTag> damageMax,
                StatsContainer<DamageType, CreatureTag> armor,
                StatsContainer<DamageType, CreatureTag> barrier,
                StatsContainer<DamageType, CreatureTag> resist,
                Set<DamageType> attackDamageTypes,
                String name,
                int powerLevel,
                int additionalHP) {
            this.statusSet = statusSet;
            this.actions = actions;
            this.statsContainer = statsContainer;
            this.damageMin = damageMin;
            this.damageMax = damageMax;
            this.armor = armor;
            this.barrier = barrier;
            this.resist = resist;
            this.attackDamageTypes = attackDamageTypes;
            this.name = name;
            this.powerLevel = powerLevel;
            this.additionalHP = additionalHP;
        }

        public static CreatureTemplate empty() {
            Map<ResolveTime, List<Action>> actions = new HashMap<>();
            for (ResolveTime resolveTime : ResolveTime.values()) {
                actions.put(resolveTime, new ArrayList<>());
            }
            return new CreatureTemplate(new HashSet<>(), actions, new StatsContainer<>(), new StatsContainer<>(), new StatsContainer<>(), new StatsContainer<>(), new StatsContainer<>(), new StatsContainer<>(), new HashSet<>(), "", 0, 0);
        }

        public CreatureTemplate withStatus(ObjectStatus status) {
            this.statusSet.add(status);
            return this;
        }

        public CreatureTemplate withName(String name) {
            this.name = name;
            return this;
        }

        public CreatureTemplate withFloatDamage(DamageType damageType, double min, double max) {
            this.damageMin.addStatChange(damageType, ChangeDuration.PERMANENT, min, false);
            this.damageMax.addStatChange(damageType, ChangeDuration.PERMANENT, max, false);
            return this;
        }

        public CreatureTemplate withPercentageDamage(DamageType damageType, double min, double max) {
            this.damageMin.addStatChange(damageType, ChangeDuration.PERMANENT, min, true);
            this.damageMax.addStatChange(damageType, ChangeDuration.PERMANENT, max, true);
            return this;
        }

        public CreatureTemplate withFloatArmor(DamageType damageType, double value) {
            this.armor.addStatChange(damageType, ChangeDuration.PERMANENT, value, false);
            return this;
        }

        public CreatureTemplate withPercentageArmor(DamageType damageType, double value) {
            this.armor.addStatChange(damageType, ChangeDuration.PERMANENT, value, true);
            return this;
        }

        public CreatureTemplate withFloatBarrier(DamageType damageType, double value) {
            this.barrier.addStatChange(damageType, ChangeDuration.PERMANENT, value, false);
            return this;
        }

        public CreatureTemplate withPercentageBarrier(DamageType damageType, double value) {
            this.barrier.addStatChange(damageType, ChangeDuration.PERMANENT, value, true);
            return this;
        }

        public CreatureTemplate withFloatResist(DamageType damageType, double value) {
            this.resist.addStatChange(damageType, ChangeDuration.PERMANENT, value, false);
            return this;
        }

        public CreatureTemplate withPercentageResist(DamageType damageType, double value) {
            this.resist.addStatChange(damageType, ChangeDuration.PERMANENT, value, true);
            return this;
        }


        public CreatureTemplate withStatChange(CharacterStat stat, double amount, boolean isPercentage) {
            if (amount != 0) {
                statsContainer.addStatChange(stat, ChangeDuration.PERMANENT, amount, isPercentage);
            }
            return this;
        }

        public CreatureTemplate withTagChange(CreatureTag tag, double amount, boolean isPercentage) {
            if (amount != 0) {
                statsContainer.addTagChange(tag, ChangeDuration.PERMANENT, amount, isPercentage);
            }
            return this;
        }

        public CreatureTemplate withAttackDamageType(DamageType damageType) {
            attackDamageTypes.add(damageType);
            return this;
        }

        public CreatureTemplate wrapAction(ResolveTime resolveTime, Action action) {
            actions.get(resolveTime).add(action);
            return this;
        }

        public CreatureTemplate wrapAction(Action action) {
            actions.get(action.getActionInfo().resolveTime).add(action);
            return this;
        }

        public CreatureTemplate withPowerLevel(int powerLevel) {
            this.powerLevel = powerLevel;
            return this;
        }

        public CreatureTemplate withAdditionalHP(int hp) {
            this.additionalHP += hp;
            return this;
        }

        public Creature build() {
            for (DamageType damageType : DamageType.values()) {
                if (damageType.isNonCreatureType()) {
                    this.withFloatResist(damageType, 100);
                }
            }
            if (attackDamageTypes.isEmpty()) {
                this.withAttackDamageType(DamageType.PHYSICAL);
            }
            for (DamageType damageType : attackDamageTypes) {
                this.wrapAction(ActionFactory.basicAttackAction(damageType));
            }
            for (DamageType damageType : DamageType.values()) {
                this.withFloatDamage(damageType, damageType.calculateMinDamage(statsContainer), damageType.calculateMaxDamage(statsContainer));
            }
            int maxHP = Calculator.calculateMaxHP(statsContainer.get(CharacterStat.CONSTITUTION), statsContainer.get(CharacterStat.STRENGTH)) + additionalHP;
            if (maxHP > 0) {
                statusSet.add(ObjectStatus.ALIVE);
            }
            return new Creature(
                    statusSet,
                    actions,
                    null,
                    null,
                    statsContainer,
                    damageMin,
                    damageMax,
                    armor,
                    barrier,
                    resist,
                    new ArrayList<>(attackDamageTypes),
                    name,
                    maxHP,
                    maxHP,
                    powerLevel
            );
        }

        public Ore buildOre() {
            for (DamageType damageType : DamageType.values()) {
                if (damageType != DamageType.MINING) {
                    this.withFloatResist(damageType, 100);
                }
            }
            int maxHP = additionalHP;
            if (maxHP > 0) {
                statusSet.add(ObjectStatus.ALIVE);
            }
            return new Ore(
                    statusSet,
                    actions,
                    null,
                    null,
                    statsContainer,
                    damageMin,
                    damageMax,
                    armor,
                    barrier,
                    resist,
                    new ArrayList<>(attackDamageTypes),
                    name,
                    maxHP,
                    maxHP,
                    powerLevel
            );
        }
    }
}
