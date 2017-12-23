package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.chat.Channel;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.module.town.Town;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Zoyn
 * @since 2017-12-16
 */
public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        User user = CoreAPI.getUserManager().getUserByName(player.getName());

        // 防止 reload 时出现用户数据不存在的问题
        if (user == null) {
            Channel channel = CoreAPI.getChannelManager().getDefaultChannel();
            Town town = CoreAPI.getTownManager().getTownByMember(player.getName());
            user = new User(player.getName(), channel, town, null);

            channel.addUser(user);
            CoreAPI.getUserManager().addElement(user);
        }

        Channel channel = user.getChannel();
        // 发送消息
        String message = channel.sendMessage(user, event.getMessage());

        Bukkit.getConsoleSender().sendMessage(message);
        event.setCancelled(true);
    }

}
