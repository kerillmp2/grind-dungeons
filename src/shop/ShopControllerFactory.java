package shop;

import profile.Profile;
import profile.ProfileTagEnum;

public class ShopControllerFactory {

    public static ShopController slimeShopController(Profile profile) {
        return new ShopController(profile, ProfileTagEnum.SLIME_SHOP_LEVEL);
    }

    public static ShopController oreShopController(Profile profile) {
        return new ShopController(profile, ProfileTagEnum.ORE_SHOP_LEVEL);
    }
}
