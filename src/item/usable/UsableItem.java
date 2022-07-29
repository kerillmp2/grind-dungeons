package item.usable;

import java.io.Serializable;
import java.util.List;

import item.Item;
import item.PickUpAction;
import item.Rarity;
import logic.ProfileCondition;
import profile.Profile;

public class UsableItem extends Item implements Serializable {
    UseAction onUse;
    List<ProfileCondition> useConditions;

    protected UsableItem(String name, String description, Rarity rarity, int value, PickUpAction pickUpAction, List<ProfileCondition> useConditions, UseAction onUse) {
        super(name, description, rarity, value, pickUpAction);
        this.onUse = onUse;
        this.useConditions = useConditions;
    }

    protected UsableItem(String name, String description, Rarity rarity, int value, List<ProfileCondition> useConditions, UseAction onUse) {
        this(name, description, rarity, value, PickUpAction.defaultAction(), useConditions, onUse);
    }

    public String canBeUsedBy(Profile profile) {
        StringBuilder errorMessages = new StringBuilder();
        for (ProfileCondition profileCondition : useConditions) {
            if (!profileCondition.check(profile)) {
                errorMessages.append(profileCondition.getErrorMessage());
            }
        }
        return errorMessages.toString();
    }

    public UseAction onUse() {
        return onUse;
    }
}
