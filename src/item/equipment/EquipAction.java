package item.equipment;

import java.io.Serializable;

import profile.character.Character;

public interface EquipAction extends Serializable {
    void resolve(Character character);

    static EquipAction empty() {
        return (character) -> {};
    }
}
