package com.plugin.ftb.enderdragonhunt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class MainUtils {
	
	//プレイヤーごとの経験値リスト
	public static HashMap<String, Integer> pointList = new HashMap<>();
	//pointListに基づいたトップ10(降順)のプレイヤーリスト
	public static LinkedHashMap<String,Integer> topList = new LinkedHashMap<>();
	
	//全プレイヤーに0ポイントを付与
	public static void setZeroPoint() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			pointList.put(player.getName(), 0);
		}
		
		//ソート
		updateTopList();
	}
	
	//ポイントを付与する
	public static void addPoint(Player player, int point) {
		String name = player.getName();
		
		if(pointList.containsKey(name)) {
			//既にポイントが付与されていた場合、前のポイントにプラスする
			pointList.put(name, pointList.get(name) + point);
		}else {
			//新規にポイントを追加する
			pointList.put(name, point);
		}
	}
	
	//ポイント順でソートする
	public static void updateTopList() {
		//降順でソートし、hashmapに代入
		topList = new LinkedHashMap<>();
		for(Entry<String,Integer> list : sort(pointList, 10)) {
			topList.put(list.getKey(), list.getValue());
		}
	}
	
	/*
	 * valueをソートして返すメソッド
	 * @param hashMap ソートしたいリスト
	 * @param max ソート後取り出したいリストの最大要素数
	 * @return valueを降順でソートしたリスト
	 */
	private static List<Map.Entry<String,Integer>> sort(HashMap<String, Integer> hashMap, int max) {
		//ソート後の配列を生成
        List<Map.Entry<String,Integer>> entries = 
              new ArrayList<Map.Entry<String,Integer>>(hashMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(
                  Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
                return ((Integer)entry2.getValue()).compareTo((Integer)entry1.getValue());
            }
        });
        
        //0~9番目の要素のみを返す
        int size = entries.size() >= max ? max : entries.size();
        return entries.subList(0, size);
	}
}
