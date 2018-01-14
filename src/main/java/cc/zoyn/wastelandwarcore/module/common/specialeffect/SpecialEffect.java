package cc.zoyn.wastelandwarcore.module.common.specialeffect;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecialEffect {

    private final SpecialEffectType type;
    private final long duration;
    private final int level;

}