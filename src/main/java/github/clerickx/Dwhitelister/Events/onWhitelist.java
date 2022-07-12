package github.clerickx.Dwhitelister.Events;

import github.clerickx.Dwhitelister.Dwhitelister;
import github.clerickx.Dwhitelister.Utils.whitelist;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.bukkit.Bukkit;

import java.awt.*;

public class onWhitelist extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getMessage().getAuthor().isBot()) return;
        if (e.getChannel().getId().equals(Dwhitelister.getConf.get("whitelist-add-channel"))) onWhitelistAdd(e.getMessage());
    }

    private void onWhitelistAdd(Message msg) {
        int add = whitelist.add((msg.getContentRaw()));

        switch (add) {
            case 0:
                msg.getChannel().sendMessageEmbeds(ebuild(msg.getContentRaw(), msg.getMember(), false)).queue();
                break;
            case 1:
                msg.getChannel().sendMessageEmbeds(ebuild(msg.getContentRaw(), msg.getMember(), true)).queue();
                break;
        }
    }

    private MessageEmbed ebuild(String playername, Member author, Boolean isWhitelisted) {
        EmbedBuilder embed = new EmbedBuilder();
        if (isWhitelisted) {
            embed.setDescription("**" + playername + " has been Whitelisted**\n" + author.getAsMention() + " has whitelisted " + playername).setColor(Color.green);
        } else {
            embed.setDescription("**" + playername + " is already in Whitelisted**").setColor(Color.red);
        }
        return embed.build();
    }

}
