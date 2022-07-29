package profile.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum BodyPart implements Serializable {
    UNDEFINED("UNDEFINED"),
    HEAD("Head"),
    BODY("Body"),
    GLOVES("Gloves"),
    RIGHT_HAND("Right hand"),
    LEFT_HAND("Left hand"),
    ONE_HAND("One hand"),
    TWO_HANDS("Two hands"),
    BELT("Belt"),
    LEGS("Legs"),
    BOOTS("Boots"),
    RING_1("Ring 1"),
    RING_2("Ring 2"),
    RING_3("Ring 3"),
    ANY_RING("Any ring"),
    CONSUMABLE_1("Consumable 1"),
    CONSUMABLE_2("Consumable 2"),
    CONSUMABLE_3("Consumable 3"),
    CONSUMABLE_4("Consumable 4"),
    ANY_CONSUMABLE("Any consumable"),
    NECK("Neck");

    private final String name;

    BodyPart(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<BodyPart> getMainBodyParts() {
        List<BodyPart> mainBodyParts = new ArrayList<>();
        mainBodyParts.add(RIGHT_HAND);
        mainBodyParts.add(LEFT_HAND);
        mainBodyParts.add(HEAD);
        mainBodyParts.add(BODY);
        mainBodyParts.add(GLOVES);
        mainBodyParts.add(BELT);
        mainBodyParts.add(LEGS);
        mainBodyParts.add(BOOTS);
        mainBodyParts.add(NECK);
        mainBodyParts.add(RING_1);
        mainBodyParts.add(RING_2);
        mainBodyParts.add(RING_3);
        mainBodyParts.add(CONSUMABLE_1);
        mainBodyParts.add(CONSUMABLE_2);
        mainBodyParts.add(CONSUMABLE_3);
        mainBodyParts.add(CONSUMABLE_4);
        return mainBodyParts;
    }

    public static List<BodyPart> getConsumables() {
        List<BodyPart> consumables = new ArrayList<>();
        consumables.add(CONSUMABLE_1);
        consumables.add(CONSUMABLE_2);
        consumables.add(CONSUMABLE_3);
        consumables.add(CONSUMABLE_4);
        return consumables;
    }
}
