package item.equipment;

import java.io.Serializable;

import profile.character.BodyPart;

public enum EquipmentType implements Serializable {

    UNDEFINED("UNDEFINED", BodyPart.UNDEFINED),
    HELMET("Helmet", BodyPart.HEAD),
    ONE_HANDED("One handed", BodyPart.ONE_HAND),
    TWO_HANDED("Two handed", BodyPart.TWO_HANDS),
    ARMOR("Armor", BodyPart.BODY),
    GLOVES("Gloves", BodyPart.GLOVES),
    BELT("Belt", BodyPart.BELT),
    PANTS("Pants", BodyPart.LEGS),
    BOOTS("Boots", BodyPart.BOOTS),
    RING("Ring", BodyPart.ANY_RING),
    NECKLACE("Necklace", BodyPart.NECK),
    CONSUMABLE("Consumable", BodyPart.ANY_CONSUMABLE);

    private final String name;
    private final BodyPart bodyPart;

    EquipmentType(String name, BodyPart bodyPart) {
        this.name = name;
        this.bodyPart = bodyPart;
    }

    public String getName() {
        return name;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public static EquipmentType forBodyPart(BodyPart bodyPart) {
        for (EquipmentType equipmentType : EquipmentType.values()) {
            if (equipmentType.bodyPart == bodyPart) {
                return equipmentType;
            }
        }
        return UNDEFINED;
    }
}
