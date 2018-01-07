package cc.zoyn.wastelandwarcore.runnable;

import cc.zoyn.wastelandwarcore.manager.UserManager;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectType;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.util.BasicUtils;
import org.bukkit.entity.Damageable;
import org.bukkit.scheduler.BukkitRunnable;

public class SpecialEffectRunnable extends BukkitRunnable {

    @Override
    public void run() {
        UserManager manager = UserManager.getInstance();
        BasicUtils.getOnlinePlayers().forEach(player -> {
            User user = manager.getUserByName(player.getName());
            double resistance = user.getResistance();
            SpecialEffectPlayer effectPlayer = user.getEffects();
            if (effectPlayer != null) {
                //中毒减血计算
                if (effectPlayer.hasSpecialEffect(SpecialEffectType.POISON)) {
                    Damageable d = player;
                    double heal = d.getHealth();
                    heal = heal-SpecialEffectPlayer.getPoisoningHealth(
                            effectPlayer.getSpecialEffect(SpecialEffectType.POISON).getLevel());
                    if(heal <= 0)
                        d.setHealth(0);
                    else
                        d.setHealth(heal);
                }
                //腐蚀减少饥饿度计算
                if(effectPlayer.hasSpecialEffect(SpecialEffectType.HUNGER)) {
                    player.setFoodLevel(player.getFoodLevel()-
                            SpecialEffectPlayer.getHunger(effectPlayer.getSpecialEffect(SpecialEffectType.HUNGER).getLevel()));
                }
                //速度计算(无论有减速且无减速都必须计算)
                float speed = user.getMoveSpeed();
                if(effectPlayer.hasSpecialEffect(SpecialEffectType.SLOW)) {
                    speed = SpecialEffectPlayer.getSlow(
                            effectPlayer.getSpecialEffect(SpecialEffectType.SLOW).getLevel(),
                            speed,
                            resistance
                    );
                }
                player.setWalkSpeed(speed);
            }
        });
    }
}