package main.java.me.avankziar.simplechatchannels.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import main.java.me.avankziar.simplechatchannels.spigot.SimpleChatChannels;
import main.java.me.avankziar.simplechatchannels.spigot.interfaces.CustomChannel;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandHelper
{
	private SimpleChatChannels plugin;
	private String scc = ".CmdScc.";
	
	public CommandHelper(SimpleChatChannels plugin)
	{
		this.plugin = plugin;
	}
	
	public void scc(Player player, String language)
	{
		if(player.hasPermission("scc.option.admin"))
		{
			player.spigot().sendMessage(plugin.getUtility().tcl(plugin.getYamlHandler().getL().getString(language+scc+"Info.msg10")));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg01"),
					ClickEvent.Action.SUGGEST_COMMAND, "/scc <channel>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg02"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc mute <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg03"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc unmute <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg04"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ignore <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg05"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ignorelist"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg06"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc wordfilter <add/remove> <Word>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg07"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc broadcast <Message>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg08"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc playerlist [String]"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg09"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc bungee"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg11"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc grouplist [String]"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg12"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc cccreate <Name> [Password]"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg13"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ccjoin <Name>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg14"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ccleave"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg15"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc cckick <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg16"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ccban <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg17"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ccunban <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg18"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc changepassword <Password>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg19"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc channelinfo"));
		} else
		{
			player.spigot().sendMessage(plugin.getUtility().tcl(plugin.getYamlHandler().getL().getString(language+scc+"Info.msg10")));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg01"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc <channel>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg04"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ignore <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg05"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ignorelist"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg08"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc playerlist [String]"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg11"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc grouplist [String]"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg12"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc cccreate <Name> [Password]"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg13"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ccjoin <Name>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg14"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ccleave"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg15"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc cckick <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg16"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ccban <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg17"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc ccunban <Player>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg18"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc changepassword <Password>"));
			player.spigot().sendMessage(plugin.getUtility().clickEvent(
					plugin.getYamlHandler().getL().getString(language+scc+"Info.msg19"),
					ClickEvent.Action.SUGGEST_COMMAND,  "/scc channelinfo"));
		}
	}
	
	public void playergrouplist(Player player, String language, List<BaseComponent> list, String path)
	{
		TextComponent MSG = plugin.getUtility().tc("");
		if(list.isEmpty())
		{
			player.spigot().sendMessage(plugin.getUtility().tcl(
					plugin.getYamlHandler().getL().getString(language+scc+path+".WrongInput")));
			return;
		}
		MSG.setExtra(list);
		player.spigot().sendMessage(plugin.getUtility().tcl(
				plugin.getYamlHandler().getL().getString(language+scc+path+".Headline")));
		player.spigot().sendMessage(MSG);
	}
	
	public void channeltoggle(Player player, String[] args, String language, String channel, String replacer)
	{
		if((boolean) plugin.getMysqlHandler().getDataI(player, "channel_"+channel, "player_uuid"))
		{
			///Du hast den Channel %channel% &causgeblendet!
			plugin.getMysqlHandler().updateDataI(player, false, "channel_"+channel);
			player.spigot().sendMessage(plugin.getUtility().tcl(plugin.getYamlHandler().getL().getString(language+scc+"Channel.Off")
					.replace("%channel%", replacer)));
		} else
		{
			///Du hast den Channel %channel% &aeingeblendet!
			plugin.getMysqlHandler().updateDataI(player, true, "channel_"+channel);
			player.spigot().sendMessage(plugin.getUtility().tcl(plugin.getYamlHandler().getL().getString(language+scc+"Channel.On")
					.replace("%channel%", replacer)));
		}
		return;
	}
	
	public void optiontoggle(Player player, String[] args, String language, String optionperm,
			String option, String replacer)
	{
		if(((boolean) plugin.getMysqlHandler().getDataI(player, option, "player_uuid")))
		{
			plugin.getMysqlHandler().updateDataI(player, false, option);
			///Du hast den Channel %channel% &causgeblendet!
			player.spigot().sendMessage(plugin.getUtility().tcl(plugin.getYamlHandler().getL().getString(language+scc+"Channel.Off")
					.replace("%channel%", replacer)));
		} else
		{
			plugin.getMysqlHandler().updateDataI(player, true, option);
			///Du hast den Channel %channel% &aeingeblendet!
			player.spigot().sendMessage(plugin.getUtility().tcl(plugin.getYamlHandler().getL().getString(language+scc+"Channel.On")
					.replace("%channel%", replacer)));
		}
	}
	
	//Obsolete
	public void cccreate(Player player, String language, CustomChannel cc, String name, String password)
	{
		ArrayList<Player> members = new ArrayList<Player>();
		members.add(player);
		cc = new CustomChannel(name, player, members, password, new ArrayList<Player>());
		CustomChannel.addCustomChannel(cc);
		if(password==null)
		{
			player.spigot().sendMessage(plugin.getUtility().tc(plugin.getUtility().tl(
					plugin.getYamlHandler().getL().getString(language+scc+"ChannelCreate.msg02")
					.replace("%channel%", cc.getName()))));
		} else
		{
			player.spigot().sendMessage(plugin.getUtility().tc(plugin.getUtility().tl(
					plugin.getYamlHandler().getL().getString(language+scc+"ChannelCreate.msg03")
					.replace("%channel%", cc.getName())
					.replace("%password%", password))));
		}
	}
	
	//Obsolete
	public void ccjoin(Player player, String language, String name, String password)
	{
		CustomChannel cc = CustomChannel.getCustomChannel(name);
		CustomChannel oldcc = CustomChannel.getCustomChannel(player);
		if(oldcc!=null)
		{
			player.spigot().sendMessage(plugin.getUtility().tcl(
					plugin.getYamlHandler().getL().getString(language+scc+"ChannelJoin.msg06")));
			return;
		}
		if(cc==null)
		{
			player.spigot().sendMessage(plugin.getUtility().tcl(
					plugin.getYamlHandler().getL().getString(language+scc+"ChannelJoin.msg01")
					.replace("%name%", name)));
			return;
		}
		if(cc.getBanned().contains(player))
		{
			player.spigot().sendMessage(plugin.getUtility().tcl(
					plugin.getYamlHandler().getL().getString(language+scc+"ChannelJoin.msg07")));
			return;
		}
		if(password==null)
		{
			if(cc.getPassword()!=null)
			{
				player.spigot().sendMessage(plugin.getUtility().tcl(
						plugin.getYamlHandler().getL().getString(language+scc+"ChannelJoin.msg02")));
				return;
			}
		} else
		{
			if(cc.getPassword()==null)
			{
				player.spigot().sendMessage(plugin.getUtility().tcl(
						plugin.getYamlHandler().getL().getString(language+scc+"ChannelJoin.msg04")));
				return;
			}
			if(!cc.getPassword().equals(password))
			{
				player.spigot().sendMessage(plugin.getUtility().tcl(
						plugin.getYamlHandler().getL().getString(language+scc+"ChannelJoin.msg03")));
				return;
			}
		}
		cc.addMembers(player);
		player.spigot().sendMessage(plugin.getUtility().tcl(
				plugin.getYamlHandler().getL().getString(language+scc+"ChannelJoin.msg05")
				.replace("%channel%", cc.getName())));
		return;
	}
}