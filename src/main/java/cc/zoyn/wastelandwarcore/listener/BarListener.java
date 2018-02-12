package cc.zoyn.wastelandwarcore.listener;

import java.util.ArrayList;

import javax.naming.ldap.ManageReferralControl;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


/**
 * @deprecated Not implemented yet.
 */
@Deprecated
public class BarListener implements Listener {
	public ArrayList<BossBar> pls=new ArrayList<BossBar>();
	public ArrayList<String> ms=new ArrayList<String>();
	public boolean Isinwar=false;
	public String gb=null;
	@EventHandler
	public void onJoin(PlayerJoinEvent a)
	{
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
