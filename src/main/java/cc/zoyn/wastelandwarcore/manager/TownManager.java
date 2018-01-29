package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.module.town.Town;
import cc.zoyn.wastelandwarcore.util.ConfigurationUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

/**
 * 城镇管理器
 *
 * @author Zoyn
 * @since 2017-12-09
 */
public class TownManager extends AbstractManager<Town> implements SavableManager<Town> {

    private static volatile TownManager instance;

    {
        this.addElement(
                new Town(
                        "流民",
                        1,
                        null,
                        Lists.newArrayList(),
                        null,
                        null,
                        null,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0)
        );
    }

    // 防止意外实例化
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
     * 利用名字获取城镇对象
     * <p>利用城镇名获取城镇对象,当获取不到时返回 null 值<p/>
     *
     * @param townName 城镇名
     * @return {@link Town}
     */
    public Town getTownByName(String townName) {
        for (Town town : getList()) {
            if (town.getName().equals(townName)) {
                return town;
            }
        }
        return null;
    }
    
    /**
     * 获取流民城镇对象
     * <p>获取流民城镇对象,当获取不到时返回 null 值<p/>
     *
     * @return {@link Town}
     */
    public Town getRefugeeTown() {
    	for (Town town : getList()) {
    		if (town.getName().equals("流民")) {
    			return town;
    		}
    	}
    	return null;
    }

    /**
     * 利用城镇成员名获取城镇, 若玩家不存在任何一个城镇则返回 流民
     *
     * @param memberName 城镇成员名
     * @return {@link Town}
     */
    public Town getTownByMember(String memberName) {
        return getList()
                .stream()
                .filter(town -> town.getUserByMemberName(memberName) != null)
                .findFirst()
                .orElse(getRefugeeTown());
    }
    
    /**
     * 利用位置获取城镇, 若不存在任何一个城镇则返回Null
     *
     * @param location 位置
     * @return {@link Town}
     */
    public Town getTownByLocation(Location location) {
    	return getList()
    			.stream()
    			.filter(town -> town.isInside(location))
    			.findFirst()
    			.orElse(null);
    }

    @Override
    public void saveElements(List<Town> elements) {
        elements.forEach(this::saveElement);
    }

    @Override
    public void saveElement(Town town) {
        Validate.notNull(town);

        File file = new File(Entry.getInstance().getTownFolder(), town.getName() + ".yml");
        FileConfiguration fileConfiguration = ConfigurationUtils.loadYml(file);
        fileConfiguration.set("Town", town);
        ConfigurationUtils.saveYml(fileConfiguration, file);
    }
}
