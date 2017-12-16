package cc.zoyn.wastelandwarcore.api;

import cc.zoyn.wastelandwarcore.manager.*;
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

    /**
     * 获取频道管理器的实例
     *
     * @return {@link ChannelManager}
     */
    public static ChannelManager getChannelManager() {
        return ChannelManager.getInstance();
    }

    /**
     * 获取用户管理器的实例
     *
     * @return {@link UserManager}
     */
    public static UserManager getUserManager() {
        return UserManager.getInstance();
    }

    /**
     * 获取UI管理器的实例
     *
     * @return {@link UIManager}
     */
    public static UIManager getUIManager() {
        return UIManager.getInstance();
    }

    /**
     * 获取物品管理器的实例
     *
     * @return {@link ItemManager}
     */
    public static ItemManager getItemManager() {
        return ItemManager.getInstance();
    }

    /**
     * 获取城镇管理器的实例
     *
     * @return {@link TownManager}
     */
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
