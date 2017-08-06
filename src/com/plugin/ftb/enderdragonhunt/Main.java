package com.plugin.ftb.enderdragonhunt;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	public static String prefix = ChatColor.GRAY + "[エンドラ討伐]" + ChatColor.RESET;
	
	@Override
	public void onEnable() {
		// イベントリスナ登録
		getServer().getPluginManager().registerEvents(new MainListener(), this);
	}
}
