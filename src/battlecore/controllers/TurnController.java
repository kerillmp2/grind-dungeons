package battlecore.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import battlecore.action.ResolveTime;
import battlecore.battlefield.Battlefield;
import creature.Creature;
import battlecore.battlefield.ObjectStatus;
import creature.CreatureTag;
import profile.character.CharacterStat;
import utils.Constants;
import utils.MessageController;

public class TurnController {
    private int turnCounter;
    private int turnOrderCounter;
    private List<Creature> turnOrder;

    public TurnController(int turnCounter, int turnOrderCounter, List<Creature> turnOrder) {
        this.turnCounter = turnCounter;
        this.turnOrderCounter = turnOrderCounter;
        this.turnOrder = turnOrder;
    }

    public static TurnController forBattlefield(Battlefield battlefield) {
        return new TurnController(0, 0, generateTurnOrder(battlefield.getAllCreatures()));
    }

    private static List<Creature> generateTurnOrder(List<Creature> creatures) {
        List<Creature> turnOrder = new ArrayList<>(creatures);
        Collections.shuffle(turnOrder);
        turnOrder.sort((o1, o2) -> {
            double priority1 = o1.getTag(CreatureTag.TURN_PRIORITY);
            double priority2 = o2.getTag(CreatureTag.TURN_PRIORITY);
            if (priority1 != priority2) {
                return (-1) * Double.compare(priority1, priority2);
            }
            return (-1) * (o1.getStat(CharacterStat.AGILITY) - o2.getStat(CharacterStat.AGILITY));
        });
        return turnOrder;
    }

    public void regenerateTurnOrder(int from) {
        List<Creature> head = new ArrayList<>();
        List<Creature> tail = new ArrayList<>();
        for (int i = 0; i < this.turnOrder.size(); i++) {
            if (i < from) {
                head.add(this.turnOrder.get(i));
            } else {
                tail.add(this.turnOrder.get(i));
            }
        }
        tail = generateTurnOrder(tail);
        this.turnOrder = Stream.concat(head.stream(), tail.stream()).collect(Collectors.toList());
    }

    public void regenerateTurnOrder() {
        regenerateTurnOrder(turnOrderCounter);
    }

    public void nextTurn() {
        this.regenerateTurnOrder();
        MessageController.print("Turn " + this.turnCounter + " [" + this.turnOrderCounter + "]:");
        if (!this.turnOrder.isEmpty()) {
            Creature nextCreature = this.turnOrder.get(this.turnOrderCounter);
            if (nextCreature.hasStatus(ObjectStatus.ALIVE) && !nextCreature.hasStatus(ObjectStatus.DEAD)) {
                if (Constants.SKIP_ALL_ANIMATIONS.value == 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1500);
                    } catch (InterruptedException ignored) {

                    }
                }
                makeTurn(nextCreature);
                if (!nextCreature.hasStatus(ObjectStatus.ALIVE) || nextCreature.hasStatus(ObjectStatus.DEAD)) {
                    MessageController.print(nextCreature.getName() + " dies!\n");
                }
            }
        }
        this.turnOrderCounter += 1;
        if (this.turnOrderCounter >= turnOrder.size()) {
            this.turnOrderCounter = 0;
            this.turnCounter += 1;
            this.regenerateTurnOrder();
        }
    }

    private void makeTurn(Creature creature) {
        if (!creature.hasStatus(ObjectStatus.ALIVE) || creature.hasStatus(ObjectStatus.DEAD)) {
            return;
        }
        MessageController.print(creature.getBattleName() + " starts turn\n");
        creature.resolveActionsWithTime(ResolveTime.ON_START_TURN);
        creature.resolveActionsWithTime(ResolveTime.BEFORE_MAIN_PHASE);
        creature.resolveActionsWithTime(ResolveTime.ON_MAIN_PHASE);
        creature.resolveActionsWithTime(ResolveTime.AFTER_MAIN_PHASE);
        creature.resolveActionsWithTime(ResolveTime.ON_END_TURN);
        MessageController.print(creature.getBattleName() + " ends turn\n");
    }

    public void addCreatureToTurnOrder(Creature creature) {
        turnOrder.add(creature);
        regenerateTurnOrder(this.turnOrderCounter);
    }

    public void removeCreatureFromTurnOrder(Creature creature) {
        turnOrder.remove(creature);
        regenerateTurnOrder(this.turnOrderCounter);
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public List<Creature> getTurnOrder() {
        return turnOrder;
    }
}
