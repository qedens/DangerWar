package cc.zoyn.wastelandwarcore.module.item;

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
}