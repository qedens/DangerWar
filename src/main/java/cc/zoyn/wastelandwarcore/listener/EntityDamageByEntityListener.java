package cc.zoyn.wastelandwarcore.listener;

import static cc.zoyn.wastelandwarcore.util.ItemStackUtils.itemHasDisplayName;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import cc.zoyn.wastelandwarcore.manager.ItemManager;
import cc.zoyn.wastelandwarcore.module.item.IArmor;
import cc.zoyn.wastelandwarcore.module.item.UniversalItem;

/**
 * @author DFKK
 * @since 2017-12-09
 */
public class EntityDamageByEntityListener implements Listener {
	/**
	 * 
	 * @param event 生物攻击生物事件
	 */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event) {
    	/**
    	 * 护甲削弱攻击
    	 */
    	double armor = 0;
    	if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            armor = getArmor(player);
    	}
    	if(event.getDamager() instanceof Player) {
    		Player player = (Player) event.getDamager();
    		ItemStack weaponItem = player.getItemInHand();
    		if(itemHasDisplayName(weaponItem)) {
    			
    		}
    	}
    }
    /**
     * 获取玩家的总护甲值
     * @return 总护甲值
     */
    private static double getArmor(Player player) {
    	double armor = 0;
    	PlayerInventory pi = player.getInventory();
        ItemManager manager = ItemManager.getInstance();
        ItemStack chestItem = pi.getChestplate();
        ItemStack shoeItem = pi.getBoots();
        //堆积护甲
        if (itemHasDisplayName(chestItem)) {
        	UniversalItem item = manager.getItemByName(chestItem.getItemMeta().getDisplayName());
        	if(item instanceof IArmor)
        		armor = ((IArmor)item).getDefense();
        }
        if(itemHasDisplayName(shoeItem)) {
        	UniversalItem item = manager.getItemByName(shoeItem.getItemMeta().getDisplayName());
        	if(item instanceof IArmor)
        		armor = armor+((IArmor)item).getDefense();
        }
        return armor;
    }
}
