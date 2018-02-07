package cc.zoyn.wastelandwarcore.command;

import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

import java.util.Map;

/**
 * 指令控制器
 *
 * @author Zoyn
 * @since 2017-12-16
 */
public abstract class CommandHandler implements CommandExecutor {

    public void registerSubCommand(String commandName, SubCommand subCommand) {
        if (getCommandMap().containsKey(commandName)) {
            Bukkit.getLogger().warning("duplicate add command!");
        }
        getCommandMap().put(commandName, subCommand);
    }

    public abstract Map<String, SubCommand> getCommandMap();
}
