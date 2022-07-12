package github.clerickx.Dwhitelister.Commands.Minecraft;

import github.clerickx.Dwhitelister.Utils.whitelist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Dwhitelister implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            help(commandSender);
        } else if (args.length == 1) {
            switch (args[0]) {
                case "help":
                    help(commandSender);
                    return true;
                case "list":
                    StringBuilder result = new StringBuilder();
                    for (String player : github.clerickx.Dwhitelister.Dwhitelister.whitelist) {
                        if (result.length() > 0) {
                            result.append(", ");
                        }
                        result.append(player);
                    }
                    commandSender.sendMessage("Whitelisted players: " + result);
                    return true;
                default:
                    help(commandSender);
                    return false;
            }
        } else if (args.length == 2) {
            switch (args[0]) {
                case "add":
                    int add = whitelist.add(args[1]);
                    if (add == 0) {
                        commandSender.sendMessage(ChatColor.RED + "Player " + args[1] + " already in whitelist");
                    } else if (add == 1) {
                        commandSender.sendMessage("Added " + args[1] + " to whitelist");
                    }
                    return true;
                case "remove":
                    int remove = whitelist.remove(args[1]);
                    if (remove == 0) {
                        commandSender.sendMessage(ChatColor.RED + "Player " + args[1] + " not found in whitelist");
                    } else if (remove == 1) {
                        commandSender.sendMessage("Removed " + args[1] + " to whitelist");
                    }
                    return true;
                default:
                    help(commandSender);
                    return false;
            }
        }

        return true;
    }

    private void help(CommandSender sender) {
        sender.sendMessage("/Dwhitelister help\n" +
                "/Dwhitelister [help|add|remove|list] [playername] - whitelist/help command\n" +
                "/Dwreload [all|whitelist|bot] - reload command\n" +
                "/Dwsave - saves Whitelist from memory\n" +
                "/Dwmigrate - migrates default whitelist to plugin's format"
        );
    }

}
