package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.api.event.*;
import cc.zoyn.wastelandwarcore.exception.InvalidTownCoreException;
import cc.zoyn.wastelandwarcore.manager.TownManager;
import cc.zoyn.wastelandwarcore.manager.UserManager;
import cc.zoyn.wastelandwarcore.model.Town;
import cc.zoyn.wastelandwarcore.model.TownCore;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.runnable.WarDebuffRunnable;
import cc.zoyn.wastelandwarcore.util.CommonUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

import static cc.zoyn.wastelandwarcore.manager.AllianceManager.DEFAULT_ALLIANCE;

/**
 * 战争事件监听
 *
 * @author hammer354, Zoyn
 * @since 2018-01-29
 */
public class WarListener implements Listener {
    private final PotionEffect slowDigging = new PotionEffect(PotionEffectType.SLOW_DIGGING, 1, 2);

    @EventHandler
    public void onWarStart(WarStartEvent event) {
        WarDebuffRunnable.PROCEED = true;
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
        WarDebuffRunnable.PROCEED = false;
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
        Optional<Town> town = CoreAPI.getTownManager().getTown(block.getLocation());

        //开启DEBUFF,放置的方块在城镇内，玩家非友军，方块下方为空气，取消事件
        if (WarDebuffRunnable.PROCEED)
            if (town.isPresent() && !town.get().isFriendly(player.getName()) && block.getRelative(BlockFace.DOWN).isEmpty()) {
                event.setCancelled(true);
            }
    }

    /**
     * 战争期间的方块破坏监听
     *
     * @param event 方块破坏事件
     */
    @EventHandler(ignoreCancelled = true)
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
            core.setOwner(CoreAPI.getAllianceManager().getAlliance(player).get());
            // core.setClaimer(UserManager.getInstance().getUserByPlayer(player).getTown());
            core.update();
            WarDebuffRunnable.PROCEED = false;
            event.setCancelled(true);
        } catch (InvalidTownCoreException e) {
            if(CoreAPI.isDebugMode())
                e.printStackTrace();
        }
    }

    /**
     * 盟军破坏任意非己方核心，重新占领城镇
     *
     * @param event 方块被破坏事件
     */
    @EventHandler
    public void onClaimTownCore(BlockBreakEvent event) {
        if (CoreAPI.isInWar()) {
            return;
        }
        Block block = event.getBlock();
        User resident = UserManager.getInstance().getUserByPlayer(event.getPlayer());
        try {
            TownCore core = new TownCore(block);

            if (!resident.getAlliance().equals(core.getTown().getAlliance())) {
                core.getTown().setAlliance(resident.getAlliance());
                // 抢回来了。
                //TODO: 交互，这里是不是要发个贺电?
            }
            event.setCancelled(true);
        } catch (InvalidTownCoreException e) {
            if (CoreAPI.isDebugMode())
                e.printStackTrace();
        }
    }

    /**
     * 战争结束后进行城镇所有权转移事宜
     *
     * @param event 战争结束事件
     */
    @EventHandler(ignoreCancelled = true)
    public void townTransfer(WarStopEvent event) {
        // 服务器:大家都保住自己家的核心了吗?
        for (Town town : TownManager.getInstance().getList()) {
            for (TownCore core : town.getTownCores()) {
                if (!core.getOwner().equals(town)) {
                    // town: 我没有保住..
                    town.setAlliance(DEFAULT_ALLIANCE);
                }
            }
        }
        // 服务器:你们把对方的城镇核心都占领了没?
        TownManager.getInstance().getList()
                .stream()
                .filter(town -> town.getAlliance().equals(DEFAULT_ALLIANCE)) // 满分的同学请不要来凑热闹
                .filter(town ->
                        town.getTownCores()[0].getOwner().equals(town.getTownCores()[1].getOwner()) &&
                                town.getTownCores()[1].getOwner().equals(town.getTownCores()[2].getOwner()) &&
                                town.getTownCores()[2].getOwner().equals(town.getTownCores()[3].getOwner()) &&
                                town.getTownCores()[3].getOwner().equals(town.getTownCores()[4].getOwner()))
                .forEach(town -> {
                    TownOwnerTransferEvent townOwnerTransferEvent = new TownOwnerTransferEvent(town, town.getTownCores()[0].getTown().getAlliance(), town.getAlliance());
                    Bukkit.getPluginManager().callEvent(townOwnerTransferEvent);
                    if (!townOwnerTransferEvent.isCancelled())
                        town.setAlliance(town.getTownCores()[0].getTown().getAlliance());
                    // TODO: 交互，这里城镇占领成功了。
                });

        // 系统: 重置所有核心状况
        /*
        TownManager.getInstance().getList().forEach(town -> {
            for (TownCore core : town.getTownCores()) {
                core.setTown(town);
            }
        });
        */
    }

    /**
     * 战争期间禁止自立为叛军
     *
     * @param event 联盟创建事件
     */
    @EventHandler(ignoreCancelled = true)
    public void onWarLimit(AllianceCreatedEvent event) {
        if (!CoreAPI.isInWar())
            return;
        if (event.getAlliance().isRebel())
            event.setCancelled(true);
    }

    /**
     * 战争期间禁止接纳流民
     *
     * @param event 联盟创建事件
     */
    @EventHandler(ignoreCancelled = true)
    public void onWarLimit(AllianceJoinEvent event) {
        if (!CoreAPI.isInWar())
            return;
        if (event.getAlliance().isRebel())
            event.setCancelled(true);
    }

}
