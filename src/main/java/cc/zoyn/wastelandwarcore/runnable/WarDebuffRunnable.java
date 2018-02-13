package cc.zoyn.wastelandwarcore.runnable;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.model.Town;
import cc.zoyn.wastelandwarcore.util.CommonUtils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

/**
 * 该计划任务用于
 * <p>
 * 敌方不在自家城镇攻城时所给予的debuff
 *
 * @author Zoyn
 * @since 2018-02-07
 */
public class WarDebuffRunnable extends BukkitRunnable {

    private static final PotionEffect EFFECT = new PotionEffect(PotionEffectType.SLOW_DIGGING, 20, 20);

    @Override
    public void run() {
        // 战争期间起作用
        if (CoreAPI.isInWar()) {
            CommonUtils.getOnlinePlayers().forEach(player -> {
                Optional<Town> town = CoreAPI.getTownManager().getTown(player.getLocation());
                if (town.isPresent()) {
                    // 判断是否在敌营
                    if (town.get().isFriendly(player.getName())) {
                        player.addPotionEffect(EFFECT);
                    }
                }
            });
        }
    }
}
