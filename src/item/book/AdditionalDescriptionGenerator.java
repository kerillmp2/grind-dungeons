package item.book;

import java.io.Serializable;

import profile.Profile;

public interface AdditionalDescriptionGenerator extends Serializable {
    String generateFor(Profile profile);

    static AdditionalDescriptionGenerator empty() {
        return profile -> "";
    }
}
