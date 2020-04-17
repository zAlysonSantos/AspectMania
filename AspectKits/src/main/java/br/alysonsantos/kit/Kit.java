package br.alysonsantos.kit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
@AllArgsConstructor
public class Kit {
    private String name;
    private Long cooldown;
    private List<ItemStack> itemStackList;
}
