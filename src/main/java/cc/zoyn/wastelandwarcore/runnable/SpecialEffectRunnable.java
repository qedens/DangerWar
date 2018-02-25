package cc.zoyn.wastelandwarcore.runnable;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.manager.UserManager;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectType;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.util.CommonUtils;
import org.bukkit.scheduler.BukkitRunnable;

import static cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer.*;
import static cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectType.*;


public class SpecialEffectRunnable extends BukkitRunnable {

    @Override
    public void run() {
        final UserManager manager = CoreAPI.getUserManager();
        CommonUtils.getOnlinePlayers().forEach(player -> {
            User user = manager.getUserByName(player.getName());

            if (user != null) {
                double resistance = user.getResistance();
                SpecialEffectPlayer effectPlayer = user.getEffects();
                if (effectPlayer != null) {

                    // 中毒减血计算
                    if (effectPlayer.hasSpecialEffect(POISON)) {
                        double health = player.getHealth();
                        // 取中毒等级然后自减
                        health -= getPoisoningHealth(effectPlayer.getSpecialEffect(POISON).getLevel());
//                        System.out.println("正在开始中毒自减");
                        if (health <= 0) {
                            player.damage(10); // 小于等于0时做伤害操作, 防止玩家卡在重生界面
//                            player.setHealth(0);
                        } else { // 大于0
                            player.damage(0); // 模仿中毒的视角
                            player.setHealth(health);
                        }
                    }

                    // 腐蚀减少饥饿度计算
                    if (effectPlayer.hasSpecialEffect(HUNGER)) {
//                        System.out.println("正在开始腐蚀自减");
                        player.setFoodLevel(player.getFoodLevel() - getHunger(effectPlayer.getSpecialEffect(HUNGER).getLevel()));
                    }

                    // 速度计算(无论有减速且无减速都必须计算)
                    float speed = user.getMoveSpeed();
                    if (effectPlayer.hasSpecialEffect(SLOW)) {
//                        System.out.println("正在开始减速操作");
                        speed = getSlow(effectPlayer.getSpecialEffect(SpecialEffectType.SLOW).getLevel(), speed, resistance);
                    }
                    player.setWalkSpeed(speed);
                }
            }
        });
    }
}