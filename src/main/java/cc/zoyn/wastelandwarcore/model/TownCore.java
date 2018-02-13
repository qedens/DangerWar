package cc.zoyn.wastelandwarcore.model;

import cc.zoyn.wastelandwarcore.exception.InvalidTownCoreException;
import cc.zoyn.wastelandwarcore.manager.TownManager;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * 表示城镇核心
 * @author lss233
 */
@Data
public class TownCore implements ConfigurationSerializable {
    private Town town;
    @Getter
    @Setter
    private Alliance owner;
    private final Block block;
    private final Block glass;
    private final Location location;
    private BeaconMode mode;

    /**
     * 通过方块获取城镇核心
     * @param block 信标方块
     * @throws InvalidTownCoreException 当认错城镇核心方块时抛出.
     */
    public TownCore(Block block) throws InvalidTownCoreException {
        this.town = TownManager.getInstance().getTown(block.getLocation()).orElse(TownManager.DEFAULT_TOWN);
        this.block = block;
        this.location = block.getLocation();
        this.glass = block.getWorld().getBlockAt(location.getBlockX(), location.getBlockY() + 1, location.getBlockZ());
        this.owner = town.getAlliance();
        validTownCore();
    }
    /**
     * 通过信标方块获取城镇核心
     * @param block 信标方块
     */
    public TownCore(Beacon block) {
        this.town = TownManager.getInstance().getTown(block.getLocation()).orElse(TownManager.DEFAULT_TOWN);
        this.block = block.getBlock();
        this.location = block.getLocation();
        this.glass = block.getWorld().getBlockAt(location.getBlockX(), location.getBlockY() + 1, location.getBlockZ());
        this.owner = town.getAlliance();
    }

    /**
     * 获取所属城镇
     * @return 所属城镇
     */
    public Town getTown() {
        return town;
    }

    /**
     * 设置所属城镇
     * @param town 所属城镇
     */
    public void setTown(Town town) {
        this.town = town;
    }

    /**
     * 获取对应实际方块
     * @return 实际方块
     */
    public Block getBlock() {
        return block;
    }

    /**
     * 获取信标模式
     * @return 坐标模式
     */
    public BeaconMode getMode() {
        return mode;
    }

    /**
     * 设置信标模式
     * @param mode 信标模式
     */
    public void setMode(BeaconMode mode) {
        this.mode = mode;
    }

    /**
     * 获取上方玻璃
     * @return 玻璃
     */
    public Block getGlass() {
        return glass;
    }

    /**
     * 获取核心所在位置
     * @return 位置
     */
    public Location getLocation() {
        return location;
    }

    private void validTownCore() throws InvalidTownCoreException {
        if(!(block.getType().equals(Material.BEACON))|| town == null){
            throw new InvalidTownCoreException(block);
        }
    }

    /**
     * 根据信标模式更新城镇核心显示状态
     * 修改信标上方玻璃的颜色.
     */
    @SuppressWarnings("deprecation")
    public void updateBeaconMode(){
        switch (getMode()) {
            case NORMAL:
                getGlass().setType(Material.GLASS);
                break;
            case OCCUPIED:
                getGlass().setTypeIdAndData(Material.STAINED_GLASS.getId(), DyeColor.RED.getWoolData(), true);
                break;
            case RESOURCE:
                getGlass().setTypeIdAndData(Material.STAINED_GLASS.getId(), DyeColor.PURPLE.getWoolData(), true);
                break;
            default:
                break;
        }
    }

    public static TownCore deserialize(Map<String, Object> map) throws InvalidTownCoreException {
        return new TownCore(((Location) map.get("location")).getBlock());
    }

    public void update() {
        for (int i = 0; i < town.getTownCores().length; i++) {
            if (this.equals(town.getTownCores()[i]))
                town.getTownCores()[i] = this;
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("town", getTown().getUniqueId().toString());
        map.put("owner", getOwner().getUniqueId().toString());
        map.put("location", getLocation());
        map.put("mode", getMode().toString());
        return map;
    }


    /**
     * 信标模式
     *
     * @author lss233
     */
    public enum BeaconMode {

        /**
         * 普通模式
         */
        NORMAL,
        /**
         * 占据模式
         */
        OCCUPIED,
        /**
         * 资源模式
         */
        RESOURCE
    }
}
