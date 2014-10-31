package de.JeterLP.ChatManager.Utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * @author TheJeterLP
 */
public class HookManager {

    public static boolean checkVault() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");
        return plugin != null && plugin.isEnabled();
    }

    public static boolean checkPEX() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx");
        return plugin != null && plugin.isEnabled();
    }

    public static boolean checkBPerms() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("bPermissions");
        return plugin != null && plugin.isEnabled();
    }

    public static boolean checkFactions() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Factions");
        return plugin != null && plugin.isEnabled();
    }
}
