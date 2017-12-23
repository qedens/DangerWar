package cc.zoyn.wastelandwarcore.command.subcommand;

import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Zoyn
 * @since 2017-12-23
 */
public class WhoisCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("§c权限不足!");
            return;
        }
        System.out.println(Arrays.toString(args));
    }
}
