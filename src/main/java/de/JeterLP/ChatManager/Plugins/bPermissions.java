package de.JeterLP.ChatManager.Plugins;

import de.JeterLP.ChatManager.Utils.Config;
import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 */
public class bPermissions implements PermissionsPlugin {

    @Override
    public String getName() {
        return "bPermissions";
    }

    @Override
    public String getPrefix(Player p) {
        //if (!Config.MULTIPREFIXES.getBoolean()) {
        return ApiLayer.getValue(p.getWorld().getName(), CalculableType.USER, p.getName(), "prefix");
                //}
                /* String finalPrefix = "";
         * String[] groups = ApiLayer.getGroups(p.getWorld().getName(), CalculableType.USER, p.getName());
         * for (String group : groups) {
         * String groupPrefix = ApiLayer.getValue(p.getWorld().getName(), CalculableType.GROUP, group, "prefix");
         * if (groupPrefix != null && !groupPrefix.isEmpty()) {
         * finalPrefix += groupPrefix;
         * }
         * }
         * return finalPrefix; */
    }

    @Override
    public String getSuffix(Player p) {
        //if (!Config.MULTIPREFIXES.getBoolean()) {
        return ApiLayer.getValue(p.getWorld().getName(), CalculableType.USER, p.getName(), "suffix");
                //}
                /* String finalSuffix = "";
         * String[] groups = ApiLayer.getGroups(p.getWorld().getName(), CalculableType.USER, p.getName());
         * for (String group : groups) {
         * String groupSuffix = ApiLayer.getValue(p.getWorld().getName(), CalculableType.GROUP, group, "suffix");
         * if (groupSuffix != null && !groupSuffix.isEmpty()) {
         * finalSuffix += groupSuffix;
         * }
         * }
         * return finalSuffix; */
    }

    @Override
    public String[] getGroupNames(Player p) {
        return ApiLayer.getGroups(p.getWorld().getName(), CalculableType.USER, p.getName());
    }

    @Override
    public String getMessageFormat(Player p) {
        return Config.FORMAT.getString();
    }

    @Override
    public String getGlobalMessageFormat(Player p) {
        return Config.GLOBALFORMAT.getString();
    }

}
