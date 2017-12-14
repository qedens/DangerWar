package cc.zoyn.wastelandwarcore.module.item;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

/**
 * 表示靴子
 *
 * @author Zoyn
 * @since 2017-12-10
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Shoes implements IArmor {

    private Material material;
    private int subId;
    private int amount;
    private ItemMeta itemMeta;

    /**
     * 防御
     */
    private double defense;
    /**
     * 移动速度
     */
    private float movementSpeed;
    /**
     * 特殊效果抵抗
     */
    private double resistance;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("defense", this.defense);
        map.put("movementSpeed", this.movementSpeed);
        map.put("resistance", this.resistance);
        return map;
    }
}
