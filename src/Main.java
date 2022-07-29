import java.util.Scanner;

import profile.Profile;
import profile.ProfileController;
import profile.ProfileTagEnum;
import profile.character.CharacterStat;
import stat.ChangeDuration;
import utils.MessageController;


public class Main {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        MessageController.print("Enter your name:");
        String name = in.nextLine();

        Profile profile = Profile.loadFromFile();
        if (profile.getName().equals("ERROR_ON_LOAD")) {
            profile = Profile.withName(name);
            for (CharacterStat characterStat : CharacterStat.values()) {
                profile.getCharacter().getStats().addStatChange(characterStat, ChangeDuration.PERMANENT, 1, false);
            }
            profile.getProfileTags().add(ProfileTagEnum.SLIME_SHOP_LEVEL, 1);
            profile.getProfileTags().add(ProfileTagEnum.SLIME_DUNGEON_LEVEL, 1);
            profile.getProfileTags().set(ProfileTagEnum.MINE_LEVEL, 1);
            profile.getProfileTags().set(ProfileTagEnum.ORE_SHOP_LEVEL, 1);
            profile.getProfileTags().set(ProfileTagEnum.CHALLENGE_THE_HORDE, 1);
            profile.getProfileTags().set(ProfileTagEnum.CHALLENGE_ONE_PUNCH_MAN, 1);
            profile.getProfileTags().set(ProfileTagEnum.CHALLENGE_TANK, 1);
            profile.saveProfile();
        }

        ProfileController profileController = new ProfileController(profile);
        profileController.processOpenProfile();
    }
}
