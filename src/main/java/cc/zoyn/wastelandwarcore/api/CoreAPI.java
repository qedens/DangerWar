package cc.zoyn.wastelandwarcore.api;

import cc.zoyn.wastelandwarcore.manager.TownManager;
import cc.zoyn.wastelandwarcore.util.ActionBarUtils;
import cc.zoyn.wastelandwarcore.util.TitleUtils;
import org.bukkit.entity.Player;

/**
 * 核心API
 *
 * @author Zoyn
 * @since 2017-12-09
 */
public class CoreAPI {

    public static TownManager getTownManager() {
        return TownManager.getInstance();
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subTitle) {
        TitleUtils.sendTitle(player, fadeIn, stay, fadeOut, title, subTitle);
    }

    public static void sendActionbar(Player player, String msg) {
        ActionBarUtils.sendBar(player, msg);
    }

}
