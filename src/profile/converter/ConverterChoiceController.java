package profile.converter;

import java.util.ArrayList;
import java.util.List;

import profile.Profile;
import profile.ProfileTagEnum;
import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.ListViewer;

public class ConverterChoiceController {
    public static void openConverterProcessing(Profile profile) {
        ControllerState controllerState = ControllerState.PROCESSING;
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();
            List<ConverterController> converterControllers = getConverterControllersFromProfile(profile);
            MessageController.print(ListViewer.viewList(converterControllers, "Converters"));
            int selectedNumber = Selector.itemSelect(converterControllers);
            if (selectedNumber != 0) {
                ConverterController chosenConverter = converterControllers.get(selectedNumber - 1);
                chosenConverter.converterProcessing();
            } else {
                controllerState = ControllerState.EXIT;
            }
        }
    }

    private static List<ConverterController> getConverterControllersFromProfile(Profile profile) {
        List<ConverterController> controllers = new ArrayList<>();
        if (profile.getProfileTags().get(ProfileTagEnum.SLIME_CONVERTER_BOUGHT) > 0) {
            controllers.add(ConverterControllerFactory.slimeConverter(profile));
        }
        return controllers;
    }
}
