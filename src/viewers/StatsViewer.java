package viewers;

import battlecore.DamageType;
import creature.Creature;
import profile.character.CharacterStat;
import utils.Constants;

public class StatsViewer extends Viewer {

    public static String getStatsView(Creature creature) {
        Window window = new Window();
        window.add(getStatsHead(creature));
        window.add(getStatsBody(creature));
        window.add(getStatsFooter(creature));
        return window.getView();
    }

    private static String getStatsHead(Creature creature) {
        Window window = new Window();
        window.lineWithAngles();
        window.centeredText(creature + "'s stats");
        return window.getView();
    }

    private static String getStatsBody(Creature creature) {
        Window window = new Window();
        window.lineWithAngles();

        StringBuilder mainStatsView = new StringBuilder();
        for (CharacterStat stat : CharacterStat.values()) {
            mainStatsView.append(stat.getName()).append(": ").append(creature.getStat(stat)).append("; ");
        }
        window.centeredText(mainStatsView.toString());
        window.lineWithAngles();
        for (DamageType damageType : DamageType.values()) {
            String nameStr = damageType.getName() + ":";
            String damageStr = "Damage " + creature.getMinAttackForDamageType(damageType) + "-" + creature.getMaxAttackForDamageType(damageType);
            String armorStr = "Armor " + "[" + creature.getArmor().get(damageType) + "]";
            String barrierStr = "Barrier " + "(" + creature.getBarrier().get(damageType) + ")";
            String damageTypeInfo = nameStr + " ".repeat(Constants.STATS_NAME_LEN.value - nameStr.length())
                    + damageStr + " ".repeat(Constants.STATS_DAMAGE_LEN.value - damageStr.length())
                    + armorStr + " ".repeat(Constants.STATS_ARMOR_LEN.value - armorStr.length())
                    + barrierStr + " ".repeat(Constants.STATS_BARRIER_LEN.value - barrierStr.length());
            window.line(damageTypeInfo);
        }
        return window.getView();
    }

    private static String getStatsFooter(Creature creature) {
        return new Window().lineWithAngles().line("0. Exit").lineWithAngles().getView();
    }
}
