package cc.zoyn.wastelandwarcore.module.common.chat;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import com.google.common.collect.Lists;
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
@RequiredArgsConstructor
public class Channel {

    private final String channelFormat = Entry.getInstance().getConfig().getString("GeneralOption.PlayerChatFormat");
    private String name;
    private List<User> userList;

    public Channel(String channelName) {
        this.name = channelName;
        userList = Lists.newArrayList();
    }

    public Channel(String channelName, List<User> userList) {
        this.name = channelName;
        this.userList = userList;
    }

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

    /**
     * 给这个频道里的所有人发送信息
     *
     * @param user    用户
     * @param message 消息
     * @return 发送给所有人的信息
     */
    public String sendMessage(User user, String message) {
        String formatted = formattedMessage(user, message);
        getOnlinePlayers().forEach(player -> player.sendMessage(formatted));
        return formatted;
    }

    public void addUser(User user) {
        if (!userList.contains(user)) {
            userList.add(user);
        }
    }

    public void removeUser(User user) {
        if (userList.contains(user)) {
            userList.remove(user);
        }
    }

    private String formattedMessage(User user, String message) {
        return channelFormat
                .replaceAll("&", "§")
                .replaceAll("%channel%", getName())
                .replaceAll("%alliance_name%", user.getAlliance().getName())
                .replaceAll("%player_name%", user.getName())
                .replaceAll("%message%", message);
    }
}
