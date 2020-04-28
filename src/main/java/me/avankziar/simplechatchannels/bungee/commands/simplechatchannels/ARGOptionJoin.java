package main.java.me.avankziar.simplechatchannels.bungee.commands.simplechatchannels;

import main.java.me.avankziar.simplechatchannels.bungee.SimpleChatChannels;
import main.java.me.avankziar.simplechatchannels.bungee.commands.CommandModule;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ARGOptionJoin extends CommandModule
{
	private SimpleChatChannels plugin;
	
	public ARGOptionJoin(SimpleChatChannels plugin)
	{
		super("join",
				"scc.option.join", SimpleChatChannels.sccarguments,1,1,"eintrittsnachricht");
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		ProxiedPlayer player = (ProxiedPlayer) sender;
		plugin.getCommandHelper().optiontoggle(player, "join", "joinmessage", "Join Message");
		return;
	}
}
