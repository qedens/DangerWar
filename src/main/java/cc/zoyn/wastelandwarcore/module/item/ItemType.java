package cc.zoyn.wastelandwarcore.module.item;

/**
 * 物品类型, 用于自定义物品时所做的判断
 *
 * @author Zoyn
 * @since 2017-12-30
 */
public enum ItemType {

    CHEST_PLATE("胸甲"),
    SHOES("靴子"),
    SWORD("剑"),
    WAND("法杖");

    private String displayName;

    ItemType(String displayName) {
        this.displayName = displayName;
    }

}
