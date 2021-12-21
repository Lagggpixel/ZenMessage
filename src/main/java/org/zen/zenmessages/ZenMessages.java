package org.zen.zenmessages;

import net.md_5.bungee.api.plugin.Plugin;
import org.zen.zenmessages.Commands.Message;
import org.zen.zenmessages.Commands.Reply;
import org.zen.zenmessages.Utils.ReplyConfig;

public final class ZenMessages extends Plugin {

    private static ZenMessages instance;

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
    }

    public void SetupConfigs() {
        ReplyConfig.Setup();
    }

    public static ZenMessages getInstance() {
        return instance;
    }
}
