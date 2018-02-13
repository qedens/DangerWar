package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.manager.ItemManager;
import cc.zoyn.wastelandwarcore.manager.UserManager;
import cc.zoyn.wastelandwarcore.model.Alliance;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.module.item.IWeapon;
import cc.zoyn.wastelandwarcore.util.ItemStackUtils;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * 本类进行 实体攻击 的相关处理
 *
 * @author DFKK
 * @since 2017-12-09
 */
public class EntityDamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!validPVP(event.getEntity(), event.getDamager())) {
            event.setCancelled(true);
            return;
        }
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player))
            return;
        UserManager manager = UserManager.getInstance();
        //被攻击者是玩家
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            User user = manager.getUserByName(player.getName());
            //被攻击者身上的效果
            SpecialEffectPlayer effectPlayer = user.getEffects();
            if (effectPlayer != null) {
                //护甲削弱攻击(已经过破甲处理)
                event.setDamage(PlayerUtils.getArmorDamage(effectPlayer, player, event.getDamage()));
            }
        }
        //攻击者是玩家
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            User user = manager.getUserByName(damager.getName());
            //攻击者身上的效果
            SpecialEffectPlayer effectPlayer = user.getEffects();
            if (effectPlayer != null) {
                //虚弱削弱伤害
                event.setDamage(PlayerUtils.getDamagerDamage(effectPlayer, damager, event.getDamage()));
            }

            //如果被攻击者是玩家,将施加效果至目标
            if (event.getEntity() instanceof Player) {
                User BeDamager = manager.getUserByName(event.getEntity().getName());
                SpecialEffectPlayer BeEffectPlayer = user.getEffects();
                ItemStack weaponItem = damager.getInventory().getItemInMainHand();
                if (ItemStackUtils.itemHasDisplayName(weaponItem)) {
                    IWeapon weapon = (IWeapon) ItemManager.getInstance().getItemByName(weaponItem.getItemMeta().getDisplayName());
                    for (SpecialEffect itemEffect : weapon.getEffects()) {
                        if (BeEffectPlayer.hasSpecialEffect(itemEffect.getType())) {
                            SpecialEffect playerEffect = BeEffectPlayer.getSpecialEffect(itemEffect.getType());
                            if (itemEffect.getLevel() > playerEffect.getLevel()) {
                                BeEffectPlayer.addSpecialEffect(itemEffect);
                            }
                        } else
                            BeEffectPlayer.addSpecialEffect(itemEffect);
                    }
                }
            }
        }
    }

    /**
     * 判断本次战役是否允许
     * 判断条件:
     * 1. 双方是否人类玩家
     * 2. 任意方是否在流民阵营
     * 3. 双方是否属于同联盟
     *
     * @param entity  攻击方A
     * @param damager 攻击方B
     * @return 允许就返回true
     */
    private boolean validPVP(Entity entity, Entity damager) {
        // # 1
        if (!(entity instanceof Player))
            return true;
        if (!(damager instanceof Player))
            return true;
        Optional<Alliance> allianceA = CoreAPI.getAllianceManager().getAlliance((Player) entity);
        Optional<Alliance> allianceB = CoreAPI.getAllianceManager().getAlliance((Player) damager);
        if (!(allianceA.isPresent() && allianceB.isPresent()))
            return true;
        // # 2
        if (allianceA.get().isRebel() || allianceB.get().isRebel())
            return true;
        // # 3
        if (allianceA.get().equals(allianceB.get()))
            return false;
        return false;
    }
}