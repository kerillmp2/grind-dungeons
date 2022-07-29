package item.usable;

import java.io.Serializable;

import profile.Profile;

public interface UseAction extends Serializable {
    void resolveOn(Profile profile);
}
