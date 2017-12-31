package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.manager.UserManager;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * 容器点击事件监听
 */
public class InventoryClickListener implements Listener {
    /**
     * 计算玩家切换装备后的护甲值,速度值,重量值等
     *
     * @param event 背包点击事件
     */
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        final Player player = (Player) event.getWhoClicked();
        UserManager mananer = UserManager.getInstance();
        Bukkit.getScheduler().runTask(Entry.getInstance(), () -> {
            //更新User数据
            User user = mananer.getUserByName(player.getName());
            user.setArmor(PlayerUtils.getArmor(player));
            user.setResistance(PlayerUtils.getResistance(player));
            user.setMoveSpeed(PlayerUtils.getMoveSpeed(player));
            //更新玩家的最大生命值
            ((Damageable) player).setMaxHealth(PlayerUtils.getMaxHealth(player));
        });
    }


}
