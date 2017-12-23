package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.chat.Channel;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.module.town.Town;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Zoyn
 * @since 2017-12-16
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 判断是否已经有缓存放在UserManager中
        if (CoreAPI.getUserManager().getUserByName(player.getName()) == null) {
            Channel channel = CoreAPI.getChannelManager().getDefaultChannel();
            Town town = CoreAPI.getTownManager().getTownByMember(player.getName());
            User user = new User(player.getName(), channel.getName(), town.getName(), null);

            channel.addUser(user);
            CoreAPI.getUserManager().addElement(user);
        }
    }

}
