package org.zen.zenmessages.Manager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.zen.zenmessages.Utils.ReplyConfig;
import org.zen.zenmessages.ZenMessages;

import java.util.UUID;

public class MessageManager {

    static ZenMessages instance = ZenMessages.getInstance();

    public static void SendMessage(ProxiedPlayer sender, ProxiedPlayer receiver, String[] args) {
        String server = sender.getServer().getInfo().getName();
        BaseComponent MessageSender = new TextComponent(
                ChatColor.GOLD + "(" + ChatColor.YELLOW + server + ChatColor.GOLD + ") (" + ChatColor.RED + "me" + ChatColor.GOLD + " >> " + ChatColor.YELLOW + receiver.getName() + ChatColor.GOLD + ")"
        );
        BaseComponent MessageReceiver = new TextComponent(
                ChatColor.GOLD + "(" + ChatColor.YELLOW + server + ChatColor.GOLD + ") (" + ChatColor.YELLOW + sender.getName() + ChatColor.GOLD + " >> " + ChatColor.RED + "me" + ChatColor.GOLD + ")"
        );


        int i = 0;
        for (String arg : args) {
            if (i != 0) {
                MessageSender.addExtra(" " + arg);
                MessageReceiver.addExtra(" " + arg);
            }
            i++;
        }

        ReplyConfig.Get().set(String.valueOf(sender.getName()), String.valueOf(receiver.getName()));
        ReplyConfig.Get().set(String.valueOf(receiver.getName()), String.valueOf(sender.getName()));
        ReplyConfig.Save();
        sender.sendMessage(MessageSender);
        receiver.sendMessage(MessageReceiver);
    }

    public static void ReplyMessage(ProxiedPlayer sender, String[] args) {
        String senderName = String.valueOf(sender.getName());
        String receiverName = ReplyConfig.Get().getString(senderName);
        ProxiedPlayer receiver = ZenMessages.getInstance().getProxy().getPlayer(receiverName);

        if (receiver == null) {
            BaseComponent NotOnlineMessage = new TextComponent(
                    ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " The person you are trying to message is not online."
            );
            sender.sendMessage(NotOnlineMessage);
        } else {
            SendMessage(sender, receiver, args);
        }

    }
}
