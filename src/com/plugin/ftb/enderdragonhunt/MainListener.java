package com.plugin.ftb.enderdragonhunt;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MainListener implements Listener {

	private Main plugin = Main.plugin;
	
	//private String prefix = Main.prefix;
	//火打石を使ったプレイヤー
	private ArrayList<Player> firedPlayer = new ArrayList<>();
	
	/*
	 * アイテムを拾ったとき、プレイヤーネームとアイテムを表示
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		Material material = event.getItem().getItemStack().getType();
		String itemName = "";
		
		if(material.equals(Material.BLAZE_ROD)) {
			itemName = ChatColor.YELLOW + "ブレイズロッド";
			sendPickupMessage("[" + ChatColor.YELLOW + "ブレイズ" + ChatColor.RESET + "]", itemName, player);
		}
		if(material.equals(Material.ENDER_PEARL)) {
			itemName = ChatColor.GREEN + "エンダーパール";
			sendPickupMessage("[" + ChatColor.GREEN + "エンパ" + ChatColor.RESET + "]", itemName, player);
		}
		if(material.equals(Material.EYE_OF_ENDER)) {
			itemName = ChatColor.GREEN + "エンダーアイ";
			sendPickupMessage("[" + ChatColor.GREEN + "アイ" + ChatColor.RESET + "]", itemName, player);
		}
	}
	
	/*
	 * アイテムを作ったとき、プレイヤーネームとアイテムを表示
	 */
	@EventHandler
	public void onCreateItem(CraftItemEvent event) {
		Player player = (Player) event.getWhoClicked();
		Material material = event.getCurrentItem().getType();
		String itemName = "";
		
		if(material.equals(Material.EYE_OF_ENDER)) {
			itemName = ChatColor.GREEN + "エンダーアイ";
			broadcast("[" + ChatColor.GREEN + "アイ" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "さんが"
					+ itemName + ChatColor.RESET + "を作成した");
		}
	}
	
	/*
	 * エンドポータルが開かれるとき、
	 * プレイヤーネームと座標を表示
	 */
	@EventHandler
	public void onPortal(BlockMultiPlaceEvent event) {
		for(BlockState blockState : event.getReplacedBlockStates()) {
			if(blockState.getBlock().getType().equals(Material.ENDER_PORTAL)) {
				broadcast("[" + ChatColor.DARK_GREEN + "ポータル" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "さんが"
						+ ChatColor.DARK_GREEN + "エンドポータル" + ChatColor.RESET + "を開いた");
				broadcast("[" + ChatColor.DARK_GREEN + "ポータル" + ChatColor.RESET + "]" + " 座標: " + event.getBlock().getLocation().getBlockX() + ", "
						+ event.getBlock().getLocation().getBlockY() + ", "
						+ event.getBlock().getLocation().getBlockZ() );
				return;
			}
		}
	}
	
	/*
	 * エンダーパールが投げられたとき、ネザーポータルが作られたとき通知する
	 */
	@EventHandler
    public void onClick(PlayerInteractEvent event) {
		//エンダーアイが投げられたとき
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = event.getItem();
			if(item != null && item.getType().equals(Material.EYE_OF_ENDER)) {
				if(event.getClickedBlock() != null) {
					Material material = event.getClickedBlock().getType();
					if(material != null && material.equals(Material.ENDER_PORTAL_FRAME)) {
						//エンダーポータルフレームに置いているのであれば無視
						return;
					}
				}
				broadcast("[" + ChatColor.GREEN + "アイ" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "さんが"
						+ ChatColor.GREEN + "エンダーアイ" + ChatColor.RESET + "を投げた");
			}
		}
		
		//火打石をもって黒曜石を右クリックしたプレイヤーを記録(2tick後に削除)
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = event.getItem();
			if(item != null && item.getType().equals(Material.FLINT_AND_STEEL)) {
				Material material = event.getClickedBlock().getType();
				if(material != null && material.equals(Material.OBSIDIAN)) {
					//火打石をもって黒曜石を右クリックしたとき
					firedPlayer.add(event.getPlayer());
					new BukkitRunnable() {
			            @Override
			            public void run() {
			            	//2tick後に削除
			                firedPlayer.remove(event.getPlayer());
			            }
			        }.runTaskLater(plugin, 2);
					return;
				}
			}
		}
	}
	
	/*
	 * 火打石をもって黒曜石を右クリックしたプレイヤーがポータルを作ったと推定
	 */
	@EventHandler
    public void onPortalCreate(PortalCreateEvent event){
		if(!firedPlayer.isEmpty()) {
			for(Block block : event.getBlocks()) {
				if(block.getType().equals(Material.OBSIDIAN)) {
					broadcast("[" + ChatColor.DARK_RED + "ネザー" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + firedPlayer.get(firedPlayer.size()-1).getName() + ChatColor.RESET + "さんが"
							+ ChatColor.DARK_RED + "ネザーポータル" + ChatColor.RESET + "を開いた");
					broadcast("[" + ChatColor.DARK_RED + "ネザー" + ChatColor.RESET + "]"  + " 座標: " + block.getLocation().getBlockX() + ", "
							+ block.getLocation().getBlockY() + ", "
							+ block.getLocation().getBlockZ() );
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onC(BlockPlaceEvent event) {
	}
	
	/*
	 * エンダークリスタルが爆発したとき通知する
	 */
	@EventHandler
	public void onExplode(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		if(entity.getType().equals(EntityType.ENDER_CRYSTAL)) {
			Entity damager = event.getDamager();
			if(damager.getType().equals(EntityType.ARROW)) {
				Arrow arrow = (Arrow)damager;
				if(arrow.getShooter() instanceof Player) {
					//矢によって爆発した場合
					Player player = (Player)arrow.getShooter();
					broadcast("[" + ChatColor.LIGHT_PURPLE + "クリスタル" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "さんが"
							+ ChatColor.LIGHT_PURPLE + "エンダークリスタル" + ChatColor.RESET + "を射貫いて破壊した");
					return;
				}
			}
			if(damager instanceof Player) {
				Player player = (Player)damager;
				broadcast("[" + ChatColor.LIGHT_PURPLE + "クリスタル" + ChatColor.RESET + "]" + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "さんが"
						+ ChatColor.LIGHT_PURPLE + "エンダークリスタル" + ChatColor.RESET + "を破壊した");
				return;
			}
		}
	}
	
	/*
	 * エンダードラゴンが倒されたとき、通知する
	 */
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		String playerName = "";
		if(event.getEntity().getKiller() != null) {
			playerName =  ChatColor.BOLD + event.getEntity().getKiller().getName() + ChatColor.RESET + "さんが";
		}
		if(event.getEntity() instanceof EnderDragon) {
			broadcast("[" + ChatColor.DARK_PURPLE + "おめでとう！" + ChatColor.RESET + "]"  +  " " + playerName + ChatColor.DARK_PURPLE + "エンダードラゴン" + ChatColor.RESET + "を倒した");
		}
	}
	
	
	/*
	 * 拾ったアイテムを通知
	 * @param itemName 拾ったアイテムの名前
	 * @param player 拾ったプレイヤー
	 */
	private void sendPickupMessage(String prefix, String itemName, Player player) {
		broadcast(prefix + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "さんが"
				+ itemName + ChatColor.RESET + "を手に入れた");
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		broadcast(event.getPlayer().getLocation().getBlock().getBiome() + "");
	}
	
	/*
	 * ブロードキャストの省略メソッドです。
	 * @param message ブロードキャストするメッセージ 
	 */
	public static void broadcast(String message) {
		Bukkit.broadcastMessage(message);
	}
}
