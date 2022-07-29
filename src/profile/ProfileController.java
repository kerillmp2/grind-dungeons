package profile;

import java.util.Arrays;
import java.util.List;

import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.ListViewer;

public class ProfileController {
    private final Profile profile;

    public ProfileController(Profile profile) {
        this.profile = profile;
    }

    public void processOpenProfile() {
        ControllerState controllerState = ControllerState.PROCESSING;
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();
            List<ProfileOption> profileOptions = getProfileOptions();
            MessageController.print(ListViewer.viewList(profileOptions, profile.getName()));
            int selectedNumber = Selector.itemSelect(profileOptions);
            if (selectedNumber != 0) {
                ProfileOption chosenOption = profileOptions.get(selectedNumber - 1);
                processOption(chosenOption);
            } else {
                controllerState = ControllerState.EXIT;
            }
        }
    }

    private void processOption(ProfileOption profileOption) {
        switch (profileOption) {
            case SHOPS -> profile.openShops();
            case DUNGEONS -> profile.openDungeons();
            case CHALLENGES -> profile.openChallenges();
            case LIBRARY -> profile.openLibrary();
            case INVENTORY -> profile.openInventory();
            case EQUIPMENT -> profile.openEquipment();
            case STATS -> profile.openStats();
            case CONVERTERS -> profile.openConverters();
        }
    }

    private static List<ProfileOption> getProfileOptions() {
        return Arrays.stream(ProfileOption.values()).toList();
    }
}