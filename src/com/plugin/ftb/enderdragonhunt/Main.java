package com.plugin.ftb.enderdragonhunt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	
	public static String prefix = ChatColor.GRAY + "[エンドラ討伐]" + ChatColor.RESET;
	public static Main plugin;
	
	public static boolean isHard = false;
	
	public static int dcounter = 0;
	
	public static List<UUID> ban = new ArrayList<>();
	
	public static List<UUID> admin = new ArrayList<>();
	
	public static List<UUID> immortal = new ArrayList<>();
	
	public static Map<Player, Location> desart = new HashMap<>();
	public static Map<Player, Location> endpotal = new HashMap<>();
	public static Map<Player, Location> netheryousai = new HashMap<>();
	public static Map<Player, Location> netherportal = new HashMap<>();
	
	@Override
	public void onEnable() {
		plugin = this;
		
		// イベントリスナ登録
		getServer().getPluginManager().registerEvents(new MainListener(), this);
		
		getCommand("enderdragon").setExecutor(new MainCommandExecutor(this));
		getCommand("ftb28").setExecutor(new FutabaCommand(this));
		
		getCommand("desert").setExecutor(new DesertCommandExecutor(this));
		getCommand("nether").setExecutor(new NetherCommandExecutor(this));
		getCommand("portal").setExecutor(new PortalCommandExecutor(this));
		getCommand("end").setExecutor(new EndCommandExecutor(this));

		//タブ補完登録
		getCommand("enderdragon").setTabCompleter(new MainTabCompleter());
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		ScoreBoard.setScoreboardToEveryone();
		
		//クラス作成がめんどくさかったので直接入れちゃいました
		//もしクラス分けするときは消してください！by える
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				ScoreBoard.updateScores();
				
				for (Player p : Bukkit.getOnlinePlayers()) {
					Biome biome = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
					if(biome == Biome.DESERT) {
						if(!Main.desart.containsKey(p)) {
							p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
							int x1 = p.getLocation().getBlockX();
							int y1 = p.getLocation().getBlockY();
							int z1 = p.getLocation().getBlockZ();
							Bukkit.broadcastMessage("[" + ChatColor.GOLD + "砂漠" + ChatColor.RESET
									+ "]" + " " + ChatColor.BOLD + p.getName() + ChatColor.RESET + "さんが"
									+ ChatColor.GOLD + "砂漠" + ChatColor.RESET + "を見つけた(座標: " + x1
									+ ", " + y1 + ", " + z1 + ")");
							Main.desart.put(p, new Location(p.getWorld(), x1, y1, z1));
						}
					}
					if (biome == Biome.HELL) {
						if(MainListener.netherFinders.contains(p.getUniqueId())){
							//既に発見している場合無視
							break;
						}
						Location loc = p.getLocation();
						for (int x = 0; x <= 6; x += 1) {
							for (int y = 0; y <= 6; y += 1) {
								for (int z = 0; z <= 6; z += 1) {
									Location newloc = new Location(p.getWorld(), loc.getX() + (x - 3),
											loc.getY() + (y - 3), loc.getZ() + (z - 3));
									Block b = newloc.getBlock();
									Material mat = b.getType();
									if (mat == Material.NETHER_BRICK) {
										if (!MainListener.netherFinders.contains(p.getUniqueId())) {
											MainListener.netherFinders.add(p.getUniqueId());
											p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
											int x1 = p.getLocation().getBlockX();
											int y1 = p.getLocation().getBlockY();
											int z1 = p.getLocation().getBlockZ();
											Bukkit.broadcastMessage("[" + ChatColor.DARK_RED + "ネザー" + ChatColor.RESET
													+ "]" + " " + ChatColor.BOLD + p.getName() + ChatColor.RESET + "さんが"
													+ ChatColor.DARK_RED + "ネザー要塞" + ChatColor.RESET + "を見つけた(座標: " + x1
													+ ", " + y1 + ", " + z1 + ")");
											Main.netheryousai.put(p, new Location(p.getWorld(), x1, y1, z1));
											
											if(!MainListener.netherFound) {
												//初めて発見されたあればポイント追加
												MainUtils.addPoint(p, 30);
												//発見されたことを記録
												MainListener.netherFound = true;
											}
										}

									}
								}
							}
						}
					}else{
						if(MainListener.endFinders.contains(p.getUniqueId())){
							//既に発見している場合無視
							break;
						}
						Location loc = p.getLocation();
						for (int x = 0; x <= 6; x += 1) {
							for (int y = 0; y <= 6; y += 1) {
								for (int z = 0; z <= 6; z += 1) {
									Location newloc = new Location(p.getWorld(), loc.getX() + (x - 3),
											loc.getY() + (y - 3), loc.getZ() + (z - 3));
									Block b = newloc.getBlock();
									Material mat = b.getType();
									if (mat == Material.ENDER_PORTAL_FRAME) {
										if (!MainListener.endFinders.contains(p.getUniqueId())) {
											MainListener.endFinders.add(p.getUniqueId());
											p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
											int x1 = p.getLocation().getBlockX();
											int y1 = p.getLocation().getBlockY();
											int z1 = p.getLocation().getBlockZ();
											Bukkit.broadcastMessage(
													"[" + ChatColor.DARK_GREEN + "ポータル" + ChatColor.RESET + "]" + " "
															+ ChatColor.BOLD + p.getName() + ChatColor.RESET + "さんが"
															+ ChatColor.DARK_GREEN + "エンドポータル" + ChatColor.RESET
															+ "を見つけた(座標: " + x1 + ", " + y1 + ", " + z1 + ")");
											Main.endpotal.put(p, new Location(p.getWorld(), x1, y1, z1));
											
											if(!MainListener.endFound) {
												//初めて発見されたあればポイント追加
												MainUtils.addPoint(p, 30);
												//発見されたことを記録
												MainListener.endFound = true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}, 0L, 100L);
	}
}
