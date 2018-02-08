package cc.zoyn.wastelandwarcore.module.common.user;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.chat.Channel;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.common.talent.Talent;
import cc.zoyn.wastelandwarcore.module.town.Town;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * 表示一个用户
 *
 * @author Zoyn
 * @since 2017-12-16
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements ConfigurationSerializable {
    /**
     * 用户名
     */
    @Getter
    @Setter
    private String name;
    /**
     * 用户所在频道
     */
    @Setter
    private String channel;
    /**
     * 用户所在城镇
     */
    @Setter
    private String town;
    /**
     * 用户天赋
     */
    @Getter
    @Setter
    private Talent talent;
    /**
     * 用户护甲
     */
    @Getter
    @Setter
    private double armor;
    /**
     * 用户抗性
     */
    @Getter
    @Setter
    private double resistance;
    /**
     * 用户速度(未经过效果削弱)
     */
    @Getter
    @Setter
    private float moveSpeed;
    /**
     * 玩家身上效果合集
     */
    @Getter
    @Setter
    private SpecialEffectPlayer effects;

    public static User deserialize(Map<String, Object> map) {
        Validate.notNull(map);

        User user = new User();
        user.setName((String) map.get("name"));
        user.setChannel((String) map.get("channel"));
        user.setTown((String) map.get("town"));
        user.setTalent((Talent) map.get("talent"));
        user.setArmor((double) map.get("armor"));
        user.setResistance((double) map.get("resistance"));
        // 此处需精度转换
        BigDecimal decimal = new BigDecimal(map.get("moveSpeed").toString());
        user.setMoveSpeed(decimal.floatValue());
        return user;
    }

    public Channel getChannel() {
        return CoreAPI.getChannelManager().getChannelByName(channel);
    }

    /**
     * 获取该用户所属的城镇对象
     * <p>如果该用户没有所属势力会返回流民城镇对象</p>
     *
     * @return {@link Town}
     */
    public Town getTown() {
        return Optional.ofNullable(CoreAPI.getTownManager().getTownByName(town))
                .orElse(CoreAPI.getTownManager().getRefugeeTown());
    }

    /**
     * 获取该用户的 Player 对象
     * <p>
     * 如果该用户不在线会返回 null
     *
     * @return {@link Player}
     */
    public Player getPlayer() {
        return Bukkit.getPlayerExact(name);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", name);
        map.put("channel", channel);
        map.put("town", town);
        map.put("talent", talent);
        map.put("armor", armor);
        map.put("resistance", resistance);
        map.put("moveSpeed", moveSpeed);
        return map;
    }
}
