package item;

import java.io.Serializable;

import profile.Profile;

public interface PickUpAction extends Serializable {
    void resolve(Profile profile, Item item);

    static PickUpAction defaultAction() {
        return (profile, item) -> profile.getInventory().put(item);
    }
}
