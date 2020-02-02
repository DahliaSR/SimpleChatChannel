package main.java.me.avankziar.simplechatchannels.spigot;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import main.java.me.avankziar.simplechatchannels.spigot.database.MysqlInterface;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Utility 
{
	private SimpleChatChannels plugin;
	private String language;
	
	public Utility(SimpleChatChannels plugin) 
	{
		this.plugin = plugin;
		language = plugin.getYamlHandler().get().getString("language");
	}
	
	public boolean getTarget(Player player) 
    {
    	int range = 20;
    	List<Entity> nearbyE = player.getNearbyEntities(range, range, range);
    	ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();
     
    	for (Entity e : nearbyE) {
    		if (e instanceof LivingEntity) 
    		{
    			livingE.add((LivingEntity) e);
    		}
    	}
     
    	LivingEntity target = null;
    	BlockIterator bItr = new BlockIterator(player, range);
    	Block block;
    	Location loc;
    	int bx, by, bz;
    	double ex, ey, ez;
    	// loop through player's line of sight
    	while (bItr.hasNext()) 
    	{
    		block = bItr.next();
    		bx = block.getX();
    		by = block.getY();
    		bz = block.getZ();
    		// check for entities near this block in the line of sight
    		for (LivingEntity e : livingE) 
    		{
    			loc = e.getLocation();
    			ex = loc.getX();
    			ey = loc.getY();
    			ez = loc.getZ();
    			if ((bx - .75 <= ex && ex <= bx + 1.75)
    					&& (bz - .75 <= ez && ez <= bz + 1.75)
    					&& (by - 1 <= ey && ey <= by + 2.5)) {
    				// entity is close enough, set target and stop
    				target = e;
    				break;
    			}
    		}
    	}
    	if(target==null)
    	{
    		return false;
    	}
    	return true;
    }
	
	public String tl(String path)
	{
		return ChatColor.translateAlternateColorCodes('&', path);
	}
	
	public TextComponent tc(String s)
	{
		return new TextComponent(s);
	}
	
	public TextComponent tcl(String s)
	{
		return new TextComponent(ChatColor.translateAlternateColorCodes('&', s));
	}
	
	public void sendMessage(Player player, String path)
	{
		player.spigot().sendMessage(tc(tl(path)));
	}
	
	public String getDate(String format)
	{
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dt = sdf.format(now);
		return dt;
	}
	
	public List<BaseComponent> getAllTextComponentForChannels(Player p, String eventmsg,
			String channelname, String channelsymbol, int substring)
	{
		TextComponent channel = tc(tl(plugin.getYamlHandler().getL().getString(language+".channels."+channelname)));
		channel.setClickEvent( new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, channelsymbol));
		channel.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT
				, new ComponentBuilder(tl(plugin.getYamlHandler().getL().getString(
						language+".channelextra.hover."+channelname))).create()));
		
		List<BaseComponent> prefix = getPrefix(p);
		
		TextComponent player = tc(tl(getFirstPrefixColor(p)+p.getName()));
		player.setClickEvent( new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, 
				plugin.getYamlHandler().getSymbol("pm")+p.getName()+" "));
		player.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT
				, new ComponentBuilder(tl(plugin.getYamlHandler().getL().getString(language+".channelextra.hover.message")
						.replaceAll("%player%", p.getName()))).create()));
		
		List<BaseComponent> suffix = getSuffix(p);
		
		TextComponent msg = tc(tl(plugin.getYamlHandler().getL().getString(language+".chatsplit."+channelname)
				+MsgLater(p, substring,channelname, eventmsg)));
		
		return getTCinLine(channel, prefix, player, suffix, msg);
	}
	
	public String MsgLater(Player player, int ss, String channel, String msg)
	{
		String rawmsg = msg.substring(ss);
		String[] fullmsg = rawmsg.split(" ");
		String cc = plugin.getYamlHandler().getL().getString(language+".channelcolor."+channel);
		String msglater = "";
		String safeColor = null;
		for(String splitmsg : fullmsg)
		{
			if(player.hasPermission("scc.channels.colorbypass"))
			{
				if(hasColor(splitmsg))
				{
					safeColor = splitmsg.substring(0,2);
					msglater += safeColor+splitmsg+" ";
				} else
				{
					if(safeColor==null)
					{
						safeColor = cc;
					}
					msglater += safeColor+splitmsg+" ";
				}
			} else
			{
				msglater += cc+removeColor(splitmsg)+" ";
			}
			
		}
		return msglater;
	}
	
	private boolean hasColor(String msg)
	{
		if(msg.contains("&0")||msg.contains("&1")||msg.contains("&2")||msg.contains("&3")||msg.contains("&4")
				||msg.contains("&5")||msg.contains("&6")||msg.contains("&7")||msg.contains("&8")||msg.contains("&9")
				||msg.contains("&a")||msg.contains("&A")||msg.contains("&b")||msg.contains("&B")
				||msg.contains("&c")||msg.contains("&C")||msg.contains("&d")||msg.contains("&D")
				||msg.contains("&d")||msg.contains("&D")||msg.contains("&e")||msg.contains("&E")
				||msg.contains("&f")||msg.contains("&f")||msg.contains("&k")||msg.contains("&K")
				||msg.contains("&m")||msg.contains("&M")||msg.contains("&n")||msg.contains("&N")
				||msg.contains("&l")||msg.contains("&L")||msg.contains("&o")||msg.contains("&O")
				||msg.contains("&r")||msg.contains("&R"))
		{
			return true;
		}
		return false;
	}
	
	private String removeColor(String msg)
	{
		String a = msg.replaceAll("&0", "").replaceAll("&1", "").replaceAll("&2", "").replaceAll("&3", "").replaceAll("&4", "").replaceAll("&5", "")
				.replaceAll("&6", "").replaceAll("&7", "").replaceAll("&8", "").replaceAll("&9", "")
				.replaceAll("&a", "").replaceAll("&b", "").replaceAll("&c", "").replaceAll("&d", "").replaceAll("&e", "").replaceAll("&f", "")
				.replaceAll("&k", "").replaceAll("&l", "").replaceAll("&m", "").replaceAll("&n", "").replaceAll("&o", "").replaceAll("&r", "")
				.replaceAll("&A", "").replaceAll("&B", "").replaceAll("&C", "").replaceAll("&D", "").replaceAll("&E", "").replaceAll("&F", "")
				.replaceAll("&K", "").replaceAll("&L", "").replaceAll("&M", "").replaceAll("&N", "").replaceAll("&O", "").replaceAll("&R", "");
		return a;
	}

	public void spy(Player player, TextComponent msg)
	{
		if(plugin.getYamlHandler().get().getString("bungee").equalsIgnoreCase("true"))
		{
			sendBungeeSpyMessage(player, "", msg.toLegacyText());
		} else
		{
			for(Player all : plugin.getServer().getOnlinePlayers())
			{
				if((boolean) plugin.getMysqlInterface().getDataI(all, "spy", "player_uuid"))
				{
					all.spigot().sendMessage(msg);
				}
			}
		}
	}
	
	public void sendBungeeSpyMessage(Player p, String server, String msg)  //FIN
	{
		String µ = "µ";
		String Category = "spy";
        String PlayerUUID = p.getUniqueId().toString();
        String ToServer = server;
		String message = Category+µ+PlayerUUID+µ+ToServer+µ+msg;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
        p.sendPluginMessage(plugin, "simplechatchannels:sccbungee", stream.toByteArray());
    }
	
	public String getPreOrSuffix(String preorsuffix)
	{
		int a = 1;
		int b = Integer.parseInt(plugin.getYamlHandler().getL().getString(language+".prefixsuffixamount"));
		while(a<=b)
		{
			if(plugin.getYamlHandler().getL().getString(language+".prefix."+String.valueOf(a)).substring(2).equals(preorsuffix))
			{
				String perm = "scc.prefix."+String.valueOf(a);
				return perm;
			}
			if(plugin.getYamlHandler().getL().getString(language+".suffix."+String.valueOf(a)).substring(2).equals(preorsuffix))
			{
				String perm = "scc.suffix."+String.valueOf(a);
				return perm;
			}
			a++;
		}
		return "scc.no_prefix_suffix";
	}
	
	public boolean getIgnored(Player player, Player target)
	{
		if(plugin.getMysqlInterface().existIgnore(player, target.getUniqueId().toString()))
		{
			return true;
		}
		return false;
	}
	
	public boolean getWordfilter(String msg)
	{
		List<String> wordfilter = plugin.getYamlHandler().get().getStringList("wordfilter");
		for(String wf : wordfilter)
		{
			if(msg.contains(wf))
			{
				return true;
			}
		}
		return false;
	}
	
	public List<BaseComponent> getTCinLine(TextComponent channel, List<BaseComponent> prefix, TextComponent player, 
			List<BaseComponent> suffix, TextComponent msg)
	{
		List<BaseComponent> list = new ArrayList<>();
		list.add(channel);
		for(BaseComponent bcp : prefix)
		{
			list.add(bcp);
		}
		list.add(player);
		for(BaseComponent bcs : suffix)
		{
			list.add(bcs);
		}
		list.add(msg);
		return list;
	}
	
	public List<BaseComponent> getTCinLinePN(TextComponent channel, TextComponent player, TextComponent player2, TextComponent msg)
	{
		List<BaseComponent> list = new ArrayList<>();
		list.add(channel);
		list.add(player);
		list.add(player2);
		list.add(msg);
		return list;
	}
	
	public String getFirstPrefixColor(Player p)
	{
		Boolean useprefixforplayercolor = plugin.getYamlHandler().get().getString("useprefixforplayercolor").equals("true");
		if(useprefixforplayercolor!=null && useprefixforplayercolor==false)
		{
			return plugin.getYamlHandler().getL().getString(language+".playercolor");
		}
		String prefix = null;
		int a = 1;
		int b = Integer.parseInt(plugin.getYamlHandler().getL().getString(language+".prefixsuffixamount"));
		while(a<=b)
		{
			if(p.hasPermission("scc.prefix."+String.valueOf(a))) 
			{
				String preorsuffix = plugin.getYamlHandler().getL().getString(language+".prefix."+String.valueOf(a));
				if(preorsuffix.startsWith("&"))
				{
					prefix = preorsuffix.substring(0, 2);
					break;
				}
			}
			a++;
		}		
		if(prefix == null)
		{
			prefix = plugin.getYamlHandler().getL().getString(language+".playercolor");
		}
		return prefix;
	}
	
	public List<BaseComponent> getPrefix(Player p)
	{
		List<BaseComponent> list = new ArrayList<>();
		int a = 1;
		int b = Integer.parseInt(plugin.getYamlHandler().getL().getString(language+".prefixsuffixamount"));
		while(a<=b)
		{
			if(p.hasPermission("scc.prefix."+String.valueOf(a))) 
			{
				String preorsuffix = plugin.getYamlHandler().getL().getString(language+".prefix."+String.valueOf(a));
				String pors = "";
				if(preorsuffix.startsWith("&"))
				{
					pors = preorsuffix.substring(2);
				} else
				{
					pors = preorsuffix;
				}
				TextComponent prefix = tc(tl(plugin.getYamlHandler().getL().getString(language+".prefix."+String.valueOf(a))+" "));
				prefix.setClickEvent( new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
						plugin.getYamlHandler().getSymbol("group")+pors +" "));
				prefix.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT
						, new ComponentBuilder(tl(plugin.getYamlHandler().getL().getString(
								language+".channelextra.hover.group"))).create()));
				list.add(prefix);
			}
			a++;
		}		
		if(list.isEmpty())
		{
			list.add(tc(""));
		}
		return list;
	}
	
	public List<BaseComponent> getSuffix(Player p)
	{
		List<BaseComponent> list = new ArrayList<>();
		int a = 1;
		int b = Integer.parseInt(plugin.getYamlHandler().getL().getString(language+".prefixsuffixamount"));
		while(a<=b)
		{
			if(p.hasPermission("scc.suffix."+String.valueOf(a))) 
			{
				String preorsuffix = plugin.getYamlHandler().getL().getString(language+".suffix."+String.valueOf(a));
				String pors = " ";
				if(preorsuffix.startsWith("&"))
				{
					pors = preorsuffix.substring(2);
				} else
				{
					pors = preorsuffix;
				}
				TextComponent suffix = tc(tl(" "+plugin.getYamlHandler().getL().getString(language+".suffix."+String.valueOf(a))+" "));
				suffix.setClickEvent( new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
						plugin.getYamlHandler().getSymbol("group")+pors+" "));
				suffix.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT
						, new ComponentBuilder(tl(plugin.getYamlHandler().getL().getString(
								language+".channelextra.hover.group"))).create()));
				list.add(suffix);
			}
			a++;
		}		
		if(list.isEmpty())
		{
			list.add(tc(""));
		}
		return list;
	}
	
	public boolean hasChannelRights(Player player, String mysql_channel)
	{
		if(!(boolean) plugin.getMysqlInterface().getDataI(player, mysql_channel, "player_uuid"))
		{
			sendMessage(player,plugin.getYamlHandler().getL().getString(language+".EVENT_Chat.msg01"));
			return false;
		} else
		{
			return true;
		}
	}
	
	public void sendAllMessage(Player player, String mysql_channel, TextComponent MSG)
	{
		for(Player all : plugin.getServer().getOnlinePlayers())
		{
			if((boolean) plugin.getMysqlInterface().getDataI(all, mysql_channel, "player_uuid"))
			{
				if(!getIgnored(player,all))
				{
					all.spigot().sendMessage(MSG);
				}
			}
		}
	}
	
	public boolean rightArgs(Player p, String[] args, int i)
    {
    	if(args.length!=i)
    	{
    		TextComponent msg = tc(tl(plugin.getYamlHandler().getL().getString(language+".CMD_SCC.msg01")));
			msg.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/scc"));
			p.spigot().sendMessage(msg);
			return true;
    	} else
    	{
    		return false;
    	}
    }
	
	public String getActiveChannels(Player player)
	{
		String language = plugin.getYamlHandler().get().getString("language");
		MysqlInterface mi = plugin.getMysqlInterface();
		boolean canchat = (boolean)mi.getDataI(player, "can_chat", "player_uuid");
		boolean global = (boolean)mi.getDataI(player, "channel_global", "player_uuid");
		boolean local = (boolean)mi.getDataI(player, "channel_local", "player_uuid");
		boolean auction = (boolean)mi.getDataI(player, "channel_auction", "player_uuid");
		boolean trade = (boolean)mi.getDataI(player, "channel_trade", "player_uuid");
		boolean support = (boolean)mi.getDataI(player, "channel_support", "player_uuid");
		boolean team = (boolean)mi.getDataI(player, "channel_team", "player_uuid");
		boolean admin = (boolean)mi.getDataI(player, "channel_admin", "player_uuid");
		boolean world = (boolean)mi.getDataI(player, "channel_world", "player_uuid");
		boolean group = (boolean)mi.getDataI(player, "channel_group", "player_uuid");
		boolean privatemsg = (boolean)mi.getDataI(player, "channel_pm", "player_uuid");
		boolean custom = (boolean)mi.getDataI(player, "channel_custom", "player_uuid");
		boolean spy = (boolean)mi.getDataI(player, "spy", "player_uuid");
		
		String comma = plugin.getYamlHandler().getL().getString(language+".join.comma");
		
		String ac = "";
		if(!canchat) {ac = plugin.getYamlHandler().getL().getString(language+".EVENT_JoinLeave.msg04"); return ac;}
		ac += plugin.getYamlHandler().getL().getString(language+".join.info");
		if(global) {ac += plugin.getYamlHandler().getL().getString(language+".join.global")+comma;}
		if(local) {ac += plugin.getYamlHandler().getL().getString(language+".join.local")+comma;}
		if(auction) {ac += plugin.getYamlHandler().getL().getString(language+".join.auction")+comma;}
		if(trade) {ac += plugin.getYamlHandler().getL().getString(language+".join.trade")+comma;}
		if(support) {ac += plugin.getYamlHandler().getL().getString(language+".join.support")+comma;}
		if(world) {ac += plugin.getYamlHandler().getL().getString(language+".join.world")+comma;}
		if(team) {ac += plugin.getYamlHandler().getL().getString(language+".join.team")+comma;}
		if(admin) {ac += plugin.getYamlHandler().getL().getString(language+".join.admin")+comma;}
		if(group) {ac += plugin.getYamlHandler().getL().getString(language+".join.group")+comma;}
		if(privatemsg) {ac += plugin.getYamlHandler().getL().getString(language+".join.pm")+comma;}
		if(custom) {ac += plugin.getYamlHandler().getL().getString(language+".join.custom")+comma;}
		if(spy) {ac += plugin.getYamlHandler().getL().getString(language+".join.spy")+comma;}
		return ac.substring(0, ac.length()-2);
	}
	
	public void controlChannelSaves(Player player)
	{
		if(!plugin.getMysqlInterface().hasAccount(player))
		{
			plugin.getMysqlInterface().createAccount(player);
			return;
		} else
		{
			return;
		}
	}
}
