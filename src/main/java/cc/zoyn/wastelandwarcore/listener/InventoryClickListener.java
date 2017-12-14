package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.manager.ItemManager;
import cc.zoyn.wastelandwarcore.module.item.ChestPlate;
import cc.zoyn.wastelandwarcore.module.item.Shoes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * 容器点击事件监听
 */
public class InventoryClickListener {

    /**
     * 默认玩家移动速度
     */
    private static final float DEFAULT_PLAYER_MOVEMENT_SPEED = 0.3f;

    /**
     * 计算玩家切换装备后的护甲值,速度值,重量值等
     *
     * @param event 背包点击事件
     */
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        final Player player = (Player) event.getWhoClicked();
        Bukkit.getScheduler().runTask(Entry.getInstance(), () -> {
            PlayerInventory pi = player.getInventory();
            ItemManager manager = ItemManager.getInstance();
            ItemStack chestItem = pi.getChestplate();
            ItemStack shoesItem = pi.getBoots();
            if (ItemHasDisplayName(chestItem)) {

                ChestPlate chestPlate = (ChestPlate) manager.getItem(chestItem.getItemMeta().getDisplayName());
            }
            if (ItemHasDisplayName(shoesItem)) {
                Shoes shoes = (Shoes) manager.getItem(shoesItem.getItemMeta().getDisplayName());
                player.setWalkSpeed(DEFAULT_PLAYER_MOVEMENT_SPEED + shoes.getMovementSpeed());
            }
        });
    }

    private boolean ItemHasDisplayName(ItemStack is) {
        return is != null && is.getItemMeta() != null && is.getItemMeta().hasDisplayName();
    }
}
