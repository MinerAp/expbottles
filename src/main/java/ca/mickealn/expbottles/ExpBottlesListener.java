package ca.mickealn.expbottles;

import org.bukkit.ChatColor;
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
        if (event.getInventory().getType() == InventoryType.ENCHANTING && (player.getItemInHand().getType() == Material.GLASS_BOTTLE)) {
            event.setCancelled(true);
            ItemStack stack = player.getItemInHand();
            int originalExp = player.getTotalExperience();
            final int amountOfBottles = player.getTotalExperience() / expPerBottle;
            if (player.getLevel() < 1) {
                player.sendMessage(ChatColor.DARK_RED + "You do not have enough EXP!");
            } else if (amountOfBottles <= stack.getAmount()) { // If they have more bottles then they can create into EXP bottles and need to get some back
                int playerNewExp = player.getTotalExperience() % expPerBottle; // Getting the new experience level to give back to the player.
                player.setLevel(0); // Set everything to 0 to prevent doubling of EXP
                player.setTotalExperience(0);
                player.giveExp(playerNewExp);
                player.setItemInHand(new ItemStack(Material.EXP_BOTTLE, amountOfBottles));
                player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GLASS_BOTTLE, (stack.getAmount() - amountOfBottles)));

            } else { // if they have more EXP/expPerBottle than bottles
                player.setItemInHand(new ItemStack(Material.EXP_BOTTLE, stack.getAmount()));
                player.setLevel(0); // Set everything to 0 to prevent doubling of EXP
                player.setTotalExperience(0);
                player.giveExp(originalExp - (stack.getAmount() * expPerBottle));
            }
        }
    }

    @EventHandler
    public void onExpEvent(ExpBottleEvent event) {
        event.setExperience(expPerBottle);
    }
}
