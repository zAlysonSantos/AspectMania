package br.alysonsantos.system.warps.helper;

import acore.util.json.JsonDocument;
import acore.util.specificutils.LocationUtil;
import br.alysonsantos.AspectUtil;
import br.alysonsantos.system.warps.model.Warp;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WarpHelper {
    public Map<String, Warp> cachedWarps = new HashMap<>();

    public boolean containsWarp(String warpName) {
        return cachedWarps.containsKey(warpName);
    }

    public void teleportToWarp(Player player, String warpName) {
        player.teleport(getWarp(warpName).getLocation());
    }

    public Warp getWarp(String warpName) {
        if (containsWarp(warpName))
            return cachedWarps.get(warpName);
        return null;
    }

    public void saveWarp(Warp warp) {
        try {

            JsonDocument document = JsonDocument.of(AspectUtil.getInstance().getDataFolder(), "warps.json");

            document.setLocation("warp." + warp.getName(), warp.getLocation());
            document.save();

            cachedWarps.put(warp.getName(), warp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteWarp(String warpName) {
        try {

            JsonDocument document = JsonDocument.of(AspectUtil.getInstance().getDataFolder(), "warps.json");

            document.set("warp." + warpName, null);
            document.save();

            cachedWarps.remove(warpName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWarps() {
        try {

            JsonDocument document = JsonDocument.of(AspectUtil.getInstance().getDataFolder(), "warps.json");

            if (!document.contains("warp"))
                return;


            JsonObject jsonObject = document.get("warp").getAsJsonObject();

            jsonObject.entrySet().forEach(stringJsonElementEntry -> {

                Warp warp = new Warp(stringJsonElementEntry.getKey(), LocationUtil.unserializeLocation(stringJsonElementEntry.getValue().getAsString()));
                cachedWarps.put(stringJsonElementEntry.getKey(), warp);
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getLogger().warning("[Warps] Um total de " + cachedWarps.size() + " warps foram carregadas!");
    }

}
