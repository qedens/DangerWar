package cc.zoyn.wastelandwarcore.module.item;

/**
 * @author Zoyn
 * @since 2017-12-14
 */
public interface IWeapon extends UniversalItem {


    /**
     * 获取武器的伤害
     *
     * @return 武器伤害值
     */
    double getDamage();

    /**
     * 获取武器的重量
     *
     * @return 武器重量
     */
    double getWeight();

}
