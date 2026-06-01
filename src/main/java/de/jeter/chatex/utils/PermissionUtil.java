package de.jeter.chatex.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class PermissionUtil {

    private PermissionUtil() {
    }

    public static boolean has(CommandSender sender, String permission) {
        return sender != null && (sender.isOp() || sender.hasPermission(permission));
    }

    public static boolean has(Player player, String permission) {
        return player != null && (player.isOp() || player.hasPermission(permission));
    }

    public static boolean hasRestrictionBypass(Player player, String permission) {
        if (player == null) {
            return false;
        }
        if (Config.PERMISSIONS_OP_BYPASSES_RESTRICTIONS.getBoolean() && player.isOp()) {
            return true;
        }
        return player.hasPermission(permission);
    }

    public static boolean canUseAdminCommand(CommandSender sender, String permission) {
        return has(sender, permission);
    }
}
