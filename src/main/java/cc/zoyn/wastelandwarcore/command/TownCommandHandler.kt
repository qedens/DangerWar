package cc.zoyn.wastelandwarcore.command

import cc.zoyn.wastelandwarcore.command.town.TownHelpCommand
import cc.zoyn.wastelandwarcore.command.town.TownSetCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

/**
 * 该类用于管理Town指令及相关子命令
 *
 * @author Zoyn
 * @since 2018-02-03
 */
class TownCommandHandler : CommandHandler() {

    init {
        registerSubCommand("help", TownHelpCommand())
        registerSubCommand("set", TownSetCommand())
    }

    override fun onCommand(sender: CommandSender?, cmd: Command?, label: String?, args: Array<out String>?): Boolean {
        if (!sender!!.isOp) {
            sender.sendMessage("§c权限不足!")
            return true
        }
        if (args!!.isEmpty()) {
            commandMap["help"]?.execute(sender, args)
            return true
        }
        if (!commandMap.containsKey(args[0])) {
            sender.sendMessage("§c未知命令!")
            return true
        }

        // args[0] ---> 子命令
        val subCommand = commandMap[args[0]]
        subCommand?.execute(sender, args)
        return true
    }
}