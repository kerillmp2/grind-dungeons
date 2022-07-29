package profile;

import java.io.Serializable;

import challenges.ChallengeChoiceController;
import dungeon.DungeonChoiceController;
import item.usable.UsableItem;
import profile.character.Character;
import profile.character.EquipmentController;
import profile.converter.ConverterChoiceController;
import profile.inventory.Inventory;
import profile.inventory.InventoryController;
import profile.library.Library;
import profile.library.LibraryController;
import shop.ShopChoiceController;
import utils.Constants;
import utils.TagContainer;

public class Profile implements Serializable {
    private final String name;
    private final Character character;
    private final Inventory inventory;
    private final Library library;
    private final TagContainer<ProfileTagEnum> profileTags;

    public Profile(String name, Character character, Inventory inventory, Library library, TagContainer<ProfileTagEnum> profileTags) {
        this.name = name;
        this.character = character;
        this.inventory = inventory;
        this.library = library;
        this.profileTags = profileTags;
    }

    public static Profile withName(String name) {
        return new Profile(name, Character.empty(), Inventory.empty(), Library.empty(), new TagContainer<>());
    }

    public static Profile loadFromFile() {
        return ProfileLoader.loadProfileFrom(Constants.getSavePath());
    }

    public void saveProfile() {
        ProfileSaver.saveProfile(this, Constants.getSavePath());
    }

    public void openInventory() {
        InventoryController.defaultController(this).openInventoryProcessing();
    }

    public void openEquipment() {
        EquipmentController.openEquipmentProcessing(this);
    }

    public void openStats() {
        StatsController.openStatsProcessing(this);
    }

    public void openShops() {
        ShopChoiceController.openShopsProcessing(this);
    }

    public void openDungeons() {
        DungeonChoiceController.openDungeonProcessing(this);
    }

    public void openLibrary() {
        LibraryController.defaultController(this).openLibraryProcessing();
    }

    public void openConverters() {
        ConverterChoiceController.openConverterProcessing(this);
    }

    public void openChallenges() {
        ChallengeChoiceController.openChallengeChoice(this);
    }

    public void useItem(UsableItem item) {
        item.onUse().resolveOn(this);
    }

    public void onBattleEnd() {
        character.onBattleEnd(this);
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Character getCharacter() {
        return character;
    }

    public Library getLibrary() {
        return library;
    }

    public TagContainer<ProfileTagEnum> getProfileTags() {
        return profileTags;
    }
}
