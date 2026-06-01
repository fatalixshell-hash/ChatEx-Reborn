package de.jeter.chatex.utils;

import de.jeter.chatex.ChatEx;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MentionManager {

    private MentionManager() {
    }

    public static String process(Player sender, String message) {
        if (!Config.MENTIONS_ENABLED.getBoolean()) {
            return message;
        }
        if (Config.MENTIONS_REQUIRE_PERMISSION.getBoolean() && !PermissionUtil.hasRestrictionBypass(sender, "chatex.mentions.use")) {
            return message;
        }

        String processed = message;
        Set<Player> mentionedPlayers = new HashSet<>();

        for (Player target : Bukkit.getOnlinePlayers()) {
            Pattern mentionPattern = Pattern.compile("(?i)(?<![A-Za-z0-9_])@" + Pattern.quote(target.getName()) + "(?![A-Za-z0-9_])");
            Matcher matcher = mentionPattern.matcher(processed);
            if (!matcher.find()) {
                continue;
            }

            mentionedPlayers.add(target);
            String replacement = Matcher.quoteReplacement(formatMention(sender, target));
            processed = matcher.replaceAll(replacement);
        }

        if (!mentionedPlayers.isEmpty() && Config.MENTIONS_NOTIFY_ENABLED.getBoolean()) {
            Bukkit.getScheduler().runTask(ChatEx.getInstance(), () -> {
                for (Player target : mentionedPlayers) {
                    notifyMention(sender, target);
                }
            });
        }

        return processed;
    }

    private static String formatMention(Player sender, Player target) {
        String format = Config.MENTIONS_FORMAT.getString();
        format = format.replace("%player", target.getName());
        format = format.replace("%sender", sender.getName());
        return Utils.replacePlayerPlaceholders(target, format);
    }

    private static void notifyMention(Player sender, Player target) {
        if (!target.isOnline()) {
            return;
        }
        if (Config.MENTIONS_NOTIFY_PERMISSION.getBoolean() && !PermissionUtil.hasRestrictionBypass(target, "chatex.mentions.notify")) {
            return;
        }

        if (Config.MENTIONS_SOUND_ENABLED.getBoolean()) {
            playMentionSound(target);
        }

        if (Config.MENTIONS_ACTIONBAR_ENABLED.getBoolean()) {
            String message = notificationMessage(Config.MENTIONS_ACTIONBAR_MESSAGE.getString(), sender, target);
            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }

        if (Config.MENTIONS_TITLE_ENABLED.getBoolean()) {
            String title = notificationMessage(Config.MENTIONS_TITLE_TEXT.getString(), sender, target);
            String subtitle = notificationMessage(Config.MENTIONS_TITLE_SUBTITLE.getString(), sender, target);
            target.sendTitle(title, subtitle, 5, 40, 10);
        }
    }

    private static void playMentionSound(Player target) {
        String soundName = Config.MENTIONS_SOUND_NAME.getString().toUpperCase(Locale.ROOT);
        try {
            Sound sound = Sound.valueOf(soundName);
            target.playSound(
                    target.getLocation(),
                    sound,
                    (float) Config.MENTIONS_SOUND_VOLUME.getDouble(),
                    (float) Config.MENTIONS_SOUND_PITCH.getDouble()
            );
        } catch (IllegalArgumentException ex) {
            ChatEx.getInstance().getLogger().warning("Invalid mention sound '" + soundName + "'. Mention sound skipped.");
        }
    }

    private static String notificationMessage(String message, Player sender, Player target) {
        String result = message.replace("%sender", sender.getName()).replace("%player", target.getName());
        return Utils.replacePlayerPlaceholders(target, result);
    }
}
