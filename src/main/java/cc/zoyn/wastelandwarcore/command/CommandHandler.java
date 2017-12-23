package cc.zoyn.wastelandwarcore.command;

import cc.zoyn.wastelandwarcore.command.subcommand.HelpCommand;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * 指令控制器
 *
 * @author Zoyn
 * @since 2017-12-16
 */
public class CommandHandler implements CommandExecutor {

    private final static Map<String, SubCommand> commandMap = Maps.newHashMap();

    public CommandHandler() {
        registerCommand("help", new HelpCommand());
    }

    public void registerCommand(String commandName, SubCommand subCommand) {
        if (commandMap.containsKey(commandName)) {
            Bukkit.getLogger().warning("duplicate add command!");
        }
        commandMap.put(commandName, subCommand);
    }

    public void unregisterCommand(String commandName) {
        if (commandMap.containsKey(commandName)) {
            commandMap.remove(commandName);
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            commandMap.get("help").execute(sender, args);
            return true;
        }
        if (!commandMap.containsKey(args[0])) {
            sender.sendMessage("§c未知命令!");
            return true;
        }

        // args[0] ---> 子命令
        SubCommand subCommand = commandMap.get(args[0]);
        subCommand.execute(sender, args);
        return true;
    }
}
