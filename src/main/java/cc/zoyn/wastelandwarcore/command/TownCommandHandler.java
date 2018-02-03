package cc.zoyn.wastelandwarcore.command;

import cc.zoyn.wastelandwarcore.command.town.TownHelpCommand;
import cc.zoyn.wastelandwarcore.command.town.TownSetCommand;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2018-02-03
 */
public class TownCommandHandler extends CommandHandler {

    // 初始化指令
    {
        registerSubCommand("help", new TownHelpCommand());
        registerSubCommand("set", new TownSetCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return true;
        }
        if (args.length == 0) {
            getCommandMap().get("help").execute(sender, args);
            return true;
        }
        if (!getCommandMap().containsKey(args[0])) {
            sender.sendMessage("§c未知命令!");
            return true;
        }
        // args[0] ---> 子命令
        SubCommand subCommand = getCommandMap().get(args[0]);
        subCommand.execute(sender, args);
        return true;
    }

}
