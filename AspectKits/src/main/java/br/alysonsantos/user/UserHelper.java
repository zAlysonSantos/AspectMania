package br.alysonsantos.user;

import acore.util.json.JsonDocument;
import br.alysonsantos.AspectKits;
import br.alysonsantos.core.User;
import br.alysonsantos.kit.Kit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;

public class UserHelper {

    private static UserHelper instance;

    public static UserHelper getInstance() {
        if (instance == null) {
            return instance = new UserHelper();
        }
        return instance;
    }

    @Getter
    public final Map<String, KitUser> cachedUsers = new WeakHashMap<>();

    public boolean inCooldown(KitUser user, Kit kit) {
        if (user.getKitsInCooldown().get(kit.getName()) != null
                && user.getKitsInCooldown().get(kit.getName()) < System.currentTimeMillis())
            user.getKitsInCooldown().remove(kit.getName());

        return user.getKitsInCooldown().containsKey(kit.getName());
    }

    public long getCooldown(KitUser user, Kit kit) {
        return user.getKitsInCooldown().get(kit.getName());
    }

    public CompletableFuture<Void> loadDataUser(String name) {
        return CompletableFuture.runAsync(() -> {

            if (cachedUsers.get(name) != null)
                return;

            try {

                JsonDocument document = JsonDocument.of(AspectKits.getInstance().getDataFolder(), name + ".json");

                if (!document.contains("kits")) {

                    KitUser kitUser = new KitUser(name, new HashMap<>());
                    cachedUsers.put(name, kitUser);

                    return;
                }

                final JsonObject jsonObject = document.get("kits").getAsJsonObject();
                final Map<String, Long> longMap = new HashMap<>();

                for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
                    if (stringJsonElementEntry.getValue().getAsLong() > System.currentTimeMillis()) {
                        longMap.put(stringJsonElementEntry.getKey(), stringJsonElementEntry.getValue().getAsLong());
                }
            }

            KitUser kitUser = new KitUser(name, longMap);
            cachedUsers.put(name, kitUser);

        } catch(IOException e){
            e.printStackTrace();
        }

//            if (gsonManager.containsSection("kits")) {
//                Map<String, Long> kits = new HashMap<>();
//
//                gsonManager.getSection("kits").forEach(kitName -> {
//                    long cooldown = gsonManager.get("kits." + kitName).asLong();
//                    kits.put(kitName, cooldown);
//
//                    if (cooldown < System.currentTimeMillis()) kits.remove(kitName);
//                });
//
//                KitUser kitUser = new KitUser(name, kits);
//                cachedUsers.put(name, kitUser);
//
//                return;
//            }
//
//            KitUser kitUser = new KitUser(name, new HashMap<>());
//            cachedUsers.put(name, kitUser);

    },AspectKits.getInstance().

    getExecutor());
}

    public CompletableFuture<Void> saveDataUser(String name) {
        return CompletableFuture.runAsync(() -> {

            try {

                JsonDocument document = JsonDocument.of(AspectKits.getInstance().getDataFolder(), name + ".json");
                KitUser user = getCachedUsers().get(name);

                user.getKitsInCooldown().forEach((s, aLong) -> {
                    document.set("kits." + s, aLong);
                });

                document.save();

            } catch (Exception e) {

            }

        }, AspectKits.getInstance().getExecutor());
    }
}
