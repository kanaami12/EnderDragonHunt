package com.plugin.ftb.enderdragonhunt;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommandExecutor implements CommandExecutor {
	
	public static Main _plugin = Main.plugin;
	
	public MainCommandExecutor(Main plugin) {
		MainCommandExecutor._plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(args.length == 2)) {
			sender.sendMessage(ChatColor.GREEN + "/enderdragon setHardmode [true,false]");
			return true;
		}
		
		switch(args[0]){
		case "setHardmode":
			if("true".equals(args[1])) {
				Main.isHard = true;
				Bukkit.broadcastMessage(Main.prefix + "ハードコアモードは有効です。");
				ScoreBoard.setScore();
			}
			else {
				Main.isHard = false;
				Bukkit.broadcastMessage(Main.prefix + "ハードコアモードは無効です。");
				ScoreBoard.deleteScore();
			}
			return true;
		case "setImmortal":
			if(sender instanceof Player) {
				Player player = (Player)sender;
				if(Bukkit.getPlayer(args[1]) == null ) {
					player.sendMessage("プレイヤー "  + args[1] + " は存在しません。");
				}
				else {
					UUID target = Bukkit.getPlayer(args[1]).getUniqueId();
					if(!Main.immortal.contains(target)) {
						Main.immortal.add(target);
						player.sendMessage("プレイヤー "  + args[1] + " をImmortalにしました。");
					}
					else {
						player.sendMessage("すでに追加されています。");
					}
				}
			}
			return true;
		case "setAdmin":
			if(sender instanceof Player) {
				Player player = (Player)sender;
				if(Bukkit.getPlayer(args[1]) == null ) {
					player.sendMessage("プレイヤー "  + args[1] + " は存在しません。");
				}
				else {
					UUID target = Bukkit.getPlayer(args[1]).getUniqueId();
					if(!Main.admin.contains(target)) {
						Main.admin.add(target);
						player.sendMessage("プレイヤー "  + args[1] + " をAdminにしました。");
					}
					else {
						player.sendMessage("すでに追加されています。");
					}
				}
			}
			return true;
		case "removeImmortal":
			if(sender instanceof Player) {
				Player player = (Player)sender;
				if(Bukkit.getPlayer(args[1]) == null ) {
					player.sendMessage("プレイヤー "  + args[1] + " は存在しません。");
				}
				else {
					UUID target = Bukkit.getPlayer(args[1]).getUniqueId();
					if(Main.immortal.contains(target)) {
						Main.immortal.remove(target);
						player.sendMessage("プレイヤー "  + args[1] + " をMortalにしました。");
					}
					else {
						player.sendMessage("追加されていません。");
					}
				}
			}
			return true;
		case "removeAdmin":
			if(sender instanceof Player) {
				Player player = (Player)sender;
				if(Bukkit.getPlayer(args[1]) == null ) {
					player.sendMessage("プレイヤー "  + args[1] + " は存在しません。");
				}
				else {
					UUID target = Bukkit.getPlayer(args[1]).getUniqueId();
					if(Main.admin.contains(target)) {
						Main.admin.remove(target);
						player.sendMessage("プレイヤー "  + args[1] + " からAdminを剥奪しました。");
					}
					else {
						player.sendMessage("追加されていません。");
					}
				}
			}
		}
		return true;
	}

}
