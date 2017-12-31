package cc.zoyn.wastelandwarcore.module.item;

import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect;

import java.util.List;

/**
 * 武器设计接口
 *
 * @author DFKK, Zoyn
 * @since 2017-12-25
 */
public interface IWeapon extends UniversalItem {
    /**
     * 获取武器的伤害
     *
     * @return 武器伤害值
     */
    double getDamage();
    /**
     * 附加至目标的特殊属性
     *
     * @return 特殊属性
     */
    List<SpecialEffect> getEffects();
}