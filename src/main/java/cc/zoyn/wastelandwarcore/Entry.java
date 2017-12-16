package cc.zoyn.wastelandwarcore;

import cc.zoyn.wastelandwarcore.listener.*;
import cc.zoyn.wastelandwarcore.module.town.Region;
import cc.zoyn.wastelandwarcore.module.town.Town;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
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

        saveDefaultConfig();

        // 注册序列化
        ConfigurationSerialization.registerClass(Region.class);
        ConfigurationSerialization.registerClass(Town.class);

        // 注册事件
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
    }

    public static Entry getInstance() {
        return instance;
    }
}
