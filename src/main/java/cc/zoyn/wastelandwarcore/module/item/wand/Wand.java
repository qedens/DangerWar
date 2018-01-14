package cc.zoyn.wastelandwarcore.module.item.wand;

import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect;
import cc.zoyn.wastelandwarcore.module.item.IWeapon;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
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
public class Wand implements IWeapon {

    private Material material;
    private int subId;
    private int amount;
    private ItemMeta itemMeta;

    private List<SpecialEffect> effects;
    private double damage;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("material", this.material.toString());
        map.put("subId", this.subId);
        map.put("amount", this.amount);
        map.put("itemMeta", this.itemMeta);

        map.put("damage", this.damage);
        map.put("effect", effects);
        return map;
    }
}
