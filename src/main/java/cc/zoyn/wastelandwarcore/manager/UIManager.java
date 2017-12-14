package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.module.common.ui.UI;

/**
 * UI管理器
 *
 * @author Zoyn
 * @since 2017-12-10
 */
public class UIManager extends AbstractManager<UI> {

    private static volatile UIManager instance;

    // 防止意外实例化
    private UIManager() {
    }

    /**
     * 获取UI管理器实例
     *
     * @return {@link UIManager}
     */
    public static UIManager getInstance() {
        if (instance == null) {
            synchronized (UIManager.class) {
                if (instance == null) {
                    instance = new UIManager();
                }
            }
        }
        return instance;
    }

    /**
     * 利用名字获取UI对象
     * <p>利用UI名获取UI对象,当获取不到时返回 null 值<p/>
     *
     * @param uiName 城镇名
     * @return {@link UI}
     */
    public UI getUI(String uiName) {
        for (UI ui : getList()) {
            if (ui.getInventory().getTitle().equals(uiName)) {
                return ui;
            }
        }
        return null;
    }


}
