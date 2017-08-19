
package com.plugin.ftb.enderdragonhunt;

import java.util.LinkedHashMap;

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
	
	public static int pastDcounter = 0;
	public static int higher;
	public static int lower;
	public static int ender;
	public static int nether;
	
	//全プレイヤーにスコアボードを付与
	public static void setScoreboardToEveryone() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			setScoreboard(player);
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

	//スコアボードをプレイヤーに対して登録する
	public static void setScoreboard(Player player) {
		Scoreboard board = plugin.getServer().getScoreboardManager().getNewScoreboard();

		Objective o = board.getObjective("object");
		if ( o == null ) {
			o = board.registerNewObjective("object", "dummy");
			// Objective の表示名を設定します。
			o.setDisplayName("" + ChatColor.BLUE + ChatColor.BOLD + "≫ Ender Dragon Hunt ≪");
			// Objectiveをどこに表示するかを設定します。
			o.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		
		if(Main.isHard) o.getScore("" + ChatColor.RED + ChatColor.BOLD + "殉職者数  " + ChatColor.RESET + ":      " + Main.dcounter).setScore(-1);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":      " + higher).setScore(-2);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":      " + lower).setScore(-3);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人" + ChatColor.RESET + ":      " + nether).setScore(-4);
		o.getScore("" + ChatColor.RED + ChatColor.BOLD + "エンドの人" + ChatColor.RESET + ":      " + ender).setScore(-5);
		
		board.resetScores("" + ChatColor.WHITE + ChatColor.BOLD + player.getName() + ":");
		player.setScoreboard(board);
	}
	
	//スコアをアップデート
	public static void updateScores() {
		
		//過去のスコアを記録
		LinkedHashMap<String, Integer> pastTopList = MainUtils.topList;
		int pastHigher = higher;
		int pastLower = lower;
		int pastNether = nether;
		int pastEnder = ender;
		
		//最新の情報に更新
		MainUtils.updateTopList();
		changeERs();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			
			Scoreboard personalBoard = player.getScoreboard();
	
			Objective o = personalBoard.getObjective("object");
			if ( o == null ) {
				o = personalBoard.registerNewObjective("object", "dummy");
				// Objective の表示名を設定します。
				o.setDisplayName("" + ChatColor.BLUE + ChatColor.BOLD + "≫ Ender Dragon Hunt ≪");
				// Objectiveをどこに表示するかを設定します。
				o.setDisplaySlot(DisplaySlot.SIDEBAR);
			}
			
			if(Main.isHard) o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "殉職者数  " + ChatColor.RESET + ":      " + pastDcounter);
			o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":      " + pastHigher);
			o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":      " + pastLower);
			o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人" + ChatColor.RESET + ":      " + pastNether);
			o.getScoreboard().resetScores("" + ChatColor.RED + ChatColor.BOLD + "エンドの人" + ChatColor.RESET + ":      " + pastEnder);
			
			if(Main.isHard) o.getScore("" + ChatColor.RED + ChatColor.BOLD + "殉職者数  " + ChatColor.RESET + ":      " + Main.dcounter).setScore(-1);
			o.getScore("" + ChatColor.RED + ChatColor.BOLD + "地上の人  " + ChatColor.RESET + ":      " + higher).setScore(-2);
			o.getScore("" + ChatColor.RED + ChatColor.BOLD + "地下の人  " + ChatColor.RESET + ":      " + lower).setScore(-3);
			o.getScore("" + ChatColor.RED + ChatColor.BOLD + "ネザーの人" + ChatColor.RESET + ":      " + nether).setScore(-4);
			o.getScore("" + ChatColor.RED + ChatColor.BOLD + "エンドの人" + ChatColor.RESET + ":      " + ender).setScore(-5);
			
			//過去のスコアを削除
			for(String name : pastTopList.keySet()) {
				personalBoard.resetScores("" + ChatColor.GREEN + ChatColor.BOLD + name + ":");
			}
			
			//新しいスコアを登録
			for(String name : MainUtils.topList.keySet()) {
				o.getScore("" + ChatColor.GREEN + ChatColor.BOLD + name + ":").setScore(MainUtils.topList.get(name));
			}
			
			//トップ10に含まれていない場合、自分のスコアを表示
			if(!MainUtils.topList.containsKey(player.getName())) {
				if(!MainUtils.pointList.containsKey(player.getName())) {
					//値がない場合追加
					MainUtils.pointList.put(player.getName(), 0);
				}
				o.getScore("" + ChatColor.WHITE + ChatColor.BOLD + player.getName() + ":").setScore(MainUtils.pointList.get(player.getName()));
			}else {
				//含まれている場合、個人のスコアを非表示
				personalBoard.resetScores("" + ChatColor.WHITE + ChatColor.BOLD + player.getName() + ":");
			}
			player.setScoreboard(personalBoard);
		}
		
		//過去のスコアを記録
		pastDcounter = Main.dcounter;
	}
	
	/*
	 * スコアボードをリロードする
	 */
	public static void reloadScoreboard() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.getScoreboard().getObjective("object").unregister();
		}
	}
}
