package battlecore.action;

import java.io.Serializable;

import battlecore.Resolvable;
import creature.Creature;
import utils.RandomController;

public class Action implements Serializable {
    protected final ActionInfo actionInfo;
    protected final Resolvable onResolve;
    protected String description;

    public Action(ActionInfo actionInfo, Resolvable onResolve, String description) {
        this.actionInfo = actionInfo;
        this.onResolve = onResolve;
        this.description = description;
    }

    public static Action copyOf(Action action) {
        return new Action(ActionInfo.copyOf(action.actionInfo), action.onResolve, action.description);
    }

    public static Action empty() {
        return new Action(ActionInfo.empty(), (a, c) -> {}, "");
    }

    public ActionInfo getActionInfo() {
        return actionInfo;
    }

    public void resolveWithPerformer(Creature creature) {
        if (creature.isAlive()) {
            if (RandomController.roll(this.actionInfo.chance)) {
                onResolve.resolve(actionInfo, creature);
                for (ResolveTime resolveTime : actionInfo.triggers) {
                    creature.resolveActionsWithTime(resolveTime);
                }
            }
        }
    }

    public void addTag(ActionTag actionTag) {
        actionInfo.addTag(actionTag);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Resolvable getOnResolve() {
        return onResolve;
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionInfo=" + actionInfo +
                '}';
    }
}
