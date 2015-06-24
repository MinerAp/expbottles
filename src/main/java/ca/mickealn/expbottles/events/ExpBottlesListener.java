package ca.mickealn.expbottles.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import ca.mickealn.expbottles.util.ExpBottlesConfigurationContext;

public final class ExpBottlesListener implements Listener {

    private final int expPerBottle;

    public ExpBottlesListener(ExpBottlesConfigurationContext configurationContext) {
        expPerBottle = configurationContext.expPerBottle;
    }

    @EventHandler
    public void onOpenEnhantTable(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player && InventoryType.ENCHANTING.equals(event.getInventory().getType()) && Material.GLASS_BOTTLE.equals(event.getPlayer().getItemInHand().getType())) {
            Player player = (Player) event.getPlayer();
            int originalLevel = player.getLevel();
            double originalExp;

            if (originalLevel >= 31) {
                originalExp = 4.5 * originalLevel * originalLevel - 162.5 * originalLevel + 2220;
                originalExp += player.getExp() * (9 * originalLevel - 158);
            } else if (originalLevel >= 16) {
                originalExp = 2.5 * originalLevel * originalLevel - 40.5 * originalLevel + 360;
                originalExp += player.getExp() * (5 * originalLevel - 38);
            } else {
                originalExp = originalLevel * originalLevel + 6 * originalLevel;
                originalExp += player.getExp() * (2 * originalLevel + 7);
            }

            originalExp = Math.round(originalExp);

            int bottleCount = player.getItemInHand().getAmount();
            int numEnchantedBottles = Math.min((int) (originalExp / expPerBottle), bottleCount);

            if (numEnchantedBottles > 0) {
                player.setLevel(0); // Set everything to 0 to prevent doubling of EXP
                player.setTotalExperience(0);

                int newTotalExperience = (int) (originalExp - numEnchantedBottles * expPerBottle);
                int newLevel;
                double experienceToCurrentLevel;

                if (newTotalExperience >= 1507) {
                    newLevel = (int) ((Math.sqrt(72 * newTotalExperience - 54215) + 325) / 18);
                    experienceToCurrentLevel = 4.5 * newLevel * newLevel - 162.5 * newLevel + 2220;
                } else if (newTotalExperience >= 352) {
                    newLevel = (int) ((Math.sqrt(40 * newTotalExperience - 7839) + 81) / 10);
                    experienceToCurrentLevel = 2.5 * newLevel * newLevel - 40.5 * newLevel + 360;
                } else {
                    newLevel = (int) (Math.sqrt(newTotalExperience + 9) - 3);
                    experienceToCurrentLevel = newLevel * newLevel + 6 * newLevel;
                }

                player.setTotalExperience(newTotalExperience);
                player.setLevel((int) newLevel);
                player.setExp((float) ((newTotalExperience - experienceToCurrentLevel) / player.getExpToLevel()));

                player.setItemInHand(new ItemStack(Material.EXP_BOTTLE, numEnchantedBottles));
                if (bottleCount > numEnchantedBottles) {
                    player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GLASS_BOTTLE, bottleCount - numEnchantedBottles));
                }
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExpEvent(ExpBottleEvent event) {
        event.setExperience(expPerBottle);
    }
}
