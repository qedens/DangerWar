package cc.zoyn.wastelandwarcore.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import cc.zoyn.wastelandwarcore.api.event.UIClickEvent;
import cc.zoyn.wastelandwarcore.manager.UIManager;
import cc.zoyn.wastelandwarcore.util.ItemStackUtils;
import cc.zoyn.wastelandwarcore.util.PlayerUtils;

/**
 * UI事件监听
 *
 * @author hammer354
 * @since 2018-01-04
 */
public class UIListener implements Listener {
	//记录玩家潜行时间的Map
	private Map<String, Long> sneakMap = new HashMap<>();
	
    /**
     * 处理主菜单打开
     *
     * @param event 玩家切换潜行事件
     */
    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
    	if (!event.isSneaking()) {
    		return;
    	}
    	Player player = event.getPlayer();
    	String name = player.getName();
    	long time = System.currentTimeMillis();
    	//玩家与上次潜行时间间隔很短
    	if (sneakMap.containsKey(name) && time - sneakMap.get(name) <= 500) {
    		//打开主菜单
    		UIManager.getInstance().getUIByName("主菜单").open(player);
    	}
    	//记录玩家潜行时间
    	sneakMap.put(name, time);
    }
    
    /**
     * 处理玩家退出
     *
     * @param event 玩家退出事件
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	//从潜行时间Map中移除玩家
    	sneakMap.remove(event.getPlayer().getName());
    }
    
	/**
     * 处理UI交互
     *
     * @param event UI点击事件
     */
    @EventHandler
    public void onUIClick(UIClickEvent event) {
        String title = event.getUi().getTitle();
        if (title.equals("主菜单")) {
        	ItemStack item = event.getInvEvent().getCurrentItem();
        	if (!ItemStackUtils.itemHasDisplayName(item)) {
        		return;
        	}
        	switch (ItemStackUtils.getItemDisplayName(item)) {
        		case "回城":
        			Player player = (Player) event.getInvEvent().getWhoClicked();
        			player.closeInventory();
        			player.sendMessage("准备传送, 请保持不动5秒.");
        			PlayerUtils.delayedTeleport(player, Optional.ofNullable(player.getBedSpawnLocation())
        					.orElse(player.getWorld().getSpawnLocation()), 100L);
        			break;
        		case "切换聊天频道":
        			
        			break;
        		case "处理联盟事务":
        			
        			break;
        		case "点券商城":
        			
        			break;
        		case "VIP特权服务":
        			
        			break;
        		case "特效奖励":
        			
        			break;
        		case "关闭菜单":
        			event.getInvEvent().getWhoClicked().closeInventory();
        			break;
        		default:
        	}
        }
    }
}
