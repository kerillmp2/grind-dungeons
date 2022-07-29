package shop;

import java.util.ArrayList;
import java.util.List;

import profile.Profile;
import profile.ProfileTagEnum;
import utils.ControllerState;
import utils.MessageController;
import utils.Selector;
import viewers.ListViewer;

public class ShopChoiceController {
    public static void openShopsProcessing(Profile profile) {
        ControllerState controllerState = ControllerState.PROCESSING;
        while (controllerState != ControllerState.EXIT) {
            MessageController.clearConsole();
            List<ShopController> shopControllers = getShopControllersForProfile(profile);
            MessageController.print(ListViewer.viewList(shopControllers, "Shops"));
            int selectedNumber = Selector.itemSelect(shopControllers);
            if (selectedNumber != 0) {
                ShopController chosenShop = shopControllers.get(selectedNumber - 1);
                chosenShop.shopProcessing();
            } else {
                controllerState = ControllerState.EXIT;
            }
        }
    }

    public static List<ShopController> getShopControllersForProfile(Profile profile) {
        List<ShopController> controllers = new ArrayList<>();
        if (profile.getProfileTags().get(ProfileTagEnum.SLIME_SHOP_LEVEL) > 0) {
            controllers.add(ShopControllerFactory.slimeShopController(profile));
        }
        if (profile.getProfileTags().get(ProfileTagEnum.ORE_SHOP_LEVEL) > 0) {
            controllers.add(ShopControllerFactory.oreShopController(profile));
        }
        return controllers;
    }
}
