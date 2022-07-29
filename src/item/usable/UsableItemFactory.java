package item.usable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import item.Rarity;
import logic.ProfileCondition;

public class UsableItemFactory {

    private static class UsableItemTemplate implements Serializable {
        private String name;
        private Rarity rarity;
        private String descriptionTemplate;
        private final List<ProfileCondition> useConditions;
        private final List<Integer> values;
        private int value;

        public UsableItemTemplate(
                String name,
                Rarity rarity,
                String descriptionTemplate,
                List<ProfileCondition> useConditions,
                List<Integer> values,
                int value)
        {
            this.name = name;
            this.rarity = rarity;
            this.descriptionTemplate = descriptionTemplate;
            this.useConditions = useConditions;
            this.values = values;
            this.value = value;
        }

        public static UsableItemTemplate empty() {
            return new UsableItemTemplate("", Rarity.UNDEFINED, "", new ArrayList<>(), new ArrayList<>(), 0);
        }

        public UsableItemTemplate withName(String name) {
            this.name = name;
            return this;
        }

        public UsableItemTemplate withDescription(String description) {
            this.descriptionTemplate = description;
            return this;
        }

        public UsableItemTemplate addValue(int value) {
            this.values.add(value);
            return this;
        }

        public UsableItemTemplate withRarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public UsableItemTemplate withCondition(ProfileCondition profileCondition) {
            this.useConditions.add(profileCondition);
            return this;
        }

        public UsableItemTemplate addDescription(String additionalDescription) {
            String beginDescriptionLine = descriptionTemplate.length() > 0 ? "$ " : "";
            descriptionTemplate += (beginDescriptionLine + additionalDescription);
            return this;
        }

        public UsableItemTemplate withValue(int value) {
            this.value = value;
            return this;
        }

        public UsableItem buildWithAction(UseAction onUse) {
            addDescription("Consumable;");
            for (ProfileCondition profileCondition : useConditions) {
                addDescription(profileCondition.getDescription());
            }

            return new UsableItem(name, descriptionTemplate, rarity, value, useConditions, onUse);
        }
    }
}
