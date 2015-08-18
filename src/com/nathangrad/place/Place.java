package com.nathangrad.place;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Place extends JavaPlugin implements Listener {
	public void onEnable() {
		getLogger().info("PlaceDisable enabled.");
		getServer().getPluginManager().registerEvents(this, this);
		saveConfig();
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("disable")) {
			if (sender.hasPermission("placedisable.disable")) {
				Player p = (Player) sender;
				getConfig().set("disable." + p.getItemInHand().getTypeId(), "deny");
				p.sendMessage(ChatColor.GREEN + "Item has been disabled.");
				saveConfig();
			}
			if (!sender.hasPermission("placedisable.disable")) {
				sender.sendMessage(ChatColor.RED + "You cannot perform this command.");
			}
			return true;
		}
		return false;
	}
	@EventHandler
	public void onDisableNeed(BlockPlaceEvent e) {
		Player p = (Player) e.getPlayer();
		if (getConfig().contains("disable." + e.getBlock().getTypeId())) {
			if (!p.hasPermission("placedisable.bypass")) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You may not place this item.");
				p.sendMessage(ChatColor.RED + "Please contact an Administrator if you believe that this is an error.");
			}
			
		}
	}
}