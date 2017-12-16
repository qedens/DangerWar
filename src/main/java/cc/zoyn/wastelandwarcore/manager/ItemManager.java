package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.module.item.UniversalItem;

/**
 * 物品管理器
 *
 * @author Zoyn
 * @since 2017-12-10
 */
public class ItemManager extends AbstractManager<UniversalItem> {

    private static volatile ItemManager instance;

    // 防止意外实例化
    private ItemManager() {
    }

    /**
     * 获取物品管理器实例
     *
     * @return {@link ItemManager}
     */
    public static ItemManager getInstance() {
        if (instance == null) {
            synchronized (ItemManager.class) {
                if (instance == null) {
                    instance = new ItemManager();
                }
            }
        }
        return instance;
    }

    /**
     * 利用名字获取特殊物品对象
     * <p>利用物品名获取特殊物品对象,当获取不到时返回 null 值<p/>
     *
     * @param itemName 物品名
     * @return {@link UniversalItem}
     */
    public UniversalItem getItemByName(String itemName) {
        for (UniversalItem item : getList()) {
            if (item.getItemMeta() == null) {
                continue;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                continue;
            }
            if (item.getItemMeta().getDisplayName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

}
