package cc.zoyn.wastelandwarcore.module.common.ui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author Zoyn
 * @since 2017-12-09
 */
public interface UIListener extends Listener {

    @EventHandler
    void execute(InventoryClickEvent e);

}
