package cc.zoyn.wastelandwarcore.api.event;

import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AllianceEvent extends Event implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    protected final Alliance alliance;
    protected boolean cancelled = false;
    protected User user;

    public AllianceEvent(Alliance alliance, User leader) {
        this.alliance = alliance;
        this.user = leader;
    }

    /**
     * 触发操作的联盟
     *
     * @return 联盟
     */
    public Alliance getAlliance() {
        return alliance;
    }

    /**
     * 获取影响事件的用户
     *
     * @return 用户
     */
    public User getUser() {
        return user;
    }

    /**
     * 设置影响事件的用户
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

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
