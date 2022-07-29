package creature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import battlecore.DamageSource;
import battlecore.DamageType;
import battlecore.HasBattleView;
import battlecore.action.Action;
import battlecore.action.ResolveTime;
import battlecore.battlefield.Battlefield;
import battlecore.battlefield.BattlefieldObject;
import battlecore.battlefield.BattlefieldSide;
import battlecore.battlefield.ObjectStatus;
import profile.character.CharacterStat;
import stat.ChangeDuration;
import stat.StatsContainer;
import utils.Calculator;
import utils.MessageController;
import utils.Pair;
import viewers.CreatureBattleViewer;

public class Creature extends BattlefieldObject implements HasBattleView {
    protected Battlefield battlefield;
    protected BattlefieldSide battlefieldSide;
    protected final StatsContainer<CharacterStat, CreatureTag> statsContainer;
    protected final StatsContainer<DamageType, CreatureTag> damageMin;
    protected final StatsContainer<DamageType, CreatureTag> damageMax;
    protected final StatsContainer<DamageType, CreatureTag> armor;
    protected final StatsContainer<DamageType, CreatureTag> barrier;
    protected final StatsContainer<DamageType, CreatureTag> resist;
    protected final List<DamageType> attackDamageTypes;
    protected final String name;
    protected final int maxHP;
    protected final int powerLevel;
    protected int currentHP;

    public Creature(
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
            String name,
            int currentHP,
            int maxHP,
            int powerLevel)
    {
        super(statusSet, actions);
        this.battlefield = battlefield;
        this.battlefieldSide = battlefieldSide;
        this.statsContainer = statsContainer;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.armor = armor;
        this.barrier = barrier;
        this.attackDamageTypes = attackDamageTypes;
        this.resist = resist;
        this.name = name;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.powerLevel = powerLevel;
    }

    public int getStat(CharacterStat creatureStat) {
        return (int) statsContainer.get(creatureStat);
    }

    public int getMaxHp() {
        return this.maxHP;
    }

    private void apply(CharacterStat creatureStat, ChangeDuration source, int amount, boolean isPercentage) {
        statsContainer.addStatChange(creatureStat, source, amount, isPercentage);
    }

    public void apply(CreatureTag creatureTag, ChangeDuration source, int amount, boolean isPercentage) {
        statsContainer.addTagChange(creatureTag, source, amount, isPercentage);
    }

    public int getCurrentHp() {
        return this.currentHP;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHP = Math.max(Math.min(this.maxHP, currentHp), 0);
        if (this.currentHP <= 0) {
            this.removeStatus(ObjectStatus.ALIVE);
            this.addStatus(ObjectStatus.DEAD);
        } else {
            this.removeStatus(ObjectStatus.DEAD);
            this.addStatus(ObjectStatus.ALIVE);
        }
    }

    public void takeDamage(DamageType damageType, int amount, DamageSource source) {
        int currentHp = getCurrentHp();
        int damage = Calculator.calculateDamage(damageType, amount,this, false);
        int damageAfterBarrier = reduceBarrier(damageType, damage);
        if (damage > damageAfterBarrier) {
            MessageController.print(this.getBattleName() + " absorbed " + (damage - damageAfterBarrier) + " with " + damageType.getName()
                    + " barrier! [" + damageType.getName() + " barrier left: " + barrier.get(damageType) + "]");
        }
        setCurrentHp(currentHp - damageAfterBarrier);
        MessageController.print(this.getBattleName() + " takes " + damageAfterBarrier + " " + damageType.getName() + " damage from " + source.getName() + "!");
        if (damageAfterBarrier > 0) {
            this.resolveActionsWithTime(ResolveTime.ON_TAKING_DAMAGE);
            if (this.isAlive()) {
                this.resolveActionsWithTime(ResolveTime.AFTER_TAKING_DAMAGE);
            } else {
                dieFromSource(source);
            }
        }
    }

    public void heal(int amount) {
        int currentHp = getCurrentHP();
        int healedAmount = Math.min(amount, maxHP - currentHp);
        setCurrentHp(currentHp + amount);
        MessageController.print(this.getBattleName() + " restores " + healedAmount + " HP! [" + getCurrentHP() + " / " + this.maxHP + "]");
        if (healedAmount > 0) {
            this.resolveActionsWithTime(ResolveTime.AFTER_HEALING);
        }
    }

    public int reduceBarrier(DamageType damageType, int damage) {
        int barrier = (int) this.barrier.get(damageType);
        if (barrier <= 0) {
            return damage;
        }
        int damageAfterBarrier = Math.max(damage - barrier, 0);
        this.barrier.addStatChange(damageType, ChangeDuration.UNTIL_BATTLE_END, (-1) * Math.min(damage, barrier), false);
        return damageAfterBarrier;
    }

    public int getMinAttackForDamageType(DamageType damageType) {
        return (int) damageMin.get(damageType);
    }

    public int getMaxAttackForDamageType(DamageType damageType) {
        return (int) damageMax.get(damageType);
    }

    public int getArmorForDamageType(DamageType damageType) {
        return (int) armor.get(damageType);
    }

    public String getBattleName() {
        return name + " [HP: " + getCurrentHp() + "]";
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefieldSide(BattlefieldSide battlefieldSide) {
        this.battlefieldSide = battlefieldSide;
    }

    public double getTag(CreatureTag tag) {
        return statsContainer.getTag(tag);
    }

    public boolean hasTag(CreatureTag tag) {
        return statsContainer.getTag(tag) > 0;
    }

    public void dieFromSource(DamageSource damageSource) {
        MessageController.print(this.getBattleName() + " died from " + damageSource.getName());
        damageSource.owner().resolveActionsWithTime(ResolveTime.AFTER_MONSTER_KILL);
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public List<String> getBattleView() {
        return CreatureBattleViewer.getCreatureView(name, getCurrentHp(), getMaxHP(), getAdditionalViewInfo());
    }

    private List<Pair<String, String>> getAdditionalViewInfo() {
        List<Pair<String, String>> additionalInfo = new ArrayList<>();
        for (DamageType damageType : attackDamageTypes) {
            additionalInfo.add(new Pair<>(getMinAttackForDamageType(damageType) + "-" + getMaxAttackForDamageType(damageType), damageType.getShortName() + " Damage"));
        }
        return additionalInfo;
    }

    public void resolveActionsWithTime(ResolveTime resolveTime) {
        List<Action> actions = this.getActions(resolveTime);
        for (Action action : actions) {
            action.resolveWithPerformer(this);
        }
        damageMin.clearAllChangesWithDuration(ChangeDuration.fromResolveTime(resolveTime));
        damageMax.clearAllChangesWithDuration(ChangeDuration.fromResolveTime(resolveTime));
        armor.clearAllChangesWithDuration(ChangeDuration.fromResolveTime(resolveTime));
        barrier.clearAllChangesWithDuration(ChangeDuration.fromResolveTime(resolveTime));
        resist.clearAllChangesWithDuration(ChangeDuration.fromResolveTime(resolveTime));
        statsContainer.clearAllChangesWithDuration(ChangeDuration.fromResolveTime(resolveTime));
    }

    public void addFloatDamage(DamageType damageType, int minAmount, int maxAmount) {
        this.damageMin.addStatChange(damageType, ChangeDuration.PERMANENT, minAmount, false);
        this.damageMax.addStatChange(damageType, ChangeDuration.PERMANENT, maxAmount, false);
    }

    public void addPercentageDamage(DamageType damageType, int minAmount, int maxAmount) {
        this.damageMin.addStatChange(damageType, ChangeDuration.PERMANENT, minAmount, true);
        this.damageMax.addStatChange(damageType, ChangeDuration.PERMANENT, maxAmount, true);
    }

    public void addArmor(DamageType damageType, int armor, ChangeDuration changeDuration, boolean isPercentage) {
        this.armor.addStatChange(damageType, changeDuration, armor, isPercentage);
    }

    public void addFloatArmor(DamageType damageType, int armor) {
        this.armor.addStatChange(damageType, ChangeDuration.PERMANENT, armor, false);
    }

    public void addPercentageArmor(DamageType damageType, int armor) {
        this.armor.addStatChange(damageType, ChangeDuration.PERMANENT, armor, true);
    }

    public void addFloatBarrier(DamageType damageType, int barrier) {
        this.barrier.addStatChange(damageType, ChangeDuration.PERMANENT, barrier, false);
    }

    public void addPercentageBarrier(DamageType damageType, int barrier) {
        this.barrier.addStatChange(damageType, ChangeDuration.PERMANENT, barrier, true);
    }

    public BattlefieldSide getBattlefieldSide() {
        return battlefieldSide;
    }

    public StatsContainer<CharacterStat, CreatureTag> getStatsContainer() {
        return statsContainer;
    }

    public StatsContainer<DamageType, CreatureTag> getDamageMin() {
        return damageMin;
    }

    public StatsContainer<DamageType, CreatureTag> getArmor() {
        return armor;
    }

    public StatsContainer<DamageType, CreatureTag> getBarrier() {
        return barrier;
    }

    public StatsContainer<DamageType, CreatureTag> getDamageMax() {
        return damageMax;
    }

    public List<DamageType> getAttackDamageTypes() {
        return attackDamageTypes;
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public boolean isAlive() {
        return hasStatus(ObjectStatus.ALIVE) && (!hasStatus(ObjectStatus.DEAD));
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public double getResistForDamageType(DamageType damageType) {
        return resist.get(damageType);
    }

    public StatsContainer<DamageType, CreatureTag> getResist() {
        return resist;
    }
}
