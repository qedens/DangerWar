package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.module.item.UniversalItem;
import cc.zoyn.wastelandwarcore.util.ConfigurationUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

/**
 * 物品管理器
 *
 * @author Zoyn
 * @since 2017-12-10
 */
public class ItemManager extends AbstractManager<UniversalItem> implements SavableManager<UniversalItem> {

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

    @Override
    public void saveElements(List<UniversalItem> elements) {
        elements.forEach(this::saveElement);
    }

    @Override
    public void saveElement(UniversalItem universalItem) {
        Validate.notNull(universalItem);

        File file = new File(Entry.getInstance().getItemFolder(), universalItem.getItemMeta().getDisplayName() + ".yml");
        FileConfiguration fileConfiguration = ConfigurationUtils.loadYml(file);
        fileConfiguration.set("Item", universalItem);
        ConfigurationUtils.saveYml(fileConfiguration, file);
    }
}
