package cc.zoyn.wastelandwarcore.command.town;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.model.Town;
import cc.zoyn.wastelandwarcore.model.TownCore;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.Location;
import org.bukkit.block.Beacon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

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
        Optional<Town> townOptional = CoreAPI.getTownManager().getTown(args[2]);
        if (!townOptional.isPresent()) {
            sender.sendMessage("§4无法获取该城镇，请检查是否有误!");
            return;
        }
        Town town = townOptional.get();
        Player player = (Player) sender;
        Location location = player.getLocation();
        switch (args[1].toLowerCase()) { // 转小写后进行判断
            case "alliance":
                Optional<Alliance> alliance = CoreAPI.getAllianceManager().getAlliance(CoreAPI.getUserManager().getUserByName(args[3]));
                if (!alliance.isPresent()) {
                    sender.sendMessage("§4无法获取该联盟，请检查是否有误!");
                    return;
                }
                town.setAlliance(alliance.get());
            case "center":
                town.setCenterEndBlock(location.add(0D, -1D, 0).getBlock());
            case "leftup":
                town.getTownCores()[0] = new TownCore((Beacon) location.add(0D, -1D, 0).getBlock());
            case "rightup":
                town.getTownCores()[1] = new TownCore((Beacon) location.add(0D, -1D, 0).getBlock());
            case "leftdown":
                town.getTownCores()[2] = new TownCore((Beacon) location.add(0D, -1D, 0).getBlock());
            case "rightdown":
                town.getTownCores()[3] = new TownCore((Beacon) location.add(0D, -1D, 0).getBlock());
        }
        player.sendMessage("§a设置成功!");
    }
}
