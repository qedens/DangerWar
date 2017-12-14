package cc.zoyn.wastelandwarcore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import cc.zoyn.wastelandwarcore.manager.ItemManager;
import cc.zoyn.wastelandwarcore.module.item.Chest;
import cc.zoyn.wastelandwarcore.module.item.Shoes;

public class PlayerInventoryClickListener {
	private static final float DEFAULT_PLAYER_MOVESPEED = 0.3f;
	/**
	 * 计算玩家切换装备后的护甲值,速度值,重量值等
	 * @param event
	 */
	public void PlayerInventoryClickEvent(InventoryClickEvent event) {
		if(!(event.getWhoClicked() instanceof Player))
			return ;
		final Player player = (Player) event.getWhoClicked();
		new BukkitRunnable() {
			public void run() {
				PlayerInventory pi = player.getInventory();
				ItemManager manager = ItemManager.getInstance();
				ItemStack chestitem = pi.getChestplate();
				ItemStack shoeitem = pi.getBoots();
				if(ItemHasDisplayName(chestitem)) {
					Chest chest = (Chest) manager.getItem(chestitem.getItemMeta().getDisplayName());
				}
				if(ItemHasDisplayName(shoeitem)) {
					Shoes shoe = (Shoes) manager.getItem(chestitem.getItemMeta().getDisplayName());
					player.setWalkSpeed(DEFAULT_PLAYER_MOVESPEED+shoe.getMovementSpeed());
				}
			}
		}.run();
	}
	private boolean ItemHasDisplayName(ItemStack is) {
		if(is != null && is.getItemMeta() != null && is.getItemMeta().hasDisplayName())
			return true;
		return false;
	}
}
