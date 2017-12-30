package cc.zoyn.wastelandwarcore.util;

import cc.zoyn.wastelandwarcore.manager.ItemManager;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect.SpecialEffectType;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.item.IArmor;
import cc.zoyn.wastelandwarcore.module.item.UniversalItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static cc.zoyn.wastelandwarcore.util.ItemStackUtils.itemHasDisplayName;

public final class PlayerUtils {

    private PlayerUtils() {
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
        if (effects.hasSpecialEffect(SpecialEffectType.ARMORBREAK)) {
            SpecialEffect effect = effects.getSpecialEffect(SpecialEffectType.ARMORBREAK);
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
}
