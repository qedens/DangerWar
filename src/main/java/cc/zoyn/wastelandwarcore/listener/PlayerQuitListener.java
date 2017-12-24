package cc.zoyn.wastelandwarcore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.chat.Channel;
import cc.zoyn.wastelandwarcore.module.common.user.User;

/**
 * @author Zoyn
 * @since 2017-12-16
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
    	Player player = event.getPlayer();
    	User user = CoreAPI.getUserManager().getUserByName(player.getName());
        Channel channel = user.getChannel();
        
        channel.removeUser(user);
        CoreAPI.getUserManager().removeElement(user);
    }

}
