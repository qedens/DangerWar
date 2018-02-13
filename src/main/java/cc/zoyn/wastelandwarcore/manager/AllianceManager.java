package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.util.ConfigurationUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * 联盟管理
 *
 * @author lss233
 */
public class AllianceManager extends AbstractManager<Alliance> implements SavableManager<Alliance> {
    public final static Alliance DEFAULT_ALLIANCE = new Alliance(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    private static volatile AllianceManager instance;

    /**
     * 获取联盟管理器实例
     *
     * @return {@link AllianceManager}
     */
    public static AllianceManager getInstance() {
        if (instance == null) {
            synchronized (AllianceManager.class) {
                if (instance == null) {
                    instance = new AllianceManager();
                    instance.addElement(DEFAULT_ALLIANCE);
                }
            }
        }
        return instance;
    }

    /**
     * 通过UUID获取联盟
     *
     * @param uniqueId 联盟的UUID
     * @return 联盟, 如果存在的话
     */
    public Optional<Alliance> getAlliance(UUID uniqueId) {
        return getList().stream().filter(e -> e.getUniqueId().equals(uniqueId)).findAny();
    }

    /**
     * 通过玩家获取联盟
     *
     * @param player 玩家名
     * @return 联盟, 如果存在的话
     */
    public Optional<Alliance> getAlliance(Player player) {
        return getList().stream().filter(e -> e.getAllies().stream().anyMatch(ally -> ally.getName().equals(player.getName()))).findAny();
    }

    /**
     * 通过玩家获取所属联盟
     *
     * @param user 欲获取联盟的玩家
     * @return 联盟, 如果存在的话
     * @see User#getAlliance
     */
    public Optional<Alliance> getAlliance(User user) {
        return getList().stream().filter(e -> e.getAllies().contains(user)).findAny();
    }

    @Override
    public void saveElement(Alliance alliance) {
        Validate.notNull(alliance);

        File file = new File(Entry.getInstance().getAllianceFolder(), alliance.getUniqueId() + ".yml");
        FileConfiguration fileConfiguration = ConfigurationUtils.loadYml(file);
        fileConfiguration.set("Alliance", alliance);
        ConfigurationUtils.saveYml(fileConfiguration, file);
    }
}
