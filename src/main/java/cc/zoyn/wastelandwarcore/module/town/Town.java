package cc.zoyn.wastelandwarcore.module.town;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.block.Beacon;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * 表示一个城镇
 *
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Town implements ConfigurationSerializable {

    private String name;
    private int level;
    private String owner;
    private List<String> members;
    private String residence;

    private Beacon centerBeacon;
    private boolean fighting = false;
    private Timer resourceOutput = new Timer("resourceOutputTimer");

    /**
     * 魂晶
     */
    private AtomicDouble ghostCrystal = new AtomicDouble(0D);
    /**
     * 奥晶
     */
    private AtomicDouble arcaneCrystal = new AtomicDouble(0D);
    /**
     * 圣晶
     */
    private AtomicDouble holyCrystal = new AtomicDouble(0D);
    /**
     * 暗钢
     */
    private AtomicDouble darkSteelMagicMatter = new AtomicDouble(0D);
    /**
     * 秘银
     */
    private AtomicDouble mithrilMagicMatter = new AtomicDouble(0D);
    /**
     * 恒金
     */
    private AtomicDouble eternalGoldMagicMatter = new AtomicDouble(0D);

    public void setResidence(String residenceName) {
        this.residence = residenceName;
    }

    public void setResidence(ClaimedResidence residence) {
        this.residence = Validate.notNull(residence).getName();
    }

    public ClaimedResidence getResidence() {
        return ResidenceApi.getResidenceManager().getByName(residence);
    }

    /**
     * 与另一城镇发起战斗
     *
     * @param town 另一城镇
     */
    public void fight(Town town) {
        Validate.notNull(town);

        // 判断是否为相同城镇
        if (town.getName().equals(this.name)) {
            return;
        }

        getOnlineMembers().forEach(user -> {
            Player player = user.getPlayer();
            if (player != null) {
                CoreAPI.sendTitle(player, 2, 20, 2, "&e战争冲突", "&f城镇 &a&l" + town.getName() + " &f对我们发起了进攻");
            }
        });

        setFighting(true);
        town.setFighting(true);

    }

    /**
     * 获取在线的用户
     *
     * @return {@link List}
     */
    public List<User> getOnlineMembers() {
        List<User> onlineUsers = Lists.newArrayList();
        members.forEach(s -> {
            Player player = Bukkit.getPlayerExact(s);
            if (player != null) {
                onlineUsers.add(getUserByMemberName(s));
            }
        });

        return onlineUsers;
    }

    /**
     * 利用 城镇成员名 获取用户对象
     *
     * @param memberName 成员名
     * @return {@link User}
     */
    public User getUserByMemberName(String memberName) {
        return CoreAPI.getUserManager().getUserByName(memberName);
    }

    /**
     * 判断一个玩家名是不是城主
     *
     * @param playerName 玩家名
     * @return true代表是/false代表不是
     */
    public boolean isOwner(String playerName) {
        return owner.equals(playerName);
    }

    /**
     * 判断一个玩家名是不是成员
     *
     * @param playerName 玩家名
     * @return true代表是/false代表不是
     */
    public boolean isMember(String playerName) {
        return members
                .stream()
                .anyMatch(s -> s.equals(playerName));
    }

    @SuppressWarnings("unchecked")
    public static Town deserialize(Map<String, Object> map) {
        Validate.notNull(map);

        Town town = new Town();
        town.setName((String) map.getOrDefault("name", "null"));
        town.setLevel((int) map.getOrDefault("level", 1));
        town.setOwner((String) map.getOrDefault("owner", "null"));
        town.setMembers((List<String>) map.get("members"));
        town.setResidence((String) map.get("residence"));
        town.setFighting((boolean) map.getOrDefault("fighting", false));
        // 魂晶 魔质
        town.setGhostCrystal((AtomicDouble) map.get("ghost-crystal"));
        town.setArcaneCrystal((AtomicDouble) map.get("arcane-crystal"));
        town.setHolyCrystal((AtomicDouble) map.get("holy-crystal"));
        town.setDarkSteelMagicMatter((AtomicDouble) map.get("dark-steel-magic-matter"));
        town.setMithrilMagicMatter((AtomicDouble) map.get("mithril-magic-matter"));
        town.setEternalGoldMagicMatter((AtomicDouble) map.get("eternal-gold-magic-matter"));

        return town;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", name);
        map.put("level", level);
        map.put("owner", owner);
        map.put("members", members);
        map.put("residence", residence);
        map.put("fighting", fighting);

        map.put("ghost-crystal", ghostCrystal);
        map.put("arcane-crystal", arcaneCrystal);
        map.put("holy-crystal", holyCrystal);
        map.put("dark-steel-magic-matter", darkSteelMagicMatter);
        map.put("mithril-magic-matter", mithrilMagicMatter);
        map.put("eternal-gold-magic-matter", eternalGoldMagicMatter);
        return map;
    }


}
