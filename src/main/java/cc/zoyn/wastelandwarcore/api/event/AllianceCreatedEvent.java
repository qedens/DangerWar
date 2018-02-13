package cc.zoyn.wastelandwarcore.api.event;

import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import org.bukkit.event.HandlerList;

/**
 * 当一个联盟/叛军军团被创建的时候被触发
 */
public class AllianceCreatedEvent extends AllianceEvent {
    private static HandlerList handlerList = new HandlerList();

    public AllianceCreatedEvent(Alliance alliance, User leader) {
        super(alliance, leader);
    }

    /**
     * 将要被创建的联盟
     *
     * @return 联盟
     */
    public Alliance getAlliance() {
        return alliance;
    }

    /**
     * 获取联盟的领主
     *
     * @return 领主
     */
    public User getUser() {
        return user;
    }

    /**
     * 设置联盟的领主
     *
     * @param leader 领主
     */
    public void setUser(User leader) {
        this.user = leader;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
