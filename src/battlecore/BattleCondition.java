package battlecore;

import battlecore.controllers.BattleController;

public interface BattleCondition {
    boolean check(BattleController battleController);
}
