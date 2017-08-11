package com.plugin.ftb.enderdragonhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoard {

	public static Main plugin = Main.plugin;

	//スコアボード
	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	public static Scoreboard board = manager.getNewScoreboard();

	public static void setScore(){
		Scoreboard teamBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();

		Objective o = board.getObjective("hardmode");
		if ( o == null ) {
			o = board.registerNewObjective("hardmode", "dummy");
			// Objective の表示名を設定します。
			o.setDisplayName("" + ChatColor.BLUE + ChatColor.BOLD + "≫ Ender Dragon Hunt ≪");
			// Objectiveをどこに表示するかを設定します。
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
		}

		//スコアボードの中身
		String str = (Main.dcounter - 1) + "";
		
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "殉職者数" + ChatColor.RESET + ":      " + str);
		
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "殉職者数" + ChatColor.RESET + ":      " + Main.dcounter).setScore(0);
		
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(board);
		}
	}
	
	public static void deleteScore(){
		Scoreboard teamBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();

		Objective o = board.getObjective("hardmode");
		if ( o == null ) {
			return;
		}
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		}
	}
}