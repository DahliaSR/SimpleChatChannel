package main.java.me.avankziar.simplechatchannels.spigot.commands.simplechatchannels;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.simplechatchannels.spigot.SimpleChatChannels;
import main.java.me.avankziar.simplechatchannels.spigot.Utility;
import main.java.me.avankziar.simplechatchannels.spigot.commands.CommandModule;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ARGPlayerlist extends CommandModule
{
	private SimpleChatChannels plugin;
	
	public ARGPlayerlist(SimpleChatChannels plugin)
	{
		super("playerlist",
				"scc.cmd.playerlist", SimpleChatChannels.sccarguments,1,2,"spielerliste");
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		//All needed vars
		Player player = (Player) sender;
		Utility utility = plugin.getUtility();
		List<BaseComponent> list = new ArrayList<>();
		if(args.length==1)
		{
			for(Player pp : plugin.getServer().getOnlinePlayers())
    		{
				TextComponent prefix = utility.clickEvent("&e"+pp.getName()+" ", 
						ClickEvent.Action.SUGGEST_COMMAND, plugin.getYamlHandler().getSymbol("PrivateMessage")+pp.getName()+" ", false);
				list.add(prefix);
    		}
		} else if(args.length==2)
		{
			String s = args[1];
			for(Player pp : plugin.getServer().getOnlinePlayers())
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