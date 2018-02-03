package cc.zoyn.wastelandwarcore.api;

import cc.zoyn.wastelandwarcore.manager.*;
import cc.zoyn.wastelandwarcore.module.town.Town;
import cc.zoyn.wastelandwarcore.util.ActionBarUtils;
import cc.zoyn.wastelandwarcore.util.TitleUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 核心API
 *
 * @author Zoyn
 * @since 2017-12-09
 */
public class CoreAPI {
    private static boolean inWar;

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

    /**
     * 创建一个城镇对象
     *
     * @param name            城镇名
     * @param level           城镇等级
     * @param owner           城镇盟主
     * @param members         城镇成员
     * @param ally            城镇盟友
     * @param residences      城镇所有的领地名
     * @param leftUpBeacon    左上核心
     * @param rightUpBeacon   右上核心
     * @param leftDownBeacon  左下核心
     * @param rightDownBeacon 右下核心
     * @param centerEndBlock  中心基底
     * @return {@link Town}
     */
    public static Town createTown(String name, int level, String owner, List<String> members, String ally, List<String> residences, Beacon leftUpBeacon, Beacon rightUpBeacon, Beacon leftDownBeacon, Beacon rightDownBeacon, Block centerEndBlock) {
        Town town = new Town(name, level, owner, members, ally, residences, leftUpBeacon, rightUpBeacon, leftDownBeacon, rightDownBeacon, centerEndBlock);
        getTownManager().addElement(town);
        return town;
    }

}
