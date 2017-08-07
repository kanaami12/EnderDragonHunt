package com.plugin.ftb.enderdragonhunt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class MainTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> tab = new ArrayList<>();
		if (sender instanceof Player && args.length == 1) {
			if(label.equalsIgnoreCase("enderdragon")){
				tab.add("setHardmode");
				tab.add("setImmortal");
				tab.add("setAdmin");
				tab.add("removeImmortal");
				tab.add("removeAdmin");
			}
			return tab;//タブ補完
		}
		if (sender instanceof Player && args.length == 2) {
			if(label.equalsIgnoreCase("enderdragon")){
				if("setImmortal".equals(args[0]) || "setAdmin".equals(args[0]) || "removeImmortal".equals(args[0]) || "removeAdmin".equals(args[0])) {
					for(Player player : Bukkit.getOnlinePlayers()) {
						tab.add(player.getName());
					}
				}
				else if("setHardmode".equals(args[0])) {
					tab.add("true");
					tab.add("false");
				}
			}
			return tab;//タブ補完
		}

		return tab;
	}
}
