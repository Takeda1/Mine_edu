package me.Takeda1.test1;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ChangeGameMode{
	public static GradGames plugin;
	public final Logger logger = Logger.getLogger("Minecraft"); //This is bad format
	MySQL mysql=new MySQL(logger, "GradGames", "localhost", "3306", "test", "root", "sonic312645");
	public ChangeGameMode(GradGames instance){
		plugin = instance;
	}
	
	public void readCommand(Player player, String command, String[] args){
		
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
		}else if(command.equalsIgnoreCase("query")){
				player.sendMessage(GradGames.retrieve(Integer.parseInt(args[0])));
		}
	}
	public void readCommand(ConsoleCommandSender ccs, String command, String[] args){
		if(command.equalsIgnoreCase("query")){			
			((CommandSender)ccs).sendMessage(GradGames.retrieve(Integer.parseInt(args[0])));
		}else if(command.equalsIgnoreCase("newrow")){
			((CommandSender)ccs).sendMessage(GradGames.newRow(Integer.parseInt(args[0]), args[1], args[2]));
		}
	}
}
