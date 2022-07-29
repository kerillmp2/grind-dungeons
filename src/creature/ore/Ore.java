package creature.ore;

import java.util.List;
import java.util.Map;
import java.util.Set;

import battlecore.DamageSource;
import battlecore.DamageType;
import battlecore.action.Action;
import battlecore.action.ResolveTime;
import battlecore.battlefield.Battlefield;
import battlecore.battlefield.BattlefieldSide;
import battlecore.battlefield.ObjectStatus;
import creature.Creature;
import creature.CreatureTag;
import profile.character.CharacterStat;
import stat.StatsContainer;

public class Ore extends Creature {
    public Ore(Set<ObjectStatus> statusSet, Map<ResolveTime, List<Action>> actions, Battlefield battlefield, BattlefieldSide battlefieldSide, StatsContainer<CharacterStat, CreatureTag> statsContainer, StatsContainer<DamageType, CreatureTag> damageMin, StatsContainer<DamageType, CreatureTag> damageMax, StatsContainer<DamageType, CreatureTag> armor, StatsContainer<DamageType, CreatureTag> barrier, StatsContainer<DamageType, CreatureTag> resist, List<DamageType> attackDamageTypes, String name, int currentHP, int maxHP, int powerLevel) {
        super(statusSet, actions, battlefield, battlefieldSide, statsContainer, damageMin, damageMax, armor, barrier, resist, attackDamageTypes, name, currentHP, maxHP, powerLevel);
    }

    @Override
    public void dieFromSource(DamageSource damageSource) {

    }
}
