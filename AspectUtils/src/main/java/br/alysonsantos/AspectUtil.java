package br.alysonsantos;

import acore.plugin.CoPlugin;
import br.alysonsantos.command.*;
import br.alysonsantos.system.teleport.helper.TeleportHelper;
import br.alysonsantos.system.warps.WarpsListener;
import br.alysonsantos.system.warps.helper.SpawnHelper;
import br.alysonsantos.system.warps.helper.WarpHelper;
import br.alysonsantos.listener.OthersListeners;
import br.alysonsantos.util.SpeedCommand;
import br.alysonsantos.util.UtilTasker;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;

@Getter
public class AspectUtil extends CoPlugin {

    @Getter
    private static AspectUtil instance;
    public static TeleportHelper teleportHelper;

    private final WarpHelper warpHelper;
    private SpawnHelper spawnHelper;

    public AspectUtil() {
        super("Utils", "Alyson Santos");
        instance = this;

        spawnHelper = new SpawnHelper();
        warpHelper = new WarpHelper();

        saveConfig();
    }

    public void preInit() {
        teleportHelper = new TeleportHelper();
    }

    @Override
    public void init() {
        spawnHelper.loadLocation();
        warpHelper.loadWarps();

        registerListeners(
                new WarpsListener(),
                new OthersListeners()
        );

        startTasks();

        addCommand(this,
                new AlertaCommand(),
                new ChapeuCommand(),
                new LixeiraCommand(),
                new ClearChatCommand(),
                new EditItemCommand(),
                new GameModeCommand(),
                new TeleportCommand(),
                new EnchantCommand(),
                new InvseeCommand(),
                new EchestCommand(),
                new SpeedCommand(),
                new ClearCommand(),
                new CraftCommand(),
                new SpawnCommand(),
                new WarpCommand(),
                new FoodCommand(),
                new HealCommand(),
                new FlyCommand(),
                new LuzCommand()
        );
    }


    private void startTasks() {
        new UtilTasker().runTaskTimerAsynchronously(this, 20L, 20L);

        for (World w : Bukkit.getWorlds()) {
            w.setGameRuleValue("doDaylightCycle", "false");
            w.setTime(6000L);
        }
    }

    @Override
    public void stop() {
    }
}
