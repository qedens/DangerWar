package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.module.town.Town;

/**
 * 城镇管理器
 *
 * @author Zoyn
 * @since 2017-12-09
 */
public class TownManager extends AbstractManager<Town> {

    private static volatile TownManager instance;

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

}
