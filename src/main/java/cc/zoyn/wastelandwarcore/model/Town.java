package cc.zoyn.wastelandwarcore.model;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.api.CoreAPI;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.comphenix.executors.BukkitExecutors;
import com.comphenix.executors.BukkitScheduledExecutorService;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cc.zoyn.wastelandwarcore.manager.AllianceManager.DEFAULT_ALLIANCE;

/**
 * 表示一个城镇
 *
 * @author lss233
 */
@Data
@AllArgsConstructor
public class Town implements ConfigurationSerializable {
    private static BukkitScheduledExecutorService scheduledExecutorService = BukkitExecutors.newAsynchronous(Entry.getInstance());
    @Getter
    private final UUID uniqueId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Alliance alliance;
    @Getter
    private TownCore[] townCores = new TownCore[4];
    private Block centerEndBlock;
    @Getter
    @Setter
    private ClaimedResidence residence;
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
    private AtomicDouble secretSilverMagicMatter = new AtomicDouble(0D);
    /**
     * 恒金
     */
    private AtomicDouble eternalGoldMagicMatter = new AtomicDouble(0D);

    // 初始化块
    {
        scheduledExecutorService.schedule(new TimerTask() {
            @Override
            public void run() {
                // 和平时期
                if (!CoreAPI.isInWar()) {
                    if (ghostCrystal.get() < 10) {
                        ghostCrystal.addAndGet(1);
                    }
                    if (arcaneCrystal.get() < 10) {
                        arcaneCrystal.addAndGet(1);
                    }
                    if (holyCrystal.get() < 10) {
                        holyCrystal.addAndGet(1);
                    }
                    if (darkSteelMagicMatter.get() < 10) {
                        darkSteelMagicMatter.addAndGet(1);
                    }
                    if (secretSilverMagicMatter.get() < 10) {
                        secretSilverMagicMatter.addAndGet(1);
                    }
                    if (eternalGoldMagicMatter.get() < 10) {
                        eternalGoldMagicMatter.addAndGet(1);
                    }
                }
            }
        }, 1, TimeUnit.HOURS);
    }

    /**
     * 通过UUID创建城镇
     *
     * @param uniqueId
     */
    public Town(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * 反序列化城镇实例
     * @param map 存放序列化后的Map
     * @return 城镇实例
     */
    public static Town deserialize(Map<String, Object> map) {
        return new Town(
                UUID.fromString(String.valueOf(map.get("uniqueId"))),
                String.valueOf(map.get("name")),
                CoreAPI.getAllianceManager().getAlliance(UUID.fromString(String.valueOf(map.get("alliance")))).orElse(DEFAULT_ALLIANCE),
                (TownCore[]) map.get("townCores"),
                (Block) map.get("centerEndBlock"),
                ResidenceApi.getResidenceManager().getByName(String.valueOf(map.get("residence"))),
                new AtomicDouble((double) map.get("ghost-crystal")),
                new AtomicDouble((double) map.get("arcane-crystal")),
                new AtomicDouble((double) map.get("holy-crystal")),
                new AtomicDouble((double) map.get("dark-steel-magic-matter")),
                new AtomicDouble((double) map.get("secret-silver-magic-matter")),
                new AtomicDouble((double) map.get("eternal-gold-magic-matter"))
        );
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("centerEndBlock", getCenterEndBlock());
        map.put("uniqueId", uniqueId);
        map.put("residence", residence.getName());
        map.put("townCores", townCores);
        map.put("alliance", alliance.getUniqueId().toString());
        map.put("name", name);

        map.put("ghost-crystal", ghostCrystal.get());
        map.put("arcane-crystal", arcaneCrystal.get());
        map.put("holy-crystal", holyCrystal.get());
        map.put("dark-steel-magic-matter", darkSteelMagicMatter.get());
        map.put("secret-silver-magic-matter", secretSilverMagicMatter.get());
        map.put("eternal-gold-magic-matter", eternalGoldMagicMatter.get());
        return map;
    }

    /**
     * 判断一个位置是否在城镇范围内
     * @param location 位置
     * @return 是则返回true
     */
    public boolean containsLocation(Location location) {
        return residence.containsLoc(location);
    }

    /**
     * 判断一个玩家对该城镇是否友好
     * @param playerName 玩家名
     * @return 友好则返回true
     */
    public boolean isFriendly(String playerName) {
        return getAlliance().getAllies().stream().anyMatch(user -> user.getName().equalsIgnoreCase(playerName));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Town && this.getUniqueId().equals(((Town) other).getUniqueId());
    }
}
