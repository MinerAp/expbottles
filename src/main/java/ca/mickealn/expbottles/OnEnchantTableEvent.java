package ca.mickealn.expbottles;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class OnEnchantTableEvent extends JavaPlugin implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onOpenEnhantTable(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory().getType() == InventoryType.ENCHANTING && (player.getItemInHand().getType() == Material.GLASS_BOTTLE)) {
            event.setCancelled(true);
            ItemStack stack = player.getItemInHand();
            int playerNewExp = player.getTotalExperience() - 17; // Getting the new experience level to give back to the player.
            PlayerInventory playerInv = player.getInventory();

            if (player.getTotalExperience() >= 17) {
                if (stack.getAmount() > 1) {
                    stack.setAmount(stack.getAmount() - 1);
                }
                else {
                    playerInv.removeItem(stack);
                    player.updateInventory();
                }
                player.setLevel(0); // Set everything to 0 to prevent doubling of EXP
                player.setExp(0);
                player.setTotalExperience(0);
                player.giveExp(playerNewExp);
                HashMap<Integer, ItemStack> map = playerInv.addItem(new ItemStack(Material.EXP_BOTTLE, 1));// Return XP if inventory was full
                player.updateInventory();
                if (!map.isEmpty()) {
                    player.sendMessage(ChatColor.DARK_RED + "You did not have enough inventory space to bottle the experience.");
                    player.giveExp(17);
                    stack.setAmount(stack.getAmount() + 1);
                    player.updateInventory();

                }
            }
            else {
                player.sendMessage(ChatColor.DARK_RED + "You do not have enough EXP!");

            }

        }
    }

}
