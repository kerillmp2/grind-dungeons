package profile.converter;

import item.Item;
import profile.Profile;
import profile.inventory.Inventory;
import utils.HasName;
import utils.MessageController;

public class ConvertOption implements HasName {
    private final String name;
    private final ConvertAction convertAction;

    private ConvertOption(String name, ConvertAction convertAction) {
        this.name = name;
        this.convertAction = convertAction;
    }

    public static ConvertOption forItemsAndAmounts(Item from, Item to, int fromAmount, int toAmount) {
        String name = from.getName() + " X" + fromAmount + " ---> " +  to.getName() + " X" + toAmount;
        return new ConvertOption(name, profile -> {
            Inventory inventory = profile.getInventory();
            if (inventory.has(from, fromAmount)) {
                inventory.pull(from, fromAmount);
                inventory.put(to, toAmount);
                MessageController.print("You converted " + fromAmount + " " + from.getName() + " to " + toAmount + " " + to.getName());
            } else {
                MessageController.print("You don't have enough " + from.getName() + "!");
            }
        });
    }

    public void resolve(Profile profile) {
        convertAction.convert(profile);
    }

    @Override
    public String getName() {
        return name;
    }
}
