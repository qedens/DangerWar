package cc.zoyn.wastelandwarcore.listener;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.town.Town;

/**
 * 战争事件监听
 *
 * @author hammer354
 * @since 2018-01-29
 */
public class WarListener implements Listener {
	/**
     * 处理方块放置
     *
     * @param event 方块放置事件
     */
	@EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
		//只在战争时期处理
		if (!CoreAPI.isInWar()) {
			return;
		}
		Block block = event.getBlock();
		Town town = CoreAPI.getTownManager().getTownByLocation(block.getLocation());
		//放置的方块在城镇内，玩家非友军，方块下方为空气，取消事件
		if (town != null && !town.isFriendly(event.getPlayer().getName())
				&& block.getRelative(BlockFace.DOWN).isEmpty()) {
			event.setCancelled(true);
		}
	}
}
