package cc.zoyn.wastelandwarcore.module.common.specialeffect;

import lombok.Getter;

public class SpecialEffect {
    @Getter
    private final SpecialEffectType type;
    @Getter
    private final long duration;
    @Getter
    private final int level;

    public SpecialEffect(SpecialEffectType type, long duration, int level) {
        this.type = type;
        this.duration = duration;
        this.level = level;
    }
}