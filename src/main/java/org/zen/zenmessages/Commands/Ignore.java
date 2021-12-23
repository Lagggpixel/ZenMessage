package org.zen.zenmessages.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.zen.zenmessages.Manager.MessageManager;
import org.zen.zenmessages.ZenMessages;

import java.util.ArrayList;

public class Ignore extends Command implements TabExecutor {
    ZenMessages plugin = ZenMessages.getInstance();
    BaseComponent InvalidUsage = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " Usage: /ignore <add/remove> <player>");
    BaseComponent NotOnline = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " Error, I can't find that player");

    public Ignore() {
        super("ignore");
    }

    @Override
    public void execute(CommandSender CommandSender, String[] args) {
        if (CommandSender instanceof ProxiedPlayer) {
            ProxiedPlayer sender = (ProxiedPlayer) CommandSender;
            if(args.length != 2) {
                sender.sendMessage(InvalidUsage);
            } else {

                if (args[0].equalsIgnoreCase("add")) {
                    ProxiedPlayer player = plugin.getProxy().getPlayer(args[1]);
                    if (player == null) {
                        sender.sendMessage(NotOnline);
                    } else {
                        MessageManager.AddIgnore(sender, player);
                    }

                } else if (args[0].equalsIgnoreCase("remove")) {
                    ProxiedPlayer player = plugin.getProxy().getPlayer(args[1]);
                    if (player == null) {
                        sender.sendMessage(NotOnline);
                    } else {
                        MessageManager.RemoveIgnore(sender, player);
                    }
                } else {
                    sender.sendMessage(InvalidUsage);
                }

            }

        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> Empty = new ArrayList<String>();
        Empty.add(" ");

        if (args.length == 1) {
            ArrayList<String> AddRemove = new ArrayList<String>();
            AddRemove.add("add");
            AddRemove.add("remove");
            return AddRemove;
        } else if (args.length == 2) {
            ArrayList<ProxiedPlayer> MessageToggledList = ZenMessages.GetMessageToggledList();
            ArrayList<String> players = new ArrayList<>();

            for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
                if (MessageToggledList != null) {
                    if (!MessageToggledList.contains(player)) {
                        players.add(player.getName());
                    }
                }
            }
            return players;
        }

        return Empty;
    }
}
