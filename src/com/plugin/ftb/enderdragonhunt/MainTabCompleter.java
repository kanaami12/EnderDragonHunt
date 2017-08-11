package com.plugin.ftb.enderdragonhunt;

import java.util.ArrayList;
import java.util.List;

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
			}
			return tab;//タブ補完
		}

		return tab;
	}
}
