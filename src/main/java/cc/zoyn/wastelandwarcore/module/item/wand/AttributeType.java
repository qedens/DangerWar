package cc.zoyn.wastelandwarcore.module.item.wand;

import lombok.Getter;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * 可用属性
 *
 * @author Zoyn
 * @since 2017-12-10
 */
@Getter
public enum AttributeType {

    POISON(new SpecialAttribute("中毒", "可以使人中毒", (player, target) -> {
        player.sendMessage("你让人家中毒了...");
        target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 200));
        target.sendMessage("你中毒了...");
    })),
    HUNGER(new SpecialAttribute("腐蚀", "可以使人腐蚀", (player, target) -> {
        player.sendMessage("你让人家腐蚀了...");
    }));

    private SpecialAttribute specialAttribute;

    AttributeType(SpecialAttribute specialAttribute) {
        this.specialAttribute = specialAttribute;
    }
}