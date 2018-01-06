package cc.zoyn.wastelandwarcore.module.common.specialeffect;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 存储玩家特殊属性的集合类
 *
 * @author DFKK
 */
public class SpecialEffectPlayer {
    /**
     * 玩家的效果合集
     */
    private Map<SpecialEffectType, SpecialEffectPlayerValue> effects = Maps.newHashMap();

    public void addSpecialEffect(SpecialEffect effect) {
        effects.put(effect.getType(), new SpecialEffectPlayerValue(effect));
    }

    public boolean hasSpecialEffect(SpecialEffectType type) {
        return effects.containsKey(type) && effects.get(type).getNowDuration() > 0;
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

class SpecialEffectPlayerValue extends SpecialEffect {
    /**
     * 效果开始时间
     */
    @Getter
    private final long startDuration;
    /**
     * 效果当前经过时间
     */
    @Getter
    @Setter
    private long nowDuration;

    public SpecialEffectPlayerValue(SpecialEffect effect) {
        super(effect.getType(), effect.getDuration(), effect.getLevel());
        startDuration = System.currentTimeMillis();
        nowDuration = System.currentTimeMillis();
    }

    /**
     * 获取剩余的时间
     *
     * @return 剩余的时间
     */
    public long getRestDuration() {
        long rest = startDuration + getDuration() - nowDuration;
        return rest > 0 ? rest : 0;
    }
}