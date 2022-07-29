package creature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import battlecore.DamageType;
import battlecore.action.Action;
import battlecore.action.ResolveTime;
import battlecore.battlefield.Battlefield;
import battlecore.battlefield.BattlefieldSide;
import battlecore.battlefield.ObjectStatus;
import item.Item;
import item.equipment.ConsumableEquipment;
import profile.Profile;
import profile.character.CharacterStat;
import stat.StatsContainer;
import utils.MessageController;

public class CharacterCreature extends Creature {
    private final Profile profile;
    private List<Item> additionalLoot;

    private CharacterCreature(
            Set<ObjectStatus> statusSet,
            Map<ResolveTime, List<Action>> actions,
            Battlefield battlefield,
            BattlefieldSide battlefieldSide,
            StatsContainer<CharacterStat, CreatureTag> statsContainer,
            StatsContainer<DamageType, CreatureTag> damageMin,
            StatsContainer<DamageType, CreatureTag> damageMax,
            StatsContainer<DamageType, CreatureTag> armor,
            StatsContainer<DamageType, CreatureTag> barrier,
            StatsContainer<DamageType, CreatureTag> resist,
            List<DamageType> attackDamageTypes,
            List<Item> additionalLoot,
            String name,
            int currentHP,
            int maxHP,
            int powerLevel,
            Profile profile)
    {
        super(statusSet, actions, battlefield, battlefieldSide, statsContainer, damageMin, damageMax, armor, barrier, resist, attackDamageTypes, name, currentHP, maxHP, powerLevel);
        this.profile = profile;
        this.additionalLoot = additionalLoot;
    }

    private CharacterCreature(Creature creature, Profile profile) {
        this(creature.getStatusSet(), creature.getActions(), creature.getBattlefield(), creature.battlefieldSide, creature.getStatsContainer(), creature.damageMin, creature.damageMax, creature.armor, creature.barrier, creature.resist, creature.attackDamageTypes, new ArrayList<>(), creature.name, creature.currentHP, creature.maxHP, creature.powerLevel, profile);
    }

    public static CharacterCreature fromProfile(Profile profile) {
        return new CharacterCreature(CreatureFactory.fromProfile(profile), profile);
    }

    @Override
    public void resolveActionsWithTime(ResolveTime resolveTime) {
        super.resolveActionsWithTime(resolveTime);
        List<ConsumableEquipment> consumables = profile.getCharacter().getConsumables();
        for (ConsumableEquipment consumable : consumables) {
            if (consumable.getConsumeTime() == resolveTime) {
                if (consumable.isConsumed()) {
                    profile.getCharacter().consume(consumable, profile.getInventory());
                    MessageController.print(consumable.getName() + " consumed!");
                }
            }
        }
    }

    public void addAdditionalLoot(Item item, int amount) {
        for (int i = 0; i < amount; i++) {
            additionalLoot.add(item);
        }
    }

    public void clearAdditionalLoot() {
        additionalLoot = new ArrayList<>();
    }

    public List<Item> getAdditionalLoot() {
        return additionalLoot;
    }

    public Profile getProfile() {
        return profile;
    }
}
