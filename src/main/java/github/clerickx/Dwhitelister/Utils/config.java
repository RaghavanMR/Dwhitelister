package github.clerickx.Dwhitelister.Utils;

import github.clerickx.Dwhitelister.Dwhitelister;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class config {

    public static File file;
    private static FileConfiguration whitelist;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Dwhitelister").getDataFolder(), "whitelist.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Dwhitelister.getLogger.warning("Unable to create whitelist.yml");
            }
        }

        whitelist = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return whitelist;
    }

    public static void save() {
        try {
            whitelist.save(file);
        } catch (IOException e) {
            Dwhitelister.getLogger.warning("Unable to create whitelist.yml");
        }
    }

    public static void reload() {
        whitelist = YamlConfiguration.loadConfiguration(file);
   }

}
