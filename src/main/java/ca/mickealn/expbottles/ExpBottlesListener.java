package ca.mickealn.expbottles;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public final class ExpBottlesListener implements Listener {
    public final int expPerBottle = 17;

    @EventHandler
    public void onOpenEnhantTable(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getType() == InventoryType.ENCHANTING && player.getItemInHand().getType() == Material.GLASS_BOTTLE) {
            event.setCancelled(true);
            int stackAmount = player.getItemInHand().getAmount();
            int originalExp = player.getTotalExperience();
            final int amountOfBottles = player.getTotalExperience() / expPerBottle;
            if (player.getTotalExperience() >= expPerBottle) {
                player.setLevel(0); // Set everything to 0 to prevent doubling of EXP
                player.setTotalExperience(0);
                player.setItemInHand(new ItemStack(Material.EXP_BOTTLE, Math.min(amountOfBottles, stackAmount)));
                if (stackAmount - amountOfBottles > 0) {
                    player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GLASS_BOTTLE, stackAmount - amountOfBottles));
                }
                player.giveExp(Math.max(0, (originalExp - (Math.min(amountOfBottles, stackAmount) * expPerBottle))));
            }
        }
    }

    @EventHandler
    public void onExpEvent(ExpBottleEvent event) {
        event.setExperience(expPerBottle);
    }
}
