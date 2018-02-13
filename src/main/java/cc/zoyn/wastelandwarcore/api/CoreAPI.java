package cc.zoyn.wastelandwarcore.api;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.manager.*;
import cc.zoyn.wastelandwarcore.util.ActionBarUtils;
import cc.zoyn.wastelandwarcore.util.TitleUtils;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * 核心API
 *
 * @author Zoyn
 * @since 2017-12-09
 */
public class CoreAPI {
    private static boolean inWar;

    public static void setInWar(boolean isInWar) {
        inWar = isInWar;
    }

    /**
     * 获取当前是否为战争状态
     *
     * @return true代表是/false代表不是
     */
    public static boolean isInWar() {
        return inWar;
    }

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

    /**
     * 获取联盟管理器的实例
     *
     * @return {@link AllianceManager}
     */
    public static AllianceManager getAllianceManager() {
        return AllianceManager.getInstance();
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subTitle) {
        TitleUtils.sendTitle(player, fadeIn, stay, fadeOut, title, subTitle);
    }

    public static void sendActionbar(Player player, String msg) {
        ActionBarUtils.sendBar(player, msg);
    }


    /**
     * 判断两个玩家是否为同一阵营
     *
     * @param playerA 玩家A
     * @param playerB 玩家B
     * @return 是则返回true
     */
    public static boolean isFriendly(@Nonnull Player playerA, @Nonnull Player playerB) {
        return CoreAPI.getAllianceManager().getAlliance(playerA).equals(CoreAPI.getAllianceManager().getAlliance(playerB));
    }

    public static boolean isDebugMode() {
        return Entry.getInstance().getConfig().getBoolean("GeneralOption.DebugMode", false);
    }
}
