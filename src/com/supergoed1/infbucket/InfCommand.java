package com.supergoed1.infbucket;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfCommand implements CommandExecutor {

    public Main pl;

    public InfCommand(Main pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 1) {
            if(!pl.perms.has(sender, "infbucket.help")) {
                return true;
            }
            pl.displayHelpMessage(sender);
            return true;
        }
        String arg1 = args[0];
        if(arg1.equalsIgnoreCase("reload")) {
            if(!pl.perms.has(sender,"infbucket.reload")) {
                if(!pl.permsMessage.isEmpty()) sender.sendMessage(ChatColor.translateAlternateColorCodes('&',pl.permsMessage.replaceAll("%permission%","infbucket.reload")));
                return true;
            }
            pl.reload();
            sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
            return true;
        }
        if(arg1.equalsIgnoreCase("help")) {
            if(!pl.perms.has(sender,"infbucket.help")) {
                if(!pl.permsMessage.isEmpty()) sender.sendMessage(ChatColor.translateAlternateColorCodes('&',pl.permsMessage.replaceAll("%permission%","infbucket.help")));
                return true;
            }
            pl.displayHelpMessage(sender);
            return true;
        }
        if(arg1.equalsIgnoreCase("give")) {
            if(!pl.perms.has(sender,"infbucket.give")) {
                if(!pl.permsMessage.isEmpty()) sender.sendMessage(ChatColor.translateAlternateColorCodes('&',pl.permsMessage.replaceAll("%permission%","infbucket.give")));
                return true;
            }
            if(args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Please insert player name");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                sender.sendMessage(ChatColor.RED + "That player isn't online");
                return true;
            }
            if(args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Please insert bucket type: lava or water");
                return true;
            }
            if(args[2].equalsIgnoreCase("lava")) {
                target.getInventory().addItem(pl.getLavaBucket());
                return true;
            }
            if(args[2].equalsIgnoreCase("water")) {
                target.getInventory().addItem(pl.getWaterBucket());
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "Unknown arguments. Please try " + ChatColor.WHITE + "/infbucket help");
        return true;
    }
}
