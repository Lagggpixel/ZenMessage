package org.zen.zenmessages.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.zen.zenmessages.Manager.MessageManager;
import org.zen.zenmessages.ZenMessages;

public class Message extends Command {

    BaseComponent InvalidUsage =
            new TextComponent(ChatColor.RED + "" + ChatColor.BOLD  + "[!]" + ChatColor.RESET + ChatColor.RED + " Usage: /message <player> <message>");


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
                ProxiedPlayer receiver = ZenMessages.getInstance().getProxy().getPlayer(args[0]);
                if (receiver == null) {
                    sender.sendMessage(InvalidUsage);
                } else {
                    MessageManager.SendMessage(sender, receiver, args);
                }
            } else {
                sender.sendMessage(InvalidUsage);
            }
        }
    }
}