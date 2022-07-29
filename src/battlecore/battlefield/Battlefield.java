package battlecore.battlefield;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import battlecore.controllers.BattleController;
import creature.Creature;
import utils.RandomController;

public class Battlefield {
    private BattleController battleController;
    private final BattlefieldSide firstSide;
    private final BattlefieldSide secondSide;

    private Battlefield(BattlefieldSide firstSide, BattlefieldSide secondSide) {
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        getAllCreatures().forEach(creature -> creature.setBattlefield(this));
    }

    public static Battlefield fromLists(List<? extends Creature> firstSide, List<? extends Creature> secondSide) {
        BattlefieldSide firstBattlefieldSide = BattlefieldSide.fromCreatures(firstSide);
        BattlefieldSide secondBattlefieldSide = BattlefieldSide.fromCreatures(secondSide);
        firstBattlefieldSide.setOppositeSide(secondBattlefieldSide);
        secondBattlefieldSide.setOppositeSide(firstBattlefieldSide);

        return new Battlefield(firstBattlefieldSide, secondBattlefieldSide);
    }

    public BattlefieldSide getFirstSide() {
        return firstSide;
    }

    public BattlefieldSide getSecondSide() {
        return secondSide;
    }

    public List<Creature> getAllCreatures() {
        List<Creature> firstSideCreatures = this.getFirstSide().getCreatures();
        List<Creature> secondSideCreatures = this.getSecondSide().getCreatures();
        return Stream.concat(firstSideCreatures.stream(), secondSideCreatures.stream()).collect(Collectors.toList());
    }

    public Creature getRandomFirstSideCreature() {
        return getRandomSideCreature(this.firstSide);
    }

    public Creature getRandomSecondSideCreature() {
        return getRandomSideCreature(this.secondSide);
    }

    private Creature getRandomSideCreature(BattlefieldSide side) {
        List<Creature> allSideCreatures = side.getCreatures();
        return allSideCreatures.get(RandomController.randomInt(allSideCreatures.size()));
    }

    public BattleController getBattleController() {
        return battleController;
    }

    public void setBattleController(BattleController battleController) {
        this.battleController = battleController;
    }
}
