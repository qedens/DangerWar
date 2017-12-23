package cc.zoyn.wastelandwarcore.util;

import org.bukkit.command.CommandSender;

/**
 * 子命令接口
 *
 * @author Zoyn
 * @since 2017-12-23
 */
public interface SubCommand {

    void execute(CommandSender sender, String[] args);

}
