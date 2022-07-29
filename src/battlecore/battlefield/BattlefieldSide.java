package battlecore.battlefield;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import creature.Creature;
import profile.character.CharacterStat;
import utils.RandomController;

public class BattlefieldSide {

    private final List<Creature> creatures;
    private BattlefieldSide oppositeSide;

    private BattlefieldSide() {
        this.creatures = new ArrayList<>();
    }

    private BattlefieldSide(List<? extends Creature> creatures) {
        this();
        this.creatures.addAll(creatures);
        getCreatures().forEach(creature -> creature.setBattlefieldSide(this));
    }

    public static BattlefieldSide fromCreatures(List<? extends Creature> creatures) {
        BattlefieldSide battlefieldSide = new BattlefieldSide(creatures);
        for (Creature creature : battlefieldSide.getCreatures()) {
            creature.setBattlefieldSide(battlefieldSide);
        }
        return battlefieldSide;
    }

    public boolean hasAliveCreature() {
        return this.getCreatures().stream().anyMatch(creature -> creature.hasStatus(ObjectStatus.ALIVE));
    }

    public void addCreature(Creature creature) {
        this.creatures.add(creature);
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public Creature getRandomSideCreature() {
        return getRandomSideCreature(this);
    }

    public Creature getRandomOppositeSideCreature() {
        return getRandomSideCreature(this.oppositeSide);
    }

    public Creature getRandomOppositeSideAliveCreature() {
        return getRandomOppositeSideCreature(ObjectStatus.ALIVE);
    }

    public Creature getRandomOppositeSideCreature(ObjectStatus... filters) {
        return getRandomSideCreature(this.oppositeSide, filters);
    }

    private Creature getRandomSideCreature(BattlefieldSide side, ObjectStatus... filters) {
        List<Creature> allSideCreatures = side.getCreatures()
                .stream().filter(creature -> creature.hasStatuses(filters)).collect(Collectors.toList());
        if (allSideCreatures.size() == 0) {
            return null;
        }
        return allSideCreatures.get(RandomController.randomInt(allSideCreatures.size()));
    }

    public List<Creature> getCreatures(ObjectStatus... filters) {
        return getCreatures().stream().filter(c -> c.hasStatuses(filters)).collect(Collectors.toList());
    }

    public Creature getCreatureWithHighest(CharacterStat creatureStat) {
        List<Creature> allCreatures = getCreatures();
        if (allCreatures.isEmpty()) {
            return null;
        }
        return allCreatures.stream().max(Comparator.comparingInt(o -> o.getStat(creatureStat))).get();
    }

    public Creature getCreatureWithLowest(CharacterStat creatureStat) {
        List<Creature> allCreatures = getCreatures().stream().filter(c -> c.hasStatuses(ObjectStatus.ALIVE)).collect(Collectors.toList());
        if (allCreatures.isEmpty()) {
            return null;
        }
        return allCreatures.stream().min(Comparator.comparingInt(o -> o.getStat(creatureStat))).get();
    }

    public Creature getCreatureWithHighestBy(Comparator<Creature> comparator) {
        List<Creature> allCreatures = getCreatures().stream().filter(c -> c.hasStatuses(ObjectStatus.ALIVE)).sorted(comparator).collect(Collectors.toList());
        if (allCreatures.isEmpty()) {
            return null;
        }
        return allCreatures.get(0);
    }

    public Creature getCreatureWithLowestBy(Comparator<Creature> comparator) {
        List<Creature> allCreatures = getCreatures().stream().filter(c -> c.hasStatuses(ObjectStatus.ALIVE)).sorted(comparator).collect(Collectors.toList());
        if (allCreatures.isEmpty()) {
            return null;
        }
        return allCreatures.get(allCreatures.size() - 1);
    }

    public void setOppositeSide(BattlefieldSide oppositeSide) {
        this.oppositeSide = oppositeSide;
    }

    public BattlefieldSide getOppositeSide() {
        return oppositeSide;
    }
}
