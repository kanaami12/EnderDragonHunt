
package com.plugin.ftb.enderdragonhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoard{

	public static Main plugin = Main.plugin;

	//スコアボード
	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	public static Scoreboard board = manager.getNewScoreboard();
	
	public static int higher;
	public static int lower;
	public static int ender;
	public static int nether;
	
	public static void setScoreHard(){
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
		
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "殉職者数  " + ChatColor.RESET + ":      " + str);
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":      " + higher);
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":      " + lower);
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人" + ChatColor.RESET + ":      " + nether);
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "エンドの人" + ChatColor.RESET + ":      " + ender);
		
		changeERs();
		
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "殉職者数  " + ChatColor.RESET + ":      " + Main.dcounter).setScore(4);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":      " + higher).setScore(3);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":      " + lower).setScore(2);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人" + ChatColor.RESET + ":      " + nether).setScore(1);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "エンドの人" + ChatColor.RESET + ":      " + ender).setScore(0);
		
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(board);
		}
	}
	
	public static void deleteScore(){
		Scoreboard teamBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();

		Objective o = board.getObjective("hardmode");
		if ( o != null ) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			}
			return;
		}
		o = board.getObjective("normalmode");
		if ( o != null ) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			}
			return;
		}
	}
	
	public static void changeERs() {
		higher = 0;
		lower = 0;
		ender = 0;
		nether = 0;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getLocation().getBlock().getBiome() == Biome.HELL) {
				nether = nether + 1;
			}
			else if(player.getLocation().getBlock().getBiome() == Biome.SKY) {
				ender = ender + 1;
			}
			else if(player.getLocation().getBlockY() <= 61) {
				lower = lower + 1;
			}
			else {
				higher = higher + 1;
			}
		}
	}

	public static void setScoreNormal() {
		Scoreboard teamBoard = plugin.getServer().getScoreboardManager().getMainScoreboard();

		Objective o = board.getObjective("normalmode");
		if ( o == null ) {
			o = board.registerNewObjective("normalmode", "dummy");
			// Objective の表示名を設定します。
			o.setDisplayName("" + ChatColor.BLUE + ChatColor.BOLD + "≫ Ender Dragon Hunt ≪");
			// Objectiveをどこに表示するかを設定します。
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":      " + higher);
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":      " + lower);
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人" + ChatColor.RESET + ":      " + nether);
		o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "エンドの人" + ChatColor.RESET + ":      " + ender);
		
		changeERs();
		
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":      " + higher).setScore(3);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":      " + lower).setScore(2);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人" + ChatColor.RESET + ":      " + nether).setScore(1);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "エンドの人" + ChatColor.RESET + ":      " + ender).setScore(0);
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(board);
		}
	}
	
	/*
	 * スコアボードをリロードする
	 */
	public static void reloadScoreboard() {
		if(Main.isHard) {
			Objective o = board.getObjective("hardmode");
			if(o != null) o.unregister();
		}else {
			Objective o = board.getObjective("normalmode");
			if(o != null) o.unregister();
		}
	}
}
