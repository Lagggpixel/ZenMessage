package org.zen.zenmessages.Commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.zen.zenmessages.ZenMessages;

import java.util.ArrayList;

public class ToggleMessage extends Command {
    ZenMessages plugin = ZenMessages.getInstance();
    BaseComponent AddMessage = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " Only server moderators can send you messages now");
    BaseComponent RemoveMessage = new TextComponent(ChatColor.RED + "" + ChatColor.BOLD + "[!]" + ChatColor.RESET + ChatColor.RED + " The entire server can send you messages now");


    public ToggleMessage() {
        super("togglemessage", "ZenPvP.Toggle", "tm", "messagetoggle");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof ProxiedPlayer) {
            ArrayList<ProxiedPlayer> temp = ZenMessages.GetMessageToggledList();
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (temp.contains(player)) {
                temp.remove(player);

                ZenMessages.SetMessageToggledList(temp);

                player.sendMessage(RemoveMessage);
            } else {
                temp.add(player);

                ZenMessages.SetMessageToggledList(temp);

                player.sendMessage(AddMessage);
            }
        } else {
            plugin.getProxy().getConsole().sendMessage(new TextComponent("[!] Error, Console can not perform this command."));
        }

    }
}
