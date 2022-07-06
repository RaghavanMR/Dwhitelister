package github.clerickx.Dwhitelister.Commands.Minecraft;

import github.clerickx.Dwhitelister.Dwhitelister;

import github.clerickx.Dwhitelister.Utils.config;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Dwmigrate implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> whitelistedplayers = new ArrayList<>();
        Dwhitelister.getLogger.warning("Don't run this command repeatedly!");

        for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
            whitelistedplayers.add(player.getName());
        }

        config.get().set("whitelist", whitelistedplayers);
        config.save();

        Dwhitelister.whitelist.clear();
        Dwhitelister.whitelist = config.get().getStringList("whitelist");
        Dwhitelister.getLogger.info("Migrated " + Dwhitelister.whitelist.size() + " whitelist(s)");
        return true;
    }

}
