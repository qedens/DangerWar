package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.model.Town;
import cc.zoyn.wastelandwarcore.util.ConfigurationUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * 城镇管理器
 *
 * @author lss233
 * @since 2017-12-09
 */
public class TownManager extends AbstractManager<Town> implements SavableManager<Town> {

    private static volatile TownManager instance;
    public static Town DEFAULT_TOWN = new Town(UUID.fromString("00000000-0000-0000-0000-000000000001"));

    private TownManager() {
    }

    /**
     * 获取城镇管理器实例
     *
     * @return {@link TownManager}
     */
    public static TownManager getInstance() {
        if (instance == null) {
            synchronized (TownManager.class) {
                if (instance == null) {
                    instance = new TownManager();
                }
            }
        }
        return instance;
    }

    /**
     * 通过位置获取城镇
     *
     * @param location 位置
     * @return 一个城镇，如果存在的话.
     */
    public Optional<Town> getTown(Location location) {
        return getList()
                .stream()
                .filter(town -> town.containsLocation(location))
                .findAny();
    }

    /**
     * 通过城镇名字获取城镇
     *
     * @param name 名字
     * @return 一个城镇，如果存在的话.
     */
    public Optional<Town> getTown(String name) {
        return getList()
                .stream()
                .filter(town -> town.getName().equalsIgnoreCase(name))
                .findAny();
    }
    @Override
    public void saveElement(Town town) {
        Validate.notNull(town);

        File file = new File(Entry.getInstance().getTownFolder(), town.getUniqueId() + ".yml");
        FileConfiguration fileConfiguration = ConfigurationUtils.loadYml(file);
        fileConfiguration.set("Town", town);
        ConfigurationUtils.saveYml(fileConfiguration, file);
    }
}
