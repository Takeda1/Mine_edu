package me.Takeda1.test1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class GradGames extends JavaPlugin{
	public static GradGames plugin;
	public final static Logger logger = Logger.getLogger("Minecraft");
	public final ChangeGameMode mode = new ChangeGameMode(this);
	static MySQL mysql=new MySQL(logger, "GradGames", "localhost", "3306", "minedu", "root", "sonic312645");
	
	

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		GradGames.logger.info(pdfFile.getName() + " is now disabled.");
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		GradGames.logger.info(pdfFile.getName() + " Plugin " + " version " + pdfFile.getVersion() + " is enabled.");
		editField("testtableEdit");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		System.out.println(sender.toString());
		if((sender instanceof Player)){
			mode.readCommand((Player) sender, commandLabel, args);
			return true;
		}else if(sender instanceof ConsoleCommandSender){
			System.out.println("Yay!");
			mode.readCommand((ConsoleCommandSender) sender, commandLabel, args);
		}
		return false;
	}

	public void editField(String table){
		try {
			mysql.open();
		}catch(Exception e){
			System.out.println("Could not connect to specified database: Please check internet " +
					"connection or database connection information");
			e.printStackTrace();
			System.exit(1);
		}
		sqlDoesTableExist("question");
		sqlDoesTableExist("topic");
		//mysql.createTable(table);
	}
	public static void sqlDoesTableExist(String table) {
		if(table=="question"){
			if(mysql.checkTable(table)){
				System.out.println(table+" table Exists");
			}else{
				mysql.query("CREATE TABLE question (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, topicid INT, FOREIGN KEY (topicid) REFERENCES topic(id), " +
						    "difficulty INT, type INT, Question VARCHAR(255),  Answer1 VARCHAR(255), Answer2 VARCHAR(255),  Answer3 VARCHAR(255), Answer4 VARCHAR(255),  Answer5 VARCHAR(255));");
				System.out.println(table+" table Created");
			}
		}
    }
	public static String retrieveQuestion(int input){
		System.out.println("Get past here please ");
		ResultSet s = null;
		String t = null;
		s = mysql.query("SELECT * FROM testdata WHERE id="+input);
		try {
			s.absolute(1);
			t=Integer.toString(s.getInt(1));
			t+=", "+s.getString(2);
			t+=", "+s.getString(3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(t);
		return "happy";
	}
	
	public static void newTopic(String[] args){
		if(topicExists(args[0])){
			System.out.println("Topic already exists");
		}else{
			String newQuestion = "INSERT INTO topic(name) VALUES("+args[0]+"');";
			mysql.query(newQuestion);
		}
	}
	public static void newQuestion(String[] args){
		if(topicExists(args[0])){
			String newQuestion = "INSERT INTO question(topicid, difficulty, type, Question, Answer1, Answer2, Answer3, Answer4, Answer5) VALUES((SELECT id FROM topic WHERE name='"+args[0]+"', ')";
			for(int i=1; i<8; i++){
				newQuestion+=args[i]+"', '";
			}
			newQuestion+=args[8]+"');";
			mysql.query(newQuestion);
		}else{
			System.out.println("Topic does not exist");
		}
	}
	public static boolean topicExists(String topic){
		ResultSet s = null;
		try{
			s = mysql.query("SELECT id FROM topic WHERE name='"+topic+"';");
			s.absolute(1);
			return true;
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	public static void printQuestions(){
		ResultSet s = null;
		s = mysql.query("SELECT topic.name, question.difficulty, question.type, question.Question FROM question INNER JOIN topic ON question.topicid=topic.id;");
		int rows = 0;
		try{
			rows = s.getFetchSize();
			if(rows<1){return;}
			for(int i=1; i<=rows; i++){
				s.absolute(i);
				System.out.println("");
				System.out.print("Topic: "+s.getString(1));
				System.out.print("Difficulty: "+s.getInt(2));
				System.out.print("Type: "+s.getInt(3));
				System.out.print("Question: "+s.getString(4));
				System.out.println("");
			}
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
}
