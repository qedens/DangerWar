package me.skymc.wastelandwarcore.command;

import cc.zoyn.wastelandwarcore.util.SubCommand;
import me.skymc.wastelandwarcore.utils.InventoryUtils;
import me.skymc.wastelandwarcore.utils.NumberUtils;
import me.skymc.wastelandwarcore.weaponlevel.WeaponLevelManager;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * 
 * @author sky
 * @since 2018年1月27日00:42:46
 */
public class UpgradeCommand implements SubCommand {

    @SuppressWarnings("deprecation")
	@Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return;
        }
        
        if (!(sender instanceof Player)) {
        	sender.sendMessage("§4后台不允许这么做");
        	return;
        }
        
        ((Player) sender).setItemInHand(WeaponLevelManager.getInst().upgradeWeapon(((Player) sender).getItemInHand()));
        sender.sendMessage("§7星级完成!");
        
        int[] stage = WeaponLevelManager.getInst().getWeaponStage(((Player) sender).getItemInHand());
        if (stage != null) {
        	sender.sendMessage("§7当前星级: " + stage[0] + "阶" + stage[1] + "星");
        }
    }
}
