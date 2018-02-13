package cc.zoyn.wastelandwarcore;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.manager.AllianceManager;
import cc.zoyn.wastelandwarcore.model.Alliance;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Optional;

/**
 * "默认联盟无人权"监听器.
 * 默认联盟存放不属于叛军也不属于盟军的玩家，这些人被称为流民
 *
 * @author lss233
 */
public class DefaultAllianceLimitListener implements Listener {

    /**
     * 流民不能破坏城镇核心
     *
     * @param event 核心破坏事件
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onBreakTownCore(BlockBreakEvent event) {
        if (!validRefugee(event.getPlayer()))
            return;
        if (event.getBlock().getType().equals(Material.BEACON) && CoreAPI.getTownManager().getTown(event.getBlock().getLocation()).isPresent())
            event.setCancelled(true);

    }

    /**
     * 判断是否为流民
     *
     * @param player 欲判断的玩家
     * @return 是则返回true
     */
    private boolean validRefugee(Player player) {
        Optional<Alliance> alliance = CoreAPI.getAllianceManager().getAlliance(player);
        return alliance.map(alliance1 -> alliance1.equals(AllianceManager.DEFAULT_ALLIANCE)).orElse(true);

    }
}
