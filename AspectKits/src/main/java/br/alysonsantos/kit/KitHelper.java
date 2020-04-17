package br.alysonsantos.kit;

import acore.util.item.ItemUtil;
import acore.util.json.JsonDocument;
import br.alysonsantos.AspectKits;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class KitHelper {

    private static KitHelper instance;

    public static KitHelper getInstance() {
        if (instance == null) {
            return instance = new KitHelper();
        }
        return instance;
    }

    @Getter
    public final Map<String, Kit> cachedKits = new WeakHashMap<>();

    public boolean containsKit(String name) {
        Kit kit = cachedKits.get(name);
        return kit != null;
    }

    public void deleteKit(Kit kit) {
        cachedKits.remove(kit.getName());

        JsonDocument jsonDocument = null;
        try {
            jsonDocument = JsonDocument.of(AspectKits.getInstance().getDataFolder(), "kits.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonDocument.set("kit." + kit.getName(), null);
        jsonDocument.save();
    }

    public void createKit(Kit kit) {

        JsonDocument jsonDocument = null;
        try {
            jsonDocument = JsonDocument.of(AspectKits.getInstance().getDataFolder(), "kits.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String path = "kit." + kit.getName();

        jsonDocument.setItemStackList(path + ".items", kit.getItemStackList());
        jsonDocument.set(path + ".cooldown", kit.getCooldown());
        jsonDocument.set(path + ".name", kit.getName());

        jsonDocument.save();
        cachedKits.put(kit.getName(), kit);
    }

    public void loadData() {
        JsonDocument jsonDocument = null;
        try {
            jsonDocument = JsonDocument.of(AspectKits.getInstance().getDataFolder(), "kits.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject jsonObject = jsonDocument.get("kit").getAsJsonObject();

        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {

            String name = stringJsonElementEntry.getKey();

            final JsonElement jsonElement = jsonObject.get(name);
            final JsonObject info = jsonElement.getAsJsonObject();

            List<ItemStack> itemStackList = new ArrayList<>();

            info.getAsJsonArray("items").forEach(jsonElement1 -> {

                ItemStack itemStack = ItemUtil.fromString(jsonElement1.getAsString());
                itemStackList.add(itemStack);

            });

            long cooldown = info.get("cooldown").getAsInt();

            Kit kit = new Kit(name, cooldown, itemStackList);
            cachedKits.put(name, kit);
        }
    }
}
