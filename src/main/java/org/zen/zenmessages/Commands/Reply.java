package org.zen.zenmessages.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.zen.zenmessages.Manager.MessageManager;

public class Reply extends Command {
    BaseComponent NoArgs = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED +" Usage: /reply <message>");

    public Reply() {
        super("reply", null, "r");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer sender = (ProxiedPlayer) commandSender;
            if (args.length == 0) {
                sender.sendMessage(NoArgs);
            } else {
                MessageManager.ReplyMessage(sender, args);
            }
        }
    }
}


