package cc.zoyn.wastelandwarcore.module.item;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

/**
 * 表示胸甲
 *
 * @author Zoyn
 * @since 2017-12-10
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Chest implements UniversalItem {

    private Material material;
    private int subId;
    private int amount;
    private ItemMeta itemMeta;

    private double defense;
    private double health;
    private double weight;
    private double resistance;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        return map;
    }
}
