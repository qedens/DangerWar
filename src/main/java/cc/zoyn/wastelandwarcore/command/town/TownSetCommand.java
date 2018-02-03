package cc.zoyn.wastelandwarcore.command.town;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.town.Town;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.Location;
import org.bukkit.block.Beacon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Zoyn
 * @since 2018-02-03
 */
public class TownSetCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§f指令参数§c§l不正确§f!,请输入§a§l/town help§f查阅帮助");
            return;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("你必须是一名玩家!");
            return;
        }
        Town town = CoreAPI.getTownManager().getTownByName(args[2]);
        Player player = (Player) sender;
        Location location = player.getLocation();
        switch (args[1].toLowerCase()) { // 转小写后进行判断
            case "owner":
                town.setOwner(args[3]);
            case "center":
                town.setCenterEndBlock(location.add(0D, -1D, 0).getBlock());
            case "leftup":
                town.setLeftUpBeacon((Beacon) location.add(0D, -1D, 0).getBlock());
            case "rightup":
                town.setRightUpBeacon((Beacon) location.add(0D, -1D, 0).getBlock());
            case "leftdown":
                town.setLeftDownBeacon((Beacon) location.add(0D, -1D, 0).getBlock());
            case "rightdown":
                town.setRightDownBeacon((Beacon) location.add(0D, -1D, 0).getBlock());
        }
        player.sendMessage("§a设置成功!");
    }
}
