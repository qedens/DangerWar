package cc.zoyn.wastelandwarcore.player.effects;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.player.effects.SpecialEffect.SpecialEffectType;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;

public class SpecialEffectRunnable {
	public SpecialEffectRunnable(Entry plugin) {
		new BukkitRunnable() {
			public void run() {
				for(World world : Bukkit.getWorlds()) {
					for(Player player : world.getPlayers()) {
						double resistance = PlayerUtils.getResistance(player);
			    		SpecialEffectPlayer effectPlayer = SpecialEffectPlayer.getPlayerEffect(player.getName());
			    		if(effectPlayer != null) {
			    			//中毒减血计算
			    			if(effectPlayer.hasSpecialEffect(SpecialEffectType.POISON)) {
			    				
			    			}
			    		}
					}
				}
			}
		}.runTaskTimer(plugin,0l,20l);
	}
}