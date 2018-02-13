package cc.zoyn.wastelandwarcore.api.event;

import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.model.Town;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TownOwnerTransferEvent extends Event implements Cancellable {
    private static HandlerList handlerList;
    private boolean cancelled = false;
    private Town town;
    private Alliance newAlliance, oldAlliance;

    public TownOwnerTransferEvent(Town town, Alliance newAlliance, Alliance oldAlliance) {
        this.town = town;
        this.newAlliance = newAlliance;
        this.oldAlliance = oldAlliance;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public static void setHandlerList(HandlerList handlerList) {
        TownOwnerTransferEvent.handlerList = handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Alliance getNewAlliance() {
        return newAlliance;
    }

    public void setNewAlliance(Alliance newAlliance) {
        this.newAlliance = newAlliance;
    }

    public Alliance getOldAlliance() {
        return oldAlliance;
    }

    public void setOldAlliance(Alliance oldAlliance) {
        this.oldAlliance = oldAlliance;
    }
}
