package profile;

import java.util.List;

import creature.CharacterCreature;
import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.StatsViewer;

public class StatsController {
    public static void openStatsProcessing(Profile profile) {
        ControllerState controllerState = ControllerState.PROCESSING;
        CharacterCreature creature = CharacterCreature.fromProfile(profile);
        int selectedNumber;
        while (controllerState != ControllerState.EXIT) {
            MessageController.print(StatsViewer.getStatsView(creature));
            selectedNumber = Selector.itemSelect(List.of(0));
            if (selectedNumber == 0) {
                controllerState = ControllerState.EXIT;
            }
        }
    }
}
