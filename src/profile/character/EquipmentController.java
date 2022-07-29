package profile.character;

import java.util.List;

import logic.ConditionFactory;
import profile.Profile;
import profile.inventory.InventoryController;
import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.EquipmentViewer;

public class EquipmentController {

    public static void openEquipmentProcessing(Profile profile) {
        ControllerState controllerState = ControllerState.PROCESSING;
        Character character = profile.getCharacter();
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();
            List<BodyPart> bodyParts = BodyPart.getMainBodyParts();
            MessageController.print(EquipmentViewer.getEquipmentView(profile, bodyParts));
            int selectedNumber = Selector.itemSelect(bodyParts);
            if (selectedNumber != 0) {
                BodyPart selectedBodyPart = bodyParts.get(selectedNumber - 1);
                if (character.hasEquipmentOn(selectedBodyPart)) {
                    character.unequip(selectedBodyPart, profile.getInventory());
                } else {
                    chooseEquipmentProcessing(profile, selectedBodyPart);
                }
            } else {
                controllerState = ControllerState.EXIT;
            }
        }
    }

    public static void chooseEquipmentProcessing(Profile profile, BodyPart bodyPart) {
        InventoryController inventoryController = InventoryController.withFilters(profile, ConditionFactory.forBodyPart(bodyPart));
        inventoryController.openInventoryProcessing();
    }
}
