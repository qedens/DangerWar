package cc.zoyn.wastelandwarcore.module.item;

/**
 * @author Zoyn
 * @since 2017-12-14
 */
public interface IArmor extends UniversalItem {

    /**
     * 获取该防具的防御值
     * @return
     */
    double getDefense();

    /**
     * 获取该防具的特殊属性抵抗
     * @return
     */
    double getResistance();

}
