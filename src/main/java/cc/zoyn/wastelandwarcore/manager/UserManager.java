package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.module.common.user.User;

/**
 * 用户管理器
 *
 * @author Zoyn
 * @since 2017-12-16
 */
public class UserManager extends AbstractManager<User> {

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
     * <p>利用用户名字获取UI对象,当获取不到时返回 null 值<p/>
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

}
