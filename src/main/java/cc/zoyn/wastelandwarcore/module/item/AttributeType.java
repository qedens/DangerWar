package cc.zoyn.wastelandwarcore.module.item;

import lombok.Getter;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Snowball;

/**
 * 可用属性
 *
 * @author Zoyn
 * @since 2017-12-10
 */
@Getter
public enum AttributeType {

    FIREBALL(new SpecialAttribute("火球", "发射一个火球",
            (player, arguments) -> {
                Fireball fireball = player.launchProjectile(Fireball.class);
                fireball.setVelocity(player.getLocation().getDirection().multiply(5));
                return true;
            })),
    SNOWBALL(new SpecialAttribute("雪球", "发射一个雪球",
            (player, arguments) -> {
                Snowball snowball = player.launchProjectile(Snowball.class);
                snowball.setVelocity(player.getLocation().getDirection().multiply(5));
                return true;
            }));

    private SpecialAttribute specialAttribute;

    AttributeType(SpecialAttribute specialAttribute) {
        this.specialAttribute = specialAttribute;
    }
}