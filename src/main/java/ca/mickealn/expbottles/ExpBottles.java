package ca.mickealn.expbottles;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ca.mickealn.expbottles.ExpBottlesListener;

public final class ExpBottles extends JavaPlugin {

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ExpBottlesListener(), this);
    }
}
