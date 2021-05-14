package main.java.me.avankziar.scc.spigot.commands.mail;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import main.java.me.avankziar.scc.objects.ChatApi;
import main.java.me.avankziar.scc.objects.KeyHandler;
import main.java.me.avankziar.scc.objects.Mail;
import main.java.me.avankziar.scc.spigot.SimpleChatChannels;
import main.java.me.avankziar.scc.spigot.assistance.Utility;
import main.java.me.avankziar.scc.spigot.commands.tree.ArgumentConstructor;
import main.java.me.avankziar.scc.spigot.commands.tree.ArgumentModule;
import main.java.me.avankziar.scc.spigot.database.MysqlHandler.Type;
import main.java.me.avankziar.scc.spigot.objects.PluginSettings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class ARGSend extends ArgumentModule
{
	private SimpleChatChannels plugin;
	
	public ARGSend(SimpleChatChannels plugin, ArgumentConstructor argumentConstructor)
	{
		super(argumentConstructor);
		this.plugin = plugin;
	}

	@Override
	public void run(CommandSender sender, String[] args) throws IOException
	{
		String senderuuid = plugin.getYamlHandler().getConfig().getString("Mail.ConsoleReplacerInSendedMails");
		String sendername = plugin.getYamlHandler().getConfig().getString("Mail.ConsoleReplacerInSendedMails");
		boolean isPlayer = false;
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			senderuuid = player.getUniqueId().toString();
			sendername = player.getName();
			isPlayer = true;
		}
		String reciver = args[1];
		LinkedHashMap<UUID, String> recivers = new LinkedHashMap<>(); //uuid, name
		String ccuuid = "";
		String ccname = "";
		String ccseperator = plugin.getYamlHandler().getConfig().getString("Mail.CCSeperator");
		if(reciver.contains(ccseperator))
		{
			String[] split = reciver.split(ccseperator);
			for(String s : split)
			{
				UUID uuid = Utility.convertNameToUUID(s);
				if(uuid == null)
				{
					sender.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdMail.Send.PlayerNotExist")));
					return;
				}
				ccuuid += uuid.toString()+ccseperator;
				ccname += s+ccseperator;
				recivers.put(uuid, s);
			}
			ccuuid = ccuuid.substring(0, ccuuid.length()-ccseperator.length());
			ccname = ccname.substring(0, ccname.length()-ccseperator.length());
		} else
		{
			UUID uuid = Utility.convertNameToUUID(reciver);
			if(uuid == null)
			{
				sender.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdMail.Send.PlayerNotExist")));
				return;
			}
			ccuuid += uuid.toString();
			ccname += reciver;
			recivers.put(uuid, reciver);
		}
		String subject = "";
		String rawText = "";
		boolean seperator = false;
		for(int i = 2; i < args.length; i++)
		{
			if(args[i].equals(plugin.getYamlHandler().getConfig().getString("Mail.SubjectMessageSeperator"))
					&& !seperator)
			{
				seperator = true;
				continue;
			}
			if(seperator)
			{
				rawText += args[i]+" ";
			} else
			{
				subject += args[i]+" ";
			}
		}
		if(rawText.isEmpty())
		{
			sender.sendMessage(ChatApi.tl(plugin.getYamlHandler().getLang().getString("CmdMail.Send.OneWordMinimum")));
			return;
		}
		boolean alreadySended = false;
		for(Entry<UUID, String> set : recivers.entrySet())
		{
			UUID u = set.getKey();
			String s = set.getValue();
			String ccu = ccuuid;
			String ccn = ccname;
			if(ccu.contains(ccseperator+u.toString()))
			{
				ccu = ccu.replace(ccseperator+u.toString(), "");
				ccn = ccn.replace(ccseperator+s, "");
			} else if(ccu.contains(u.toString()+ccseperator))
			{
				ccu = ccu.replace(u.toString()+ccseperator, "");
				ccn = ccn.replace(s+ccseperator, "");
			} else
			{
				//Only set, if cc is one person, and the person is the ONLY reciver.
				ccu = "";
				ccn = "";
			}
			final Mail mail = new Mail(0, senderuuid, sendername, u, s,
					ccu, ccn, System.currentTimeMillis(), 0L, subject, rawText);
			plugin.getMysqlHandler().create(Type.MAIL, mail);
			if(isPlayer && !alreadySended)
			{
				alreadySended = true;
				Player originPlayer = (Player) sender;
				originPlayer.spigot().sendMessage(ChatApi.hoverEvent(plugin.getYamlHandler().getLang().getString("CmdMail.Send.Sended"),
						HoverEvent.Action.SHOW_TEXT,
						plugin.getYamlHandler().getLang().getString("CmdMail.Send.SendedHover")
						.replace("%subject%", subject)
						.replace("%cc%", (ccn.isEmpty() ? reciver : ccn))));
			}
			Player player = plugin.getServer().getPlayer(u);
			if(player != null)
			{
				player.spigot().sendMessage(ChatApi.apiChat(
						plugin.getYamlHandler().getLang().getString("CmdMail.Send.HasNewMail"),
						ClickEvent.Action.RUN_COMMAND,
						PluginSettings.settings.getCommands(KeyHandler.MAIL).trim(),
						HoverEvent.Action.SHOW_TEXT,
						plugin.getYamlHandler().getLang().getString("CmdMail.Send.Hover")));
			}
		}
	}
}