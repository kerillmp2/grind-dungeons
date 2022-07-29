package battlecore.controllers;

import java.util.Comparator;

import battlecore.BattleCondition;
import battlecore.action.ResolveTime;
import battlecore.BattleStatus;
import battlecore.battlefield.Battlefield;
import battlecore.battlefield.ObjectStatus;
import profile.character.CharacterStat;
import utils.MessageController;
import battlecore.Battler;
import utils.threads.ExitThread;
import viewers.BattlefieldViewer;
import utils.Constants;

public class BattleController {
    private final Battlefield battlefield;
    private final TurnController turnController;
    private final ExitThread exitThread;

    private BattleController(Battlefield battlefield, TurnController turnController, ExitThread exitThread) {
        this.battlefield = battlefield;
        this.turnController = turnController;
        this.exitThread = exitThread;
    }

    private static BattleController forBattlefield(Battlefield battlefield, ExitThread exitThread) {
        return new BattleController(battlefield, TurnController.forBattlefield(battlefield), exitThread);
    }

    public static BattleStatus processBattleFor(Battler firstBattler, Battler secondBattler, ExitThread exitThread) {
        return processBattleWithLoseCondition(firstBattler, secondBattler, c -> false, exitThread);
    }

    public static BattleStatus processBattleWithLoseCondition(Battler firstBattler, Battler secondBattler, BattleCondition loseCondition, ExitThread exitThread) {
        Battlefield battlefield = Battlefield.fromLists(firstBattler.getCreatures(), secondBattler.getCreatures());
        BattleController battleController = BattleController.forBattlefield(battlefield, exitThread);
        battlefield.setBattleController(battleController);
        return battleController.battleWithLoseCondition(loseCondition);
    }

    private BattleStatus battleWithLoseCondition(BattleCondition loseCondition) {
        BattleStatus battleStatus = BattleStatus.TURN_LIMIT_REACHED;
        onBattleStart();
        while (turnController.getTurnCounter() < Constants.BATTLE_TURN_LIMIT.value) {
            if (exitThread.isFinished()) {
                battleStatus = BattleStatus.INTERRUPTED;
                break;
            }
            if (loseCondition.check(this)) {
                battleStatus = BattleStatus.SECOND_PLAYER_WIN;
                break;
            }
            MessageController.print(BattlefieldViewer.getBattleFieldView(battlefield));
            boolean firstSideHasAlive = this.battlefield.getFirstSide().hasAliveCreature();
            boolean secondSideHasAlive = this.battlefield.getSecondSide().hasAliveCreature();
            if (firstSideHasAlive && !secondSideHasAlive) {
                battleStatus = BattleStatus.FIRST_PLAYER_WIN;
                break;
            }
            if (!firstSideHasAlive && secondSideHasAlive) {
                battleStatus = BattleStatus.SECOND_PLAYER_WIN;
                break;
            }
            if (!firstSideHasAlive && !secondSideHasAlive) {
                battleStatus = BattleStatus.DRAW;
                break;
            }
            turnController.nextTurn();
            if (loseCondition.check(this)) {
                battleStatus = BattleStatus.SECOND_PLAYER_WIN;
                break;
            }
        }
        onBattleEnd();
        return battleStatus;
    }

    private BattleStatus battle() {
        return battleWithLoseCondition(c -> false);
    }

    private void onBattleStart() {
        battlefield.getAllCreatures().stream().filter(c -> c.hasStatuses(ObjectStatus.ALIVE))
                .sorted(Comparator.comparingInt(c -> c.getStat(CharacterStat.AGILITY)))
                .forEach(c -> c.resolveActionsWithTime(ResolveTime.ON_BATTLE_START));
    }

    private void onBattleEnd() {
        battlefield.getAllCreatures().forEach(c -> c.resolveActionsWithTime(ResolveTime.ON_BATTLE_END));
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public TurnController getTurnController() {
        return turnController;
    }
}
