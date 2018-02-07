package cc.zoyn.wastelandwarcore.command;

import cc.zoyn.wastelandwarcore.command.town.TownHelpCommand;
import cc.zoyn.wastelandwarcore.command.town.TownSetCommand;
import cc.zoyn.wastelandwarcore.command.town.TownStartWarCommand;
import cc.zoyn.wastelandwarcore.command.town.TownStopWarCommand;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import com.google.common.collect.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * @author Zoyn
 * @since 2018-02-03
 */
public class TownCommandHandler extends CommandHandler {

    private final static Map<String, SubCommand> commandMap = Maps.newHashMap();

    // 初始化指令
    public TownCommandHandler() {
        registerSubCommand("help", new TownHelpCommand());
        registerSubCommand("set", new TownSetCommand());
        registerSubCommand("startwar", new TownStartWarCommand());
        registerSubCommand("stopwar", new TownStopWarCommand());
    }

    @Override
    public Map<String, SubCommand> getCommandMap() {
        return commandMap;
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
        if (!getCommandMap().containsKey(args[0].toLowerCase())) {
            sender.sendMessage("§c未知命令!");
            return true;
        }
        // args[0] ---> 子命令
        SubCommand subCommand = getCommandMap().get(args[0].toLowerCase());
        subCommand.execute(sender, args);
        return true;
    }

}
