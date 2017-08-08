package com.plugin.ftb.enderdragonhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class FutabaCommand implements CommandExecutor {

	public static Main _plugin = Main.plugin;
	
	public FutabaCommand(Main plugin) {
		MainCommandExecutor._plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			if(Bukkit.getPlayerExact("ftb28") == null) {
				return true;
			}
			Player ftb28 = Bukkit.getPlayerExact("ftb28");
			for(ItemStack itemStack : player.getInventory().getContents()) {
				if(itemStack != null) {
					if(itemStack.getType() == Material.BLAZE_ROD || itemStack.getType() == Material.ENDER_PEARL) {
						int amount = itemStack.getAmount();
						Material item = itemStack.getType();
						ftb28.getInventory().addItem(new ItemStack(item, amount));
						String name;
						if(item == Material.BLAZE_ROD) {
							name = "ブレイズロッド";
						}
						else if(item == Material.ENDER_PEARL) {
							name ="エンダーパール";
						}
						else {
							name = "null";
						}
						ftb28.sendMessage("[アイテム受取] " + ChatColor.GREEN + player.getName() + ChatColor.RESET + " さんから " + ChatColor.BLUE + name + ChatColor.RESET + " を " + ChatColor.GOLD + itemStack.getAmount() + ChatColor.RESET + "個 受け取りました。");
						itemStack.setAmount(0);
					}
				}
			}
		}
		
		
		
		return true;
	}

}
