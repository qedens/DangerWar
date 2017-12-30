package cc.zoyn.wastelandwarcore.runnable;

import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect.SpecialEffectType;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.util.BasicUtils;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class SpecialEffectRunnable extends BukkitRunnable {

    @Override
    public void run() {
        BasicUtils.getOnlinePlayers().forEach(player -> {
            double resistance = PlayerUtils.getResistance(player);
            SpecialEffectPlayer effectPlayer = SpecialEffectPlayer.getPlayerEffect(player.getName());
            if (effectPlayer != null) {
                //中毒减血计算
                if (effectPlayer.hasSpecialEffect(SpecialEffectType.POISON)) {

                }
            }
        });

    }
}