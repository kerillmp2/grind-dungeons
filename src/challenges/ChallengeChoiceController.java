package challenges;

import java.util.ArrayList;
import java.util.List;

import profile.Profile;
import profile.ProfileTagEnum;
import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.ListViewer;

public class ChallengeChoiceController {

    public static void openChallengeChoice(Profile profile) {
        ControllerState controllerState = ControllerState.PROCESSING;
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();
            List<ChallengeController> challengeControllers = getChallengesFromProfile(profile);
            MessageController.print(ListViewer.viewList(challengeControllers, "Challenges"));
            int selectedNumber = Selector.itemSelect(challengeControllers);
            if (selectedNumber != 0) {
                ChallengeController challengeController = challengeControllers.get(selectedNumber - 1);
                challengeController.launchChallenge();
            } else {
                controllerState = ControllerState.EXIT;
            }
        }
    }

    private static List<ChallengeController> getChallengesFromProfile(Profile profile) {
        List<ChallengeController> controllers = new ArrayList<>();
        profile.getProfileTags().getTags().stream()
                .filter(ProfileTagEnum::isChallengeTag)
                .forEach(tag -> controllers.add(ChallengeControllerFactory.fromTag(tag, profile)));
        return controllers;
    }
}
