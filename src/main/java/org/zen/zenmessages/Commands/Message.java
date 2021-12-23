package org.zen.zenmessages.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.zen.zenmessages.Manager.MessageManager;
import org.zen.zenmessages.Utils.IgnoreConfig;
import org.zen.zenmessages.ZenMessages;

import java.util.ArrayList;
import java.util.List;

public class Message extends Command implements TabExecutor {

    BaseComponent InvalidUsage =
            new TextComponent(ChatColor.RED + "" + ChatColor.BOLD  + "[!]" + ChatColor.RESET + ChatColor.RED + " Usage: /message <player> <message>");


    ZenMessages plugin = ZenMessages.getInstance();

    public Message() {
        super("message",
                null,
                "msg", "tell", "t", "whisper", "w"
        );
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {

        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer sender = (ProxiedPlayer) commandSender;
            if (args.length > 1) {
                BaseComponent NotOnline =
                        new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " " + args[0] + " is not online");
                BaseComponent Ignore =
                        new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " You can't send messages to that player");


                ArrayList<ProxiedPlayer> MessageToggled = ZenMessages.GetMessageToggledList();

                ProxiedPlayer receiver = ZenMessages.getInstance().getProxy().getPlayer(args[0]);
                if (receiver == null) {
                    sender.sendMessage(NotOnline);
                } else if ((!sender.hasPermission("ZenPvP.Toggle")) && MessageToggled.contains(receiver)) {
                    sender.sendMessage(NotOnline);
                } else if (MessageManager.Ignored(sender, receiver)) {
                    sender.sendMessage(Ignore);
                } else {
                    MessageManager.SendMessage(sender, receiver, args);
                }
            } else {
                sender.sendMessage(InvalidUsage);
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> Empty = new ArrayList<String>();
        Empty.add(" ");
        if (args.length == 1) {
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