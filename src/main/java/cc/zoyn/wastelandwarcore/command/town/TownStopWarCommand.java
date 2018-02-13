package cc.zoyn.wastelandwarcore.command.town;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.api.event.WarStopEvent;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2018-02-07
 */
public class TownStopWarCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        WarStopEvent event = new WarStopEvent();
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        if (!CoreAPI.isInWar()) {
            sender.sendMessage("§c当前已经是和平时期!");
            return;
        }
        CoreAPI.setInWar(false);
        //TODO CoreAPI.getTownManager().getList().forEach(Town::stopWar);
        sender.sendMessage("§a设置成功!");
    }
}
