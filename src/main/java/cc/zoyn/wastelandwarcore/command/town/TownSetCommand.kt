package cc.zoyn.wastelandwarcore.command.town

import cc.zoyn.wastelandwarcore.api.CoreAPI
import cc.zoyn.wastelandwarcore.module.town.Town
import cc.zoyn.wastelandwarcore.util.SubCommand
import org.bukkit.Location
import org.bukkit.block.Beacon
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * 该类用于OP设置城镇数据时所做的操作
 *
 * @author Zoyn
 * @since 2018-02-03
 */
class TownSetCommand : SubCommand {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (args.size < 2) {
            sender.sendMessage("§f指令参数§c§l不正确§f!,请输入§a§l/town help§f查阅帮助")
            return
        }

        if (sender !is Player) {
            sender.sendMessage("你必须是一名玩家!")
            return
        }
        val town: Town = CoreAPI.getTownManager().getTownByName(args[2])
        val player: Player = sender
        val location: Location = player.location
        when (args[1]) {
            "owner" -> {
                town.owner = args[3]
            }
            "center" -> {
                town.centerEndBlock = location.add(0.toDouble(), (-1).toDouble(), 0.toDouble()).block
            }
            "leftUp" -> {
                town.leftUpBeacon = location.add(0.toDouble(), (-1).toDouble(), 0.toDouble()).block as Beacon
            }
            "rightUp" -> {
                town.rightUpBeacon = location.add(0.toDouble(), (-1).toDouble(), 0.toDouble()).block as Beacon
            }
            "leftDown" -> {
                town.leftDownBeacon = location.add(0.toDouble(), (-1).toDouble(), 0.toDouble()).block as Beacon
            }
            "rightDown" -> {
                town.rightDownBeacon = location.add(0.toDouble(), (-1).toDouble(), 0.toDouble()).block as Beacon
            }
        }
        player.sendMessage("§a设置成功!")
    }
}