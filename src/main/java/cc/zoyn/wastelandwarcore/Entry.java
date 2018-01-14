package cc.zoyn.wastelandwarcore;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.command.CommandHandler;
import cc.zoyn.wastelandwarcore.listener.*;
import cc.zoyn.wastelandwarcore.module.common.chat.Channel;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.module.town.Town;
import cc.zoyn.wastelandwarcore.runnable.SpecialEffectRunnable;
import cc.zoyn.wastelandwarcore.runnable.TownDataSaveRunnable;
import cc.zoyn.wastelandwarcore.runnable.UserDataSaveRunnable;
import cc.zoyn.wastelandwarcore.util.CommonUtils;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
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
    @Getter
    @Setter
    private Location shelterLocation;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        townFolder = new File(getDataFolder(), "town");
        itemFolder = new File(getDataFolder(), "item");
        userFolder = new File(getDataFolder(), "user");
        createFiles();

        // 注册序列化
        ConfigurationSerialization.registerClass(Town.class);
        ConfigurationSerialization.registerClass(User.class);

        // 缓存用户
        for (Player player : CommonUtils.getOnlinePlayers()) {
            Channel channel = CoreAPI.getChannelManager().getDefaultChannel();
            Town town = CoreAPI.getTownManager().getTownByMember(player.getName());
            User user = new User(player.getName(), channel.getName(), town.getName(), null,
                    PlayerUtils.getArmor(player), PlayerUtils.getResistance(player), PlayerUtils.getMoveSpeed(player),
                    new SpecialEffectPlayer());

            channel.addUser(user);
            CoreAPI.getUserManager().addElement(user);
        }

        // 读取武器数据
        CoreAPI.getItemManager().loadItems();

        // 注册事件
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportInterruptListener(), this);
        Bukkit.getPluginManager().registerEvents(new UIListener(), this);

        // 注册命令
        Bukkit.getPluginCommand("core").setExecutor(new CommandHandler());


        // 注册调度器
        TownDataSaveRunnable townRunnable = new TownDataSaveRunnable();
        UserDataSaveRunnable userRunnable = new UserDataSaveRunnable();
        SpecialEffectRunnable effectRunnable = new SpecialEffectRunnable();

        effectRunnable.runTaskTimer(this, 20L, 20L);
        townRunnable.runTaskTimerAsynchronously(this, 20L, 10 * 60 * 20L);
        userRunnable.runTaskTimerAsynchronously(this, 20L, 10 * 60 * 20L);
    }

    /**
     * 获取主类实例
     *
     * @return {@link Entry}
     */
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
