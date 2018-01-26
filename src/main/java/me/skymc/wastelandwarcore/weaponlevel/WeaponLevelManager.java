package me.skymc.wastelandwarcore.weaponlevel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.util.ItemStackUtils;
import me.skymc.wastelandwarcore.utils.NumberUtils;

/**
 * 物品星级/阶级工具
 * 
 * @author sky
 * @since 2018年1月27日00:24:01
 */
public class WeaponLevelManager {
	
	private static WeaponLevelManager inst;
	private Pattern pattern;
	
	public static WeaponLevelManager getInst() {
		if (inst == null) {
			synchronized (WeaponLevelManager.class) {
				if (inst == null) {
					inst = new WeaponLevelManager();
				}
			}
		}
		return inst;
	}
	
	public void reloadPattern() {
		pattern = Pattern.compile(Entry.getInstance().getConfig().getString("WeaponLevelOption.Pattern"));
	}
	
	/**
	 * 获取物品等级
	 * 
	 * @param item 物品
	 * @return {@link Integer} -1 代表无等级
	 */
	public int[] getWeaponStage(ItemStack item) {
		if (!ItemStackUtils.itemHasLore(item)) {
			return null;
		}
		Matcher matcher = pattern.matcher(ChatColor.stripColor(item.getItemMeta().getLore().toString()));
		if (matcher.find()) {
			return new int[] { NumberUtils.parserInteger(matcher.group(1), 0), NumberUtils.parserInteger(matcher.group(2), 0) };
		}
		return null;
	}
	
	/**
	 * 给装备提升 1 颗星, 6进制
	 * 
	 * @param item 物品
	 * @return {@link ItemStack}
	 */
	public ItemStack upgradeWeapon(ItemStack item) {
		int[] stage = getWeaponStage(item);
		if (stage == null) {
			return item;
		}
		
		if (stage[0] < 5) {
			if (stage[1] == 5) {
				stage[0] = stage[0] + 1;
				stage[1] = 0;
			}
			else {
				stage[1] = stage[1] + 1;
			}
		}
		
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		for (int i = 0; i < lore.size() ; i++) {
			Matcher matcher = pattern.matcher(ChatColor.stripColor(lore.get(i)));
			if (matcher.find()) {
				lore.set(i, Entry.getInstance().getConfig().getString("WeaponLevelOption.UpgradeLore")
						.replace("&", "§")
						.replace("$1", String.valueOf(stage[0]))
						.replace("$2", String.valueOf(stage[1])));
			}
		}
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
}
