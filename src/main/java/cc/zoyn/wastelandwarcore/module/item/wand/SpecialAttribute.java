package cc.zoyn.wastelandwarcore.module.item.wand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

/**
 * 特殊属性
 *
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SpecialAttribute {

    private String name;
    private String description;
    private BiConsumer<Player, LivingEntity> consumer;

}
