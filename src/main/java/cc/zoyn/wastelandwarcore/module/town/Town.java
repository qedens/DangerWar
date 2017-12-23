package cc.zoyn.wastelandwarcore.module.town;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.bukkit.block.Beacon;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;
import java.util.Map;

/**
 * 表示一个城镇
 *
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Town implements ConfigurationSerializable {

    private String name;
    private int level;
    private String owner;
    private List<String> members;
    private Region region;

    private Beacon centerBeacon;

    /**
     * 魂晶
     */
    private double ghostCrystal;
    /**
     * 奥晶
     */
    private double arcaneCrystal;
    /**
     * 圣晶
     */
    private double holyCrystal;
    /**
     * 暗钢
     */
    private double darkSteelMagicMatter;
    /**
     * 秘银
     */
    private double mithrilMagicMatter;
    /**
     * 恒金
     */
    private double eternalGoldMagicMatter;

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
     * @return
     */
    public boolean isOwner(String playerName) {
        return owner.equals(playerName);
    }

    /**
     * 判断一个玩家名是不是成员
     *
     * @param playerName 玩家名
     * @return
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
        town.setRegion((Region) map.get("region"));

        return town;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", name);
        map.put("level", level);
        map.put("owner", owner);
        map.put("members", members);
        map.put("region", region);

        return map;
    }
}
