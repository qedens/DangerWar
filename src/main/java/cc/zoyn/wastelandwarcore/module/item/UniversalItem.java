package cc.zoyn.wastelandwarcore.module.item;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * 表示一个物品
 *
 * @author Zoyn
 * @since 2017-12-10
 */
public interface UniversalItem extends ConfigurationSerializable {

    Material getMaterial();

    int getSubId();

    int getAmount();

    ItemMeta getItemMeta();

    default ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(getMaterial(), getAmount(), (short) getSubId());
        if (getItemMeta() != null) {
            itemStack.setItemMeta(getItemMeta());
        }
        return itemStack;
    }

}
