package cc.zoyn.wastelandwarcore.util;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * 通用工具类
 *
 * @author Zoyn
 * @since 2017-12-30
 */
public final class CommonUtils {

    /**
     * 获取在线玩家集合
     *
     * @return {@link List}
     */
    public static List<Player> getOnlinePlayers() {
        List<Player> playerList = Lists.newArrayList();

        for (World world : Bukkit.getWorlds()) {
            if (!world.getPlayers().isEmpty()) {
                playerList.addAll(world.getPlayers());
            }
        }
        return playerList;
    }

}
