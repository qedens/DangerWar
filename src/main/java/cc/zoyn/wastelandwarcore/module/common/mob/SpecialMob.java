package cc.zoyn.wastelandwarcore.module.common.mob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;

/**
 * 特殊怪物
 *
 * @author Zoyn
 * @since 2017-12-16
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SpecialMob {

    private String name;
    private EntityType type;

}
