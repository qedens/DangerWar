package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.town.Town;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * 战争事件监听
 *
 * @author hammer354, Zoyn
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
        Player player = event.getPlayer();
        Town town = CoreAPI.getTownManager().getTownByLocation(block.getLocation());

        //放置的方块在城镇内，玩家非友军，方块下方为空气，取消事件
        if (town != null && !town.isFriendly(player.getName()) && block.getRelative(BlockFace.DOWN).isEmpty()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!CoreAPI.isInWar()) {
            return;
        }
        Block block = event.getBlock();
        Player player = event.getPlayer();

    }
}
