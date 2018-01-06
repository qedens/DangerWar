package cc.zoyn.wastelandwarcore.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import cc.zoyn.wastelandwarcore.util.PlayerUtils;

/**
 * 玩家传送打断监听器处理
 *
 * @author hammer354
 * @since 2018-01-06
 */
public class TeleportInterruptListener implements Listener {
	/**
     * 处理玩家移动
     *
     * @param event 玩家移动事件
     */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();
		if (from.getBlockX() != to.getBlockX()
				|| from.getBlockY() != to.getBlockY()
				|| from.getBlockZ() != to.getBlockZ()) {
			PlayerUtils.cancelDelayedTeleport(event.getPlayer(), "传送被打断");
		}
	}
	
	/**
	 * 处理玩家死亡
	 *
	 * @param event 实体死亡事件
	 */
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			PlayerUtils.cancelDelayedTeleport((Player) event.getEntity(), "传送被打断");
		}
	}
	
	/**
	 * 处理玩家退出
	 *
	 * @param event 玩家退出事件
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		PlayerUtils.cancelDelayedTeleport(event.getPlayer());
	}
}
