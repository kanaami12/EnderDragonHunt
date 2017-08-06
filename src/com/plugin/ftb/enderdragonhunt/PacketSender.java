package com.plugin.ftb.enderdragonhunt;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ChatType;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class PacketSender{
	
	public static final void actionText(Player p, String text){
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.CHAT);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText(text));
		packet.getChatTypes().write(0, ChatType.GAME_INFO);
		sendPacket(p, packet);
	}
	
	public static final void title(Player p){
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
		packet.getTitleActions().write(0, TitleAction.RESET);
		sendPacket(p, packet);
	}
	public static final void title(Player p, String title){
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
		packet.getTitleActions().write(0, TitleAction.TITLE);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText(title));
		sendPacket(p, packet);
	}
	public static final void subtitle(Player p, String subtitle){
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
		packet.getTitleActions().write(0, TitleAction.SUBTITLE);
		packet.getChatComponents().write(0, WrappedChatComponent.fromText(subtitle));
		sendPacket(p, packet);
	}
	public static final void title(Player p, String title, String subtitle){
		title(p, title);
		subtitle(p, subtitle);
	}
	public static final void title(Player p, int fadein, int stay, int fadeout){
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
		packet.getTitleActions().write(0, TitleAction.TIMES);
		packet.getIntegers().write(0, fadein);
		packet.getIntegers().write(1, stay);
		packet.getIntegers().write(2, fadeout);
		sendPacket(p, packet);
	}
	public static final void title(Player p, String title, String subtitle, int fadein, int stay, int fadeout){
		title(p, fadein, stay, fadeout);
		title(p, title, subtitle);
	}
	
	public static final void sendPacket(Player p, PacketContainer packet){
		try{
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
		}catch(InvocationTargetException ex){
			ex.printStackTrace();
		}
	}
	
}
