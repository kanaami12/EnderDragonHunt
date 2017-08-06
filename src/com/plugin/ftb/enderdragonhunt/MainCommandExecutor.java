package com.plugin.ftb.enderdragonhunt;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommandExecutor implements CommandExecutor {
	
	public static Main _plugin = Main.plugin;
	
	public MainCommandExecutor(Main plugin) {
		MainCommandExecutor._plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		switch(args[0]){
		case "setHardmode":
			if("true".equals(args[1])) {
				Main.isHard = true;
				Bukkit.broadcastMessage(Main.prefix + "ハードコアモードは有効です");
			}
			else {
				Main.isHard = false;
				Bukkit.broadcastMessage(Main.prefix + "ハードコアモードは無効です");
			}
			
		}
		return true;
	}

}
