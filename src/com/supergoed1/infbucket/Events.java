package com.supergoed1.infbucket;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    private final Main pl;

    public Events(Main pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        if(item.hasItemMeta()) {
            if(item.getItemMeta().hasCustomModelData()) {
                if(item.getItemMeta().getCustomModelData() == 1) {
                    if(item.getType() == Material.LAVA_BUCKET) {
                        if(!pl.perms.has(e.getPlayer(),"infbucket.use.lava")) {
                            if(!pl.permsMessage.isEmpty()) e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',pl.permsMessage.replaceAll("%permission%","infbucket.use.lava")));
                            e.setCancelled(true);
                            return;
                        }
                        if(pl.lavaCost <= 0) {
                            e.getBlock().setType(Material.LAVA);
                            e.setCancelled(true);
                            return;
                        }
                        if(!pl.econ.has(pl.getOfflinePlayer(p),pl.lavaCost)) {
                            if(!pl.notEnoughMoney.isEmpty()) p.sendMessage(ChatColor.translateAlternateColorCodes('&',pl.notEnoughMoney));
                            e.setCancelled(true);
                            return;
                        }
                        pl.econ.withdrawPlayer(pl.getOfflinePlayer(p),pl.lavaCost);
                        if(!pl.subtractMoney.isEmpty()) p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.subtractMoney.replaceAll("%cost%", pl.lavaCost+"")));
                        e.getBlock().setType(Material.LAVA);
                        e.setCancelled(true);
                        return;
                    }
                    if(item.getType() == Material.WATER_BUCKET) {
                        if(!pl.perms.has(e.getPlayer(),"infbucket.use.water")) {
                            if(!pl.permsMessage.isEmpty()) e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',pl.permsMessage.replaceAll("%permission%","infbucket.use.water")));
                            e.setCancelled(true);
                            return;
                        }
                        if(pl.waterCost <= 0) {
                            e.getBlock().setType(Material.WATER);
                            e.setCancelled(true);
                            return;
                        }
                        if(!pl.econ.has(pl.getOfflinePlayer(p),pl.waterCost)) {
                            if(!pl.notEnoughMoney.isEmpty()) p.sendMessage(ChatColor.translateAlternateColorCodes('&',pl.notEnoughMoney));
                            e.setCancelled(true);
                            return;
                        }
                        pl.econ.withdrawPlayer(pl.getOfflinePlayer(p),pl.waterCost);
                        if(!pl.subtractMoney.isEmpty()) p.sendMessage(ChatColor.translateAlternateColorCodes('&', pl.subtractMoney.replaceAll("%cost%", pl.waterCost+"")));
                        e.getBlock().setType(Material.WATER);
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHoldItem(PlayerItemHeldEvent e) {
        ItemStack item = e.getPlayer().getInventory().getItem(e.getNewSlot());
        if(item == null) return;
        if(item.hasItemMeta()) {
            if(item.getItemMeta().hasCustomModelData()) {
                if(item.getItemMeta().getCustomModelData() == 1) {
                    if(item.getType() == Material.LAVA_BUCKET) {
                        if(!item.getItemMeta().equals(pl.getLavaBucket().getItemMeta())) {
                            item.setItemMeta(pl.getLavaBucket().getItemMeta());
                        }
                        return;
                    }
                    if(item.getType() == Material.WATER_BUCKET) {
                        if(!item.getItemMeta().equals(pl.getWaterBucket().getItemMeta())) {
                            item.setItemMeta(pl.getWaterBucket().getItemMeta());
                        }
                    }
                }
            }
        }
    }
}
