package ca.mickealn.expbottles.util;

import org.bukkit.configuration.file.FileConfiguration;

import com.amshulman.mbapi.MbapiPlugin;
import com.amshulman.mbapi.util.ConfigurationContext;

public class ExpBottlesConfigurationContext extends ConfigurationContext {

    public final int expPerBottle;

    public ExpBottlesConfigurationContext(MbapiPlugin plugin) {
        super(plugin);

        plugin.saveDefaultConfig();
        FileConfiguration configuration = plugin.getConfig();

        expPerBottle = configuration.getInt("expPerBottle", 0);
    }
}
