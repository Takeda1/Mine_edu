package me.Takeda1.test1;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class GradGames extends JavaPlugin{
	public static GradGames plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public final ChangeGameMode mode = new ChangeGameMode(this);
	

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " is now disabled.");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Plugin " + " version " + pdfFile.getVersion() + " is enabled.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if((sender instanceof Player)){
			mode.readCommand((Player) sender, commandLabel);
			return true;
		}
		return false;
	}
}
