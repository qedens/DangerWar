package cc.zoyn.wastelandwarcore.module.item;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

/**
 * 法杖
 *
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Wand implements UniversalItem,Weapon,ConfigurationSerializable {

    private Material material;
    private int subId;
    private int amount;
    private ItemMeta itemMeta;

    private double damage;
    private int level;
    private AttributeType attribute;

    @Override
    public Map<String, Object> serialize() {
//    	a
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", itemMeta.getDisplayName());
        map.put("damage", this.damage);
        map.put("level", this.level);

        return map;
    }

}


