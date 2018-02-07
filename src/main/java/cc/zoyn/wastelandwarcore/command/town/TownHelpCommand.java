package cc.zoyn.wastelandwarcore.command.town;

import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2018-02-03
 */
public class TownHelpCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§e======== §c[§6Town §f§l| §6城镇§c] §e========");
        sender.sendMessage(" §b/town help §7显示帮助");
        sender.sendMessage(" §b/town set owner [城镇名] [城主名] §7 设置该城镇的城主");
        sender.sendMessage(" §b/town set center [城镇名] §7设置你脚下的方块为该城镇的基底");
        sender.sendMessage(" §b/town set leftUp [城镇名] §7设置你脚下的方块为该城镇的左上核心");
        sender.sendMessage(" §b/town set rightUp [城镇名] §7设置你脚下的方块为该城镇的右上核心");
        sender.sendMessage(" §b/town set leftDown [城镇名] §7设置你脚下的方块为该城镇的左下核心");
        sender.sendMessage(" §b/town set rightDown [城镇名] §7设置你脚下的方块为该城镇的右下核心");
        sender.sendMessage(" §b/town startWar §7开启城镇战争");
    }
}
