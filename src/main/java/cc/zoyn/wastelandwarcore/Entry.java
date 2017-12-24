package cc.zoyn.wastelandwarcore;

import cc.zoyn.wastelandwarcore.command.CommandHandler;
import cc.zoyn.wastelandwarcore.listener.*;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.module.town.Region;
import cc.zoyn.wastelandwarcore.module.town.Town;
import cc.zoyn.wastelandwarcore.runnable.TownDataSaveRunnable;
import cc.zoyn.wastelandwarcore.runnable.UserDataSaveRunnable;
import lombok.Getter;
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
    @Getter
    private File townFolder;
    @Getter
    private File itemFolder;
    @Getter
    private File userFolder;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        townFolder = new File(getDataFolder(), "town");
        itemFolder = new File(getDataFolder(), "item");
        userFolder = new File(getDataFolder(), "user");
        createFiles();

        // 注册序列化
        ConfigurationSerialization.registerClass(Region.class);
        ConfigurationSerialization.registerClass(Town.class);
        ConfigurationSerialization.registerClass(User.class);

        // 注册事件
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);

        // 注册命令
        Bukkit.getPluginCommand("core").setExecutor(new CommandHandler());

        TownDataSaveRunnable townRunnable = new TownDataSaveRunnable();
        UserDataSaveRunnable userRunnable = new UserDataSaveRunnable();

        townRunnable.runTaskTimerAsynchronously(this, 20L, 10 * 60 * 20L);
        userRunnable.runTaskTimerAsynchronously(this, 20L, 10 * 60 * 20L);
    }

    public static Entry getInstance() {
        return instance;
    }

    private void createFiles() {
        if (!townFolder.exists()) {
            townFolder.mkdirs();
        }
        if (!itemFolder.exists()) {
            itemFolder.mkdirs();
        }
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }
    }
}
