package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.api.event.WarStartEvent;
import cc.zoyn.wastelandwarcore.api.event.WarStopEvent;
import cc.zoyn.wastelandwarcore.exception.InvalidTownCoreException;
import cc.zoyn.wastelandwarcore.manager.TownManager;
import cc.zoyn.wastelandwarcore.manager.UserManager;
import cc.zoyn.wastelandwarcore.model.TownCore;
import cc.zoyn.wastelandwarcore.module.town.BeaconMode;
import cc.zoyn.wastelandwarcore.module.town.Town;
import cc.zoyn.wastelandwarcore.util.CommonUtils;
import org.bukkit.block.Beacon;
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

    @EventHandler
    public void onWarStart(WarStartEvent event) {
        CommonUtils.getOnlinePlayers().forEach(user -> {
            Player player = user.getPlayer();
            if (player != null) {
                CoreAPI.sendTitle(player, 2, 20, 2, "§6§l[ §e战争冲突 §6§l]", "所有城镇进入§c§l战争§f时期!");
            }
        });
    }

    @EventHandler
    public void onWarStop(WarStopEvent event) {
        CommonUtils.getOnlinePlayers().forEach(user -> {
            Player player = user.getPlayer();
            if (player != null) {
                CoreAPI.sendTitle(player, 2, 20, 2, "§6§l[ §e和平时期 §6§l]", "所有城镇进入§a§l和平§f状态!");
            }
        });
    }

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

    /**
     * 战争期间的方块破坏监听
     *
     * @param event 方块破坏事件
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!CoreAPI.isInWar()) {
            return;
        }
        Block block = event.getBlock();
        Player player = event.getPlayer();

        try {
            TownCore core  = new TownCore(block);

            // 是否为敌军破坏
            if (core.getTown().isFriendly(player.getName())) {
                event.setCancelled(true);
                return; // 友军破坏
            }
            core.updateBeaconMode();
            core.setTown(UserManager.getInstance().getUserByPlayer(player).getTown());
        } catch (InvalidTownCoreException e) {
            if(CoreAPI.isDebugMode())
                e.printStackTrace();
            return;
        }
    }
}
