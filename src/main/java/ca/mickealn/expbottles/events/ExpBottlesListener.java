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
            int originalExp = Math.round(getExpToLevel(originalLevel) + getExpForProgressToNextLevel(player.getExp(), originalLevel));

            int bottleCount = player.getItemInHand().getAmount();
            int numEnchantedBottles = Math.min((int) (originalExp / expPerBottle), bottleCount);

            if (numEnchantedBottles > 0) {
                player.setLevel(0); // Set everything to 0 to prevent doubling of EXP
                player.setTotalExperience(0);

                int newTotalExperience = (int) (originalExp - numEnchantedBottles * expPerBottle);
                int newLevel = getLevelFromExp(newTotalExperience);
                int experienceToCurrentLevel = Math.round(getExpToLevel(newLevel));

                player.setTotalExperience(newTotalExperience);
                player.setLevel((int) newLevel);
                player.setExp(((newTotalExperience - experienceToCurrentLevel) / (float) player.getExpToLevel()));

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

    private float getExpToLevel(int level) {
        if (level >= 31) {
            return 4.5f * level * level - 162.5f * level + 2220f;
        } else if (level >= 16) {
            return 2.5f * level * level - 40.5f * level + 360f;
        } else {
            return level * level + 6f * level;
        }
    }

    private float getExpForProgressToNextLevel(float progress, int originalLevel) {
        if (originalLevel >= 31) {
            return progress * (9 * originalLevel - 158);
        } else if (originalLevel >= 16) {
            return progress * (5 * originalLevel - 38);
        } else {
            return progress * (2 * originalLevel + 7);
        }
    }

    private int getLevelFromExp(int exp) {
        if (exp >= 1507) {
            return (int) ((Math.sqrt(72 * exp - 54215) + 325) / 18);
        } else if (exp >= 352) {
            return (int) ((Math.sqrt(40 * exp - 7839) + 81) / 10);
        } else {
            return (int) (Math.sqrt(exp + 9) - 3);
        }
    }
}
