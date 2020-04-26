package main.java.me.avankziar.simplechatchannels.spigot.commands.simplechatchannels;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.simplechatchannels.spigot.SimpleChatChannels;
import main.java.me.avankziar.simplechatchannels.spigot.Utility;
import main.java.me.avankziar.simplechatchannels.spigot.commands.CommandModule;
import main.java.me.avankziar.simplechatchannels.spigot.interfaces.PermanentChannel;

public class ARGPermanentChannelVice extends CommandModule
{
	private SimpleChatChannels plugin;
	
	public ARGPermanentChannelVice(SimpleChatChannels plugin)
	{
		super("pcvice","scc.cmd.pc.vice",SimpleChatChannels.sccarguments,3,3,"pcstellvertreter");
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		Player player = (Player) sender;
		Utility utility = plugin.getUtility();
		String language = utility.getLanguage() + ".CmdScc.";
		PermanentChannel cc = PermanentChannel.getChannelFromName(args[1]);
		if(cc==null)
		{
			///Der angegebene Channel &5perma&fnenten %channel% existiert nicht!
			player.spigot().sendMessage(utility.tctl(plugin.getYamlHandler().getL().getString(language+"ChannelGeneral.ChannelNotExistII")
					.replace("%channel%", args[1])));
			return;
		}
		if(!cc.getCreator().equals(player.getUniqueId().toString()))
		{
			///Du bist nicht der Ersteller des CustomChannel!
			player.spigot().sendMessage(utility.tctlYaml(language+"ChannelGeneral.NotTheCreatorII"));
			return;
		}
		if(plugin.getServer().getPlayer(args[2])==null)
		{
			///Der Spieler ist nicht online oder existiert nicht!
			player.spigot().sendMessage(utility.tctlYaml(language+"NoPlayerExist"));
			return;
		}
		Player target = plugin.getServer().getPlayer(args[2]); 
		if(!cc.getMembers().contains(target.getUniqueId().toString()))
		{
			///Der angegebene Spieler ist nicht Mitglied im CustomChannel!
			player.spigot().sendMessage(utility.tctlYaml(language+"ChannelGeneral.NotChannelMemberII"));
			return;
		}
		if(cc.getVice().contains(target.getUniqueId().toString()))
		{
			cc.getVice().remove(target.getUniqueId().toString());
			plugin.getUtility().updatePermanentChannels(cc);
			for(Player members : plugin.getServer().getOnlinePlayers())
			{
				if(cc.getMembers().contains(members.getUniqueId().toString()))
				{
					///Der Spieler &f%player% &ewurde zum Mitglied im &5perma&fnenten &eChannel &f%channel% &cdegradiert&e!
					members.spigot().sendMessage(utility.tctl(
							plugin.getYamlHandler().getL().getString(language+"PCVice.Degraded")
							.replace("%player%", args[2]).replace("%channel%", cc.getNameColor()+cc.getName())));
				}
			}
		} else
		{
			cc.getVice().add(target.getUniqueId().toString());
			plugin.getUtility().updatePermanentChannels(cc);
			for(Player members : plugin.getServer().getOnlinePlayers())
			{
				if(cc.getMembers().contains(members.getUniqueId().toString()))
				{
					///Der Spieler &f%player% &ewurde zum Stellvertreter im &5perma&fnenten &eChannel &f%channel% &abefördert&e!
					members.spigot().sendMessage(utility.tctl(
							plugin.getYamlHandler().getL().getString(language+"PCVice.Promoted")
							.replace("%player%", args[2]).replace("%channel%", cc.getNameColor()+cc.getName())));
				}
			}
		}
		return;
	}
}