package cc.zoyn.wastelandwarcore.listener;

import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectPlayer;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.module.item.wand.Wand;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;

/**
 * @author Zoyn
 * @since 2018-01-06
 */
public class WandListener implements Listener {

    // 内部结构 -> 玩家名:snowball to 法杖
    private static Map<String, Wand> map = Maps.newHashMap();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        // 左击
//        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
//
//        }
        // 右击
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.hasItem()) {
                // 是否是法杖
                if (CoreAPI.getItemManager().isSpecificItem(event.getItem(), Wand.class)) {
                    Snowball ball = player.launchProjectile(Snowball.class, player.getLocation().getDirection());
                    ball.setCustomName(player.getName() + ":snowball");
                    ball.setCustomNameVisible(false);
                    ball.setBounce(true);
                    ball.setGlowing(true);
                    ball.setGravity(false);
                    // 缓存
                    map.put(player.getName() + ":snowball", CoreAPI.getItemManager().getItemByName(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName(), Wand.class));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageByEntityEvent event) {
        // 以下考虑抛射物命中
        if (event.getDamager() instanceof Projectile && event.getEntity() instanceof Player) {
            Projectile projectile = (Projectile) event.getDamager();
            String name = projectile.getCustomName();
            // 空检查
            if (name == null || name.equalsIgnoreCase("") || name.isEmpty() || !map.containsKey(name)) {
                return;
            }
            // 获取承受者的用户对象
            User user = CoreAPI.getUserManager().getUserByName(event.getEntity().getName());
            SpecialEffectPlayer effectPlayer = user.getEffects();

            Wand wand = map.get(name);
            wand.getEffects().forEach(specialEffect -> {
//                System.out.println("正在给予特殊效果..." + specialEffect.getType().toString());
                if (effectPlayer.hasSpecialEffect(specialEffect.getType())) {
                    SpecialEffect playerEffect = effectPlayer.getSpecialEffect(specialEffect.getType());
                    if (specialEffect.getLevel() > playerEffect.getLevel()) {
                        effectPlayer.addSpecialEffect(specialEffect);
                    }
                } else {
                    effectPlayer.addSpecialEffect(specialEffect);
                }
            });
        }
    }

}
