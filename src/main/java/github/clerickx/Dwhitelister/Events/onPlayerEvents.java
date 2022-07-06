package github.clerickx.Dwhitelister.Events;

import github.clerickx.Dwhitelister.Dwhitelister;
import github.clerickx.Dwhitelister.Utils.whitelist;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class onPlayerEvents implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (Dwhitelister.whitelist.contains(e.getPlayer().getName())) {
            e.allow();
        } else {
            if (e.getPlayer().isBanned()) {
                whitelist.remove(e.getPlayer());
            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Dwhitelister.getConf.getString("whitelist-kick-message"));
            }
        }
    }

}
