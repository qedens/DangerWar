package cc.zoyn.wastelandwarcore.modules.wand;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

/**
 * 法杖
 *
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
public class Wand implements ConfigurationSerializable {

    private String name;
    private double damage;
    private int level;
    private WandAddition addition;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", this.name);
        map.put("damage", this.damage);
        map.put("level", this.level);

        return map;
    }
}
