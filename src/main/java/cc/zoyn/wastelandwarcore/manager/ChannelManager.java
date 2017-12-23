package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.module.common.chat.Channel;

import java.util.List;

/**
 * 频道管理器
 *
 * @author Zoyn
 * @since 2017-12-16
 */
public class ChannelManager extends AbstractManager<Channel> {

    private static volatile ChannelManager instance;
    private final static Channel DEFAULT_CHANNEL = getInstance().getChannelByName(Entry.getInstance().getConfig().getString("GeneralOption.ChannelOption.DefaultChannel"));

    {
        List<String> channelList = Entry.getInstance().getConfig().getStringList("GeneralOption.ChannelOption.Channels");
        channelList.forEach(s -> this.addElement(new Channel(s)));
    }

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
     * 获取默认频道
     *
     * @return {@link Channel}
     */
    public Channel getDefaultChannel() {
        return DEFAULT_CHANNEL;
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
