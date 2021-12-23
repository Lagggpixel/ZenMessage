package org.zen.zenmessages.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.zen.zenmessages.ZenMessages;

import java.util.ArrayList;

public class MessageMod extends Command implements TabExecutor {

    BaseComponent InvalidUsage =
            new TextComponent(ChatColor.RED + "" + ChatColor.BOLD  + "[!]" + ChatColor.RESET + ChatColor.RED + " Usage: /messagespy");

    BaseComponent AddMessage = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " You can see all private messages now");
    BaseComponent RemoveMessage = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " You can only see your own messages now");


    public MessageMod() {
        super("messagemod", "ZenPvP.Spy");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length != 0) {
                player.sendMessage(InvalidUsage);
            } else {
                ArrayList<ProxiedPlayer> temp = ZenMessages.GetSocialSpyList();

                if (temp.contains(player)) {
                    temp.remove(player);
                    ZenMessages.SetSocialSpyList(temp);
                    player.sendMessage(RemoveMessage);

                } else {
                    temp.add(player);
                    ZenMessages.SetSocialSpyList(temp);
                    player.sendMessage(AddMessage);
                }


            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
