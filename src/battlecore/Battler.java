package battlecore;

import java.util.ArrayList;
import java.util.List;

import creature.Creature;
import utils.HasName;

public class Battler implements HasName {
    private final String name;
    List<? extends Creature> creatures;

    public Battler(String name, List<? extends Creature> creatures) {
        this.name = name;
        this.creatures = creatures;
    }

    public static Battler newBattlerWithName(String name) {
        return new Battler(name, new ArrayList<>());
    }

    public List<? extends Creature> getCreatures() {
        return creatures;
    }

    public void onBattleEnd() {
    }

    @Override
    public String getName() {
        return name;
    }
}