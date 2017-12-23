package cc.zoyn.wastelandwarcore.runnable;

import cc.zoyn.wastelandwarcore.Entry;
import cc.zoyn.wastelandwarcore.api.CoreAPI;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 城镇数据保存线程
 *
 * @author Zoyn
 * @since 2017-12-23
 */
public class TownDataSaveRunnable extends BukkitRunnable {

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        CoreAPI.getTownManager().saveElements(CoreAPI.getTownManager().getList());
        Entry.getInstance().getLogger().info("正在进行城镇数据保存 耗时 " + (System.currentTimeMillis() - start) + " ms");
    }
}
