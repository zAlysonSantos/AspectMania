package br.alysonsantos.system.warps.helper;

import acore.util.json.JsonDocument;
import br.alysonsantos.AspectUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpawnHelper {
    @Getter
    private final List<Location> locationList;
    @Getter
    private JsonDocument document;

    public SpawnHelper()  {
        this.locationList = new ArrayList<>();

        try {
            this.document = JsonDocument.of(AspectUtil.getInstance().getDataFolder(), "spawn.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void teleportToSpawn(Player player) {
        int loc = (int) (Math.random() * locationList.size());
        player.teleport(locationList.get(loc));
    }

    public void loadLocation() {
        locationList.addAll(document.getLocationListOr("locs", new ArrayList<>()));
    }

    public void saveLocation(Location location) {
        locationList.add(location);
        if (locationList.size() == 25) {
            document.setLocationList("locs", locationList);
            document.save();
        }
    }
}
