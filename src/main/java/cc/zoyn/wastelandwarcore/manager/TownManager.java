package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.module.town.Town;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * @author Zoyn
 * @since 2017-12-09
 */
public class TownManager {

    private static volatile TownManager instance;
    private List<Town> towns = Lists.newArrayList();

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

    public void addTown(Town town) {
        towns.add(Validate.notNull(town));
    }

    public void removeTown(Town town) {
        towns.remove(town);
    }

    /**
     * 利用名字获取城镇对象
     * <p>利用城镇名获取城镇对象,当获取不到时返回 null 值<p/>
     *
     * @param townName 城镇名
     * @return {@link Town}
     */
    public Town getTown(String townName) {
        for (Town town : towns) {
            if (town.getName().equals(townName)) {
                return town;
            }
        }
        return null;
    }

}
