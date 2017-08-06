package com.plugin.ftb.enderdragonhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class MainListener implements Listener {

	private String prefix = Main.prefix;
	
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
			sendPickupMessage(itemName, player);
		}
		if(material.equals(Material.ENDER_PEARL)) {
			itemName = ChatColor.GREEN + "エンダーパール";
			sendPickupMessage(itemName, player);
		}
		if(material.equals(Material.EYE_OF_ENDER)) {
			itemName = ChatColor.GREEN + "エンダーアイ";
			sendPickupMessage(itemName, player);
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
			broadcast(prefix + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "が"
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
				broadcast(prefix + " " + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "が"
						+ ChatColor.DARK_GREEN + "エンドポータル" + ChatColor.RESET + "を開いた");
				broadcast(prefix + " 座標: " + event.getBlock().getLocation().getBlockX() + ", "
						+ event.getBlock().getLocation().getBlockY() + ", "
						+ event.getBlock().getLocation().getBlockZ() );
				return;
			}
		}
	}
	
	/*
	 * エンダーパールが投げられたとき、通知する
	 */
	@EventHandler
    public void onClick(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = event.getItem();
			if(item != null && item.getType().equals(Material.EYE_OF_ENDER)) {
				Material material = event.getClickedBlock().getType();
				if(material != null && material.equals(Material.ENDER_PORTAL_FRAME)) {
					//エンダーポータルフレームに置いているのであれば無視
					return;
				}
				broadcast(prefix + " " + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "が"
						+ ChatColor.GREEN + "エンダーアイ" + ChatColor.RESET + "を投げた");
			}
		}
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
					broadcast(prefix + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "が"
							+ ChatColor.LIGHT_PURPLE + "エンダークリスタル" + ChatColor.RESET + "を射貫いて破壊した");
					return;
				}
			}
			if(damager instanceof Player) {
				Player player = (Player)damager;
				broadcast(prefix + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "が"
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
			playerName =  ChatColor.BOLD + event.getEntity().getKiller().getName() + ChatColor.RESET + "が";
		}
		if(event.getEntity() instanceof EnderDragon) {
			broadcast(prefix +  " " + playerName + ChatColor.DARK_PURPLE + "エンダードラゴン" + ChatColor.RESET + "を倒した");
		}
	}
	
	
	/*
	 * 拾ったアイテムを通知
	 * @param itemName 拾ったアイテムの名前
	 * @param player 拾ったプレイヤー
	 */
	private void sendPickupMessage(String itemName, Player player) {
		broadcast(prefix + " " + ChatColor.BOLD + player.getName() + ChatColor.RESET + "が"
				+ itemName + ChatColor.RESET + "を手に入れた");
	}
	
	/*
	 * ブロードキャストの省略メソッドです。
	 * @param message ブロードキャストするメッセージ 
	 */
	public static void broadcast(String message) {
		Bukkit.broadcastMessage(message);
	}
}
