package cc.zoyn.wastelandwarcore.listener;

import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;


/**
 * @deprecated Not implemented yet.
 */
@Deprecated
public class BarListener implements Listener {
    public ArrayList<BossBar> pls = new ArrayList<BossBar>();
    public ArrayList<String> ms = new ArrayList<String>();
    public boolean Isinwar = false;
    public String gb = null;

    @EventHandler
    public void onJoin(PlayerJoinEvent a) {
		/*
		Player player=a.getPlayer();
		ScoreboardManager scm=Bukkit.getScoreboardManager();
		Scoreboard scb=scm.getNewScoreboard();
		Objective obj=scb.registerNewObjective("竞技分", "1");
		for (String string : ms) {

		}
		player.setScoreboard(scb);
		BossBar tbBar=Bukkit.createBossBar("广播："+gb, BarColor.BLUE, BarStyle.SOLID, BarFlag.CREATE_FOG);
		pls.add(tbBar);
		for(BossBar bar:pls)
			{
			bar.addPlayer(player);
			}
		*/
        // TODO 交互部分.
    }
}
