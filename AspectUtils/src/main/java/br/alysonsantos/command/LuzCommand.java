package br.alysonsantos.command;

import acore.util.command.AbstractCommand;
import acore.util.command.CommandResponse;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LuzCommand extends AbstractCommand {

    public LuzCommand() {
        super("luz", "", "");

        this.setPlayersOnly(true);
    }

    @Override
    public CommandResponse run(CommandSender commandSender, String[] strings) {

        Player player = (Player) commandSender;

        if (hasPotionEffect(player, PotionEffectType.NIGHT_VISION, 100)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);

            return success(
                    "",
                    "§e Lanterna desabilitada com sucesso.",
                    ""
            );
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 100, true, false));

        return success(
                "",
                "§a Lanterna habilitada com sucesso.",
                ""
        );
    }

    private boolean hasPotionEffect(Player p, PotionEffectType potionEffectType, int amplifier) {
        for (PotionEffect effect : p.getActivePotionEffects()) {
            if (effect.getType().equals(potionEffectType) && effect.getAmplifier() == amplifier) {
                return true;
            }
        }
        return false;
    }
}
