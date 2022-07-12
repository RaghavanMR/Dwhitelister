package github.clerickx.Dwhitelister.Commands.Minecraft;

import github.clerickx.Dwhitelister.Dwhitelister;
import github.clerickx.Dwhitelister.Utils.config;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Dwreload implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.BLUE + "/Dwreload " + ChatColor.YELLOW + "[bot|whitelist|all]");
            return false;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "whitelist":
                    reloadWhitelist(sender);
                    return true;
                case "bot": {
                    Boolean reload = Dwhitelister.reloadJDA();
                    if (reload) {
                        sender.sendMessage("Successfully reloaded JDA");
                    }
                    return true;
                }
                case "all": {
                    reloadWhitelist(sender);
                    Boolean reload = Dwhitelister.reloadJDA();
                    if (reload) {
                        sender.sendMessage("Successfully reloaded JDA");
                    }
                    return true;
                }
                default:
                    return false;
            }
        } else {
            return false;

        }
    }

    private void reloadWhitelist(CommandSender sender){
        if (!Dwhitelister.whitelist.equals(config.get().getStringList("whitelist"))) {
            config.reload();
            Dwhitelister.whitelist.clear();
            Dwhitelister.whitelist = config.get().getStringList("whitelist");
            sender.sendMessage("Reloaded " + Dwhitelister.whitelist.size() + " whitelist(s)");
        } else {
            sender.sendMessage(ChatColor.RED + "Error! New whitelist(s) detected unable to reload whitelist file");
        }
    }
}
