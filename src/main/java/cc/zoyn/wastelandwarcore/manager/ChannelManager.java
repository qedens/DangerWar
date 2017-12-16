package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.module.common.chat.Channel;

/**
 * 频道管理器
 *
 * @author Zoyn
 * @since 2017-12-16
 */
public class ChannelManager extends AbstractManager<Channel> {

    private static volatile ChannelManager instance;

    // 防止意外实例化
    private ChannelManager() {
    }

    /**
     * 获取频道管理器实例
     *
     * @return {@link ChannelManager}
     */
    public static ChannelManager getInstance() {
        if (instance == null) {
            synchronized (ChannelManager.class) {
                if (instance == null) {
                    instance = new ChannelManager();
                }
            }
        }
        return instance;
    }

    /**
     * 利用频道名获取频道对象
     * <p>利用频道名获取频道对象对象,当获取不到时返回 null 值<p/>
     *
     * @param channelName 频道名
     * @return {@link Channel}
     */
    public Channel getChannelByName(String channelName) {
        for (Channel channel : getList()) {
            if (channel.getName().equals(channelName)) {
                return channel;
            }
        }
        return null;
    }

}
