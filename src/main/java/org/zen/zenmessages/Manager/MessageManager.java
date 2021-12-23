package org.zen.zenmessages.Manager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.zen.zenmessages.Utils.IgnoreConfig;
import org.zen.zenmessages.Utils.ReplyConfig;
import org.zen.zenmessages.ZenMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageManager {

    static ZenMessages instance = ZenMessages.getInstance();

    public static void SendMessage(ProxiedPlayer sender, ProxiedPlayer receiver, String[] args) {
        String server = sender.getServer().getInfo().getName();
        ArrayList<ProxiedPlayer> spy = ZenMessages.GetSocialSpyList();
        BaseComponent MessageSender = new TextComponent(
                ChatColor.GOLD + "(" + ChatColor.YELLOW + server + ChatColor.GOLD + ") (" + ChatColor.RED + "me" + ChatColor.GOLD + " >> " + ChatColor.YELLOW + receiver.getName() + ChatColor.GOLD + ")"
        );
        BaseComponent MessageReceiver = new TextComponent(
                ChatColor.GOLD + "(" + ChatColor.YELLOW + server + ChatColor.GOLD + ") (" + ChatColor.YELLOW + sender.getName() + ChatColor.GOLD + " >> " + ChatColor.RED + "me" + ChatColor.GOLD + ")"
        );
        BaseComponent MessageMod = new TextComponent(
                ChatColor.GOLD + "(" + ChatColor.YELLOW + server + ChatColor.GOLD + ") (" + ChatColor.YELLOW + sender.getName() + ChatColor.GOLD + " >> " + ChatColor.YELLOW + receiver.getName() + ChatColor.GOLD + ")"
        );

        int i = 0;
        for (String arg : args) {
            if (i != 0) {
                MessageSender.addExtra(" " + arg);
                MessageReceiver.addExtra(" " + arg);
                MessageMod.addExtra(" " + arg);
            }
            i++;
        }

        ReplyConfig.Get().set(String.valueOf(sender.getName()), String.valueOf(receiver.getName()));
        ReplyConfig.Get().set(String.valueOf(receiver.getName()), String.valueOf(sender.getName()));
        ReplyConfig.Save();
        sender.sendMessage(MessageSender);
        receiver.sendMessage(MessageReceiver);

        if (!spy.isEmpty()) {
            for (ProxiedPlayer player : spy) {
                player.sendMessage(MessageMod);
            }
        }
    }

    public static void ReplyMessage(ProxiedPlayer sender, String[] args) {
        String senderName = String.valueOf(sender.getName());
        String receiverName = ReplyConfig.Get().getString(senderName);
        ProxiedPlayer receiver = ZenMessages.getInstance().getProxy().getPlayer(receiverName);
        ArrayList<ProxiedPlayer> MessageToggled = ZenMessages.GetMessageToggledList();

        ArrayList<ProxiedPlayer> spy = ZenMessages.GetSocialSpyList();

        BaseComponent NotOnlineMessage = new TextComponent(
                ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " The person you are trying to message is not online."
        );

        BaseComponent Ignore = new TextComponent(
                ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " You can't send messages to that player"
        );

        if (receiver == null || MessageToggled.contains(receiver)) {

            sender.sendMessage(NotOnlineMessage);
        } else if (MessageManager.Ignored(sender, receiver)) {
            sender.sendMessage(Ignore);
        } else {
            String server = sender.getServer().getInfo().getName();
            BaseComponent MessageSender = new TextComponent(
                    ChatColor.GOLD + "(" + ChatColor.YELLOW + server + ChatColor.GOLD + ") (" + ChatColor.RED + "me" + ChatColor.GOLD + " >> " + ChatColor.YELLOW + receiver.getName() + ChatColor.GOLD + ")"
            );
            BaseComponent MessageReceiver = new TextComponent(
                    ChatColor.GOLD + "(" + ChatColor.YELLOW + server + ChatColor.GOLD + ") (" + ChatColor.YELLOW + sender.getName() + ChatColor.GOLD + " >> " + ChatColor.RED + "me" + ChatColor.GOLD + ")"
            );

            BaseComponent MessageMod = new TextComponent(
                    ChatColor.GOLD + "(" + ChatColor.YELLOW + server + ChatColor.GOLD + ") (" + ChatColor.YELLOW + sender.getName() + ChatColor.GOLD + " >> " + ChatColor.YELLOW + receiver.getName() + ChatColor.GOLD + ")"
            );

            for (String arg : args) {
                MessageSender.addExtra(" " + arg);
                MessageReceiver.addExtra(" " + arg);
                MessageMod.addExtra(" " + arg);
            }

            ReplyConfig.Get().set(String.valueOf(sender.getName()), String.valueOf(receiver.getName()));
            ReplyConfig.Get().set(String.valueOf(receiver.getName()), String.valueOf(sender.getName()));
            ReplyConfig.Save();
            sender.sendMessage(MessageSender);
            receiver.sendMessage(MessageReceiver);

            if (!spy.isEmpty()) {
                for (ProxiedPlayer player : spy) {
                    player.sendMessage(MessageMod);
                }
            }

        }

    }

    public static void AddIgnore(ProxiedPlayer player, ProxiedPlayer ignored) {
        TextComponent Error = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " That player is already on your ignore list");
        TextComponent Success = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " Successfully added " + ignored.getName() + " into your ignore list");

        String playerUUID = player.getUniqueId().toString();
        String ignoredUUID = ignored.getUniqueId().toString();
        List<?> IgnoreList = IgnoreConfig.Get().getList(playerUUID);
        List<String> IgnoreStringList;

        if (IgnoreConfig.Get().getList(playerUUID) != null) {
            IgnoreStringList = (List<String>) IgnoreList;
            if (!IgnoreConfig.Get().getList(playerUUID).contains(ignoredUUID)) {
                IgnoreStringList.add(ignoredUUID);
                player.sendMessage(Success);
            } else {
                player.sendMessage(Error);
            }
        } else {
            IgnoreStringList = new ArrayList<String>();
            IgnoreStringList.add(ignoredUUID);
        }

        IgnoreConfig.Get().set(playerUUID, IgnoreStringList);
        IgnoreConfig.Save();
    }

    public static void RemoveIgnore(ProxiedPlayer player, ProxiedPlayer ignored) {
        TextComponent Error = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " That player is not on your ignore list");
        TextComponent Success = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " Successfully removed " + ignored.getName() + " from your ignore list");

        String playerUUID = player.getUniqueId().toString();
        String ignoredUUID = ignored.getUniqueId().toString();

        List<String> IgnoreList = null;

        if (IgnoreConfig.Get().getList(playerUUID) != null) {
            IgnoreList = (List<String>) IgnoreConfig.Get().getList(playerUUID);
            if (IgnoreList.contains(ignoredUUID)) {
                IgnoreList.remove(ignoredUUID);
                player.sendMessage(Success);
            } else {
                player.sendMessage(Error);
            }
        } else {
            player.sendMessage(Error);
        }

        IgnoreConfig.Get().set(playerUUID, IgnoreList);
        IgnoreConfig.Save();
    }

    public static Boolean Ignored(ProxiedPlayer sender, ProxiedPlayer receiver) {
        String senderUUID = sender.getUniqueId().toString();
        String receiverUUID = receiver.getUniqueId().toString();

        List<?> IgnoreListSender = IgnoreConfig.Get().getList(senderUUID);
        List<?> IgnoreListReceiver = IgnoreConfig.Get().getList(receiverUUID);

        if (IgnoreListReceiver != null) {
            if (IgnoreListReceiver.contains(senderUUID)) {
                return true;
            }
        }
        if (IgnoreListSender != null) {
            if (IgnoreListSender.contains(receiverUUID)) {
                return true;
            }
        }

        return false;

    }
}
