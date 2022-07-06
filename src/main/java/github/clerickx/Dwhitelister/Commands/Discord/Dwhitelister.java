package github.clerickx.Dwhitelister.Commands.Discord;

import github.clerickx.Dwhitelister.Utils.whitelist;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import org.bukkit.Bukkit;

import java.awt.*;

public class Dwhitelister extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent e) {

        if (!e.getName().equals("dwhitelister")) return;

        e.deferReply().queue();

        if (!PermissionUtil.checkPermission(e.getMember(), Permission.MESSAGE_MANAGE)) {
            e.getHook().sendMessage(":x: You don't have permission to use that command").setEphemeral(true).queue();
            return;
        }

        String playername = e.getOption("playername").getAsString();

        switch (e.getOption("type").getAsString()) {
            case "add":
                int add = whitelist.add(Bukkit.getOfflinePlayer(playername));
                if (add == 0) {
                    e.getHook().sendMessageEmbeds(ebuild(playername, e.getMember(), "add_fail")).queue();
                } else if (add == 1) {
                    e.getHook().sendMessageEmbeds(ebuild(playername, e.getMember(), "add_success")).queue();
                }
                break;
            case "remove":
                int remove = whitelist.remove(Bukkit.getOfflinePlayer(playername));
                if (remove == 0) {
                    e.getHook().sendMessageEmbeds(ebuild(playername, e.getMember(), "remove_fail")).queue();
                } else if (remove == 1) {
                    e.getHook().sendMessageEmbeds(ebuild(playername, e.getMember(), "remove_success")).queue();
                }
                break;
            case "list":
                e.getHook().sendMessage("Here is the whitelist file").addFile(whitelist.getFile(), "whitelist.yml").queue();
                break;
        }

    }

    private MessageEmbed ebuild(String playername, Member author, String action) {
        EmbedBuilder embed = new EmbedBuilder();
        switch (action) {
            case "add_success":
                embed.setDescription("**" + playername + " has been Whitelisted**\n" + author.getAsMention() + " has whitelisted " + playername).setColor(Color.green);
                break;
            case "remove_success":
                embed.setDescription("**" + playername + " has been Removed from whitelist**\n" + author.getAsMention() + " has removed " + playername + " from whitelist").setColor(Color.green);
                break;
            case "add_fail":
                embed.setDescription("**" + playername + " is already in Whitelisted**").setColor(Color.red);
                break;
            case "remove_fail":
                embed.setDescription("**" + playername + " not found in Whitelisted**").setColor(Color.red);
                break;
        }
        return embed.build();
    }

}