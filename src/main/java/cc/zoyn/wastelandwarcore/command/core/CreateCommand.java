package cc.zoyn.wastelandwarcore.command.core;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.util.SubCommand;
import org.bukkit.command.CommandSender;

public class CreateCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3)
            sender.sendMessage("请检查您的参数.");
        else if (args[1].equalsIgnoreCase("alliance")) {
            Alliance alliance = new Alliance(args[2]);
            CoreAPI.getAllianceManager().addElement(alliance);
            CoreAPI.getUserManager().getUserByName(args[2]).setAlliance(alliance);
            sender.sendMessage("创建成功.");
        }
    }
}
