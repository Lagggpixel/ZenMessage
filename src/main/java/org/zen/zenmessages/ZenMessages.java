package org.zen.zenmessages;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import org.zen.zenmessages.Commands.*;
import org.zen.zenmessages.Utils.IgnoreConfig;
import org.zen.zenmessages.Utils.ReplyConfig;

import java.util.ArrayList;

public final class ZenMessages extends Plugin {

    private static ZenMessages instance;
    private static ArrayList<ProxiedPlayer> SocialSpyList = new ArrayList<ProxiedPlayer>();
    private static ArrayList<ProxiedPlayer> MessageToggledList = new ArrayList<ProxiedPlayer>();

    @Override
    public void onEnable() {
        instance = this;
        RegisterCommands();
        SetupConfigs();
    }

    @Override
    public void onDisable() {
    }

    public void RegisterCommands() {
        this.getProxy().getPluginManager().registerCommand(this, new Message());
        this.getProxy().getPluginManager().registerCommand(this, new Reply());
        this.getProxy().getPluginManager().registerCommand(this, new ToggleMessage());
        this.getProxy().getPluginManager().registerCommand(this, new MessageMod());
        this.getProxy().getPluginManager().registerCommand(this, new Ignore());
    }

    public void SetupConfigs() {
        ReplyConfig.Setup();
        IgnoreConfig.Setup();
    }

    public static ZenMessages getInstance() {
        return instance;
    }

    public static ArrayList<ProxiedPlayer> GetSocialSpyList() {
        return SocialSpyList;
    }
    public static void SetSocialSpyList(ArrayList<ProxiedPlayer> socialSpyList) {
        SocialSpyList = socialSpyList;
    }

    public static ArrayList<ProxiedPlayer> GetMessageToggledList() {
        return MessageToggledList;
    }
    public static void SetMessageToggledList(ArrayList<ProxiedPlayer> messageToggledList) {
        MessageToggledList = messageToggledList;
    }
}
