package me.Takeda1.test1;


import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ChangeGameMode{
	public static GradGames plugin;
	
	public ChangeGameMode(GradGames instance){
		plugin = instance;
	}
	
	public void readCommand(Player player, String command){
		if(command.equalsIgnoreCase("creative")){
			if(player.hasPermission("GradGames.creative")){
				player.setGameMode(GameMode.CREATIVE);
				player.sendMessage("Now entering " + ChatColor.GOLD + "creative mode");
			}else{
				player.sendMessage("Y U no haz creative Perm?");
			}
		}else if(command.equalsIgnoreCase("survival")){
			if(player.hasPermission("GradGames.creative")){
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage("Now entering " + ChatColor.GOLD + "survival mode");
			}else{
				player.sendMessage("Y U no haz survival Perm?");
			}
		}
	}

}
