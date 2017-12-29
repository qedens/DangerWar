package cc.zoyn.wastelandwarcore.module.item;

/**
 * @author DFKK
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