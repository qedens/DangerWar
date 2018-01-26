package me.skymc.wastelandwarcore.command;

import cc.zoyn.wastelandwarcore.util.SubCommand;
import me.skymc.wastelandwarcore.utils.InventoryUtils;
import me.skymc.wastelandwarcore.utils.NumberUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author Zoyn
 * @since 2017-12-23
 */
public class TakeItemCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return;
        }
        
        if (args.length < 4) {
        	sender.sendMessage("§4指令不完整");
        	return;
        }
        
        Player player = Bukkit.getPlayerExact(args[1]);
        if (player == null) {
        	sender.sendMessage("§4玩家不在线");
        	return;
        }
        
        int amount = NumberUtils.parserInteger(args[2], 0);
        if (amount <= 0) {
        	sender.sendMessage("§4数量必须大于 0");
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 3; i < args.length ; i++) {
        	sb.append(args[i].replace("&", ""));
        	sb.append(" ");
        }
        
        InventoryUtils.takeItemStackAsDisplay(player.getInventory(), sb.substring(0, sb.length() - 1), amount);
        sender.sendMessage("§7移除完成!");
    }
}
