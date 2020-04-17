package br.alysonsantos.listener;

import br.alysonsantos.AspectKits;
import br.alysonsantos.user.UserHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    public final UserHelper userHelper = AspectKits.getInstance().getUserHelper();

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        userHelper.loadDataUser(player.getName()).join();
    }
}
