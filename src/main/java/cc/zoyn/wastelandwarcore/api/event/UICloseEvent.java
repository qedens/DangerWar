package cc.zoyn.wastelandwarcore.api.event;

import cc.zoyn.wastelandwarcore.module.common.ui.UI;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * 当UI被关闭时触发此事件
 *
 * @author hammer354
 * @since 2017-12-13
 */
public class UICloseEvent extends Event {

    @Getter
    private final UI ui;
    @Getter
    private final InventoryCloseEvent invEvent;

    private static final HandlerList handlers = new HandlerList();

    public UICloseEvent(UI ui, InventoryCloseEvent invEvent) {
        this.ui = ui;
        this.invEvent = invEvent;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
