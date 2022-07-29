package profile.converter;

import java.util.ArrayList;
import java.util.List;

import item.Item;
import item.ItemFactory;
import profile.Profile;
import profile.ProfileTagEnum;
import utils.Pair;

public class ConverterControllerFactory {

    public static ConverterController slimeConverter(Profile profile) {
        List<Pair<Item, Double>> values = getValuesForItems(profile,
                ItemFactory.GREEN_SLIME,
                ItemFactory.YELLOW_SLIME,
                ItemFactory.RED_SLIME);
        return new ConverterController(profile, "Slime converter", values);
    }

    private static List<Pair<Item, Double>> getValuesForItems(Profile profile, Item... items) {
        List<Pair<Item, Double>> values = new ArrayList<>();
        for (Item item : items) {
            values.add(new Pair<>(item, profile.getProfileTags().getOrDefault(ProfileTagEnum.getValueTagForItem(item), 1)));
        }
        return values;
    }
}
