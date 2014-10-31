package de.JeterLP.ChatManager.Plugins;

import de.JeterLP.ChatManager.ChatEX;
import de.JeterLP.ChatManager.Utils.HookManager;
import de.JeterLP.ChatManager.Utils.Utils;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 */
public class PluginManager implements PermissionsPlugin {

    private static PermissionsPlugin handler;

    public static PermissionsPlugin getInstance() {
        return ChatEX.getManager();
    }

    public PluginManager() {
        ChatEX.debug("Checking for Plugins...");
        if (HookManager.checkPEX()) {
            handler = new PermissionsEx();
        } else if (HookManager.checkBPerms()) {
            handler = new bPermissions();
        } else if (HookManager.checkVault() && Vault.setupChat()) {
            handler = new Vault();
        } else {
            handler = new Nothing();
        }
        ChatEX.debug("Plugin found!");
    }

    @Override
    public String getName() {
        ChatEX.debug("Getting name of plugin...");
        return handler.getName();
    }

    @Override
    public String getPrefix(Player p) {
        ChatEX.debug("Getting prefix from " + p.getName());
        return handler.getPrefix(p);
    }

    @Override
    public String getSuffix(Player p) {
        ChatEX.debug("Getting suffix from " + p.getName());
        return handler.getSuffix(p);
    }

    @Override
    public String[] getGroupNames(Player p) {
        ChatEX.debug("Getting groups from " + p.getName());
        return handler.getGroupNames(p);
    }

    @Override
    public String getMessageFormat(Player p) {
        ChatEX.debug("Getting message-format from " + p.getName());
        return Utils.replaceColors(handler.getMessageFormat(p));
    }

    @Override
    public String getGlobalMessageFormat(Player p) {
        ChatEX.debug("Getting global message-format from " + p.getName());
        return Utils.replaceColors(handler.getGlobalMessageFormat(p));
    }
}
