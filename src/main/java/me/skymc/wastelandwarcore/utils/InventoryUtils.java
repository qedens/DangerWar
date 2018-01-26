package me.skymc.wastelandwarcore.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import cc.zoyn.wastelandwarcore.util.ItemStackUtils;

/**
 * 物品工具
 * 
 * @author sky
 * @since 2018年1月26日23:25:12
 */
public class InventoryUtils {
	
	/**
	 * 如果玩家背包里含有指定数量的指定物品
	 * 
	 * @param inventory 检查背包
	 * @param itemDisplay 检查名称
	 * @param amount 检查数量
	 * @return {@link Boolean}
	 */
	public static boolean containsItemStackAsDisplay(Inventory inventory, String itemDisplay, int amount) {
		int lastAmount = amount;
		for (ItemStack item : inventory.getContents()) {
			if (ItemStackUtils.itemHasDisplayName(item) && ItemStackUtils.getItemDisplayName(item).contains(itemDisplay)) {
				if ((lastAmount = lastAmount - item.getAmount()) <= 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 取走玩家背包内指定数量的指定物品
	 * 
	 * @param inventory 检查背包
	 * @param itemDisplay 检查名称
	 * @param amount 检查数量
	 */
	public static void takeItemStackAsDisplay(Inventory inventory, String itemDisplay, int amount) {
		int lastAmount = amount;
		for (int i = 0 ; i < inventory.getContents().length && lastAmount > 0 ; i++) {
			ItemStack item = inventory.getContents()[i];
			if (ItemStackUtils.itemHasDisplayName(item) && ItemStackUtils.getItemDisplayName(item).contains(itemDisplay)) {
				if (item.getAmount() > lastAmount) {
					inventory.getContents()[i] = takeAmountInItemStack(item, lastAmount);
					return;
				}
				else {
					lastAmount = lastAmount - item.getAmount();
					inventory.setItem(i, null);
				}
			}
		}
	}
	
	/**
	 * 减少物品数量
	 * 
	 * @param item 物品
	 * @param amount 数量
	 * @return {@link ItemStack}
	 */
	public static ItemStack takeAmountInItemStack(ItemStack item, int amount) {
		if (item.getAmount() - amount <= 0) {
			return new ItemStack(Material.AIR);
		}
		else {
			item.setAmount(item.getAmount() - amount);
			return item;
		}
	}
}
