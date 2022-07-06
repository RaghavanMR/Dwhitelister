package github.clerickx.Dwhitelister.Commands.Minecraft;

import github.clerickx.Dwhitelister.Dwhitelister;

import github.clerickx.Dwhitelister.Utils.config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Dwreload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            Dwhitelister.getLogger.info(ChatColor.BLUE + "/Dwreload " + ChatColor.YELLOW + "[bot|whitelist|all]");
            return false;
        }

        if (args.length == 1) {
            if (args[0].equals("whitelist")) {
                reloadWhitelist(sender);
                return true;
            } else if (args[0].equals("bot")) {
                Boolean reload = Dwhitelister.reloadJDA();
                if (reload) { Dwhitelister.getLogger.info("Successfully reloaded JDA"); }
                return true;
            } else if (args[0].equals("all")) {
                reloadWhitelist(sender);
                Boolean reload = Dwhitelister.reloadJDA();
                if (reload) { Dwhitelister.getLogger.info("Successfully reloaded JDA"); }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void reloadWhitelist(CommandSender sender) {
        if (!Dwhitelister.whitelist.equals(config.get().getStringList("whitelist"))) {
            config.reload();
            Dwhitelister.whitelist.clear();
            Dwhitelister.whitelist = config.get().getStringList("whitelist");
            Dwhitelister.getLogger.info("Reloaded " + Dwhitelister.whitelist.size() + " whitelist(s)");
        } else {
            sender.sendMessage(ChatColor.RED + "Error! New whitelist(s) detected unable to reload whitelist file");
        }
    }

}
