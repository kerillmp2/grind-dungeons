package challenges;

import profile.Profile;
import profile.ProfileTagEnum;

public class ChallengeControllerFactory {

    public static ChallengeController fromTag(ProfileTagEnum challengeTag, Profile profile) {
        return new ChallengeController(
                challengeTag.getName(),
                challengeDescriptionFromTag(challengeTag),
                challengeTag,
                profile
        );
    }

    public static String challengeDescriptionFromTag(ProfileTagEnum challengeTag) {
        switch (challengeTag) {
            case CHALLENGE_THE_HORDE -> {
                return "Beat The Horde of monsters!";
            }
            case CHALLENGE_ONE_PUNCH_MAN -> {
                return "Destroy dummy with ONE PUNCH!";
            }
            case CHALLENGE_TANK -> {
                return "Survive deadly attack!";
            }
            default -> {
                return "";
            }
        }
    }
}
