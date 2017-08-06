package com.plugin.ftb.enderdragonhunt;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	public static String prefix = ChatColor.GRAY + "[エンドラ討伐]" + ChatColor.RESET;
	public static Main plugin;
	
	public static boolean isHard = false;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		// イベントリスナ登録
		getServer().getPluginManager().registerEvents(new MainListener(), this);
		
		getCommand("enderdragon").setExecutor(new MainCommandExecutor(this));

		//タブ補完登録
		getCommand("enderdragon").setTabCompleter(new MainTabCompleter());
	}
}
