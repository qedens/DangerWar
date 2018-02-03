package cc.zoyn.wastelandwarcore.command.core;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * 数据保存指令
 *
 * @author Zoyn
 * @since 2017-12-23
 */
public class SaveCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        CoreAPI.getTownManager().saveElements(CoreAPI.getTownManager().getList());
        CoreAPI.getUserManager().saveElements(CoreAPI.getUserManager().getList());
        sender.sendMessage("§c[§6WastelandWarCore§c] §a保存成功!");
    }
}
