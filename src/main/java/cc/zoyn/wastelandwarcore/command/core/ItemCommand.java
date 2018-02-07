package cc.zoyn.wastelandwarcore.command.core;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Zoyn
 * @since 2018-02-07
 */
public class ItemCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        String displayName = args[1];
        Player player = (Player) sender;
        player.getInventory().addItem(CoreAPI.getItemManager().getItemByName(displayName).getItemStack());
        player.sendMessage("已发送至你的背包中!");
    }
}
