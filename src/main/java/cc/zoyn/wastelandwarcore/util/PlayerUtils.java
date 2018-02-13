package cc.zoyn.wastelandwarcore.util;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.manager.ItemManager;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectType;
import cc.zoyn.wastelandwarcore.module.item.ChestPlate;
import cc.zoyn.wastelandwarcore.module.item.IArmor;
import cc.zoyn.wastelandwarcore.module.item.Shoes;
import cc.zoyn.wastelandwarcore.module.item.UniversalItem;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cc.zoyn.wastelandwarcore.util.ItemStackUtils.itemHasDisplayName;

public final class PlayerUtils {
    /**
     * 默认玩家移动速度
     */
    private static final float DEFAULT_PLAYER_MOVEMENT_SPEED = 0.3f;
    /**
     * 默认玩家最大血量上限
     */
    private static final double DEFAULT_PLAYER_MAX_HEALTH = 20.0;
    /**
     * 延迟传送线程列表
     */
    private static List<PlayerTeleportRunnable> delayedTeleportList = new ArrayList<>();

    // 防止意外构造
    private PlayerUtils() {
    }

    /**
     * 获取玩家的最大生命值上限
     *
     * @param player 玩家
     * @return 玩家的最大生命值上限
     */
    public static double getMaxHealth(Player player) {
        ItemStack chestItem = player.getInventory().getChestplate();
        if (ItemStackUtils.itemHasDisplayName(chestItem)) {
            UniversalItem uitem = ItemManager.getInstance().getItemByName(chestItem.getItemMeta().getDisplayName());
            if (uitem instanceof ChestPlate) {
                return DEFAULT_PLAYER_MAX_HEALTH + ((ChestPlate) uitem).getHealth();
            }
        }
        return DEFAULT_PLAYER_MAX_HEALTH;
    }

    /**
     * 获取玩家的速度(未经效果削弱)
     *
     * @param player 玩家
     * @return 玩家的速度(未经效果削弱)
     */
    public static float getMoveSpeed(Player player) {
        ItemStack shoeItem = player.getInventory().getBoots();
        if (ItemStackUtils.itemHasDisplayName(shoeItem)) {
            UniversalItem uitem = ItemManager.getInstance().getItemByName(shoeItem.getItemMeta().getDisplayName());
            if (uitem instanceof Shoes) {
                return DEFAULT_PLAYER_MOVEMENT_SPEED + ((Shoes) uitem).getMovementSpeed();
            }
        }
        return DEFAULT_PLAYER_MOVEMENT_SPEED;
    }

    /**
     * 攻击者伤害处理(虚弱等负面状态削弱攻击)
     *
     * @return 攻击力(已经过 虚弱等负面效果 削弱)
     */
    public static double getDamagerDamage(SpecialEffectPlayer effects, Player player, double damage) {
        // 判断玩家是否处于 虚弱 状态
        if (effects.hasSpecialEffect(SpecialEffectType.WEAKNESS)) {
            SpecialEffect effect = effects.getSpecialEffect(SpecialEffectType.WEAKNESS);
            return SpecialEffectPlayer.getWeaknessDamage(effect.getLevel(), damage, getResistance(player));
        }
        return damage;
    }

    /**
     * 护甲削弱攻击(已经过 破甲效果 削弱)
     *
     * @return 护甲削弱后的攻击力(破甲效果已经实现)
     */
    public static double getArmorDamage(SpecialEffectPlayer effects, Player player, double damage) {
        double armor = getArmor(player);
        double resistance = 0;
        //破甲效果 削弱护甲
        if (effects.hasSpecialEffect(SpecialEffectType.ARMOR_BREAK)) {
            SpecialEffect effect = effects.getSpecialEffect(SpecialEffectType.ARMOR_BREAK);
            armor = SpecialEffectPlayer.getArmorBreak(effect.getLevel(), armor, resistance);
        }
        return damage * 30 / (30 + armor);
    }

    /**
     * 玩家身上护甲的总和
     *
     * @param player 玩家
     * @return 玩家护甲的总和
     */
    public static double getArmor(Player player) {
        PlayerInventory pi = player.getInventory();
        ItemStack chestItem = pi.getChestplate();
        ItemStack shoeItem = pi.getBoots();
        //堆积护甲
        return getArmor(chestItem) + getArmor(shoeItem);
    }

    /**
     * 玩家身上抗性的总和
     *
     * @param player 玩家
     * @return 玩家抗性的总和
     */
    public static double getResistance(Player player) {
        PlayerInventory pi = player.getInventory();
        ItemStack chestItem = pi.getChestplate();
        ItemStack shoeItem = pi.getBoots();
        //堆积抗性
        return getResistance(chestItem) + getResistance(shoeItem);
    }

    /**
     * 单个防具的护甲
     *
     * @param stack 防具
     * @return 防具的护甲值
     */
    public static double getArmor(ItemStack stack) {
        if (itemHasDisplayName(stack)) {
            UniversalItem item = ItemManager.getInstance().getItemByName(stack.getItemMeta().getDisplayName());
            if (item instanceof IArmor)
                return ((IArmor) item).getDefense();
        }
        return 0;
    }

    /**
     * 单个防具的抗性
     *
     * @param stack 防具
     * @return 防具的抗性
     */
    public static double getResistance(ItemStack stack) {
        if (itemHasDisplayName(stack)) {
            UniversalItem item = ItemManager.getInstance().getItemByName(stack.getItemMeta().getDisplayName());
            if (item instanceof IArmor)
                return ((IArmor) item).getResistance();
        }
        return 0;
    }
    
    /**
     * 延迟传送
     *
     * @param player 玩家
     * @param location 位置
     * @param delay 延迟（Ticks）
     */
    public static void delayedTeleport(Player player, Location location, long delay) {
    	new PlayerTeleportRunnable(player, location).runTaskLater(Entry.getInstance(), delay);
    }
    
    /**
     * 取消延迟传送
     *
     * @param player 玩家
     */
    public static void cancelDelayedTeleport(Player player) {
    	cancelDelayedTeleport(player, null);
    }
    
    /**
     * 取消延迟传送
     *
     * @param player 玩家
     * @param message 发送给玩家的提示
     */
    public static void cancelDelayedTeleport(Player player, String message) {
    	//取消成功标志位
    	boolean canceled = false;
    	//找出列表中该玩家的所有延迟传送线程并取消
    	for (Iterator<PlayerTeleportRunnable> iterator = delayedTeleportList.iterator(); iterator.hasNext();) {
    		PlayerTeleportRunnable run = iterator.next();
    		if (run.getPlayer().getName().equals(player.getName())) {
    			run.cancel();
    			iterator.remove();
    			canceled = true;
    		}
    	}
    	//取消成功且提示不为空
    	if (canceled && message != null && !message.equals("")) {
    		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    	}
    }
    
    /**
     * 延迟传送线程
     * 
     * @author hammer354
     * @since 2018-01-06
     */
    private static class PlayerTeleportRunnable extends BukkitRunnable {
    	@Getter
		private Player player;
    	private Location location;
		
		private PlayerTeleportRunnable(Player player, Location location) {
			Validate.notNull(player);
	    	Validate.notNull(location);
	    	
	    	this.player = player;
	    	this.location = location;
			delayedTeleportList.add(this);
		}
		
		@Override
		public void run() {
			player.sendMessage("正在传送...");
			player.teleport(location);
			delayedTeleportList.remove(this);
		}
    }
    
    /**
     * 检查玩家是否为流民
     *
     * @param player 玩家
     * @return true代表是/false代表不是
     */
    public static boolean isRefugee(Player player) {
        return CoreAPI.getUserManager().getUserByName(player.getName()).getAlliance() == null;
    }
    
    /**
     * 检查玩家是否为叛军
     *
     * @param player 玩家
     * @return true代表是/false代表不是
     */
    public static boolean isRebel(Player player) {
        if (isRefugee(player)) return false;
        return CoreAPI.getUserManager().getUserByName(player.getName()).getAlliance().isRebel();
    }
}
