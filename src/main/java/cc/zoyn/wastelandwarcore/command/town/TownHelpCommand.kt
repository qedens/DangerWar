package cc.zoyn.wastelandwarcore.command.town

import cc.zoyn.wastelandwarcore.util.SubCommand
import org.bukkit.command.CommandSender

/**
 * 该类用于提示OP关于Town相关操作的指令提示
 *
 * @author Zoyn
 * @since 2018-02-03
 */
class TownHelpCommand : SubCommand {

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (sender!!.isOp) {
            sender.sendMessage("§e======== §c[§6Town §f§l| §6城镇§c] §e========")
            sender.sendMessage(" §b/town help §7显示帮助")
            sender.sendMessage(" §b/town set owner [城镇名] [城主名] §7 设置该城镇的城主")
            sender.sendMessage(" §b/town set center [城镇名] §7设置你脚下的方块为该城镇的基底")
            return
        }
        sender.sendMessage("§c权限不足!")
    }
}