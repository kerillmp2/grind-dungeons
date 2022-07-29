package battlecore;

import creature.Creature;
import utils.HasName;

public record DamageSource(Creature owner, String name) implements HasName {

    public DamageSource(Creature owner) {
        this(owner, owner.getName());
    }

    @Override
    public String getName() {
        return name;
    }
}
