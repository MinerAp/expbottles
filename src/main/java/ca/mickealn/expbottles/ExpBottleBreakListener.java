package ca.mickealn.expbottles;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpBottleBreakListener extends JavaPlugin implements Listener {

    @EventHandler
    public void onExpEvent(ExpBottleEvent event) {
        event.setExperience(17);
    }

}
