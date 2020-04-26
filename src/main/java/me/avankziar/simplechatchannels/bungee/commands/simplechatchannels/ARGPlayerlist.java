package main.java.me.avankziar.simplechatchannels.bungee.commands.simplechatchannels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.me.avankziar.simplechatchannels.bungee.SimpleChatChannels;
import main.java.me.avankziar.simplechatchannels.bungee.Utility;
import main.java.me.avankziar.simplechatchannels.bungee.commands.CommandModule;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ARGPlayerlist extends CommandModule
{
	private SimpleChatChannels plugin;
	
	public ARGPlayerlist(SimpleChatChannels plugin)
	{
		super("playerlist",
				"scc.cmd.playerlist", SimpleChatChannels.sccarguments,1,2,"spielerliste",
				new ArrayList<String>(Arrays.asList("[Players]".split(";"))));
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		//All needed vars
		ProxiedPlayer player = (ProxiedPlayer) sender;
		Utility utility = plugin.getUtility();
		List<BaseComponent> list = new ArrayList<>();
		if(args.length==1)
		{
			for(ProxiedPlayer pp : plugin.getProxy().getPlayers())
    		{
				TextComponent prefix = utility.clickEvent("&e"+pp.getName()+" ", 
						ClickEvent.Action.SUGGEST_COMMAND, plugin.getYamlHandler().getSymbol("PrivateMessage")+pp.getName()+" ", false);
				list.add(prefix);
    		}
		} else if(args.length==2)
		{
			String s = args[1];
			for(ProxiedPlayer pp : plugin.getProxy().getPlayers())
    		{
				if(Utility.containsIgnoreCase(pp.getName(), s))
				{
					TextComponent prefix = utility.clickEvent("&e"+pp.getName()+" ", 
							ClickEvent.Action.SUGGEST_COMMAND, plugin.getYamlHandler().getSymbol("PrivateMessage")+pp.getName()+" ", false);
    				list.add(prefix);
				}
    		}
		} else
		{
			utility.rightArgs(player,args,2);
			return;
		}
		plugin.getCommandHelper().playergrouplist(player, list, "PlayerList");
		return;
	}

}
