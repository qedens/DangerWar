package cc.zoyn.wastelandwarcore.module.item;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

/**
 * 剑
 * 
 * @author Zoyn
 * @since 2017-12-10
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Sword implements UniversalItem,Weapon {

    private Material material;
    private int subId;
    private int amount;
    private ItemMeta itemMeta;

    private double damage;
    private double weight;
    private AttributeType attribute;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("damage",this.damage);
        map.put("weight",this.weight);
        return map;
    }
}