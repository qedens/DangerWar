package cc.zoyn.wastelandwarcore.command.town;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.town.Town;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2018-02-07
 */
public class TownStartWarCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        CoreAPI.setInWar(true);
        CoreAPI.getTownManager().getList().forEach(Town::startWar);
    }
}
