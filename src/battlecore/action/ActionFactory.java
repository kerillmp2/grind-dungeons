package battlecore.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import battlecore.DamageSource;
import battlecore.DamageType;
import battlecore.Resolvable;
import creature.CharacterCreature;
import creature.Creature;
import creature.CreatureTag;
import item.Item;
import profile.character.CharacterStat;
import stat.ChangeDuration;
import utils.MessageController;
import utils.RandomController;

public class ActionFactory {

    public static Action basicAttackAction(DamageType damageType) {
        return ActionTemplate.empty().withTime(ResolveTime.ON_MAIN_PHASE).withTag(ActionTag.TARGET_RANDOM_ALIVE_ENEMY, 1)
                .withTrigger(ResolveTime.AFTER_ATTACK)
                .withDamage(damageType).withAdditionalMessage("attacks").build(dealDamage());
    }

    public static Action dealDamageAction(ResolveTime resolveTime, DamageType damageType, double chance, String message) {
        Action action = ActionTemplate.empty().withTime(resolveTime).withTag(ActionTag.TARGET_RANDOM_ALIVE_ENEMY, 1)
                .withChance(chance).withDamage(damageType).withAdditionalMessage(message).build(dealDamage());
        action.setDescription(chance + "%% chance to deal " + damageType.getName() + " damage " + resolveTime.name);
        return action;
    }

    public static Action dealDamageAction(ResolveTime resolveTime, DamageType damageType, double coeff, double chance, String message) {
        Action action = ActionTemplate.empty().withTime(resolveTime).withTag(ActionTag.TARGET_RANDOM_ALIVE_ENEMY, 1)
                .withChance(chance).withDamage(damageType).withAdditionalMessage(message).build(dealDamage(coeff / 100.0));
        action.setDescription(chance + "%% chance to deal " + coeff + "%% of " + damageType.getName() + " damage " + resolveTime.name);
        return action;
    }

    public static Action healAction(ResolveTime resolveTime, int amount, double chance) {
        Action action = ActionTemplate.empty().withTime(resolveTime).withTag(ActionTag.TARGET_SELF, 1)
                .withTag(ActionTag.HEAL_FLOAT, amount)
                .withChance(chance).withAdditionalMessage("heals").build(heal());
        action.setDescription(chance + "%% chance to heal " + amount + " health " + resolveTime.name);
        return action;
    }

    public static Action gainArmor(ResolveTime resolveTime, DamageType damageType, ChangeDuration changeDuration, int amount, boolean isPercentage, double chance, String additionalMessage) {
        String percentage = isPercentage ? "%%" : "";
        Action action = ActionTemplate.empty().withTime(resolveTime).withChance(chance).withTag(ActionTag.TARGET_SELF).withAdditionalMessage(additionalMessage)
                .build(((actionInfo, performer) -> {
                    List<Creature> targets = getTargets(actionInfo, performer);
                    targets.forEach(target -> {
                        target.addArmor(damageType, amount, changeDuration, isPercentage);
                        if (!actionInfo.additionalMessage.isBlank()) {
                            MessageController.print(target.getBattleName() + " " + actionInfo.additionalMessage);
                        }
                        MessageController.print(target.getBattleName() + " gains " + amount + percentage + " " + damageType.getName() + " armor " + resolveTime.name + " " + changeDuration.getName());
                    });
                }));
        action.setDescription(chance + "%% chance to gain " + amount + percentage + " " + damageType.getName() + " armor " + resolveTime.name + " " + changeDuration.getName());
        return action;
    }

    public static Action trigger(ResolveTime resolveTime, ResolveTime trigger) {
        return ActionTemplate.empty().withTime(resolveTime).withTrigger(trigger).build((actionInfo, performer) -> { });
    }

    public static Action giveItem(ResolveTime resolveTime, Item item, int amount, double chance) {
        Action action = ActionTemplate.empty().withTime(resolveTime).withChance(chance).withTag(ActionTag.TARGET_CHARACTER_CREATURE)
                .build(((actionInfo, performer) -> {
                    List<Creature> targets = getTargets(actionInfo, performer);
                    targets.forEach(target -> {
                        if (target instanceof CharacterCreature characterCreature) {
                            MessageController.print(target.getBattleName() + " gains " + amount + " " + item.getName() + "!");
                            characterCreature.addAdditionalLoot(item, amount);
                        }
                    });
                }));
        action.setDescription(chance + "%% chance to get " + amount + " " + item.getName() + " " + resolveTime.name);
        return action;
    }

    public static Action farmer(Item item, int amount, double chance) {
        return ActionTemplate.empty().withTime(ResolveTime.AFTER_MONSTER_KILL).withChance(chance)
                .build((actionInfo, performer) -> {
                    if (performer instanceof CharacterCreature creature) {
                        MessageController.print(creature.getName() + " loots additional X" + amount + " " + item.getName());
                        creature.addAdditionalLoot(item, amount);
                    }
                });
    }

    public static Action undefinedAction() {
        return ActionTemplate.empty().build((i, c) -> {
        });
    }

    private static Resolvable heal() {
        return (actionInfo, performer) -> {
            List<Creature> targets = getTargets(actionInfo, performer);
            if (performer != null && targets.size() > 0) {
                StringBuilder targetNamesBuilder = new StringBuilder();
                for (int i = 0; i < targets.size(); i++) {
                    Creature target = targets.get(i);
                    if (performer != target) {
                        targetNamesBuilder.append(target.getBattleName());

                    } else {
                        targetNamesBuilder.append("self");
                    }
                    if (i < targets.size() - 1) {
                        targetNamesBuilder.append(", ");
                    }
                }
                String targetNames = targetNamesBuilder.toString();
                MessageController.print(performer.getBattleName() + " " + actionInfo.additionalMessage + " " + targetNames);
                double floatAmount = actionInfo.get(ActionTag.HEAL_FLOAT);
                double percentageOfMax = actionInfo.get(ActionTag.HEAL_PERCENTAGE_OF_MAX);
                double percentageOfMissing = actionInfo.get(ActionTag.HEAL_PERCENTAGE_OF_MISSING);
                for (Creature target : targets) {
                    if (floatAmount > 0) {
                        target.heal((int) floatAmount);
                    }
                    if (percentageOfMax > 0) {
                        target.heal((int) percentageOfMax * target.getMaxHp() / 100);
                    }
                    if (percentageOfMissing > 0) {
                        target.heal((int) percentageOfMissing * (target.getMaxHp() - target.getCurrentHP()) / 100);
                    }
                }
            }
        };
    }

    private static Resolvable dealDamage() {
        return dealDamage(1.0);
    }

    private static Resolvable dealDamage(double coeff) {
        return (actionInfo, performer) -> {
            List<Creature> targets = getTargets(actionInfo, performer);
            if (performer != null && targets.size() > 0) {
                StringBuilder targetNamesBuilder = new StringBuilder();
                for (int i = 0; i < targets.size(); i++) {
                    targetNamesBuilder.append(targets.get(i).getBattleName());
                    if (i < targets.size() - 1) {
                        targetNamesBuilder.append(", ");
                    }
                }
                String targetNames = targetNamesBuilder.toString();
                MessageController.print(performer.getBattleName() + " " + actionInfo.additionalMessage + " " + targetNames);
                for (Map.Entry<DamageType, Double> damageType : actionInfo.damageTypes.getTagValues().entrySet()) {
                    int amount = (int) (coeff * RandomController.randomInt(performer.getMinAttackForDamageType(damageType.getKey()), performer.getMaxAttackForDamageType(damageType.getKey())));
                    boolean isCritical = RandomController.roll(performer.getStatsContainer().getTag(CreatureTag.CRIT_CHANCE) / 100.0);
                    if (isCritical) {
                        amount = (int) (amount * performer.getStatsContainer().getTag(CreatureTag.CRIT_DAMAGE_MULTIPLIER) / 100.0);
                    }
                    for (Creature target : targets) {
                        target.takeDamage(damageType.getKey(), amount, new DamageSource(performer));
                        if (amount > 0) {
                            performer.resolveActionsWithTime(ResolveTime.AFTER_DEALING_DAMAGE);
                        }
                    }
                }
            }
        };
    }

    private static List<Creature> getTargets(ActionInfo actionInfo, Creature performer) {
        List<Creature> targets = new ArrayList<>();
        for (int i = 0; i < actionInfo.get(ActionTag.TARGET_SELF); i++) {
            targets.add(performer);
        }
        for (int i = 0; i < actionInfo.get(ActionTag.TARGET_RANDOM_ALIVE_ENEMY); i++) {
            Creature creature = performer.getBattlefieldSide().getRandomOppositeSideAliveCreature();
            if (creature != null) {
                targets.add(creature);
            }
        }
        List<Creature> characterCreatures = performer.getBattlefield().getAllCreatures().stream().filter(c -> c instanceof CharacterCreature).collect(Collectors.toList());
        for (int i = 0; i < actionInfo.get(ActionTag.TARGET_CHARACTER_CREATURE); i++) {
            if (characterCreatures.size() > 0) {
                Creature creature = RandomController.randomElementOf(characterCreatures);
                if (creature != null) {
                    targets.add(creature);
                }
            }
        }
        return targets;
    }

    private static class ActionTemplate {
        ActionInfo actionInfo;

        private ActionTemplate(ActionInfo actionInfo) {
            this.actionInfo = actionInfo;
        }

        public static ActionTemplate empty() {
            return new ActionTemplate(ActionInfo.empty());
        }

        public ActionTemplate withTag(ActionTag tag) {
            actionInfo.wrapTag(tag);
            return this;
        }

        public ActionTemplate withTag(ActionTag tag, Integer value) {
            actionInfo.wrapTag(tag, value);
            return this;
        }

        public ActionTemplate withDamage(DamageType damageType, int amount) {
            actionInfo.damageTypes.add(damageType, amount);
            return this;
        }

        public ActionTemplate withDamage(DamageType damageType) {
            actionInfo.damageTypes.add(damageType, 0);
            return this;
        }

        public ActionTemplate withStat(CharacterStat stat, int amount) {
            actionInfo.characterStats.add(stat, amount);
            return this;
        }

        public ActionTemplate withTag(CreatureTag tag, int amount) {
            actionInfo.creatureTags.add(tag, amount);
            return this;
        }

        public ActionTemplate withTime(ResolveTime resolveTime) {
            actionInfo.resolveTime = resolveTime;
            return this;
        }

        public ActionTemplate withTrigger(ResolveTime resolveTime) {
            actionInfo.withTrigger(resolveTime);
            return this;
        }

        public ActionTemplate withChance(double chance) {
            actionInfo.withChance(chance);
            return this;
        }

        public ActionTemplate withAdditionalMessage(String additionalMessage) {
            actionInfo.withAdditionalMessage(additionalMessage);
            return this;
        }

        public Action build(Resolvable resolvable) {
            return new Action(actionInfo, resolvable, "");
        }
    }
}
