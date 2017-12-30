package cc.zoyn.wastelandwarcore.api;

import cc.zoyn.wastelandwarcore.manager.*;
import cc.zoyn.wastelandwarcore.module.town.Town;
import cc.zoyn.wastelandwarcore.util.ActionBarUtils;
import cc.zoyn.wastelandwarcore.util.TitleUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

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

    /**
     * 判断两个玩家是否为同一阵营
     *
     * @param playerAName 玩家A的名字
     * @param playerBName 玩家B的名字
     * @return {@link Boolean}
     */
    public static boolean isFriendly(String playerAName, String playerBName) {
        Town townA = getTownManager().getTownByMember(playerAName);
        Town townB = getTownManager().getTownByMember(playerBName);
        return townA.getName().equals(townB.getName());
    }

    /**
     * 判断两个玩家是否为同一阵营
     *
     * @param playerA 玩家A
     * @param playerB 玩家B
     * @return {@link Boolean}
     */
    public static boolean isFriendly(@Nonnull Player playerA, @Nonnull Player playerB) {
        return isFriendly(playerA.getName(), playerB.getName());
    }

    /**
     * 获取城镇的 盟主 名字
     *
     * @param town 城镇对象
     * @return 盟主的名字
     */
    public static String getTownOwnerName(Town town) {
        return Validate.notNull(town.getOwner());
    }

    /**
     * 获取城镇的 盟主 名字
     *
     * @param townName 城镇名
     * @return 盟主的名字
     */
    public static String getTownOwnerName(String townName) {
        return getTownOwnerName(getTownManager().getTownByName(townName));
    }

}
