package stat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import utils.Tag;

public class StatsContainer<S extends Tag, T extends Tag> implements Serializable {
    private final Map<S, List<Change<S>>> floatStatChanges;
    private final Map<S, List<Change<S>>> percentageStatChanges;
    private final Map<T, List<Change<T>>> floatTagChanges;
    private final Map<T, List<Change<T>>> percentageTagChanges;
    int tagReduceCounter = 0;
    int statReduceCounter = 0;


    public StatsContainer() {
        super();
        floatStatChanges = new HashMap<>();
        percentageStatChanges = new HashMap<>();
        floatTagChanges = new HashMap<>();
        percentageTagChanges = new HashMap<>();
    }

    public StatsContainer(Map<S, List<Change<S>>> floatStatChanges, Map<S, List<Change<S>>> percentageStatChanges, Map<T, List<Change<T>>> floatTagChanges, Map<T, List<Change<T>>> percentageTagChanges, int tagReduceCounter, int statReduceCounter) {
        this.floatStatChanges = floatStatChanges;
        this.percentageStatChanges = percentageStatChanges;
        this.floatTagChanges = floatTagChanges;
        this.percentageTagChanges = percentageTagChanges;
        this.tagReduceCounter = tagReduceCounter;
        this.statReduceCounter = statReduceCounter;
    }

    public int get(S stat) {
        double floatChange = getFloat(stat);
        double percentageChange = getPercentage(stat);
        statReduceCounter++;
        if (statReduceCounter > 50) {
            statReduceCounter = 0;
            reduceStatChanges(stat);
        }
        return (int) Math.max((floatChange) * (1 + percentageChange / 100.0), 0);
    }

    public static <S extends Tag, T extends Tag>  StatsContainer<S, T> copyOf(StatsContainer<S, T> statsContainer) {
        Map<S, List<Change<S>>> floatStatChanges = copyOf(statsContainer.floatStatChanges);
        Map<S, List<Change<S>>> percentageStatChanges = copyOf(statsContainer.percentageStatChanges);
        Map<T, List<Change<T>>> floatTagChanges = copyOf(statsContainer.floatTagChanges);
        Map<T, List<Change<T>>> percentageTagChanges = copyOf(statsContainer.percentageTagChanges);
        return new StatsContainer<>(floatStatChanges, percentageStatChanges, floatTagChanges, percentageTagChanges, 0, 0);
    }

    public static <T extends Tag> Map<T, List<Change<T>>> copyOf(Map<T, List<Change<T>>> map) {
        Map<T, List<Change<T>>> copy = new HashMap<>();
        for (Map.Entry<T, List<Change<T>>> entry : map.entrySet()) {
            T tag = entry.getKey();
            List<Change<T>> changes = entry.getValue();
            List<Change<T>> copyOfChanges = changes.stream().map(Change::copyOf).collect(Collectors.toList());
            copy.put(tag, copyOfChanges);
        }
        return copy;
    }

    public int getFloat(S stat) {
        return (int) floatStatChanges.getOrDefault(stat, new ArrayList<>()).stream().mapToDouble(change -> change.amount).sum();
    }

    public int getPercentage(S stat) {
        return (int) percentageStatChanges.getOrDefault(stat, new ArrayList<>()).stream().mapToDouble(change -> change.amount).sum();
    }

    public double getTag(T tag) {
        double floatChange = getFloatTag(tag);
        double percentageChange = getPercentageTag(tag);
        tagReduceCounter++;
        if (tagReduceCounter > 50) {
            tagReduceCounter = 0;
            reduceTagChanges(tag);
        }
        return Math.max(floatChange * (1 + percentageChange / 100.0), 0);
    }

    public double getFloatTag(T tag) {
        return floatTagChanges.getOrDefault(tag, new ArrayList<>()).stream().mapToDouble(change -> change.amount).sum();
    }

    public double getPercentageTag(T tag) {
        return percentageTagChanges.getOrDefault(tag, new ArrayList<>()).stream().mapToDouble(change -> change.amount).sum();
    }

    private void reduceStatChanges(S stat) {
        if (floatStatChanges.containsKey(stat) && floatStatChanges.get(stat).size() > 20) {
            for (ChangeDuration duration : ChangeDuration.values()) {
                double totalFloatChange = floatStatChanges.get(stat).stream()
                        .filter(change -> change.duration == duration).mapToDouble(change -> change.amount).sum();
                clearFloatChangesForStat(stat, duration);
                addStatChange(new Change<>(stat, duration, totalFloatChange, false));
            }
        }
        if (percentageStatChanges.containsKey(stat) && percentageStatChanges.get(stat).size() > 20) {
            for (ChangeDuration duration : ChangeDuration.values()) {
                double totalFloatChange = percentageStatChanges.get(stat).stream()
                        .filter(change -> change.duration == duration).mapToDouble(change -> change.amount).sum();
                clearPercentageChangesForStat(stat, duration);
                addStatChange(new Change<>(stat, duration, totalFloatChange, true));
            }
        }
    }

    private void reduceTagChanges(T tag) {
        if (floatTagChanges.containsKey(tag) && floatTagChanges.get(tag).size() > 20) {
            for (ChangeDuration duration : ChangeDuration.values()) {
                double totalFloatChange = floatTagChanges.get(tag).stream()
                        .filter(change -> change.duration == duration).mapToDouble(change -> change.amount).sum();
                clearFloatChangesForTag(tag, duration);
                addTagChange(new Change<>(tag, duration, totalFloatChange, false));
            }
        }
        if (percentageTagChanges.containsKey(tag) && percentageTagChanges.get(tag).size() > 20) {
            for (ChangeDuration duration : ChangeDuration.values()) {
                double totalFloatChange = percentageTagChanges.get(tag).stream()
                        .filter(change -> change.duration == duration).mapToDouble(change -> change.amount).sum();
                clearPercentageChangesForTag(tag, duration);
                addTagChange(new Change<>(tag, duration, totalFloatChange, true));
            }
        }
    }

    public void addStatChange(S stat, ChangeDuration duration, double amount, boolean isPercentage) {
        addStatChange(new Change<>(stat, duration, amount, isPercentage));
    }

    public void addStatChange(Change<S> change) {
        if (change.isPercentage) {
            addPercentageStatChange(change);
        } else {
            addFloatStatChange(change);
        }
    }

    public void addTagChange(T tag, ChangeDuration duration, double amount, boolean isPercentage) {
        addTagChange(new Change<>(tag, duration, amount, isPercentage));
    }

    public void addTagChange(Change<T> change) {
        if (change.isPercentage) {
            addPercentageTagChange(change);
        } else {
            addFloatTagChange(change);
        }
    }

    private void addFloatStatChange(Change<S> change) {
        if (!floatStatChanges.containsKey(change.tag)) {
            floatStatChanges.put(change.tag, new ArrayList<>());
        }
        floatStatChanges.get(change.tag).add(change);
    }

    private void addPercentageStatChange(Change<S> change) {
        if (!percentageStatChanges.containsKey(change.tag)) {
            percentageStatChanges.put(change.tag, new ArrayList<>());
        }
        percentageStatChanges.get(change.tag).add(change);
    }

    private void addFloatTagChange(Change<T> change) {
        if (!floatTagChanges.containsKey(change.tag)) {
            floatTagChanges.put(change.tag, new ArrayList<>());
        }
        floatTagChanges.get(change.tag).add(change);
    }

    private void addPercentageTagChange(Change<T> change) {
        if (!percentageTagChanges.containsKey(change.tag)) {
            percentageTagChanges.put(change.tag, new ArrayList<>());
        }
        percentageTagChanges.get(change.tag).add(change);
    }


    public void clearAllChangesForTag(T tag) {
        floatTagChanges.put(tag, new ArrayList<>());
        percentageTagChanges.put(tag, new ArrayList<>());
    }

    public void clearAllChangesForStat(S stat) {
        floatStatChanges.put(stat, new ArrayList<>());
        percentageStatChanges.put(stat, new ArrayList<>());
    }

    public void clearAllChangesForStat(S stat, ChangeDuration duration) {
        clearFloatChangesForStat(stat, duration);
        clearPercentageChangesForStat(stat, duration);
    }

    public void clearFloatChangesForStat(S stat, ChangeDuration duration) {
        floatStatChanges.get(stat).removeIf(change -> change.duration == duration);
    }

    public void clearPercentageChangesForStat(S stat, ChangeDuration duration) {
        percentageStatChanges.get(stat).removeIf(change -> change.duration == duration);
    }


    public void clearAllChangesForTag(T tag, ChangeDuration duration) {
        clearFloatChangesForTag(tag, duration);
        clearPercentageChangesForTag(tag, duration);
    }

    public void clearFloatChangesForTag(T tag, ChangeDuration duration) {
        floatTagChanges.get(tag).removeIf(change -> change.duration == duration);
    }

    public void clearPercentageChangesForTag(T tag, ChangeDuration duration) {
        percentageTagChanges.get(tag).removeIf(change -> change.duration == duration);
    }

    public void clearAllChangesWithDuration(ChangeDuration duration) {
        clearStatChangesWithDuration(duration);
        clearTagChangesWithDuration(duration);
    }

    public void clearStatChangesWithDuration(ChangeDuration duration) {
        floatStatChanges.values().forEach(tagChanges -> tagChanges.removeIf(tagChange -> tagChange.duration == duration));
        percentageStatChanges.values().forEach(tagChanges -> tagChanges.removeIf(tagChange -> tagChange.duration == duration));
    }

    public void clearStatChangesWithDuration(S stat, ChangeDuration duration) {
        floatStatChanges.get(stat).removeIf(tagChange -> tagChange.duration == duration);
        percentageStatChanges.get(stat).removeIf(tagChange -> tagChange.duration == duration);
    }

    public void clearTagChangesWithDuration(ChangeDuration duration) {
        floatTagChanges.values().forEach(tagChanges -> tagChanges.removeIf(tagChange -> tagChange.duration == duration));
        percentageTagChanges.values().forEach(tagChanges -> tagChanges.removeIf(tagChange -> tagChange.duration == duration));
    }

    public void clearTagChangesWithDuration(T tag, ChangeDuration duration) {
        floatTagChanges.get(tag).removeIf(tagChange -> tagChange.duration == duration);
        percentageTagChanges.get(tag).removeIf(tagChange -> tagChange.duration == duration);
    }

    public Map<S, List<Change<S>>> getFloatStatChanges() {
        return floatStatChanges;
    }

    public Map<S, List<Change<S>>> getPercentageStatChanges() {
        return percentageStatChanges;
    }

    public Map<T, List<Change<T>>> getFloatTagChanges() {
        return floatTagChanges;
    }

    public Map<T, List<Change<T>>> getPercentageTagChanges() {
        return percentageTagChanges;
    }

    public static class Change<T extends Tag> implements Serializable{
        private final T tag;
        private final ChangeDuration duration;
        private final double amount;
        private final boolean isPercentage;

        public Change(T tag, ChangeDuration duration, double amount, boolean isPercentage) {
            this.tag = tag;
            this.duration = duration;
            this.amount = amount;
            this.isPercentage = isPercentage;
        }

        public T getTag() {
            return tag;
        }

        public ChangeDuration getDuration() {
            return duration;
        }

        public double getAmount() {
            return amount;
        }

        public boolean isPercentage() {
            return isPercentage;
        }

        public Change<T> getReversed() {
            return new Change<>(tag, duration, (-1) * amount, isPercentage);
        }

        public static <T extends Tag> Change<T> copyOf(Change<T> change) {
            return new Change<>(change.tag, change.duration, change.amount, change.isPercentage);
        }
    }
}
