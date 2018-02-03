package cc.zoyn.wastelandwarcore.command;

import cc.zoyn.wastelandwarcore.util.SubCommand;
import com.google.common.collect.Maps;
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

    private final static Map<String, SubCommand> commandMap = Maps.newHashMap();

    public void registerSubCommand(String commandName, SubCommand subCommand) {
        if (commandMap.containsKey(commandName)) {
            Bukkit.getLogger().warning("duplicate add command!");
        }
        commandMap.put(commandName, subCommand);
    }

    public Map<String, SubCommand> getCommandMap() {
        return commandMap;
    }
}
