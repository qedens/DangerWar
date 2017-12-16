package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.manager.ItemManager;
import cc.zoyn.wastelandwarcore.module.item.ChestPlate;
import cc.zoyn.wastelandwarcore.module.item.Shoes;
import cc.zoyn.wastelandwarcore.module.item.UniversalItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static cc.zoyn.wastelandwarcore.util.ItemStackUtils.itemHasDisplayName;

/**
 * 容器点击事件监听
 */
public class InventoryClickListener implements Listener {

    /**
     * 默认玩家移动速度
     */
    private static final float DEFAULT_PLAYER_MOVEMENT_SPEED = 0.3f;
    /**
     * 默认玩家最大血量上限
     */
    private static final double DEFAULT_PLAYER_MAXHEALTH = 20.0;

    /**
     * 计算玩家切换装备后的护甲值,速度值,重量值等
     *
     * @param event 背包点击事件
     */
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        final Player player = (Player) event.getWhoClicked();
        Bukkit.getScheduler().runTask(Entry.getInstance(), () -> {
            PlayerInventory pi = player.getInventory();
            ItemManager manager = ItemManager.getInstance();
            ItemStack chestItem = pi.getChestplate();
            ItemStack shoesItem = pi.getBoots();

            // 判断胸甲
            if (itemHasDisplayName(chestItem)) {
                UniversalItem item = manager.getItemByName(chestItem.getItemMeta().getDisplayName());
                if (item instanceof ChestPlate) {
                    ChestPlate chestPlate = (ChestPlate) manager.getItemByName(chestItem.getItemMeta().getDisplayName());
                    player.setMaxHealth(DEFAULT_PLAYER_MAXHEALTH + chestPlate.getHealth());
                }
            }

            // 判断鞋子
            if (itemHasDisplayName(shoesItem)) {
                UniversalItem item = manager.getItemByName(shoesItem.getItemMeta().getDisplayName());
                if (item instanceof Shoes) {
                    Shoes shoes = (Shoes) item;
                    player.setWalkSpeed(DEFAULT_PLAYER_MOVEMENT_SPEED + shoes.getMovementSpeed());
                }
            }
        });
    }


}
