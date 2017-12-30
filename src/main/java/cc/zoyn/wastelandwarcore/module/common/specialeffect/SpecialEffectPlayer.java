package cc.zoyn.wastelandwarcore.module.common.specialeffect;

import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect.SpecialEffectType;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 存储玩家特殊属性的集合类
 *
 * @author DFKK
 */
public class SpecialEffectPlayer {
    /**
     * 全服玩家的属性合集
     */
    private static final Map<String, SpecialEffectPlayer> players = Maps.newHashMap();

    public static SpecialEffectPlayer getPlayerEffect(String name) {
        if (players.containsKey(name))
            return players.get(name);
        return null;
    }

    public static void removePlayerEffect(String name) {
        players.remove(name);
    }

    /**
     * 单独一个玩家的属性合集
     */
    private Map<SpecialEffectType, SpecialEffect> effects = Maps.newHashMap();

    public void addSpecialEffect(SpecialEffectType type, SpecialEffect effect) {
        effects.put(type, effect);
    }

    public boolean hasSpecialEffect(SpecialEffectType type) {
        if (!effects.containsKey(type))
            return false;
        return effects.get(type).getNowDuration() > 0;
    }

    public void removeSpecialEffect(SpecialEffectType type) {
        if (effects.containsKey(type))
            effects.remove(type);
    }

    public SpecialEffect getSpecialEffect(SpecialEffectType type) {
        if (effects.containsKey(type))
            return effects.get(type);
        return null;
    }

    /**
     * 效果计算方法
     */
    public static double getPoisoningHealth(int level) {
        return level / 10;
    }

    public static int getHunger(int level) {
        return level / 10;
    }

    public static float getSlow(int level, float speed, double resistance) {
        double slowValue = level - resistance;
        if (slowValue <= 0)
            return speed;
        return (float) (speed * 30 / (30 + slowValue));
    }

    public static double getWeaknessDamage(int level, double damage, double resistance) {
        double weaknessValue = level - resistance;
        if (weaknessValue <= 0)
            return damage;
        return damage * 30 / (30 + weaknessValue);
    }

    public static double getArmorBreak(int level, double armor, double resistance) {
        double armorValue = level - resistance;
        if (armorValue <= 0)
            return armor;
        return armor * 30 / (30 + armorValue);
    }
}