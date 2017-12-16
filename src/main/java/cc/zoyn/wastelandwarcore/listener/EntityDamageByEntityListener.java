package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.manager.ItemManager;
import cc.zoyn.wastelandwarcore.module.item.IArmor;
import cc.zoyn.wastelandwarcore.module.item.UniversalItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static cc.zoyn.wastelandwarcore.util.ItemStackUtils.itemHasDisplayName;

/**
 * @author Zoyn
 * @since 2017-12-09
 */
public class EntityDamageByEntityListener implements Listener {

    /**
     * @param event 生物攻击生物事件
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        // 护甲削弱攻击
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            PlayerInventory pi = player.getInventory();
            ItemManager manager = ItemManager.getInstance();
            ItemStack chestItem = pi.getChestplate();
            ItemStack shoeItem = pi.getBoots();

            // 判断胸甲
            if (itemHasDisplayName(chestItem)) {
                UniversalItem item = manager.getItemByName(chestItem.getItemMeta().getDisplayName());
                if (item instanceof IArmor)
                    event.setDamage(event.getDamage() * 100 / (((IArmor) item).getDefense() + 100));
            }

            // 判断鞋子
            if (itemHasDisplayName(shoeItem)) {
                UniversalItem item = manager.getItemByName(shoeItem.getItemMeta().getDisplayName());
                if (item instanceof IArmor)
                    event.setDamage(event.getDamage() * 100 / (((IArmor) item).getDefense() + 100));
            }
        }
    }

}
