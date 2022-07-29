package utils;

import battlecore.DamageType;
import creature.Creature;

public class Calculator {
    public static int calculateDamage(DamageType damageType, int amount, Creature target, boolean canBeZero) {
        int armor = target.getArmorForDamageType(damageType);
        double resist = target.getResistForDamageType(damageType);
        return (int) Math.max((amount - armor) * (Math.max(1 - resist / 100, 0)), canBeZero ? 0 : 1);
    }

    public static int calculateMaxHP(int constitution, int str) {
        return (int) (constitution * 3.5 + str * 1.34);
    }

    public static Pair<Integer, Integer> calculateConvertation(double fromValue, double toValue) {
        if (fromValue >= toValue) {
            return new Pair<>(1, Math.floorDiv((int) fromValue, (int) toValue));
        } else {
            return new Pair<>(Math.floorDiv((int) toValue, (int) fromValue), 1);
        }
    }
}
