package main.java.me.avankziar.simplechatchannels.bungee.commands.simplechatchannels;

import main.java.me.avankziar.simplechatchannels.bungee.SimpleChatChannels;
import main.java.me.avankziar.simplechatchannels.bungee.commands.CommandModule;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ARGOptionSpy extends CommandModule
{
	private SimpleChatChannels plugin;
	
	public ARGOptionSpy(SimpleChatChannels plugin)
	{
		super("spy",
				"scc.option.spy", SimpleChatChannels.sccarguments,1,1,"spitzeln",null);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		ProxiedPlayer player = (ProxiedPlayer) sender;
		plugin.getCommandHelper().optiontoggle(player, "spy", "spy", "Spy");
		return;
	}
}
