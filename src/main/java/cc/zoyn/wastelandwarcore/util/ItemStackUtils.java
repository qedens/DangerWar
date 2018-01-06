package cc.zoyn.wastelandwarcore.util;

import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物品工具类
 *
 * @author Zoyn
 * @since 2017-12-14
 */
public final class ItemStackUtils {

    // 防止意外操作
    private ItemStackUtils() {
    }
    
    /**
     * 判断一个物品有无 displayName
     *
     * @param itemStack 物品
     * @return true就是有, false就没有
     */
    public static boolean itemHasDisplayName(@Nullable ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName();
    }
    
    /**
     * 获取一个物品的展示名
     * 调用本方法前应先调用itemHasDisplayName方法进行检查
     *
     * @param itemStack 物品
     * @return 物品的展示名
     */
    public static String getItemDisplayName(@Nullable ItemStack itemStack) {
    	Validate.notNull(itemStack);
    	
    	return itemStack.getItemMeta().getDisplayName();
    }

    /**
     * 给一个物品设置展示名
     *
     * @param itemStack   物品
     * @param displayName 展示名
     */
    public static ItemStack setItemDisplayName(@Nullable ItemStack itemStack, @Nullable String displayName) {
        Validate.notNull(itemStack);

        if (displayName == null || displayName.isEmpty() || displayName.equalsIgnoreCase(" ")) {
            return itemStack;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        String translatedDisplayName = ChatColor.translateAlternateColorCodes('&', displayName);
        itemMeta.setDisplayName(translatedDisplayName);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * 给一个物品设置 Lore
     *
     * @param itemStack 物品
     * @param lore      Lore展示信息
     * @param translate 是否翻译颜色代码
     */
    public static ItemStack setItemLore(@Nullable ItemStack itemStack, boolean translate, @Nullable List<String> lore) {
        Validate.notNull(itemStack);
        Validate.notNull(lore);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (translate) {
            List<String> translatedLore = lore
                    .stream()
                    .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                    .collect(Collectors.toList());
            itemMeta.setLore(translatedLore);
        } else {
            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack setItemLore(ItemStack itemStack, boolean translate, String... lore) {
        return setItemLore(itemStack, translate, Arrays.asList(lore));
    }

    /**
     * 判断一个物品有无 lore
     *
     * @param itemStack 物品
     * @return true就是有, false就没有
     */
    public static boolean itemHasLore(@Nullable ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasLore();
    }

}
