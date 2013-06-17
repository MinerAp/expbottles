package ca.mickealn.expbottles;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExpBottles extends JavaPlugin implements Listener {

    OnEnchantTableEvent enchantListener = new OnEnchantTableEvent();
    ExpBottleBreakListener bottleListener = new ExpBottleBreakListener();

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(enchantListener, this);
        Bukkit.getPluginManager().registerEvents(bottleListener, this);
    }
}
