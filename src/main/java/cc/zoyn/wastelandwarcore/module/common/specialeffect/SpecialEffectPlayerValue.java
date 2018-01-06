package cc.zoyn.wastelandwarcore.module.common.specialeffect;

import lombok.Getter;
import lombok.Setter;

/**
 * 应用到玩家身上的特殊属性
 *
 * @author DFKK
 */
public class SpecialEffectPlayerValue extends SpecialEffect {
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
        super(effect.getType(),effect.getDuration(),effect.getLevel());
        startDuration = System.currentTimeMillis();
        nowDuration = System.currentTimeMillis();
    }
    /**
     * 获取剩余的时间
     * @return 剩余的时间
     */
    public long getRestDuration() {
        long rest = startDuration+getDuration()-nowDuration;
        return rest>0 ? rest : 0;
    }
}
