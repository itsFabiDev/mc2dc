package eu.zentomomc.mc2dc.Listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class onPrepareAnvilListener implements Listener {
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack[] items = event.getInventory().getContents();

        if (items.length < 2) {
            return;
        }

        ItemStack axe = null;
        ItemStack book = null;

        // Check if the items being combined are a diamond axe and a book of looting
        for (ItemStack item : items) {
            if (item == null) {
                continue;
            }
            if (item.getType() == Material.DIAMOND_AXE) {
                axe = item;
            } else if (item.getType() == Material.ENCHANTED_BOOK && item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                book = item;
            }
        }

        if (axe != null && book != null) {
            // Create a new ItemStack with both enchantments
            ItemStack result = new ItemStack(axe);
            result.addEnchantments(axe.getEnchantments());
            result.addEnchantments(book.getEnchantments());

            // Set the resulting item in the AnvilInventory
            event.setResult(result);
        }
    }
}
