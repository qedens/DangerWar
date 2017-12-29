package cc.zoyn.wastelandwarcore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import cc.zoyn.wastelandwarcore.player.effects.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;

/**
 * @author DFKK
 * @since 2017-12-09
 */
public class EntityDamageByEntityListener implements Listener {
	/**
	 * @param event 生物攻击生物事件
	 */
    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent event) {
    	/**
    	 * 如果攻击者是玩家
    	 * 虚弱 削弱伤害
    	 */
    	if(event.getDamager() instanceof Player) {
    		Player damager = (Player) event.getDamager();
    		SpecialEffectPlayer effectPlayer = SpecialEffectPlayer.getPlayerEffect(damager.getName());
    		if(effectPlayer != null) {
    			//虚弱削弱伤害
        		event.setDamage(PlayerUtils.getDamagerDamage(effectPlayer,damager,event.getDamage()));
    		}
    	}
    	/**
    	 * 如果 被攻击者是玩家
    	 * 获取 玩家身上的总护甲值
    	 */
    	if(event.getEntity() instanceof Player) {
	        Player player = (Player) event.getEntity();
	        SpecialEffectPlayer effectPlayer = SpecialEffectPlayer.getPlayerEffect(player.getName());
	        if(effectPlayer != null) {
	        	event.setDamage(PlayerUtils.getArmorDamage(effectPlayer,player,event.getDamage()));
	        }
    	}
    }
}
