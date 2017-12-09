package cc.zoyn.wastelandwarcore.module.town;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;
import java.util.Map;

/**
 * 区域
 *
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Region implements ConfigurationSerializable {

    private String name = "";
    private String owner;
    private String worldName;
    private Location border1;
    private Location border2;
    private long createTime;
    private List<String> allowedPlayers;
    private Location teleportPoint;

    public static Region deserialize(Map<String, Object> map) {
        Validate.notNull(map);

        Region region = new Region();
        region.setName((String) map.getOrDefault("name", "null"));
        region.setOwner((String) map.getOrDefault("owner", "null"));
        region.setWorldName((String) map.get("worldname"));

        return region;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", name);
        map.put("owner", owner);
        map.put("worldname", worldName);
        map.put("border1", border1);
        map.put("border2", border2);
        map.put("createtime", createTime);
        map.put("allowedplayers", allowedPlayers);
        map.put("teleportpoint", teleportPoint);

        return map;
    }
}
