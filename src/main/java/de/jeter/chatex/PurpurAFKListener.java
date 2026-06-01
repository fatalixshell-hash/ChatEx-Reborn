package de.jeter.chatex;

import de.jeter.chatex.utils.Config;
import de.jeter.chatex.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public class PurpurAFKListener implements Listener {

    public boolean register() {
        try {
            Class<? extends Event> eventClass = Class.forName("org.purpurmc.purpur.event.PlayerAFKEvent").asSubclass(Event.class);
            EventExecutor executor = (listener, event) -> handleAfkEvent(event);
            ChatEx.getInstance().getServer().getPluginManager().registerEvent(eventClass, this, EventPriority.NORMAL, executor, ChatEx.getInstance(), true);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    private void handleAfkEvent(Event event) throws EventException {
        try {
            Player player = (Player) event.getClass().getMethod("getPlayer").invoke(event);
            boolean goingAfk = (boolean) event.getClass().getMethod("isGoingAfk").invoke(event);
            updateTabListName(player, goingAfk);
        } catch (ReflectiveOperationException ex) {
            throw new EventException(ex);
        }
    }

    private void updateTabListName(Player player, boolean goingAfk) {
        if (goingAfk) {
            String format = Config.TABLIST_FORMAT.getString().replace("%afk", Config.AFK_FORMAT.getString());
            player.setPlayerListName(Utils.replacePlayerPlaceholders(player, format));
            return;
        }
        player.setPlayerListName(Utils.replacePlayerPlaceholders(player, Config.TABLIST_FORMAT.getString()));
    }
}
