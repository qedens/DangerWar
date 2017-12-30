package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * 本类进行 实体攻击 的相关处理
 *
 * @author DFKK
 * @since 2017-12-09
 */
public class EntityDamageByEntityListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent event) {
        /*
         * 判断攻击者
         * 虚弱 削弱伤害
         */
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            SpecialEffectPlayer effectPlayer = SpecialEffectPlayer.getPlayerEffect(damager.getName());
            if (effectPlayer != null) {
                //虚弱削弱伤害
                event.setDamage(PlayerUtils.getDamagerDamage(effectPlayer, damager, event.getDamage()));
            }
        }
        /*
         * 被攻击者判断
         * 获取 玩家身上的总护甲值
         */
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            SpecialEffectPlayer effectPlayer = SpecialEffectPlayer.getPlayerEffect(player.getName());
            if (effectPlayer != null) {
                event.setDamage(PlayerUtils.getArmorDamage(effectPlayer, player, event.getDamage()));
            }
        }
    }
}
