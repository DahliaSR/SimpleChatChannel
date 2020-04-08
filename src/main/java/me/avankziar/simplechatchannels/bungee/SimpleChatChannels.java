package main.java.me.avankziar.simplechatchannels.bungee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import main.java.de.avankziar.afkrecord.bungee.AfkRecord;
import main.java.de.avankziar.punisher.bungee.PunisherBungee;
import main.java.me.avankziar.simplechatchannels.bungee.commands.CommandExecutorClickChat;
import main.java.me.avankziar.simplechatchannels.bungee.commands.CommandExecutorSimpleChatChannel;
import main.java.me.avankziar.simplechatchannels.bungee.commands.sccargs.ARGGrouplist;
import main.java.me.avankziar.simplechatchannels.bungee.commands.sccargs.ARGPlayerlist;
import main.java.me.avankziar.simplechatchannels.bungee.commands.CommandHandler;
import main.java.me.avankziar.simplechatchannels.bungee.commands.CommandFactory;
import main.java.me.avankziar.simplechatchannels.bungee.database.MysqlInterface;
import main.java.me.avankziar.simplechatchannels.bungee.database.MysqlSetup;
import main.java.me.avankziar.simplechatchannels.bungee.database.YamlHandler;
import main.java.me.avankziar.simplechatchannels.bungee.listener.EVENTJoinLeave;
import main.java.me.avankziar.simplechatchannels.bungee.listener.EVENTTabComplete;
import main.java.me.avankziar.simplechatchannels.bungee.listener.ServerListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class SimpleChatChannels extends Plugin
{
	public static Logger log;
	public static String pluginName = "SimpleChatChannels";
	private static YamlHandler yamlHandler;
	private static MysqlSetup databaseHandler;
	private static MysqlInterface mysqlinterface;
	private static Utility utility;
	private static BackgroundTask backgroundtask;
	private static CommandFactory commandHandler;
	private PunisherBungee punisher;
	private AfkRecord afkrecord;
	
	public ArrayList<String> editorplayers;
	public static HashMap<String, CommandHandler> sccarguments;
	public static HashMap<String, CommandHandler> clcharguments;
	public static HashMap<String, CommandHandler> scceditorarguments;
	
	public void onEnable() 
	{
		log = getLogger();
		editorplayers = new ArrayList<>();
		sccarguments = new HashMap<String, CommandHandler>();
		yamlHandler = new YamlHandler(this);
		utility = new Utility(this);
		commandHandler = new CommandFactory(this);
		backgroundtask = new BackgroundTask(this);
		if(yamlHandler.get().getString("mysql.status").equalsIgnoreCase("true"))
		{
			mysqlinterface = new MysqlInterface(this);
			databaseHandler = new MysqlSetup(this);
		} else
		{
			log.severe("MySQL is not enabled! "+pluginName+" wont work correctly!");
		}
		setupPunisher();
		setupAfkRecord();
		CommandSetup();
		ListenerSetup();
	}
	
	public void onDisable()
	{
		getProxy().getScheduler().cancel(this);
		//HandlerList.unregisterAll();
		if(yamlHandler.get().getString("mysql.status").equalsIgnoreCase("true"))
		{
			if (databaseHandler.getConnection() != null) 
			{
				//backgroundtask.onShutDownDataSave();
				databaseHandler.closeConnection();
			}
		}
		
		log.info(pluginName + " is disabled!");
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public MysqlSetup getDatabaseHandler() 
	{
		return databaseHandler;
	}
	
	public MysqlInterface getMysqlInterface()
	{
		return mysqlinterface;
	}
	
	public Utility getUtility()
	{
		return utility;
	}
	
	public BackgroundTask getBackgroundTask()
	{
		return backgroundtask;
	}
	
	public CommandFactory getCommandFactory()
	{
		return commandHandler;
	}
	
	public void CommandSetup()
	{
		PluginManager pm = getProxy().getPluginManager();
		
		//CMD /scc
		//new ARGPlayerlist(this);
		//new ARGGrouplist(this);
		
		pm.registerCommand(this, new CommandExecutorSimpleChatChannel(this));
		pm.registerCommand(this, new CommandExecutorClickChat(this));
		
		/*pm.registerCommand(this, new CMDSimpleChatChannel(this));
		pm.registerCommand(this, new CMDSimpleChatChannelEditor(this));
		pm.registerCommand(this, new CMDClickChat(this));*/
	}
	
	public void ListenerSetup()
	{
		getProxy().registerChannel("simplechatchannels:sccbungee");
		PluginManager pm = getProxy().getPluginManager();
		pm.registerListener(this, new main.java.me.avankziar.simplechatchannels.bungee.listener.EVENTChat(this));
		pm.registerListener(this, new EVENTJoinLeave(this));
		pm.registerListener(this, new ServerListener(this));
		pm.registerListener(this, new EVENTTabComplete());
	}
	
	private boolean setupPunisher()
    {
        if (getProxy().getPluginManager().getPlugin("Punisher") == null) 
        {
        	punisher = null;
            return false;
        }
        punisher = PunisherBungee.getPlugin();
        return true;
    }
	
	private boolean setupAfkRecord()
	{
		if(getProxy().getPluginManager().getPlugin("AfkRecord")==null)
		{
			punisher = null;
            return false;
		}
		afkrecord = AfkRecord.getPlugin();
		return true;
	}
	
	public PunisherBungee getPunisher()
	{
		return punisher;
	}
	
	public AfkRecord getAfkRecord()
	{
		return afkrecord;
	}
}
