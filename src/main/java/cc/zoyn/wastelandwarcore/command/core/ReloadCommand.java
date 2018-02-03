package cc.zoyn.wastelandwarcore.command.core;

import cc.zoyn.wastelandwarcore.util.SubCommand;
import me.skymc.wastelandwarcore.weaponlevel.WeaponLevelManager;
import org.bukkit.command.CommandSender;

/**
 * 重载指令
 *
 * @author Zoyn
 * @since 2018-02-03
 */
public class ReloadCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        WeaponLevelManager.getInst().reloadPattern();
        sender.sendMessage("§7重载成功!");
    }
}
