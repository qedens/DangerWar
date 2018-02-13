package cc.zoyn.wastelandwarcore;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.command.CoreCommandHandler;
import cc.zoyn.wastelandwarcore.command.TownCommandHandler;
import cc.zoyn.wastelandwarcore.listener.*;
import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.model.Town;
import cc.zoyn.wastelandwarcore.model.TownCore;
import cc.zoyn.wastelandwarcore.module.common.chat.Channel;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.runnable.*;
import cc.zoyn.wastelandwarcore.util.CommonUtils;
import cc.zoyn.wastelandwarcore.util.ConfigurationUtils;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;
import lombok.Getter;
import lombok.Setter;
import me.skymc.wastelandwarcore.weaponlevel.WeaponLevelManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static cc.zoyn.wastelandwarcore.manager.AllianceManager.DEFAULT_ALLIANCE;

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
    private File allianceFolder;
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
        allianceFolder = new File(getDataFolder(), "alliance");
        createFiles();

        // 注册序列化
        ConfigurationSerialization.registerClass(Town.class);
        ConfigurationSerialization.registerClass(User.class);
        ConfigurationSerialization.registerClass(Alliance.class);
        ConfigurationSerialization.registerClass(TownCore.class);


        // 缓存用户
        for (Player player : CommonUtils.getOnlinePlayers()) {
            initializePlayer(player);
        }

        // 读取配置
        // Arrays.asList(Objects.requireNonNull(getUserFolder().listFiles())).forEach(e->CoreAPI.getUserManager().addElement((User) ConfigurationUtils.loadYml(e).get("User")));
        Arrays.asList(Objects.requireNonNull(getTownFolder().listFiles())).forEach(e -> CoreAPI.getTownManager().addElement((Town) ConfigurationUtils.loadYml(e).get("Town")));
        // Arrays.asList(Objects.requireNonNull(getItemFolder().listFiles())).forEach(e->CoreAPI.getTownManager().addElement((Item) ConfigurationUtils.loadYml(e).get("Item")));
        Arrays.asList(Objects.requireNonNull(getAllianceFolder().listFiles())).forEach(e -> CoreAPI.getAllianceManager().addElement((Alliance) ConfigurationUtils.loadYml(e).get("Alliance")));


        // 读取武器数据
        CoreAPI.getItemManager().loadItems();

        // 注册事件
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new WandListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportInterruptListener(), this);
        Bukkit.getPluginManager().registerEvents(new UIListener(), this);
        Bukkit.getPluginManager().registerEvents(new WarListener(), this);
        Bukkit.getPluginManager().registerEvents(new BarListener(), this); // 交互系统

        // 注册命令
        Bukkit.getPluginCommand("core").setExecutor(new CoreCommandHandler());
        Bukkit.getPluginCommand("town").setExecutor(new TownCommandHandler());

        // 注册调度器
        TownDataSaveRunnable townRunnable = new TownDataSaveRunnable();
        AllianceDataSaveRunnable allianceRunnable = new AllianceDataSaveRunnable();
        UserDataSaveRunnable userRunnable = new UserDataSaveRunnable();
        SpecialEffectRunnable effectRunnable = new SpecialEffectRunnable();
        WarDebuffRunnable warDebuffRunnable = new WarDebuffRunnable();


        effectRunnable.runTaskTimer(this, 20L, 20L);
        townRunnable.runTaskTimerAsynchronously(this, 20L, 10 * 60 * 20L);
        userRunnable.runTaskTimerAsynchronously(this, 20L, 10 * 60 * 20L);
        allianceRunnable.runTaskTimerAsynchronously(this, 20L, 10 * 60 * 20L);
        warDebuffRunnable.runTaskTimer(this, 20L, 3 * 20L);
        
        // 重载表达式
        WeaponLevelManager.getInst().reloadPattern();
    }

    public void initializePlayer(Player player) {
        Channel channel = CoreAPI.getChannelManager().getDefaultChannel();
        Optional<Alliance> alliance = CoreAPI.getAllianceManager().getAlliance(player);
        User user = new User(player.getName(), channel.getName(), alliance.orElse(DEFAULT_ALLIANCE), null, PlayerUtils.getArmor(player),
                PlayerUtils.getResistance(player), PlayerUtils.getMoveSpeed(player), new SpecialEffectPlayer());

        channel.addUser(user);
        CoreAPI.getUserManager().addElement(user);
    }

    /**
     * 获取主类实例
     *
     * @return {@link Entry}
     */
    public static Entry getInstance() {
        return instance;
    }

    @SuppressWarnings("all")
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
        if (!allianceFolder.exists())
            allianceFolder.mkdirs();
    }
}
