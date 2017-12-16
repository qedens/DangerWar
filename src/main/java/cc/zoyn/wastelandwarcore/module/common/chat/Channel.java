package cc.zoyn.wastelandwarcore.module.common.chat;

import cc.zoyn.wastelandwarcore.module.common.user.User;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * 频道
 *
 * @author Zoyn
 * @since 2017-12-16
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Channel {

    private final String DEFAULT_PREFIX = String.format("§c[§6%s§c] ", getName());
    private String name;
    private List<User> userList;

    /**
     * 获取该频道在线的玩家列表
     *
     * @return {@link List}
     */
    public List<Player> getOnlinePlayers() {
        List<Player> playerList = Lists.newArrayList();

        userList.forEach(
                user -> {
                    Player player = Bukkit.getPlayerExact(user.getName());
                    if (player != null) {
                        playerList.add(player);
                    }
                });

        return playerList;
    }

    public void sendMessage(Player user, String message) {
        getOnlinePlayers().forEach(player -> player.sendMessage(DEFAULT_PREFIX + message));
    }


}
