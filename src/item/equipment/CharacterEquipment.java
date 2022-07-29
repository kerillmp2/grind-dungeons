package item.equipment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import item.Item;
import item.PickUpAction;
import item.Rarity;
import logic.ProfileCondition;
import profile.character.BodyPart;
import profile.character.Character;
import profile.Profile;

public class CharacterEquipment extends Item implements Serializable {
    protected EquipAction equipAction;
    protected EquipAction unequipAction;
    protected EquipmentType equipmentType;
    protected List<ProfileCondition> equipProfileConditions;

    public CharacterEquipment(String name, String description, Rarity rarity, int value, List<ProfileCondition> equipProfileConditions, EquipmentType equipmentType, EquipAction equipAction, EquipAction unequipAction) {
        this(name, description, rarity, value, equipProfileConditions, equipmentType, equipAction, unequipAction, PickUpAction.defaultAction());
    }

    public CharacterEquipment(String name, String description, Rarity rarity, int value, List<ProfileCondition> equipProfileConditions, EquipmentType equipmentType, EquipAction equipAction, EquipAction unequipAction, PickUpAction pickUpAction) {
        super(name, description, rarity, value, pickUpAction);
        this.equipAction = equipAction;
        this.unequipAction = unequipAction;
        this.equipmentType = equipmentType;
        this.equipProfileConditions = equipProfileConditions;
    }

    public static CharacterEquipment newEquipment(
            String name,
            String description,
            Rarity rarity,
            int value,
            List<ProfileCondition> equipProfileConditions,
            EquipmentType equipmentType,
            EquipAction equipAction,
            EquipAction unequipAction)
    {
        return new CharacterEquipment(name, description, rarity, value, equipProfileConditions, equipmentType, equipAction, unequipAction);
    }

    public static CharacterEquipment empty(BodyPart bodyPart) {
        return CharacterEquipment.newEquipment("-", "", Rarity.UNDEFINED, 0, new ArrayList<>(), EquipmentType.forBodyPart(bodyPart), c -> {}, c -> {});
    }

    public String canBeEquippedBy(Profile profile) {
        StringBuilder errorMessages = new StringBuilder();
        for (ProfileCondition profileCondition : equipProfileConditions) {
            if (!profileCondition.check(profile)) {
                errorMessages.append("You can't equip ").append(this.name).append(": ").append(profileCondition.getErrorMessage());
            }
        }
        return errorMessages.toString();
    }

    public void onEquip(Character character) {
        equipAction.resolve(character);
    }

    public void onUnequip(Character character) {
        unequipAction.resolve(character);
    }

    public BodyPart getBodyPart() {
        return equipmentType.getBodyPart();
    }

    public String getName() {
        return name;
    }

    public String getNameWithRarity() {
        return name + " [" + rarity.getShortName() + "]";
    }

    public String getDescription() {
        return description;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getValue() {
        return value;
    }

    public EquipAction getEquipAction() {
        return equipAction;
    }

    public EquipAction getUnequipAction() {
        return unequipAction;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public List<ProfileCondition> getEquipProfileConditions() {
        return equipProfileConditions;
    }
}

