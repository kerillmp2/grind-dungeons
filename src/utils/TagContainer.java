package utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TagContainer<T extends Tag> implements Serializable {
    protected Map<T, Double> tagValues;
    private double step;
    private double defaultValue;

    public static <T extends Tag> TagContainer<T> copyOf(TagContainer<T> tagContainer) {
        Map<T, Double> valuesCopy = new HashMap<>();
        for (Map.Entry<T, Double> entry : tagContainer.tagValues.entrySet()) {
            valuesCopy.put(entry.getKey(), entry.getValue());
        }
        return new TagContainer<>(valuesCopy, tagContainer.step, tagContainer.defaultValue);
    }

    public TagContainer(Map<T, Double> tagValues, double step, double defaultValue) {
        this.tagValues = tagValues;
        this.step = step;
        this.defaultValue = defaultValue;
    }

    public TagContainer() {
       this(new HashMap<>(), 1.0, 0.0);
    }

    public TagContainer(Map<T, Double> tagValues) {
        this(tagValues, 1.0, 0.0);
    }

    public TagContainer(Iterable<T> tags) {
        tagValues = new HashMap<>();
        for (T tag : tags) {
            tagValues.put(tag, 0.0);
        }
    }

    public void addTag(T tag) {
        add(tag, step);
    }

    public void add(T tag, Integer value) {
        tagValues.put(tag, get(tag) + value);
    }

    public void add(T tag, Double value) {
        tagValues.put(tag, get(tag) + value);
    }

    public double get(T tag) {
        return getOrDefault(tag, defaultValue);
    }

    public double getOrDefault(T tag, double defaultValue) {
        return tagValues.getOrDefault(tag, defaultValue);
    }

    public double getOrDefault(T tag, int defaultValue) {
        return tagValues.getOrDefault(tag, (double) defaultValue);
    }

    public boolean has(T tag) {
        return get(tag) > defaultValue;
    }

    public boolean hasTags(T... tags) {
        for(T tag : tags) {
            if (!this.has(tag)) {
                return false;
            }
        }
        return true;
    }

    public double remove(T tag) {
        return tagValues.remove(tag);
    }

    public void set(T tag, double value) {
        tagValues.put(tag, value);
    }

    public void clear(T tag) {
        if (tagValues.containsKey(tag)) {
            tagValues.put(tag, defaultValue);
        }
    }

    public Map<T, Double> getTagValues() {
        return tagValues;
    }

    public Set<T> getTags() {
        return tagValues.keySet();
    }
}
