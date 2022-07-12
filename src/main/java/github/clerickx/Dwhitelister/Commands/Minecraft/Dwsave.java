package github.clerickx.Dwhitelister.Commands.Minecraft;

import github.clerickx.Dwhitelister.Dwhitelister;
import github.clerickx.Dwhitelister.Utils.config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Dwsave implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        config.get().set("whitelist", Dwhitelister.whitelist);
        config.save();
        sender.sendMessage(ChatColor.AQUA + "Saved " + Dwhitelister.whitelist.size() + " whitelist(s)");
        return true;

    }

}
