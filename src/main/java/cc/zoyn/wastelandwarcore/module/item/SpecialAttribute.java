package cc.zoyn.wastelandwarcore.module.item;

import java.util.Map;
import java.util.function.BiFunction;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 特殊属性
 *
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SpecialAttribute implements ConfigurationSerializable {
    private String name;
    private String description;
    private BiFunction<Player, Arguments, Boolean> executor;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", description);
        return map;
    }
}
