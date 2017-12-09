package cc.zoyn.wastelandwarcore;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * 插件主类
 *
 * @author Zoyn
 * @since 2017-12-09
 */
public class Entry extends JavaPlugin {

    private static Entry instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    public static Entry getInstance() {
        return instance;
    }
}
