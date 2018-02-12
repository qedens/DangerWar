package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.util.ConfigurationUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

/**
 * 用户管理器
 *
 * @author Zoyn
 * @since 2017-12-16
 */
public class UserManager extends AbstractManager<User> implements SavableManager<User> {

    private static volatile UserManager instance;

    // 防止意外实例化
    private UserManager() {
    }

    /**
     * 获取用户管理器实例
     *
     * @return {@link UserManager}
     */
    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    /**
     * 利用用户名字获取用户对象
     * <p>利用用户名字获取UI对象,当获取不到时返回 null 值</p>
     *
     * @param userName 用户名
     * @return {@link User}
     */
    public User getUserByName(String userName) {
        for (User user : getList()) {
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void saveElements(List<User> elements) {
        elements.forEach(this::saveElement);
    }

    @Override
    public void saveElement(User user) {
        Validate.notNull(user);

        File file = new File(Entry.getInstance().getUserFolder(), user.getName() + ".yml");
        FileConfiguration fileConfiguration = ConfigurationUtils.loadYml(file);
        fileConfiguration.set("User", user);
        ConfigurationUtils.saveYml(fileConfiguration, file);
    }

    /**
     * 根据Player获取UI对象
     * <p>根据Player获取UI对象,当获取不到时返回 null 值</p>
     *
     * @param player 玩家实例
     * @return {@link User}
     */
    public User getUserByPlayer(Player player) {
        return getUserByName(player.getName());
    }
}
