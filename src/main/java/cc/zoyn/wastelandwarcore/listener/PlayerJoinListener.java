package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.Entry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 玩家加入监听器
 *
 * @author Zoyn
 * @since 2017-12-16
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Entry.getInstance().initializePlayer(event.getPlayer());
    }

}
