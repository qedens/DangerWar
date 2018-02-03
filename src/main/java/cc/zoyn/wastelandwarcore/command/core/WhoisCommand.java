package cc.zoyn.wastelandwarcore.command.core;

import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * 信息查询命令
 *
 * @author Zoyn
 * @since 2017-12-23
 */
public class WhoisCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        System.out.println(Arrays.toString(args));
    }
}
