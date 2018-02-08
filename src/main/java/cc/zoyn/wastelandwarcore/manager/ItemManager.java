package cc.zoyn.wastelandwarcore.manager;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffect;
import cc.zoyn.wastelandwarcore.module.common.specialeffect.SpecialEffectType;
import cc.zoyn.wastelandwarcore.module.item.*;
import cc.zoyn.wastelandwarcore.module.item.wand.Wand;
import cc.zoyn.wastelandwarcore.util.ConfigurationUtils;
import cc.zoyn.wastelandwarcore.util.ItemStackUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 物品管理器
 *
 * @author Zoyn
 * @since 2017-12-10
 */
public class ItemManager extends AbstractManager<UniversalItem> implements SavableManager<UniversalItem> {

    private static volatile ItemManager instance;

    // 防止意外实例化
    private ItemManager() {
    }

    /**
     * 获取物品管理器实例
     *
     * @return {@link ItemManager}
     */
    public static ItemManager getInstance() {
        if (instance == null) {
            synchronized (ItemManager.class) {
                if (instance == null) {
                    instance = new ItemManager();
                }
            }
        }
        return instance;
    }

    public List<UniversalItem> loadItems() {
        // clear list
        getList().clear();

        File[] files = Entry.getInstance().getItemFolder().listFiles();
        Arrays.stream(Validate.notNull(files))
                .forEach(file -> {
                    FileConfiguration fileConfiguration = ConfigurationUtils.loadYml(file);
                    ItemType type = ItemType.valueOf(fileConfiguration.getString("Item.ItemType"));

                    // 以下为共有属性
                    int material = fileConfiguration.getInt("Item.Material");
                    int data = fileConfiguration.getInt("Item.Data");
                    String displayName = fileConfiguration.getString("Item.DisplayName").replaceAll("&", "§");
                    List<String> lore = fileConfiguration.getStringList("Item.Lore")
                            .stream()
                            .map(s -> s.replaceAll("&", "§"))
                            .collect(Collectors.toList());

                    Set<String> keys = fileConfiguration.getConfigurationSection("Item").getKeys(false);
                    // 以下为独有属性
                    double damage = 0;
                    List<SpecialEffect> effects = Lists.newArrayList();

                    /* 防具 */
                    double defense = 0;
                    double health = 0;
                    double resistance = 0;

                    float movementSpeed = 0;

                    if (keys.contains("Damage")) {
                        damage = fileConfiguration.getDouble("Item.Damage");
                    }
                    if (keys.contains("SpecialEffect")) {
                        // 加载特殊属性
                        fileConfiguration.getStringList("Item.SpecialEffect")
                                .forEach(s -> {
                                    String[] arguments = s.split(":");
                                    effects.add(new SpecialEffect(SpecialEffectType.valueOf(arguments[0]), Long.valueOf(arguments[1]), Integer.valueOf(arguments[2])));
                                });
                    }
                    if (keys.contains("Defense")) {
                        defense = fileConfiguration.getDouble("Item.Damage");
                    }
                    if (keys.contains("Health")) {
                        health = fileConfiguration.getDouble("Item.Health");
                    }
                    if (keys.contains("Resistance")) {
                        resistance = fileConfiguration.getDouble("Item.Resistance");
                    }
                    if (keys.contains("MovementSpeed")) {
                        movementSpeed = Float.valueOf(fileConfiguration.getString("Item.MovementSpeed"));
                    }

//                    ItemStack itemStack = new ItemStack(Material.getMaterial(material), 1, (short) data);
                    ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.getMaterial(material));
                    itemMeta.setDisplayName(displayName);
                    itemMeta.setLore(lore);

                    // 对象实例化
                    if (type.equals(ItemType.WAND)) {
                        Wand wand = new Wand(Material.getMaterial(material), data, 1, itemMeta, effects, damage);
                        addElement(wand);
                    } else if (type.equals(ItemType.SWORD)) {
                        Sword sword = new Sword(Material.getMaterial(material), data, 1, itemMeta, effects, damage);
                        addElement(sword);
                    } else if (type.equals(ItemType.CHEST_PLATE)) {
                        ChestPlate chestPlate = new ChestPlate(Material.getMaterial(material), data, 1, itemMeta, defense, health, resistance);
                        addElement(chestPlate);
                    } else if (type.equals(ItemType.SHOES)) {
                        Shoes shoes = new Shoes(Material.getMaterial(material), data, 1, itemMeta, defense, movementSpeed, resistance);
                        addElement(shoes);
                    }
                    Entry.getInstance().getLogger().info("读取武器 " + displayName + " 成功!");
                });
        return getList();
    }

    public boolean isSpecificItem(ItemStack itemStack, Class<? extends UniversalItem> clazz) {
        if (ItemStackUtils.itemHasDisplayName(itemStack)) {
            UniversalItem item = getItemByName(itemStack.getItemMeta().getDisplayName());
            return item.getClass().getName().equals(clazz.getName());
        }
        return false;
    }

    /**
     * 利用名字获取特殊物品对象
     * <p>利用物品名获取特殊物品对象,当获取不到时返回 null 值<p/>
     *
     * @param itemName 物品名
     * @return {@link UniversalItem}
     */
    public UniversalItem getItemByName(String itemName) {
        for (UniversalItem item : getList()) {
            if (item.getItemMeta() == null) {
                continue;
            }
            if (!item.getItemMeta().hasDisplayName()) {
                continue;
            }
            if (item.getItemMeta().getDisplayName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public <T> T getItemByName(String itemName, Class<T> t) {
        return (T) getItemByName(itemName);
    }

    @Override
    public void saveElements(List<UniversalItem> elements) {
        elements.forEach(this::saveElement);
    }

    @Override
    public void saveElement(UniversalItem universalItem) {
        Validate.notNull(universalItem);

        File file = new File(Entry.getInstance().getItemFolder(), universalItem.getItemMeta().getDisplayName() + ".yml");
        FileConfiguration fileConfiguration = ConfigurationUtils.loadYml(file);
        fileConfiguration.set("Item", universalItem);
        ConfigurationUtils.saveYml(fileConfiguration, file);
    }
}
