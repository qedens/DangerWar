package cc.zoyn.wastelandwarcore.module.town;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.api.CoreAPI;
import cc.zoyn.wastelandwarcore.module.common.user.User;
import cc.zoyn.wastelandwarcore.util.TimeUtils;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.comphenix.executors.BukkitExecutors;
import com.comphenix.executors.BukkitScheduledExecutorService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicDouble;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 表示一个城镇
 *
 * @author Zoyn
 * @since 2017-12-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Town implements ConfigurationSerializable {

    private String name;
    private int level;
    private String owner;
    private List<String> members;
    private String ally;
    private List<String> residences;

    // 四个核心(信标)
    private Beacon leftUpBeacon;
    private Beacon rightUpBeacon;
    private Beacon leftDownBeacon;
    private Beacon rightDownBeacon;
    // 基底
    private Block centerEndBlock;
    private boolean fighting = false;
    // 这里试试用 ProtocolLib 的计划任务
    private static BukkitScheduledExecutorService scheduledExecutorService = BukkitExecutors.newAsynchronous(Entry.getInstance());

    // 初始化块
    {
        scheduledExecutorService.schedule(new TimerTask() {
            @Override
            public void run() {
                // 和平时期
                if (!isFighting()) {
                    if (ghostCrystal.get() < 10) {
                        ghostCrystal.addAndGet(1);
                    }
                    if (arcaneCrystal.get() < 10) {
                        arcaneCrystal.addAndGet(1);
                    }
                    if (holyCrystal.get() < 10) {
                        holyCrystal.addAndGet(1);
                    }
                    if (darkSteelMagicMatter.get() < 10) {
                        darkSteelMagicMatter.addAndGet(1);
                    }
                    if (mithrilMagicMatter.get() < 10) {
                        mithrilMagicMatter.addAndGet(1);
                    }
                    if (eternalGoldMagicMatter.get() < 10) {
                        eternalGoldMagicMatter.addAndGet(1);
                    }
                }
            }
        }, 1, TimeUnit.HOURS);
    }

    /**
     * 魂晶
     */
    private AtomicDouble ghostCrystal = new AtomicDouble(0D);
    /**
     * 奥晶
     */
    private AtomicDouble arcaneCrystal = new AtomicDouble(0D);
    /**
     * 圣晶
     */
    private AtomicDouble holyCrystal = new AtomicDouble(0D);
    /**
     * 暗钢
     */
    private AtomicDouble darkSteelMagicMatter = new AtomicDouble(0D);
    /**
     * 秘银
     */
    private AtomicDouble mithrilMagicMatter = new AtomicDouble(0D);
    /**
     * 恒金
     */
    private AtomicDouble eternalGoldMagicMatter = new AtomicDouble(0D);

    public Town(String name, int level, String owner, List<String> members, String ally, List<String> residences, Beacon leftUpBeacon, Beacon rightUpBeacon, Beacon leftDownBeacon, Beacon rightDownBeacon, Block centerEndBlock) {
        this.name = name;
        this.level = level;
        this.owner = owner;
        this.members = members;
        this.ally = ally;
        this.residences = residences;
        this.leftUpBeacon = leftUpBeacon;
        this.rightUpBeacon = rightUpBeacon;
        this.leftDownBeacon = leftDownBeacon;
        this.rightDownBeacon = rightDownBeacon;
        this.centerEndBlock = centerEndBlock;
    }

    public Town(String name, int level, String owner, List<String> members, String ally, List<String> residences, Beacon leftUpBeacon, Beacon rightUpBeacon, Beacon leftDownBeacon, Beacon rightDownBeacon, Block centerEndBlock, boolean fighting, double ghostCrystal, double arcaneCrystal, double holyCrystal, double darkSteelMagicMatter, double mithrilMagicMatter, double eternalGoldMagicMatter) {
        this.name = name;
        this.level = level;
        this.owner = owner;
        this.members = members;
        this.ally = ally;
        this.residences = residences;
        this.leftUpBeacon = leftUpBeacon;
        this.rightUpBeacon = rightUpBeacon;
        this.leftDownBeacon = leftDownBeacon;
        this.rightDownBeacon = rightDownBeacon;
        this.centerEndBlock = centerEndBlock;
        this.fighting = fighting;
        this.ghostCrystal = new AtomicDouble(ghostCrystal);
        this.arcaneCrystal = new AtomicDouble(arcaneCrystal);
        this.holyCrystal = new AtomicDouble(holyCrystal);
        this.darkSteelMagicMatter = new AtomicDouble(darkSteelMagicMatter);
        this.mithrilMagicMatter = new AtomicDouble(mithrilMagicMatter);
        this.eternalGoldMagicMatter = new AtomicDouble(eternalGoldMagicMatter);
    }

    /**
     * 判断一个方块是否为该城镇的其中一个核心
     *
     * @param block 给定的方块
     * @return 当是的时候返回该核心(信标)对象, 否则返回null
     */
    public Beacon isTownBeacon(Block block) {
        if (leftUpBeacon.equals(block)) {
            return leftUpBeacon;
        } else if (rightUpBeacon.equals(block)) {
            return rightUpBeacon;
        } else if (leftDownBeacon.equals(block)) {
            return leftDownBeacon;
        } else if (rightDownBeacon.equals(block)) {
            return rightDownBeacon;
        }

        return null;
    }

    /**
     * 修改信标上方玻璃的颜色.
     *
     * @param loc  信标的坐标
     * @param mode 模式
     */
    @SuppressWarnings("deprecation")
    public void updateBeaconMode(Location loc, BeaconMode mode) {
        Block glass = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());
        switch (mode) {
            case NORMAL:
                glass.setType(Material.GLASS);
                break;
            case OCCUPIED:
                glass.setTypeIdAndData(Material.STAINED_GLASS.getId(), DyeColor.RED.getWoolData(), true);
                break;
            case RESOURCE:
                glass.setTypeIdAndData(Material.STAINED_GLASS.getId(), DyeColor.PURPLE.getWoolData(), true);
                break;
            default:
                break;
        }
    }

    /**
     * 获取该城镇可容纳的最大人数
     *
     * @return 最大人数
     */
    public int getMaxPeople() {
        return getResidences().size() * 15;
    }

    /**
     * 增加一个成员
     *
     * @param memberName 成员名
     * @return 当添加成功时返回true
     */
    public boolean addMember(String memberName) {
        return members.size() < getMaxPeople() && this.members.add(memberName);
    }

    /**
     * 移除一个成员
     *
     * @param memberName 成员名
     * @return 当移除成功时返回true
     */
    public boolean removeMember(String memberName) {
        return this.members.remove(memberName);
    }

    /**
     * 利用城镇名设置盟友
     * <p>传入Null为解除盟约</p>
     *
     * @param townName 城镇名
     */
    private void setAllyByName(String townName) {
        ally = townName;
    }

    /**
     * 利用城镇对象设置盟友
     * <p>传入Null为解除盟约</p>
     *
     * @param town 城镇对象
     */
    public void setAlly(Town town) {
        //解除原有盟约
        if (ally != null) {
            getAlly().setAllyByName(null);
        }
        //设置新盟友
        if (town != null) {
            ally = town.getName();
            town.setAllyByName(name);
        } else {
            ally = null;
        }
    }

    /**
     * 获取盟友对象
     * <p>无盟友时返回Null</p>
     *
     * @return {@link Town}
     */
    public Town getAlly() {
        return CoreAPI.getTownManager().getTownByName(ally);
    }

    @SuppressWarnings("unchecked")
    public static Town deserialize(Map<String, Object> map) {
        Validate.notNull(map);

        System.out.println(map.toString());

        Town town = new Town();
        town.setName((String) map.getOrDefault("name", "null"));
        town.setLevel((int) map.getOrDefault("level", 1));
        town.setOwner((String) map.getOrDefault("owner", "null"));
        town.setMembers((List<String>) map.getOrDefault("members", Lists.newArrayList()));
        town.setAllyByName((String) map.get("ally"));
        town.residences = (List<String>) map.getOrDefault("residences", Lists.newArrayList());
        town.setFighting((boolean) map.getOrDefault("fighting", false));
        // 魂晶 魔质
        town.setGhostCrystal(new AtomicDouble((double) map.get("ghost-crystal")));
        town.setArcaneCrystal(new AtomicDouble((double) map.get("arcane-crystal")));
        town.setHolyCrystal(new AtomicDouble((double) map.get("holy-crystal")));
        town.setDarkSteelMagicMatter(new AtomicDouble((double) map.get("dark-steel-magic-matter")));
        town.setMithrilMagicMatter(new AtomicDouble((double) map.get("mithril-magic-matter")));
        town.setEternalGoldMagicMatter(new AtomicDouble((double) map.get("eternal-gold-magic-matter")));
        return town;
    }

    public void setResidences(List<String> residenceName) {
        this.residences.clear();
        if (residenceName == null || residenceName.isEmpty()) {
            return;
        }
        this.residences.addAll(residenceName);
    }

    public List<ClaimedResidence> getResidences() {
        List<ClaimedResidence> residences = Lists.newArrayList();
        this.residences.forEach(s -> residences.add(ResidenceApi.getResidenceManager().getByName(s)));
        return residences;
    }

    /**
     * 检查位置是否在城镇内
     *
     * @param location 位置
     * @return true代表是/false代表不是
     */
    public boolean isInside(Location location) {
        Validate.notNull(location);

        List<ClaimedResidence> residences = getResidences();
        boolean isIn = false;
        for (ClaimedResidence residence : residences) {
            if (residence != null && residence.containsLoc(location)) {
                isIn = true;
                break;
            }
        }
        return isIn;
    }

    /**
     * 检查玩家是否在城镇内
     *
     * @param player 玩家
     * @return true代表是/false代表不是
     */
    public boolean isInside(Player player) {
        Validate.notNull(player);

        return isInside(player.getLocation());
    }

    /**
     * 检查方块是否在城镇内
     *
     * @param block 方块
     * @return true代表是/false代表不是
     */
    public boolean isInside(Block block) {
        Validate.notNull(block);

        return isInside(block.getLocation());
    }

    /**
     * 将此城镇更改为战争状态
     */
    public void startWar() {
        if (TimeUtils.isWeekDay()) {

        }
        getResidences().forEach(residence -> residence.getPermissions().setFlag("break", FlagPermissions.FlagState.TRUE));
        setFighting(true);
    }

    /**
     * 将此城镇更改为和平状态
     */
    public void stopWar() {
        getResidences().forEach(residence -> residence.getPermissions().setFlag("break", FlagPermissions.FlagState.FALSE));
        setFighting(false);
    }

    /**
     * 获取在线的用户
     *
     * @return {@link List}
     */
    public List<User> getOnlineMembers() {
        List<User> onlineUsers = Lists.newArrayList();
        members.forEach(s -> {
            Player player = Bukkit.getPlayerExact(s);
            if (player != null) {
                onlineUsers.add(getUserByMemberName(s));
            }
        });

        return onlineUsers;
    }

    /**
     * 利用 城镇成员名 获取用户对象
     *
     * @param memberName 成员名
     * @return {@link User}
     */
    public User getUserByMemberName(String memberName) {
        return CoreAPI.getUserManager().getUserByName(memberName);
    }

    /**
     * 判断一个玩家名是不是城主
     *
     * @param playerName 玩家名
     * @return true代表是/false代表不是
     */
    public boolean isOwner(String playerName) {
        return owner.equals(playerName);
    }

    /**
     * 判断一个玩家名是不是成员
     *
     * @param playerName 玩家名
     * @return true代表是/false代表不是
     */
    public boolean isMember(String playerName) {
        return members
                .stream()
                .anyMatch(s -> s.equals(playerName));
    }

    /**
     * 判断一个玩家名是不是盟友
     *
     * @param playerName 玩家名
     * @return true代表是/false代表不是
     */
    public boolean isAlly(String playerName) {
        if (ally == null) {
            return false;
        }
        return getAlly().isMember(playerName);
    }

    /**
     * 判断一个玩家名是否友好
     *
     * @param playerName 玩家名
     * @return true代表是/false代表不是
     */
    public boolean isFriendly(String playerName) {
        return isMember(playerName) || isAlly(playerName);
    }

    public void setResidences(ClaimedResidence... residences) {
        this.residences.clear();
        for (ClaimedResidence residence : residences) {
            this.residences.add(residence.getName());
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", name);
        map.put("level", level);
        map.put("owner", owner);
        map.put("members", members);
        map.put("ally", ally);
        map.put("residences", residences);
        map.put("fighting", fighting);

        map.put("ghost-crystal", ghostCrystal.get());
        map.put("arcane-crystal", arcaneCrystal.get());
        map.put("holy-crystal", holyCrystal.get());
        map.put("dark-steel-magic-matter", darkSteelMagicMatter.get());
        map.put("mithril-magic-matter", mithrilMagicMatter.get());
        map.put("eternal-gold-magic-matter", eternalGoldMagicMatter.get());
        return map;
    }


}
