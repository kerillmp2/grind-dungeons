package challenges;

import profile.Profile;
import profile.ProfileTagEnum;
import utils.HasName;
import utils.MessageController;

public class ChallengeController implements HasName {
    private final String name;
    private final String description;
    private final ProfileTagEnum challengeTag;
    private final Profile profile;

    public ChallengeController(String name, String description, ProfileTagEnum challengeTag, Profile profile) {
        this.name = name;
        this.description = description;
        this.challengeTag = challengeTag;
        this.profile = profile;
    }

    public void launchChallenge() {
        int challengeLevel = (int) profile.getProfileTags().get(challengeTag);
        MessageController.print(profile.getName() + " begins challenge " + nameLevel() + "!");
        boolean wonChallenge = ChallengeProcessFactory.getChallenge(challengeTag, challengeLevel).checkOn(profile);
        if (wonChallenge) {
            MessageController.print(profile.getName() + " completes challenge " + nameLevel() + "!");
            ChallengeRewardFactory.getReward(challengeTag, challengeLevel).giveTo(profile);
            profile.getProfileTags().add(challengeTag, 1);
        } else {
            MessageController.print(profile.getName() + " lost challenge " + nameLevel() + "!");
        }
    }

    @Override
    public String getName() {
        return nameLevel() + " (" + description + ")";
    }

    public String nameLevel() {
        int challengeLevel = (int) profile.getProfileTags().get(challengeTag);
        return name + " [Level " + challengeLevel + "]";
    }
}
