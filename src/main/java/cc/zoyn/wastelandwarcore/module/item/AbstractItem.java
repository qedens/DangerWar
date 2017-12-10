package cc.zoyn.wastelandwarcore.module.item;

import lombok.Data;
import org.bukkit.Material;

import java.util.List;

/**
 * 表示一个物品
 *
 * @author Zoyn
 * @since 2017-12-10
 */
@Data
public abstract class AbstractItem {

    private Material material;
    private int subId;
    private String displayName;
    private List<String> lore;

}
