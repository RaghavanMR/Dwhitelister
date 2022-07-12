package github.clerickx.Dwhitelister.Utils;

import github.clerickx.Dwhitelister.Dwhitelister;

import org.bukkit.OfflinePlayer;

import java.io.File;
import java.util.List;

public class whitelist {

    public static int add(String player) {
        if (Dwhitelister.whitelist.contains(player)) {
            return 0;
        } else {
            Dwhitelister.whitelist.add(player);
            return 1;
        }
    }

    public static int remove(String player) {
        if (!Dwhitelister.whitelist.contains(player)) {
            return 0;
        } else {
            Dwhitelister.whitelist.remove(player);
            return 1;
        }
    }

    public static File getFile() {
        return config.file;
    }

}
