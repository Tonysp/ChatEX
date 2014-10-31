package de.JeterLP.ChatManager;

import de.JeterLP.ChatManager.Plugins.PluginManager;
import de.JeterLP.ChatManager.Utils.ChatLogger;
import de.JeterLP.ChatManager.Utils.Config;
import de.JeterLP.ChatManager.Utils.Locales;
import de.JeterLP.ChatManager.Utils.Utils;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author TheJeterLP
 */
public abstract class ChatListener implements Listener {

    public void register() {
        Bukkit.getServer().getPluginManager().registerEvents(this, ChatEX.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        if (!Config.CHANGE_JOIN_AND_QUIT.getBoolean()) return;
        String msg = (e.getPlayer().hasPlayedBefore() ? Locales.PLAYER_FIRST_JOIN.getString() : Locales.PLAYER_JOIN.getString());
        e.setJoinMessage(Utils.replacePlayerPlaceholders(e.getPlayer(), msg));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(final PlayerQuitEvent e) {
        if (!Config.CHANGE_JOIN_AND_QUIT.getBoolean()) return;
        String msg = Locales.PLAYER_QUIT.getString();
        e.setQuitMessage(Utils.replacePlayerPlaceholders(e.getPlayer(), msg));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onKick(final PlayerKickEvent e) {
        if (!Config.CHANGE_JOIN_AND_QUIT.getBoolean()) return;
        String msg = Locales.PLAYER_KICK.getString();
        e.setLeaveMessage(Utils.replacePlayerPlaceholders(e.getPlayer(), msg));
    }

    protected void execute(AsyncPlayerChatEvent event) {
        if (!event.getPlayer().hasPermission("chatex.allowchat")) {
            Map<String, String> rep = new HashMap<String, String>();
            rep.put("%perm", "chatex.allowchat");
            Locales.COMMAND_RESULT_NO_PERM.send(event.getPlayer(), rep);
            event.setCancelled(true);
            return;
        }

        String format = PluginManager.getInstance().getMessageFormat(event.getPlayer());
        boolean localChat = Config.RANGEMODE.getBoolean();
        boolean global = false;
        Player player = event.getPlayer();
        String chatMessage = event.getMessage();

        if (Utils.check(chatMessage, player)) {
            Map<String, String> rep = new HashMap<String, String>();
            rep.put("%perm", "chatex.bypassads");
            Locales.MESSAGES_AD.send(player, rep);
            event.setCancelled(true);
            return;
        }

        if (localChat) {
            ChatEX.debug("Local chat is enabled!");
            if (chatMessage.startsWith("!") && player.hasPermission("chatex.chat.global")) {
                ChatEX.debug("Global message!");
                chatMessage = chatMessage.replaceFirst("!", "");
                format = PluginManager.getInstance().getGlobalMessageFormat(event.getPlayer());
                global = true;
            }
            if (!global) {
                event.getRecipients().clear();
                ChatEX.debug("Adding recipients to the message...");
                event.getRecipients().addAll(Utils.getLocalRecipients(player));
            }
        }
        format = format.replace("%message", "%2$s").replace("%player", "%1$s");
        format = Utils.replacePlayerPlaceholders(player, format);
        ChatEX.debug("Setting format");
        event.setFormat(format);
        chatMessage = Utils.translateColorCodes(chatMessage, player);
        ChatEX.debug("Setting message!");
        event.setMessage(chatMessage);
        ChatEX.debug("Logging chatmessage...");
        ChatLogger.writeToFile(event.getPlayer(), event.getMessage());
    }

}
