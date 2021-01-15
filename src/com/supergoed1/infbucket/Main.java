package com.supergoed1.infbucket;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public double lavaCost;
    public double waterCost;
    public String lavaDisplayName;
    public String waterDisplayName;
    public String notEnoughMoney;
    public String subtractMoney;
    public String waterLore;
    public String lavaLore;
    public String permsMessage;
    public boolean enchantGlint;

    public Economy econ;
    public Permission perms;

    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();

        getServer().getPluginManager().registerEvents(new Events(this), this);
        getCommand("infbucket").setExecutor(new InfCommand(this));

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        permsMessage = getConfig().getString("notEnoughPermissions");

        lavaCost = getConfig().getDouble("lavaCost");
        waterCost = getConfig().getDouble("waterCost");

        lavaDisplayName = getConfig().getString("lavaDisplayName");
        waterDisplayName = getConfig().getString("waterDisplayName");
        subtractMoney = getConfig().getString("subtractMoney");
        notEnoughMoney = getConfig().getString("notEnoughMoney");
        lavaLore = getConfig().getString("lavaLore");
        waterLore = getConfig().getString("waterLore");
        enchantGlint = getConfig().getBoolean("enchantGlint");
    }

    public OfflinePlayer getOfflinePlayer(Player p) {
        return Bukkit.getOfflinePlayer(p.getUniqueId());
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public void displayHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "------------" + ChatColor.GOLD + "Infinite Buckets" + ChatColor.AQUA + "------------");
        sender.sendMessage(ChatColor.WHITE + "/infbucket help" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Shows this message");
        sender.sendMessage(ChatColor.WHITE + "/infbucket reload" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Reloads the configuration");
        sender.sendMessage(ChatColor.WHITE + "/infbucket give <playername> <water|lava>" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Gives said player a infinite bucket");
        sender.sendMessage(ChatColor.AQUA + "-------------------------------------");
    }

    public void reload() {
        reloadConfig();
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        permsMessage = getConfig().getString("notEnoughPermissions");
        lavaCost = getConfig().getDouble("lavaCost");
        waterCost = getConfig().getDouble("waterCost");

        lavaDisplayName = getConfig().getString("lavaDisplayName");
        waterDisplayName = getConfig().getString("waterDisplayName");
        subtractMoney = getConfig().getString("subtractMoney");
        notEnoughMoney = getConfig().getString("notEnoughMoney");
        lavaLore = getConfig().getString("lavaLore");
        waterLore = getConfig().getString("waterLore");
        enchantGlint = getConfig().getBoolean("enchantGlint");
    }

    public ItemStack getWaterBucket() {
        ItemStack stack = new ItemStack(Material.WATER_BUCKET);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', waterDisplayName));
        meta.setCustomModelData(1);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if(!waterLore.isEmpty()) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', waterLore.replaceAll("%cost%", waterCost + "")));
            meta.setLore(lore);
        }
        stack.setItemMeta(meta);
        if(enchantGlint) stack.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        return stack;
    }

    public ItemStack getLavaBucket() {
        ItemStack stack = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', lavaDisplayName));
        meta.setCustomModelData(1);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if(!lavaLore.isEmpty()) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.translateAlternateColorCodes('&', lavaLore.replaceAll("%cost%", lavaCost + "")));
            meta.setLore(lore);
        }
        stack.setItemMeta(meta);
        if(enchantGlint) stack.addUnsafeEnchantment(Enchantment.DURABILITY,1);
        return stack;
    }
}
