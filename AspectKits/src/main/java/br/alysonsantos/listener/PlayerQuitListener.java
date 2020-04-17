package br.alysonsantos.listener;

import br.alysonsantos.AspectKits;
import br.alysonsantos.user.UserHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    public final UserHelper userHelper = AspectKits.getInstance().getUserHelper();

    @EventHandler
    public void listenerTwo(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        userHelper.saveDataUser(player.getName()).join();
    }
}
