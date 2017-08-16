package com.plugin.ftb.enderdragonhunt;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class MainCommandExecutor implements CommandExecutor {
	
	public static Main _plugin = Main.plugin;
	
	public MainCommandExecutor(Main plugin) {
		MainCommandExecutor._plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		switch(args[0]){
		case "setHardmode":
			if(args.length == 2) {
				if("true".equals(args[1])) {
					Main.isHard = true;
					Bukkit.broadcastMessage(Main.prefix + "ハードコアモードは有効です。");
				}
				else {
					Main.isHard = false;
					Bukkit.broadcastMessage(Main.prefix + "ハードコアモードは無効です。");
				}
				return true;
			}
			
		case "reload":
			//スコアボードをリロード
			if(args.length == 1) {
				ScoreBoard.reloadScoreboard();
				return true;
			}
			
		default:
			sender.sendMessage(ChatColor.GREEN + "/enderdragon setHardmode [true,false]\n"
					+ "/enderdragon reload");
			break;
		}
		return true;
	}

}
