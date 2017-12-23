package cc.zoyn.wastelandwarcore;

import cc.zoyn.wastelandwarcore.command.CommandHandler;
import cc.zoyn.wastelandwarcore.listener.*;
import cc.zoyn.wastelandwarcore.module.town.Region;
import cc.zoyn.wastelandwarcore.module.town.Town;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * 插件主类
 *
 * @author Zoyn
 * @since 2017-12-09
 */
public class Entry extends JavaPlugin {

    private static Entry instance;
    private File townFolder;
    private File itemFolder;
    private File userFolder;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        townFolder = new File(getDataFolder(), "\\town");
        itemFolder = new File(getDataFolder(), "\\item");
        userFolder = new File(getDataFolder(), "\\user");
        createFiles();

        // 注册序列化
        ConfigurationSerialization.registerClass(Region.class);
        ConfigurationSerialization.registerClass(Town.class);

        // 注册事件
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);

        // 注册命令
        Bukkit.getPluginCommand("core").setExecutor(new CommandHandler());
    }

    public static Entry getInstance() {
        return instance;
    }

    private void createFiles() {
        if (!townFolder.exists()) {
            townFolder.mkdirs();
        } else if (!itemFolder.exists()) {
            itemFolder.mkdirs();
        } else if (!userFolder.exists()) {
            userFolder.mkdirs();
        }
    }
}
