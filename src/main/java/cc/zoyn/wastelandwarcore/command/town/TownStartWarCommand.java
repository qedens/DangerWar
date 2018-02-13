package cc.zoyn.wastelandwarcore.command.town;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.api.event.WarStartEvent;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2018-02-07
 */
public class TownStartWarCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        WarStartEvent event = new WarStartEvent();
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        if (CoreAPI.isInWar()) {
            sender.sendMessage("§c当前已经是战争时期!");
            return;
        }
        CoreAPI.setInWar(true);
        //TODO CoreAPI.getTownManager().getList().forEach(Town::startWar);
        sender.sendMessage("§a设置成功!");
    }
}
