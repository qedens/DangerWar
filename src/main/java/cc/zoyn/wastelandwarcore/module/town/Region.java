package cc.zoyn.wastelandwarcore.module.town;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public Set<Location> getLocationSet() {
        Set<Location> set = Sets.newHashSet();


        return set;
    }

    /**
     * 判断坐标是否含于该区域
     *
     * @param location 坐标
     */
    public boolean isInRegion(final Location location) {
        if (location == null) {
            return false;
        } else if (!Bukkit.getWorld(worldName).equals(location.getWorld())) {
            return false;
        } else if (border1.getX() > location.getX()) {
            return false;
        } else if (border1.getY() > location.getY()) {
            return false;
        } else if (border1.getZ() > location.getZ()) {
            return false;
        } else if (border2.getX() < location.getX()) {
            return false;
        } else if (border2.getY() < location.getY()) {
            return false;
        } else if (border2.getZ() < location.getZ()) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static Region deserialize(Map<String, Object> map) {
        Validate.notNull(map);

        Region region = new Region();
        region.setName((String) map.getOrDefault("name", "null"));
        region.setOwner((String) map.getOrDefault("owner", "null"));
        region.setWorldName((String) map.get("worldname"));
        region.setBorder1((Location) map.get("border1"));
        region.setBorder2((Location) map.get("border2"));
        region.setCreateTime((long) map.get("createtime"));
        region.setAllowedPlayers((List<String>) map.get("allowedplayers"));
        region.setTeleportPoint((Location) map.get("teleportpoint"));

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
