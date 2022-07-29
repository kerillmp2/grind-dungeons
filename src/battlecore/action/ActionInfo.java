package battlecore.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import battlecore.DamageType;
import creature.CreatureTag;
import profile.character.CharacterStat;
import utils.TagContainer;

public class ActionInfo extends TagContainer<ActionTag> implements Serializable {
    public List<ResolveTime> triggers;
    public ResolveTime resolveTime;
    public TagContainer<DamageType> damageTypes;
    public TagContainer<CharacterStat> characterStats;
    public TagContainer<CreatureTag> creatureTags;
    public String additionalMessage = "";
    public double chance = 100.0;

    public ActionInfo(List<ResolveTime> triggers, ResolveTime resolveTime, TagContainer<DamageType> damageTypes, TagContainer<CharacterStat> characterStats, TagContainer<CreatureTag> creatureTags, String additionalMessage, double chance) {
        super();
        this.triggers = triggers;
        this.resolveTime = resolveTime;
        this.damageTypes = damageTypes;
        this.characterStats = characterStats;
        this.creatureTags = creatureTags;
        this.additionalMessage = additionalMessage;
        this.chance = chance;
    }

    public ActionInfo(Map<ActionTag, Double> tagValues, List<ResolveTime> triggers, ResolveTime resolveTime, TagContainer<DamageType> damageTypes, TagContainer<CharacterStat> characterStats, TagContainer<CreatureTag> creatureTags, String additionalMessage, double chance) {
        super(tagValues);
        this.triggers = triggers;
        this.resolveTime = resolveTime;
        this.damageTypes = damageTypes;
        this.characterStats = characterStats;
        this.creatureTags = creatureTags;
        this.additionalMessage = additionalMessage;
        this.chance = chance;
    }

    public static ActionInfo copyOf(ActionInfo actionInfo) {
        return new ActionInfo(
                actionInfo.tagValues,
                actionInfo.triggers,
                actionInfo.resolveTime,
                TagContainer.copyOf(actionInfo.damageTypes),
                TagContainer.copyOf(actionInfo.characterStats),
                TagContainer.copyOf(actionInfo.creatureTags),
                actionInfo.additionalMessage,
                actionInfo.chance
        );
    }

    public static ActionInfo empty() {
        return new ActionInfo(new ArrayList<>(), ResolveTime.UNDEFINED, new TagContainer<>(), new TagContainer<>(), new TagContainer<>(), "", 100.0);
    }

    public ActionInfo wrapTag(ActionTag tag) {
        addTag(tag);
        return this;
    }

    public ActionInfo wrapTag(ActionTag tag, Integer value) {
        add(tag, value);
        return this;
    }

    public ActionInfo wrapTag(ActionTag tag, Double value) {
        add(tag, value);
        return this;
    }

    public ActionInfo overrideTag(ActionTag tag, Double value) {
        tagValues.put(tag, value);
        return this;
    }

    public ActionInfo overrideTagMax(ActionTag tag, Double value) {
        if (tagValues.containsKey(tag)) {
            tagValues.put(tag, Math.max(value, tagValues.get(tag)));
        } else {
            tagValues.put(tag, value);
        }
        return this;
    }

    public ActionInfo overrideTagMin(ActionTag tag, Double value) {
        if (tagValues.containsKey(tag)) {
            tagValues.put(tag, Math.min(value, tagValues.get(tag)));
        } else {
            tagValues.put(tag, value);
        }
        return this;
    }

    public ActionInfo withTime(ResolveTime resolveTime) {
        this.resolveTime = resolveTime;
        return this;
    }

    public ActionInfo withAdditionalMessage(String message) {
        this.additionalMessage = message;
        return this;
    }

    public ActionInfo withTrigger(ResolveTime resolveTime) {
        this.triggers.add(resolveTime);
        return this;
    }

    public ActionInfo withChance(double chance) {
        this.chance = chance;
        return this;
    }

    public ResolveTime getResolveTime() {
        return resolveTime;
    }
}
