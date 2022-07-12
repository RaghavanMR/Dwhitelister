package github.clerickx.Dwhitelister;

import github.clerickx.Dwhitelister.Commands.Minecraft.Dwmigrate;
import github.clerickx.Dwhitelister.Commands.Minecraft.Dwreload;
import github.clerickx.Dwhitelister.Commands.Minecraft.Dwsave;
import github.clerickx.Dwhitelister.Events.onPlayerEvents;
import github.clerickx.Dwhitelister.Events.onWhitelist;
import github.clerickx.Dwhitelister.Utils.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Dwhitelister extends JavaPlugin {

    private static JDA jda;
    public static FileConfiguration getConf;
    public static Logger getLogger;

    public static List<String> whitelist = new ArrayList<>();
    public static Timer saveTimer = new Timer();

    @Override
    public void onLoad() {
        getLogger = this.getLogger();
        saveDefaultConfig();
        config.setup();
        getConf = getConfig();
        whitelist = config.get().getStringList("whitelist");
        getLogger.info("Loaded " + whitelist.size() + " whitelist(s)");
    }

    @Override
    public void onEnable() {

        if (!Bukkit.isWhitelistEnforced()) {
            getCommand("Dwhitelister").setExecutor(new github.clerickx.Dwhitelister.Commands.Minecraft.Dwhitelister());
            getCommand("Dwreload").setExecutor(new Dwreload());
            getCommand("Dwmigrate").setExecutor(new Dwmigrate());
            getCommand("Dwsave").setExecutor(new Dwsave());
        } else {
            getLogger().warning(ChatColor.RED + "Plugin Disabled! disable whitelist and restart");
        }
        getServer().getPluginManager().registerEvents(new onPlayerEvents(), this);
        try {
            jda = JDABuilder.createDefault(getConf.getString("token"))
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(new onWhitelist())
                    .addEventListeners(new github.clerickx.Dwhitelister.Commands.Discord.Dwhitelister())
                    .build().awaitReady();
        } catch (LoginException | InterruptedException | IllegalStateException e) {
            getLogger().warning(e.getMessage());
        }
        try {
            jda.getGuildById(getConfig().getString("guild-id")).upsertCommand(new CommandData("dwhitelister", "Command for Dwhitelister")
                    .addOptions(new OptionData(OptionType.STRING, "type", "Type of action")
                            .setRequired(true)
                            .addChoice("add", "add")
                            .addChoice("remove", "remove")
                            .addChoice("list", "list"))
                    .addOptions(new OptionData(OptionType.STRING, "playername", "Player name to take actions").setRequired(false))
            ).queue(null, new ErrorHandler()
                    .handle(ErrorResponse.MISSING_ACCESS, (e) -> getLogger().warning("Insufficient permissions! Slash commands can't be registered"))
            );
        } catch (NullPointerException e) {
            getLogger().warning(e.getMessage());
        }
        saveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                config.get().set("whitelist", whitelist);
                config.save();
            }
        }, 0, 1800000);

    }

    @Override
    public void onDisable() {
        saveTimer.cancel();
        config.get().set("whitelist", whitelist);
        config.save();
        if (jda != null) {
            jda.shutdownNow();
        }
    }

    public static Boolean reloadJDA() {
        jda.shutdownNow();
        try {
            jda = JDABuilder.createDefault(getConf.getString("token"))
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(new onWhitelist())
                    .addEventListeners(new github.clerickx.Dwhitelister.Commands.Discord.Dwhitelister())
                    .build().awaitReady();
            return true;
        } catch (LoginException | InterruptedException e) {
            getLogger.warning(e.getMessage());
            return false;
        }
    }

}
