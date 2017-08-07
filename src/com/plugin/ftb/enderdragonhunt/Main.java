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
		
		//クラス作成がめんどくさかったので直接入れちゃいました
		//もしクラス分けするときは消してください！by える
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					Biome biome = p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
					if (biome == Biome.HELL && MainListener.cnetheryousai == false) {
						Location loc = p.getLocation();
						for (int x = 0; x <= 6; x += 2) {
							for (int y = 0; y <= 6; y += 2) {
								for (int z = 0; z <= 6; z += 2) {
									Location newloc = new Location(p.getWorld(), loc.getX() + (x - 3),
											loc.getY() + (y - 3), loc.getZ() + (z - 3));
									Block b = newloc.getBlock();
									Material mat = b.getType();
									if (mat == Material.NETHER_BRICK) {
										if (!MainListener.cnetheryousai) {
											MainListener.cnetheryousai = true;
											p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
											int x1 = p.getLocation().getBlockX();
											int y1 = p.getLocation().getBlockY();
											int z1 = p.getLocation().getBlockZ();
											Bukkit.broadcastMessage("[" + ChatColor.DARK_RED + "ネザー" + ChatColor.RESET
													+ "]" + " " + ChatColor.BOLD + p.getName() + ChatColor.RESET + "さんが"
													+ ChatColor.DARK_RED + "ネザー要塞" + ChatColor.RESET + "を見つけた(座標: " + x1
													+ ", " + y1 + ", " + z1 + ")");
										}

									}
								}
							}
						}
					} else if (biome != Biome.HELL && MainListener.cendpotal == false) {
						Location loc = p.getLocation();
						for (int x = 0; x <= 6; x += 2) {
							for (int y = 0; y <= 6; y += 2) {
								for (int z = 0; z <= 6; z += 2) {
									Location newloc = new Location(p.getWorld(), loc.getX() + (x - 3),
											loc.getY() + (y - 3), loc.getZ() + (z - 3));
									Block b = newloc.getBlock();
									Material mat = b.getType();
									if (mat == Material.ENDER_PORTAL_FRAME) {
										if (!MainListener.cendpotal) {
											MainListener.cendpotal = true;
											p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
											int x1 = p.getLocation().getBlockX();
											int y1 = p.getLocation().getBlockY();
											int z1 = p.getLocation().getBlockZ();
											Bukkit.broadcastMessage(
													"[" + ChatColor.DARK_GREEN + "ポータル" + ChatColor.RESET + "]" + " "
															+ ChatColor.BOLD + p.getName() + ChatColor.RESET + "さんが"
															+ ChatColor.DARK_GREEN + "エンドポータル" + ChatColor.RESET
															+ "を見つけた(座標: " + x1 + ", " + y1 + ", " + z1 + ")");
										}
									}
								}
							}
						}
					}
				}
			}
		}, 0L, 60L);
	}
}
