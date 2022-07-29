package battlecore;

import java.io.Serializable;

import battlecore.action.ActionInfo;
import creature.Creature;

public interface Resolvable extends Serializable {
    void resolve(ActionInfo actionInfo, Creature performer);
}