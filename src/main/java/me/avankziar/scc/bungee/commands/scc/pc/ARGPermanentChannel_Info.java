package main.java.me.avankziar.scc.bungee.commands.scc.pc;

import java.util.ArrayList;
import java.util.UUID;

import main.java.me.avankziar.scc.bungee.SimpleChatChannels;
import main.java.me.avankziar.scc.bungee.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.scc.bungee.commands.tree.ArgumentModule;
import main.java.me.avankziar.scc.bungee.objects.BypassPermission;
import main.java.me.avankziar.scc.bungee.objects.ChatUserHandler;
import main.java.me.avankziar.scc.objects.ChatApi;
import main.java.me.avankziar.scc.objects.ChatUser;
import main.java.me.avankziar.scc.objects.PermanentChannel;
import main.java.me.avankziar.scc.objects.chat.Channel;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ARGPermanentChannel_Info extends ArgumentModule
{
	private SimpleChatChannels plugin;
	
	public ARGPermanentChannel_Info(SimpleChatChannels plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args)
	{
		ProxiedPlayer player = (ProxiedPlayer) sender;
		String channel = args[2];
		PermanentChannel cc = null;
		if(args.length == 2)
		{
			cc = PermanentChannel.getChannelFromPlayer(player.getUniqueId().toString());
			if(cc == null)
			{
				player.sendMessage(ChatApi.tctl(plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.YouAreNotInAChannel")));
				return;
			}
		} else
		{
			cc = PermanentChannel.getChannelFromName(channel);
			if(cc == null)
			{
				player.sendMessage(ChatApi.tctl(plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.YouAreNotInAChannel")
						.replace("%channel%", channel)));
				return;
			}
		}
		Channel c = plugin.getChannel("Permanent");
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.Headline")
				.replace("%channel%", cc.getNameColor()+cc.getName())));
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.ID")
				.replace("%id%", cc.getId()+"")));
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.Creator")
				.replace("%creator%", getPlayer(cc.getCreator()))));
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.Vice")
				.replace("%amount%", String.valueOf(cc.getVice().size()))
				.replace("%vice%", getPlayers(cc.getVice()))));
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.Members")
				.replace("%amount%", String.valueOf(cc.getMembers().size()))
				.replace("%members%", getPlayers(cc.getMembers()))));
		if(cc.getPassword()!=null)
		{
			if(cc.getCreator().equals(player.getUniqueId().toString())
					|| cc.getVice().contains(player.getUniqueId().toString())
					|| player.hasPermission(BypassPermission.PERMBYPASSPC))
			{
				player.sendMessage(ChatApi.tctl(
						plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.Password")
						.replace("%password%", cc.getPassword())));
			}
		}
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.Symbol")
				.replace("%symbol%", (c != null) ? c.getSymbol() : "" + cc.getSymbolExtra())));
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.NameColor")
				.replace("%color%", cc.getNameColor()+cc.getName())));
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.ChatColor")
				.replace("%color%", cc.getChatColor())));
		player.sendMessage(ChatApi.tctl(
				plugin.getYamlHandler().getLang().getString("CmdScc.PermanentChannel.Info.Banned")
				.replace("%amount%", String.valueOf(cc.getBanned().size()))
				.replace("%banned%", getPlayers(cc.getBanned()))));
		return;
	}
	
	private String getPlayer(String uuid)
	{
		return (ChatUserHandler.getChatUser(UUID.fromString(uuid)) != null) ? ChatUserHandler.getChatUser(UUID.fromString(uuid)).getName() : uuid;
	}
	
	private String getPlayers(ArrayList<String> uuids)
	{
		String s = "[";
		if(!uuids.isEmpty())
		{
			for(int i = 0; i < uuids.size(); i++)
			{
				String uuid = uuids.get(i);
				try
				{
					ChatUser cuu = ChatUserHandler.getChatUser(UUID.fromString(uuid));
					if(cuu != null && !uuid.equals("null"))
					{
						if(uuids.size()-1 == i)
						{
							s += cuu.getName();
						} else
						{
							s += cuu.getName()+", ";
						}
					}
				} catch(IllegalArgumentException e)
				{
					continue;
				}
			}
		}
		s += "]";
		return s;
	}
}