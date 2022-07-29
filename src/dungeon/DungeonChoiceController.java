package dungeon;

import java.util.ArrayList;
import java.util.List;

import profile.Profile;
import profile.ProfileTagEnum;
import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.ListViewer;

public class DungeonChoiceController {

    public static void openDungeonProcessing(Profile profile) {
        ControllerState controllerState = ControllerState.PROCESSING;
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();
            List<DungeonController> dungeonControllers = getDungeonControllersForProfile(profile);
            MessageController.print(ListViewer.viewList(dungeonControllers, "Dungeons"));
            int selectedNumber = Selector.itemSelect(dungeonControllers);
            if (selectedNumber != 0) {
                DungeonController chosenDungeon = dungeonControllers.get(selectedNumber - 1);
                chosenDungeon.dungeonProcessing();
            } else {
                controllerState = ControllerState.EXIT;
            }
        }
    }

    private static List<DungeonController> getDungeonControllersForProfile(Profile profile) {
        List<DungeonController> controllers = new ArrayList<>();
        if (profile.getProfileTags().get(ProfileTagEnum.SLIME_DUNGEON_LEVEL) > 0) {
            controllers.add(DungeonControllerFactory.slimeDungeon(profile));
        }
        if (profile.getProfileTags().get(ProfileTagEnum.MINE_LEVEL) > 0) {
            controllers.add(DungeonControllerFactory.mines(profile));
        }
        return controllers;
    }
}
