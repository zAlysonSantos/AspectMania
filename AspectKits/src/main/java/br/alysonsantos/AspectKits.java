package br.alysonsantos;

import acore.plugin.CoPlugin;
import br.alysonsantos.command.CommandKit;
import br.alysonsantos.command.CommandKitCriar;
import br.alysonsantos.kit.KitHelper;
import br.alysonsantos.listener.PlayerJoinListener;
import br.alysonsantos.listener.PlayerQuitListener;
import br.alysonsantos.user.UserHelper;
import lombok.Getter;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

@Getter
public class AspectKits extends CoPlugin {

    @Getter
    private static AspectKits instance;

    private final KitHelper kitHelper;
    private final UserHelper userHelper;
    private final Executor executor = ForkJoinPool.commonPool();

    public AspectKits() {
        super("Kits", "Alyson Santos");
        instance = this;

        kitHelper = new KitHelper();
        userHelper = new UserHelper();
    }

    @Override
    public void preInit() {

    }

    @Override
    public void init() {
        kitHelper.loadData();

        registerListeners(
                new PlayerJoinListener(),
                new PlayerQuitListener()
        );

        addCommand(
                this,
                new CommandKitCriar(),
                new CommandKit()
        );
    }

    @Override
    public void stop() {

    }
}
