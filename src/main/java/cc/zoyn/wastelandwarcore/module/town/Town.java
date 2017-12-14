package cc.zoyn.wastelandwarcore.module.town;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;
import java.util.Map;

/**
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Town implements ConfigurationSerializable {

    private String name;
    private int level;
    private String owner;
    private List<String> members;
    private Region region;

    @SuppressWarnings("unchecked")
	public static Town deserialize(Map<String, Object> map) {
        Validate.notNull(map);

        Town town = new Town();
        town.setName((String) map.getOrDefault("name", "null"));
        town.setLevel((int) map.getOrDefault("level", 1));
        town.setOwner((String) map.getOrDefault("owner", "null"));
        town.setMembers((List<String>) map.get("members"));
        town.setRegion((Region) map.get("region"));

        return town;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", name);
        map.put("level", level);
        map.put("owner", owner);
        map.put("members", members);
        map.put("region", region);

        return map;
    }
}
