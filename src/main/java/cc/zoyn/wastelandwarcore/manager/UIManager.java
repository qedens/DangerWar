package cc.zoyn.wastelandwarcore.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import cc.zoyn.wastelandwarcore.module.common.ui.UI;
import cc.zoyn.wastelandwarcore.util.ItemStackUtils;

/**
 * UI管理器
 *
 * @author Zoyn
 * @since 2017-12-10
 */
public class UIManager extends AbstractManager<UI> {

    private static volatile UIManager instance;
    
    {
    	//主菜单
    	addElement(
    			UI.builder()
    			.inventory(Bukkit.createInventory(null, InventoryType.CHEST, "主菜单"))
    			.item(11, ItemStackUtils.setItemDisplayName(new ItemStack(Material.BED), "回城"))
    			.item(12, ItemStackUtils.setItemDisplayName(new ItemStack(Material.SIGN), "切换聊天频道"))
    			.item(13, ItemStackUtils.setItemDisplayName(new ItemStack(Material.BOOK), "处理联盟事务"))
    			.item(14, ItemStackUtils.setItemDisplayName(new ItemStack(Material.GOLD_INGOT), "点券商城"))
    			.item(15, ItemStackUtils.setItemDisplayName(new ItemStack(Material.DIAMOND), "VIP特权服务"))
    			.item(16, ItemStackUtils.setItemDisplayName(new ItemStack(Material.FIREWORK), "特效奖励"))
    			.item(17, ItemStackUtils.setItemDisplayName(new ItemStack(Material.BARRIER), "关闭菜单"))
    			.allowOperation(false)
    			.build()
    			.registerEvents());
    }

    // 防止意外实例化
    private UIManager() {
    }

    /**
     * 获取UI管理器实例
     *
     * @return {@link UIManager}
     */
    public static UIManager getInstance() {
        if (instance == null) {
            synchronized (UIManager.class) {
                if (instance == null) {
                    instance = new UIManager();
                }
            }
        }
        return instance;
    }

    /**
     * 利用名字获取UI对象
     * <p>利用UI名获取UI对象,当获取不到时返回 null 值<p/>
     *
     * @param uiName 城镇名
     * @return {@link UI}
     */
    public UI getUIByName(String uiName) {
        for (UI ui : getList()) {
            if (ui.getTitle().equals(uiName)) {
                return ui;
            }
        }
        return null;
    }


}
