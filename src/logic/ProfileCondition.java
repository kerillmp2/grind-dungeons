package logic;

import java.io.Serializable;

import profile.Profile;

public interface ProfileCondition extends Serializable {
    ProfileCondition FALSE = alwaysFalse();
    ProfileCondition TRUE = alwaysTrue();

    boolean check(Profile profile);
    String getDescription();
    String getErrorMessage();

    private static ProfileCondition alwaysFalse() {
        return new ProfileCondition() {
            @Override
            public boolean check(Profile profile) {
                return false;
            }

            @Override
            public String getDescription() {
                return "";
            }

            @Override
            public String getErrorMessage() {
                return "";
            }
        };
    }

    private static ProfileCondition alwaysTrue() {
        return new ProfileCondition() {
            @Override
            public boolean check(Profile profile) {
                return true;
            }

            @Override
            public String getDescription() {
                return "";
            }

            @Override
            public String getErrorMessage() {
                return "";
            }
        };
    }
}
