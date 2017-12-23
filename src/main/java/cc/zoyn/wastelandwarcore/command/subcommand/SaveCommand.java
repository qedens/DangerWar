package cc.zoyn.wastelandwarcore.command.subcommand;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2017-12-23
 */
public class SaveCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return;
        }
        CoreAPI.getTownManager().saveElements(CoreAPI.getTownManager().getList());
        CoreAPI.getUserManager().saveElements(CoreAPI.getUserManager().getList());
        sender.sendMessage("§c[§6WastelandWarCore§c] §a保存成功!");
    }
}
