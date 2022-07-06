package github.clerickx.Dwhitelister.Utils;

import github.clerickx.Dwhitelister.Dwhitelister;

import org.bukkit.OfflinePlayer;

import java.io.File;
import java.util.List;

public class whitelist {

    public static int add(OfflinePlayer player) {
        if (Dwhitelister.whitelist.contains(player.getName())) {
            return 0;
        } else {
            Dwhitelister.whitelist.add(player.getName());
            return 1;
        }
    }

    public static int remove(OfflinePlayer player) {
        if (!Dwhitelister.whitelist.contains(player.getName())) {
            return 0;
        } else {
            Dwhitelister.whitelist.remove(player.getName());
            return 1;
        }
    }

    public static File getFile() {
        return config.file;
    }

}
