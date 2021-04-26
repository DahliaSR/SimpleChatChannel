package main.java.me.avankziar.scc.bungee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import main.java.de.avankziar.afkrecord.bungee.AfkRecord;
import main.java.me.avankziar.scc.bungee.assistance.BackgroundTask;
import main.java.me.avankziar.scc.bungee.assistance.Utility;
import main.java.me.avankziar.scc.bungee.commands.ClickChatCommandExecutor;
import main.java.me.avankziar.scc.bungee.commands.MessageCommandExecutor;
import main.java.me.avankziar.scc.bungee.commands.RCommandExecutor;
import main.java.me.avankziar.scc.bungee.commands.ReCommandExecutor;
import main.java.me.avankziar.scc.bungee.commands.SccCommandExecutor;
import main.java.me.avankziar.scc.bungee.commands.TabCompletionListener;
import main.java.me.avankziar.scc.bungee.commands.WCommandExecutor;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGBroadcast;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGChannel;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGIgnore;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGIgnoreList;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGMute;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGOption;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGOption_Channel;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGOption_Join;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGOption_Spy;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGUnmute;
import main.java.me.avankziar.scc.bungee.commands.scc.ARGUpdatePlayer;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Ban;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_ChangePassword;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Channels;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_ChatColor;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Create;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Delete;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Info;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Inherit;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Invite;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Join;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Kick;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Leave;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_NameColor;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Player;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Rename;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Symbol;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Unban;
import main.java.me.avankziar.scc.bungee.commands.scc.pc.ARGPermanentChannel_Vice;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_Ban;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_ChangePassword;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_Create;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_Info;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_Invite;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_Join;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_Kick;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_Leave;
import main.java.me.avankziar.scc.bungee.commands.scc.tc.ARGTemporaryChannel_Unban;
import main.java.me.avankziar.scc.bungee.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.scc.bungee.commands.tree.ArgumentModule;
import main.java.me.avankziar.scc.bungee.commands.tree.BaseConstructor;
import main.java.me.avankziar.scc.bungee.commands.tree.CommandConstructor;
import main.java.me.avankziar.scc.bungee.database.MysqlHandler;
import main.java.me.avankziar.scc.bungee.database.MysqlSetup;
import main.java.me.avankziar.scc.bungee.database.YamlHandler;
import main.java.me.avankziar.scc.bungee.handler.ChatHandler;
import main.java.me.avankziar.scc.bungee.listener.ChatListener;
import main.java.me.avankziar.scc.bungee.listener.JoinLeaveListener;
import main.java.me.avankziar.scc.bungee.listener.ServerListener;
import main.java.me.avankziar.scc.bungee.metrics.Metrics;
import main.java.me.avankziar.scc.bungee.objects.BypassPermission;
import main.java.me.avankziar.scc.bungee.objects.PluginSettings;
import main.java.me.avankziar.scc.bungee.objects.chat.TemporaryChannel;
import main.java.me.avankziar.scc.database.YamlManager;
import main.java.me.avankziar.scc.handlers.ConvertHandler;
import main.java.me.avankziar.scc.objects.ChatUser;
import main.java.me.avankziar.scc.objects.KeyHandler;
import main.java.me.avankziar.scc.objects.PermanentChannel;
import main.java.me.avankziar.scc.objects.StaticValues;
import main.java.me.avankziar.scc.objects.chat.Channel;
import main.java.me.avankziar.scc.objects.chat.ChatTitle;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;

public class SimpleChatChannels extends Plugin
{
	public static SimpleChatChannels plugin;
	public static Logger log;
	public static String pluginName = "SimpleChatChannels";
	private static YamlHandler yamlHandler;
	private static YamlManager yamlManager;
	private static MysqlSetup mysqlSetup;
	private static MysqlHandler mysqlHandler;
	private static Utility utility;
	private static BackgroundTask backgroundtask;
	private AfkRecord afkrecord;
	
	public ArrayList<String> editorplayers = new ArrayList<>();
	private ArrayList<String> players = new ArrayList<>();
	public static ArrayList<String> onlinePlayers = new ArrayList<>();
	public static LinkedHashMap<String, ArrayList<String>> rePlayers = new LinkedHashMap<>();
	public static LinkedHashMap<String, String> rPlayers = new LinkedHashMap<>();
	
	private ArrayList<CommandConstructor> commandTree = new ArrayList<>();
	private ArrayList<BaseConstructor> helpList = new ArrayList<>();
	private LinkedHashMap<String, ArgumentModule> argumentMap = new LinkedHashMap<>();
	
	public static String baseCommandI = "scc"; //Pfad angabe + ürspungliches Commandname
	public static String baseCommandII = "clch";
	public static String baseCommandIII = "scceditor";
	public static String baseCommandIV = "msg";
	public static String baseCommandV = "re";
	public static String baseCommandVI = "r";
	public static String baseCommandVII = "w";
	
	public static String baseCommandIName = ""; //CustomCommand name
	public static String baseCommandIIName = "";
	public static String baseCommandIIIName = "";
	public static String baseCommandIVName = "";
	public static String baseCommandVName = "";
	public static String baseCommandVIName = "";
	public static String baseCommandVIIName = "";
	
	public static String infoCommandPath = "CmdScc";
	public static String infoCommand = "/"; //InfoComamnd
	
	public static ArrayList<ChatTitle> chatTitlesPrefix = new ArrayList<>();
	public static ArrayList<ChatTitle> chatTitlesSuffix = new ArrayList<>();
	/**
	 * The Channel without entrysymbol.
	 */
	public static Channel nullChannel = null;
	/**
	 * The other channels, which has all entrysymbols.
	 */
	public static LinkedHashMap<String, Channel> channels = new LinkedHashMap<>();
	
	public void onEnable() 
	{
		plugin = this;
		log = getLogger();
		
		//https://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=SCC
		log.info(" ███████╗ ██████╗ ██████╗ | Version: "+plugin.getDescription().getVersion());
		log.info(" ██╔════╝██╔════╝██╔════╝ | Author: "+plugin.getDescription().getAuthor());
		log.info(" ███████╗██║     ██║      | Plugin Website: https://www.spigotmc.org/resources/simple-chat-channels.35220/");
		log.info(" ╚════██║██║     ██║      | Depend Plugins: "+plugin.getDescription().getDepends().toString());
		log.info(" ███████║╚██████╗╚██████╗ | SoftDepend Plugins: "+plugin.getDescription().getSoftDepends().toString());
		log.info(" ╚══════╝ ╚═════╝ ╚═════╝ | Have Fun^^");
		
		yamlHandler = new YamlHandler(plugin);
		utility = new Utility(plugin);
		
		if(yamlHandler.getConfig().getBoolean("Mysql.Status", false))
		{
			mysqlHandler = new MysqlHandler(plugin);
			mysqlSetup = new MysqlSetup(plugin);
		} else
		{
			log.severe("MySQL is not enabled! "+pluginName+" wont work correctly!");
		}
		PluginSettings.initSettings(plugin);
		ChatHandler.initPrivateChatColors();
		setupPlayers();
		backgroundtask = new BackgroundTask(plugin);
		setupStrings();
		setupCommandTree();
		ListenerSetup();
		setupBstats();
		setupChannels();
		setupChatTitles();
		BypassPermission.init(plugin);
	}
	
	public void onDisable()
	{
		getProxy().getScheduler().cancel(plugin);
		//HandlerList.unregisterAll();
		
		if(yamlHandler.getConfig().getBoolean("Mysql.Status", false))
		{
			if (mysqlSetup.getConnection() != null) 
			{
				//backgroundtask.onShutDownDataSave();
				mysqlSetup.closeConnection();
			}
		}
		
		log.info(pluginName + " is disabled!");
	}
	
	public static SimpleChatChannels getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public YamlManager getYamlManager()
	{
		return yamlManager;
	}
	
	public void setYamlManager(YamlManager yamlManager)
	{
		SimpleChatChannels.yamlManager = yamlManager;
	}
	
	public MysqlSetup getMysqlSetup() 
	{
		return mysqlSetup;
	}
	
	public MysqlHandler getMysqlHandler()
	{
		return mysqlHandler;
	}
	
	public Utility getUtility()
	{
		return utility;
	}
	
	public BackgroundTask getBackgroundTask()
	{
		return backgroundtask;
	}
	
	private void setupStrings()
	{
		//Hier baseCommands
		baseCommandIName = plugin.getYamlHandler().getCommands().getString(baseCommandI+".Name");
		baseCommandIIName = plugin.getYamlHandler().getCommands().getString(baseCommandII+".Name");
		baseCommandIIIName = plugin.getYamlHandler().getCommands().getString(baseCommandIII+".Name");
		baseCommandIVName = plugin.getYamlHandler().getCommands().getString(baseCommandIV+".Name");
		baseCommandVName = plugin.getYamlHandler().getCommands().getString(baseCommandV+".Name");
		baseCommandVIName = plugin.getYamlHandler().getCommands().getString(baseCommandVI+".Name");
		baseCommandVIIName = plugin.getYamlHandler().getCommands().getString(baseCommandVII+".Name");
		
		//Zuletzt infoCommand deklarieren
		infoCommand += baseCommandIName;
	}
	
	private void setupCommandTree()
	{
		LinkedHashMap<Integer, ArrayList<String>> pcPlayerMap = new LinkedHashMap<>();
		LinkedHashMap<Integer, ArrayList<String>> pcMap = new LinkedHashMap<>();
		LinkedHashMap<Integer, ArrayList<String>> tcMap = new LinkedHashMap<>();
		LinkedHashMap<Integer, ArrayList<String>> playerMap = new LinkedHashMap<>();
		LinkedHashMap<Integer, ArrayList<String>> channelMap = new LinkedHashMap<>();
		
		ArrayList<String> channelarray = new ArrayList<>();
		for(Channel channel : channels.values())
		{
			channelarray.add(channel.getUniqueIdentifierName());
		}
		channelMap.put(1, channelarray);
		
		ArrayList<String> pcarray = new ArrayList<>();
		for(PermanentChannel pc : PermanentChannel.getPermanentChannel())
		{
			pcarray.add(pc.getName());
		}
		Collections.sort(pcarray);
		pcPlayerMap.put(1, pcarray);
		pcMap.put(2, pcarray);
		ArrayList<String> playerarray = getPlayers();
		Collections.sort(playerarray);
		pcPlayerMap.put(3, playerarray);
		playerMap.put(2, playerarray);
		ArrayList<String> tcarray = new ArrayList<>();
		for(TemporaryChannel tc : TemporaryChannel.getCustomChannel())
		{
			tcarray.add(tc.getName());
		}
		Collections.sort(tcarray);
		tcMap.put(1, tcarray);
		
		PluginManager pm = getProxy().getPluginManager();
		
		ArgumentConstructor book = new ArgumentConstructor(baseCommandI+"_book", 0, 1, 2, false, null);
		PluginSettings.settings.addCommands(KeyHandler.SCC_BOOK, book.getCommandString());
		ArgumentConstructor broadcast = new ArgumentConstructor(baseCommandI+"_broadcast", 0, 1, 9999, true, null);
		ArgumentConstructor channel = new ArgumentConstructor(baseCommandI+"_channel", 0, 1, 1, false, channelMap);

		ArgumentConstructor ignore = new ArgumentConstructor(baseCommandI+"_ignore", 0, 1, 1, false, playerMap);
		ArgumentConstructor ignorelist = new ArgumentConstructor(baseCommandI+"_ignorelist", 0, 0, 1,false,  null);
		
		ArgumentConstructor mute = new ArgumentConstructor(baseCommandI+"_mute", 0, 1, 999, false, null);
		ArgumentConstructor unmute = new ArgumentConstructor(baseCommandI+"_unmute", 0, 1, 1, false, null);
		
		ArgumentConstructor option_channel = new ArgumentConstructor(baseCommandI+"_option_channel", 1, 1, 1, false, null);
		ArgumentConstructor option_join = new ArgumentConstructor(baseCommandI+"_option_join", 1, 1, 1, false, null);
		ArgumentConstructor option_spy = new ArgumentConstructor(baseCommandI+"_option_spy", 1, 1, 1, false, null);
		ArgumentConstructor option = new ArgumentConstructor(baseCommandI+"_optionchannel", 0, 0, 0, false, null,
				option_channel, option_join, option_spy);
		
		ArgumentConstructor pc_ban = new ArgumentConstructor(baseCommandI+"_pcban", 1, 3, 3, false, pcPlayerMap);
		ArgumentConstructor pc_changepassword = new ArgumentConstructor(baseCommandI+"_pc_changepassword", 1, 3, 3, false, pcMap);
		ArgumentConstructor pc_channels = new ArgumentConstructor(baseCommandI+"_pc_channels", 1, 1, 1, false, null);
		ArgumentConstructor pc_chatcolor = new ArgumentConstructor(baseCommandI+"_pc_chatcolor", 1, 3, 3, false, pcMap);
		ArgumentConstructor pc_create = new ArgumentConstructor(baseCommandI+"_pc_create", 1, 2, 3, false, null);
		ArgumentConstructor pc_delete = new ArgumentConstructor(baseCommandI+"_pc_delete", 1, 2, 3, false, pcMap);
		PluginSettings.settings.addCommands(KeyHandler.SCC_PC_DELETE, pc_delete.getCommandString());
		ArgumentConstructor pc_info = new ArgumentConstructor(baseCommandI+"_pc_info", 1, 1, 2, false, pcMap);
		PluginSettings.settings.addCommands(KeyHandler.SCC_PC_INFO, pc_info.getCommandString());
		ArgumentConstructor pc_inherit = new ArgumentConstructor(baseCommandI+"_pc_inherit", 1, 3, 3, false, pcPlayerMap);
		ArgumentConstructor pc_invite = new ArgumentConstructor(baseCommandI+"_pc_invite", 1, 3, 3, false, pcPlayerMap);
		ArgumentConstructor pc_join = new ArgumentConstructor(baseCommandI+"_pc_join", 1, 2, 3, false, pcMap);
		PluginSettings.settings.addCommands(KeyHandler.SCC_PC_JOIN, pc_join.getCommandString());
		ArgumentConstructor pc_kick = new ArgumentConstructor(baseCommandI+"_pc_kick", 1, 3, 3, false, pcPlayerMap);
		ArgumentConstructor pc_leave = new ArgumentConstructor(baseCommandI+"_pc_leave", 1, 2, 3, false, pcMap);
		PluginSettings.settings.addCommands(KeyHandler.SCC_PC_LEAVE, pc_leave.getCommandString());
		ArgumentConstructor pc_namecolor = new ArgumentConstructor(baseCommandI+"_pc_namecolor", 1, 3, 3, false, pcMap);
		ArgumentConstructor pc_player = new ArgumentConstructor(baseCommandI+"_pc_player", 1, 1, 2, false, playerMap);
		ArgumentConstructor pc_rename = new ArgumentConstructor(baseCommandI+"_pc_rename", 1, 3, 3, false, pcMap);
		ArgumentConstructor pc_symbol = new ArgumentConstructor(baseCommandI+"_pc_symbol", 1, 3, 3, false, pcMap);
		ArgumentConstructor pc_unban = new ArgumentConstructor(baseCommandI+"_pc_unban", 1, 3, 3, false, pcPlayerMap);
		ArgumentConstructor pc_vice = new ArgumentConstructor(baseCommandI+"_pc_vice", 1, 3, 3, false, pcPlayerMap);
		ArgumentConstructor pc = new ArgumentConstructor(baseCommandI+"_pc", 0, 0, 0, false, null,
				pc_ban, pc_changepassword, pc_channels, pc_chatcolor, pc_create, pc_delete, pc_info, pc_inherit, pc_invite, pc_join,
				pc_kick, pc_leave, pc_namecolor, pc_player, pc_rename, pc_symbol, pc_unban, pc_vice);
		
		ArgumentConstructor tc_ban = new ArgumentConstructor(baseCommandI+"_tc_ban", 1, 2, 2, false, playerMap);
		ArgumentConstructor tc_changepassword = new ArgumentConstructor(baseCommandI+"_tc_changepassword", 1, 2, 2, false, null);
		ArgumentConstructor tc_create = new ArgumentConstructor(baseCommandI+"_tc_create", 1, 2, 3, false, null);
		ArgumentConstructor tc_info = new ArgumentConstructor(baseCommandI+"_tc_info", 1, 2, 2, false, null);
		ArgumentConstructor tc_invite = new ArgumentConstructor(baseCommandI+"_tc_invite", 2, 2, 2, false, playerMap);
		ArgumentConstructor tc_join = new ArgumentConstructor(baseCommandI+"_tc_join", 1, 2, 3, false, tcMap);
		PluginSettings.settings.addCommands(KeyHandler.SCC_TC_JOIN, tc_join.getCommandString());
		ArgumentConstructor tc_kick = new ArgumentConstructor(baseCommandI+"_tc_kick", 1, 2, 2, false, playerMap);
		ArgumentConstructor tc_leave = new ArgumentConstructor(baseCommandI+"_tc_leave", 1, 1, 1, false, null);
		ArgumentConstructor tc_unban = new ArgumentConstructor(baseCommandI+"_tc_unban", 1, 2, 2, false, playerMap);
		ArgumentConstructor tc = new ArgumentConstructor(baseCommandI+"_tc", 0, 0, 0, false, null,
				tc_ban, tc_changepassword, tc_create, tc_info, tc_invite, tc_join, tc_kick, tc_leave, tc_unban);
		
		ArgumentConstructor updateplayer = new ArgumentConstructor(baseCommandI+"_updateplayer", 0, 1, 1, false, playerMap);
		ArgumentConstructor wordfilter = new ArgumentConstructor(baseCommandI+"_wordfilter", 0, 1, 1, false, null);
		
		CommandConstructor scc = new CommandConstructor(baseCommandI, true,
				broadcast, channel,
				ignore, ignorelist, mute, option,
				pc, tc,
				unmute, updateplayer, wordfilter);
		
		CommandConstructor clch = new CommandConstructor(baseCommandII, true); 
		
		//CommandConstructor scceditor = new CommandConstructor(baseCommandIII, false); 
		
		CommandConstructor msg = new CommandConstructor(baseCommandIV, false);
		
		CommandConstructor re = new CommandConstructor(baseCommandV, false);
		
		CommandConstructor r = new CommandConstructor(baseCommandVI, false);
		
		CommandConstructor w = new CommandConstructor(baseCommandVII, true);
		
		pm.registerCommand(plugin, new SccCommandExecutor(plugin, scc));
		pm.registerCommand(plugin, new ClickChatCommandExecutor(plugin, clch));
		//pm.registerCommand(plugin, new SccEditorCommandExecutor(plugin, scceditor));
		pm.registerCommand(plugin, new MessageCommandExecutor(plugin, msg));
		pm.registerCommand(plugin, new ReCommandExecutor(plugin, re));
		pm.registerCommand(plugin, new RCommandExecutor(plugin, r));
		pm.registerCommand(plugin, new WCommandExecutor(plugin, w));
		
		addingHelps(scc,
					broadcast, channel,
					ignore, ignorelist, mute,
					option, option_channel, option_join, option_spy,
					pc, pc_ban, pc_changepassword, pc_channels, pc_chatcolor, pc_create, pc_delete, pc_info, pc_inherit, pc_invite,
						pc_join, pc_kick, pc_leave, pc_namecolor, pc_player, pc_rename, pc_symbol, pc_unban, pc_vice,
					tc_ban, tc_changepassword, tc_create, tc_info, tc_invite, tc_join, tc_kick, tc_leave, tc_unban,
					unmute, updateplayer, wordfilter,
				clch, //scceditor, 
				msg, re, r, w);
		
		new ARGBroadcast(plugin, broadcast);
		
		new ARGChannel(plugin, channel);
		
		new ARGIgnore(plugin, ignore);
		new ARGIgnoreList(plugin, ignorelist);
		
		new ARGMute(plugin, mute);
		
		new ARGOption(plugin, option);
		new ARGOption_Channel(plugin, option_channel);
		new ARGOption_Join(plugin, option_join);
		new ARGOption_Spy(plugin, option_spy);
		
		new ARGPermanentChannel(plugin, pc);
		new ARGPermanentChannel_Ban(plugin, pc_ban);
		new ARGPermanentChannel_ChangePassword(plugin, pc_changepassword);
		new ARGPermanentChannel_Channels(plugin, pc_channels);
		new ARGPermanentChannel_ChatColor(plugin, pc_chatcolor);
		new ARGPermanentChannel_Create(plugin, pc_create);
		new ARGPermanentChannel_Delete(plugin, pc_delete);
		new ARGPermanentChannel_Info(plugin, pc_info);
		new ARGPermanentChannel_Inherit(plugin, pc_inherit);
		new ARGPermanentChannel_Invite(plugin, pc_invite);
		new ARGPermanentChannel_Join(plugin, pc_join);
		new ARGPermanentChannel_Kick(plugin, pc_kick);
		new ARGPermanentChannel_Leave(plugin, pc_leave);
		new ARGPermanentChannel_NameColor(plugin, pc_namecolor);
		new ARGPermanentChannel_Player(plugin, pc_player);
		new ARGPermanentChannel_Rename(plugin, pc_rename);
		new ARGPermanentChannel_Symbol(plugin, pc_symbol);
		new ARGPermanentChannel_Unban(plugin, pc_unban);
		new ARGPermanentChannel_Vice(plugin, pc_vice);
		
		new ARGTemporaryChannel(plugin, tc);
		new ARGTemporaryChannel_Ban(plugin, tc_ban);
		new ARGTemporaryChannel_ChangePassword(plugin, tc_changepassword);
		new ARGTemporaryChannel_Create(plugin, tc_create);
		new ARGTemporaryChannel_Info(plugin, tc_info);
		new ARGTemporaryChannel_Invite(plugin, tc_invite);
		new ARGTemporaryChannel_Join(plugin, tc_join);
		new ARGTemporaryChannel_Kick(plugin, tc_kick);
		new ARGTemporaryChannel_Leave(plugin, tc_leave);
		new ARGTemporaryChannel_Unban(plugin, tc_unban);
		
		new ARGUpdatePlayer(plugin, updateplayer);
		new ARGUnmute(plugin, unmute);
	}
	
	public void ListenerSetup()
	{
		getProxy().registerChannel(StaticValues.SCC_TOSPIGOT);
		getProxy().registerChannel(StaticValues.SCC_TOBUNGEE);
		PluginManager pm = getProxy().getPluginManager();
		pm.registerListener(plugin, new ChatListener(plugin));
		pm.registerListener(plugin, new JoinLeaveListener(plugin));
		pm.registerListener(plugin, new ServerListener(plugin));
		pm.registerListener(plugin, new TabCompletionListener(plugin));
	}
	
	public ArrayList<BaseConstructor> getHelpList()
	{
		return helpList;
	}
	
	public void addingHelps(BaseConstructor... objects)
	{
		for(BaseConstructor bc : objects)
		{
			helpList.add(bc);
		}
	}
	
	public ArrayList<CommandConstructor> getCommandTree()
	{
		return commandTree;
	}
	
	public CommandConstructor getCommandFromPath(String commandpath)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getPath().equalsIgnoreCase(commandpath))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}
	
	public CommandConstructor getCommandFromCommandString(String command)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getName().equalsIgnoreCase(command))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}

	public LinkedHashMap<String, ArgumentModule> getArgumentMap()
	{
		return argumentMap;
	}
	
	//FIXME AfkrRecord Version update
	public AfkRecord getAfkRecord()
	{
		return afkrecord;
	}
	
	/*public boolean PlayerToChatEditor(String playername)
	{
		if(editorplayers.contains(playername))
		{
			editorplayers.remove(playername);
			String message = "editor"+"µ"+playername+"µ"+"remove";
			utility.sendSpigotMessage("simplechatchannels:sccbungee", message);
			return false;
		} else if(!editorplayers.contains(playername))
		{
			editorplayers.add(playername);
			String message = "editor"+"µ"+playername+"µ"+"add";
			utility.sendSpigotMessage("simplechatchannels:sccbungee", message);
			return true;
		}
		return false;
	}*/

	public ArrayList<String> getPlayers()
	{
		return players;
	}

	public void setPlayers(ArrayList<String> players)
	{
		this.players = players;
	}
	
	public void setupPlayers()
	{
		ArrayList<ChatUser> cu = ConvertHandler.convertListI(
				plugin.getMysqlHandler().getTop(MysqlHandler.Type.CHATUSER,
						"`id`", true, 0,
						plugin.getMysqlHandler().lastID(MysqlHandler.Type.CHATUSER)));
		ArrayList<String> cus = new ArrayList<>();
		for(ChatUser chus : cu) 
		{
			cus.add(chus.getName());	
		}
		setPlayers(cus);
	}
	
	private void setupChatTitles()
	{
		Configuration cti = yamlHandler.getChatTitle();
		for(String key : cti.getKeys())
		{
			if(cti.get(key+".UniqueIdentifierName") == null 
					|| cti.get(key+".IsPrefix") == null 
					|| cti.get(key+".InChatName") == null
					|| cti.get(key+".InChatColorCode") == null
					|| cti.get(key+".Hover") == null
					|| cti.get(key+".Permission") == null
					|| cti.get(key+".Weight") == null)
			{
				continue;
			}
			ChatTitle ct = new ChatTitle(cti.getString(key+".UniqueIdentifierName"),
					cti.getBoolean(key+".IsPrefix"),
					cti.getString(key+".InChatName"),
					cti.getString(key+".InChatColorCode"),
					cti.getString(key+".Hover"),
					cti.getString(key+".Permission"),
					cti.getInt(key+".Weight"));
			if(ct.isPrefix())
			{
				chatTitlesPrefix.add(ct);
			} else
			{
				chatTitlesSuffix.add(ct);
			}
		}
		chatTitlesPrefix.sort(Comparator.comparing(ChatTitle::getWeight));
		Collections.reverse(chatTitlesPrefix);
		chatTitlesPrefix.sort(Comparator.comparing(ChatTitle::getWeight));
		Collections.reverse(chatTitlesSuffix);
	}
	
	private void setupChannels()
	{
		Configuration cha = yamlHandler.getChannels();
		for(String key : cha.getKeys())
		{
			if(cha.get(key+".UniqueIdentifierName") == null
					|| cha.get(key+".Symbol") == null
					|| cha.get(key+".InChatName") == null
					|| cha.get(key+".InChatColorMessage") == null
					|| cha.get(key+".Permission") == null
					|| cha.get(key+".JoinPart") == null
					|| cha.get(key+".ChatFormat") == null)
			{
				continue;
			}
			Channel c = new Channel(
					cha.getString(key+".UniqueIdentifierName"),
					cha.getString(key+".Symbol"),
					cha.getString(key+".InChatName"),
					cha.getString(key+".InChatColorMessage"),
					cha.getString(key+".Permission"),
					cha.getString(key+".JoinPart"),
					cha.getString(key+".ChatFormat"),
					cha.getBoolean(key+".UseSpecificServer", false),
					cha.getBoolean(key+".UseSpecificsWorld", false),
					cha.getInt(key+".UseBlockRadius", 0),
					cha.getLong(key+".MinimumTimeBetweenMessages", 500),
					cha.getLong(key+".MinimumTimeBetweenSameMessages", 1000),
					cha.getDouble(key+".PercentOfSimiliarityOrLess", 75.0),
					cha.getBoolean(key+".UseColor", false),
					cha.getBoolean(key+".UseItemReplacer", false),
					cha.getBoolean(key+".UseBookReplacer", false),
					cha.getBoolean(key+".UseRunCommandReplacer", false),
					cha.getBoolean(key+".UseSuggestCommandReplacer", false),
					cha.getBoolean(key+".UseWebsiteReplacer", false),
					cha.getBoolean(key+".UseEmojiReplacer", false),
					cha.getBoolean(key+".UseMentionReplacer", false),
					cha.getBoolean(key+".UsePositionReplacer", false));
			if(key.equalsIgnoreCase("permanent") || key.equalsIgnoreCase("temporary")
					|| key.equalsIgnoreCase("private"))
			{
				if(key.equalsIgnoreCase("permanent"))
				{
					c.setUniqueIdentifierName("Permanent");
					if(c.getSymbol().equalsIgnoreCase("NULL"))
					{
						c.setSymbol(".");
					}
				}if(key.equalsIgnoreCase("temporary"))
				{
					c.setUniqueIdentifierName("Temporary");
					if(c.getSymbol().equalsIgnoreCase("NULL"))
					{
						c.setSymbol(";");
					}
				} else
				{
					c.setUniqueIdentifierName("Private");
					c.setUseMentionReplacer(false);
				}
			} else
			{
				if(c.getSymbol().equalsIgnoreCase("null"))
				{
					nullChannel = c;
				} else
				{
					channels.put(c.getSymbol(), c);
				}
			}
		}
	}
	
	public void setupEmojis()
	{
		for(String e : yamlHandler.getEmojis().getKeys())
		{
			String emoji = plugin.getYamlHandler().getConfig().getString("ChatReplacer.Emoji.Start")
					+ e
					+ plugin.getYamlHandler().getConfig().getString("ChatReplacer.Emoji.End");
			ChatHandler.emojiList.put(emoji, yamlHandler.getEmojis().getString(e));
		}
	}
	
	public void setupBstats()
	{
		int pluginId = 8403;
        new Metrics(this, pluginId);
	}
}