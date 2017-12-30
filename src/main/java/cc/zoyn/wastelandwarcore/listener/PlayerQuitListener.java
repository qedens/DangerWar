package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.chat.Channel;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * 玩家退出监听器处理
 *
 * @author Zoyn
 * @since 2017-12-16
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = CoreAPI.getUserManager().getUserByName(player.getName());
        Channel channel = user.getChannel();

        // 移除玩家
        channel.removeUser(user);
        CoreAPI.getUserManager().removeElement(user);
    }

}
