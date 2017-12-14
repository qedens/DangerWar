package cc.zoyn.wastelandwarcore.module.common.ui;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.api.event.UIClickEvent;
import cc.zoyn.wastelandwarcore.api.event.UICloseEvent;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Zoyn
 * @since 2017-12-09
 */
public class UI implements Listener {

    @Getter
    private Inventory inventory;
    @Setter
    @Getter
    private boolean allowOperation;

    private UI(Inventory inventory) {
        Validate.notNull(inventory);

        this.inventory = inventory;
    }

    public static UIBuilder builder() {
        return new UIBuilder();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().equals(inventory)) {
            UIClickEvent uEvent = new UIClickEvent(this);
            Bukkit.getPluginManager().callEvent(uEvent);

            if (!isAllowOperation()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) {
            Bukkit.getPluginManager().callEvent(new UICloseEvent(this));
        }
    }

    public void open(Player player) {
        Validate.notNull(player);

        player.closeInventory();
        player.openInventory(inventory);
    }

    public void close(Player player) {
        Validate.notNull(player);

        if (player.getOpenInventory().getTopInventory().equals(inventory)) {
            player.closeInventory();
        }
    }

    public UI setItem(int index, ItemStack item) {
        inventory.setItem(--index, Validate.notNull(item));
        return this;
    }

    private UI registerEvents() {
        Bukkit.getPluginManager().registerEvents(this, Entry.getInstance());
        return this;
    }

    private UI unregisterEvents() {
        HandlerList.unregisterAll(this);
        return this;
    }

    public static class UIBuilder {
        private Inventory inventory;

        private UIBuilder() {
        }

        public UIBuilder inventory(Inventory inventory) {
            this.inventory = inventory;
            return this;
        }

        public UIBuilder inventory(InventoryType type) {
            this.inventory = Bukkit.createInventory(null, type);
            return this;
        }

        public UIBuilder item(int index, ItemStack itemStack) {
            inventory.setItem(--index, Validate.notNull(itemStack));
            return this;
        }

        public UI build() {
            return new UI(inventory);
        }
    }
}
