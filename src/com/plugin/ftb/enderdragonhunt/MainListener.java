package com.plugin.ftb.enderdragonhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
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
	 * エンダーポータルが開かれるとき、
	 * プレイヤーネームと座標を表示
	 */
	@EventHandler
	public void onPortal(BlockMultiPlaceEvent event) {
		for(BlockState blockState : event.getReplacedBlockStates()) {
			broadcast(blockState.getBlock().getType() + "");
			if(blockState.getBlock().getType().equals(Material.ENDER_PORTAL)) {
				broadcast(prefix + " " + ChatColor.BOLD + event.getPlayer().getName() + ChatColor.RESET + "が"
						+ ChatColor.DARK_GREEN + "エンダーポータル" + ChatColor.RESET + "を開いた");
				broadcast(prefix + " 座標: " + event.getBlock().getLocation().getBlockX() + ", "
						+ event.getBlock().getLocation().getBlockY() + ", "
						+ event.getBlock().getLocation().getBlockZ() );
				return;
			}
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
