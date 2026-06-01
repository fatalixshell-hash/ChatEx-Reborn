/*
 * This file is part of ChatEx
 * Copyright (C) 2022 ChatEx Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package de.jeter.chatex.utils;

import de.jeter.chatex.ChatEx;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventPriority;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public enum Config {

    CONFIG_VERSION("config-version", 2, "Internal config template version. Do not edit unless you know what you are doing."),
    CHECK_UPDATE("check-for-updates", true, "Should the plugin check for updates by itself?"),
    B_STATS("enable-bstats", true, "Do you want to use bstats?"),
    BUNGEECORD("bungeecord", false, "If you use bungeecord, players can chat cross-server wide with the range mode (! in front of the message).", "BungeeCord"),
    CROSS_SERVER_TIMEOUT("cross_server_timeout", 3, "If this timeout (In seconds) is exceeded the cross-server-message will not be send.", "cross-server-timeout"),
    FORMAT("message-format", "%prefix%displayname%suffix &8» &f%message", "The standard message-format."),
    GLOBALFORMAT("global-message-format", "%prefix%displayname%suffix &8» &f%message", "The message-format if ranged-mode is enabled."),
    MULTIPREFIXES("multi-prefixes", false, "Should the multi-prefixes be enabled?"),
    MULTISUFFIXES("multi-suffixes", false, "Should the multi-suffixes be enabled?"),
    RANGEMODE("ranged-mode", false, "Should the ranged-mode be enabled?"),
    RANGEPREFIX("ranged-prefix", "!", "The Prefix to use for Range Mode"),
    SHOW_NO_RECEIVER_MSG("show-no-players-near", false, "Should we check if any player would receiver your chat message?"),
    RANGE("chat-range", 100, "The range to talk to other players. Set to -1 to enable world-wide-chat"),
    LOGCHAT("logChat", false, "Should the chat be logged?", "log-chat"),
    DEBUG("debug", false, "Should the debug log be enabled?"),
    PRIORITY("EventPriority", EventPriority.LOWEST.name(), "Choose the Eventpriority here of ChatEx. Listeners are called in following order: LOWEST -> LOW -> NORMAL -> HIGH -> HIGHEST -> MONITOR", "event-priority"),
    LOCALE("Locale", "en-EN", "Which language do you want? (You can choose betwenn de-DE, fr-FR, pt-BR, zh-CN and en-EN by default.)", "locale"),
    PERMISSIONS_OP_BYPASSES_RESTRICTIONS("Permissions.OpBypassesRestrictions", true, "Allow OP players to bypass chat restrictions and bypass-style permission checks."),
    ADS_ENABLED("Ads.Enabled", true, "Should we check for ads?"),
    ADS_BYPASS("Ads.Bypass", Arrays.asList("127.0.0.1", "my-domain.com"), "A list with allowed ips or domains."),
    ADS_LOG("Ads.Log", true, "Should the ads be logged in a file?"),
    ADS_SMART_MANAGER("Ads.SmartManager", true, "Should the \"Smart Manager\" be used? (For more information read: https://github.com/TheJeterLP/ChatEx/wiki/Ad-Manager)"),
    ADS_SMART_DOMAIN_ENDINGS("Ads.SmartConfig.DomainEndings", Arrays.asList(
            "com", "net", "org", "de", "icu", "uk", "ru", "me", "info", "top", "xyz", "tk", "cn", "ga", "cf", "nl", "eu"
    ), "The endings the SmartManager applies the multiplier to."),
    ADS_REPLACE_COMMAS("Ads.ReplaceCommas", false, "Should commas be replaced with \".\" for the add test?"),
    ADS_SMART_MULTIPLIER("Ads.SmartConfig.Multiplier", 4, "If a domain pattern contains an ending from Ads.SmartConfig.DomainEndings the score get multiplied by this number."),
    ADS_SMART_UN_MULTIPLIER("Ads.SmartConfig.UnMultiplier", 1, "If a domain pattern contains NOT an ending from Ads.SmartConfig.DomainEndings the score get multiplied by this number."),
    ADS_THRESHOLD("Ads.Threshold.Block", 0.3, "The threshold required to cancel a message."),
    ADS_REDUCE_THRESHOLD("Ads.Threshold.ReduceThreshold", 0.1, "How much threshold is removed per message"),
    ADS_MAX_LENGTH("Ads.Threshold.MaxLinkLength", 10, "What the max detected link length is (For more information read: https://github.com/TheJeterLP/ChatEx/wiki/Ad-Manager)"),
    ANTISPAM_SECONDS("AntiSpam.Seconds", 5, "The delay between player messages to prevent spam"),
    ANTISPAM_ENABLED("AntiSpam.Enable", true, "Should antispam be enabled?"),
    BLOCKED_WORDS("BlockedWords", Arrays.asList("shit", "@everyone"), "A list of words that should be blocked."),
    CHANGE_TABLIST_NAME("Tablist.Change", true, "Do you want to have the prefixes and suffixes in the tablist?"),
    TABLIST_FORMAT("Tablist.format", "%prefix%player%suffix", "The format of the tablist name"),
    CHANGE_JOIN_AND_QUIT("Messages.JoinAndQuit.Enabled", false, "Do you want to change the join and the quit messages?"),
    RGB_COLORS("colors", null, "Requires 1.16+, Colors you want to use."),
    RGB_COLORS_EXAMPLE("colors.$g", "#00ff00", "Default color code. &g in chat will be used with the #00ff00."),
    AFK_PLACEHOLDER("AfkPlaceholder.Enabled", false, "Enable the %afk placeholder. You can use it to display AFK players on tablist. (Requires Essentials or Purpur)."),
    AFK_FORMAT("AfkPlaceholder.format", "&r[&7AFK&r] ", "The format of the afk placeholder."),
    MENTIONS_ENABLED("Mentions.Enabled", true, "Enable @player mentions in chat."),
    MENTIONS_REQUIRE_PERMISSION("Mentions.RequirePermission", false, "Require chatex.mentions.use to mention players."),
    MENTIONS_FORMAT("Mentions.Format", "&e@%player%&r", "How mentioned player names should look in chat."),
    MENTIONS_NOTIFY_ENABLED("Mentions.Notify.Enabled", true, "Notify players when they are mentioned."),
    MENTIONS_NOTIFY_PERMISSION("Mentions.Notify.RequirePermission", false, "Require chatex.mentions.notify to receive mention notifications."),
    MENTIONS_SOUND_ENABLED("Mentions.Notify.Sound.Enabled", true, "Play a sound when a player is mentioned."),
    MENTIONS_SOUND_NAME("Mentions.Notify.Sound.Name", "ENTITY_EXPERIENCE_ORB_PICKUP", "Bukkit sound name played for mentions."),
    MENTIONS_SOUND_VOLUME("Mentions.Notify.Sound.Volume", 1.0D, "Mention sound volume."),
    MENTIONS_SOUND_PITCH("Mentions.Notify.Sound.Pitch", 1.2D, "Mention sound pitch."),
    MENTIONS_ACTIONBAR_ENABLED("Mentions.Notify.ActionBar.Enabled", true, "Show an actionbar message when a player is mentioned."),
    MENTIONS_ACTIONBAR_MESSAGE("Mentions.Notify.ActionBar.Message", "&e%sender% mentioned you in chat.", "Actionbar message shown to mentioned players."),
    MENTIONS_TITLE_ENABLED("Mentions.Notify.Title.Enabled", false, "Show a title when a player is mentioned."),
    MENTIONS_TITLE_TEXT("Mentions.Notify.Title.Title", "&eMentioned", "Title text shown to mentioned players."),
    MENTIONS_TITLE_SUBTITLE("Mentions.Notify.Title.Subtitle", "&f%sender% mentioned you in chat.", "Subtitle text shown to mentioned players.");

    private static final int CURRENT_CONFIG_VERSION = 2;
    private static final List<String> LOCALES = List.of("de-DE", "en-EN", "es-ES", "fr-FR", "ja-JP", "pt-BR", "ru-RU", "tr-TR", "zh-CN");
    private static File f;
    private static YamlConfiguration cfg;
    private static ValidationResult lastValidationResult = new ValidationResult(List.of(), false);
    private static boolean generatedNewConfig;
    private final Object value;
    private final String path;
    private final String description;
    private final String[] deprecatedPaths;

    Config(String path, Object val, String description, String... deprecatedPaths) {
        this.path = path;
        this.value = val;
        this.description = description;
        this.deprecatedPaths = deprecatedPaths;
    }

    public static ValidationResult load() {
        File dataFolder = ChatEx.getInstance().getDataFolder();
        dataFolder.mkdirs();
        f = new File(dataFolder, "config.yml");
        generatedNewConfig = false;

        if (!f.exists()) {
            writeConfigTemplate(f);
        }

        reload(false);
        handleOutdatedConfig();
        lastValidationResult = validate();
        return lastValidationResult;
    }

    public static ValidationResult reload(boolean complete) {
        if (complete) {
            return load();
        }
        cfg = YamlConfiguration.loadConfiguration(f);
        return lastValidationResult;
    }

    public static ValidationResult getLastValidationResult() {
        return lastValidationResult;
    }

    public static boolean generatedNewConfig() {
        return generatedNewConfig;
    }

    public static int getCurrentConfigVersion() {
        return CURRENT_CONFIG_VERSION;
    }

    public static int getLoadedConfigVersion() {
        return cfg.getInt(CONFIG_VERSION.path, 1);
    }

    private static void handleOutdatedConfig() {
        if (getLoadedConfigVersion() >= CURRENT_CONFIG_VERSION) {
            return;
        }

        File newConfig = new File(ChatEx.getInstance().getDataFolder(), "config-new.yml");
        generatedNewConfig = !newConfig.exists();
        if (generatedNewConfig) {
            writeConfigTemplate(newConfig);
        }

        Logger logger = ChatEx.getInstance().getLogger();
        if (!cfg.contains(CONFIG_VERSION.path)) {
            logger.warning("Your ChatEx config.yml does not contain config-version. It is treated as an older ChatEx config.");
        } else {
            logger.warning("Your ChatEx config.yml uses config-version " + getLoadedConfigVersion() + ", current is " + CURRENT_CONFIG_VERSION + ".");
        }
        logger.warning("Your existing config.yml was NOT overwritten.");
        logger.warning("A documented template is available at: " + newConfig.getName());
        logger.warning("Review it and migrate settings manually when you are ready.");
    }

    private static void writeConfigTemplate(File target) {
        try {
            Files.writeString(target.toPath(), defaultConfigTemplate(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ChatEx.getInstance().getLogger().severe("Could not write " + target.getName() + ": " + ex.getMessage());
        }
    }

    public static ValidationResult validate() {
        List<String> warnings = new ArrayList<>();

        warnMissing(warnings, CONFIG_VERSION);
        warnMissing(warnings, FORMAT);
        warnMissing(warnings, GLOBALFORMAT);
        warnMissing(warnings, RANGEPREFIX);

        validateBoolean(warnings, CHECK_UPDATE);
        validateBoolean(warnings, B_STATS);
        validateBoolean(warnings, BUNGEECORD);
        validateBoolean(warnings, MULTIPREFIXES);
        validateBoolean(warnings, MULTISUFFIXES);
        validateBoolean(warnings, RANGEMODE);
        validateBoolean(warnings, SHOW_NO_RECEIVER_MSG);
        validateBoolean(warnings, LOGCHAT);
        validateBoolean(warnings, DEBUG);
        validateBoolean(warnings, PERMISSIONS_OP_BYPASSES_RESTRICTIONS);
        validateBoolean(warnings, ADS_ENABLED);
        validateBoolean(warnings, ADS_LOG);
        validateBoolean(warnings, ADS_SMART_MANAGER);
        validateBoolean(warnings, ADS_REPLACE_COMMAS);
        validateBoolean(warnings, ANTISPAM_ENABLED);
        validateBoolean(warnings, CHANGE_TABLIST_NAME);
        validateBoolean(warnings, CHANGE_JOIN_AND_QUIT);
        validateBoolean(warnings, AFK_PLACEHOLDER);
        validateBoolean(warnings, MENTIONS_ENABLED);
        validateBoolean(warnings, MENTIONS_REQUIRE_PERMISSION);
        validateBoolean(warnings, MENTIONS_NOTIFY_ENABLED);
        validateBoolean(warnings, MENTIONS_NOTIFY_PERMISSION);
        validateBoolean(warnings, MENTIONS_SOUND_ENABLED);
        validateBoolean(warnings, MENTIONS_ACTIONBAR_ENABLED);
        validateBoolean(warnings, MENTIONS_TITLE_ENABLED);

        validateInt(warnings, CONFIG_VERSION, 1, Integer.MAX_VALUE);
        validateInt(warnings, CROSS_SERVER_TIMEOUT, 1, 300);
        validateInt(warnings, RANGE, -1, 100000);
        validateInt(warnings, ADS_MAX_LENGTH, 1, 1000);
        validateInt(warnings, ANTISPAM_SECONDS, 0, 3600);
        validateDouble(warnings, ADS_SMART_MULTIPLIER, 0.0D, 1000.0D);
        validateDouble(warnings, ADS_SMART_UN_MULTIPLIER, 0.0D, 1000.0D);
        validateDouble(warnings, ADS_THRESHOLD, 0.0D, 1000.0D);
        validateDouble(warnings, ADS_REDUCE_THRESHOLD, 0.0D, 1000.0D);
        validateDouble(warnings, MENTIONS_SOUND_VOLUME, 0.0D, 10.0D);
        validateDouble(warnings, MENTIONS_SOUND_PITCH, 0.0D, 2.0D);
        validateList(warnings, ADS_BYPASS);
        validateList(warnings, ADS_SMART_DOMAIN_ENDINGS);
        validateList(warnings, BLOCKED_WORDS);
        warnDeprecatedSettings(warnings);

        if (!cfg.contains(RGB_COLORS.path)) {
            warnings.add("Missing optional section '" + RGB_COLORS.path + "'. RGB shortcut colors are disabled until you add it.");
        }

        String priority = rawString(PRIORITY);
        try {
            EventPriority.valueOf(priority.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            warnings.add("Invalid EventPriority '" + priority + "'. Using default '" + PRIORITY.value + "'.");
        }

        String locale = rawString(LOCALE);
        if (!LOCALES.contains(locale)) {
            warnings.add("Locale '" + locale + "' is not bundled. Bundled locales: " + String.join(", ", LOCALES) + ".");
        }

        if (rawString(FORMAT).isBlank()) {
            warnings.add("message-format is empty. Chat may look blank; using default internally where needed.");
        }
        if (rawString(GLOBALFORMAT).isBlank()) {
            warnings.add("global-message-format is empty. Global chat may look blank; using default internally where needed.");
        }
        if (rawString(RANGEPREFIX).isBlank()) {
            warnings.add("ranged-prefix is empty. Global prefix matching will be confusing; default is '!'.");
        }
        if (rawString(MENTIONS_FORMAT).isBlank()) {
            warnings.add("Mentions.Format is empty. Mentioned names will not be highlighted.");
        }

        ValidationResult result = new ValidationResult(List.copyOf(warnings), generatedNewConfig);
        printWarnings(result);
        return result;
    }

    private static void printWarnings(ValidationResult result) {
        Logger logger = ChatEx.getInstance().getLogger();
        for (String warning : result.warnings()) {
            logger.warning(warning);
        }
    }

    private static void warnMissing(List<String> warnings, Config option) {
        if (!cfg.contains(option.path) && !containsDeprecatedPath(option)) {
            warnings.add("Missing setting '" + option.path + "'. Default value will be used: " + option.value + ".");
        }
    }

    private static void warnDeprecatedSettings(List<String> warnings) {
        for (Config option : values()) {
            for (String deprecatedPath : option.deprecatedPaths) {
                if (cfg.contains(deprecatedPath)) {
                    warnings.add("Deprecated setting '" + deprecatedPath + "' detected. It still works, but please migrate to '" + option.path + "'.");
                }
            }
        }
    }

    private static void validateBoolean(List<String> warnings, Config option) {
        if (!cfg.contains(option.path)) {
            return;
        }
        Object raw = cfg.get(option.path);
        if (!(raw instanceof Boolean)) {
            warnings.add("Invalid boolean for '" + option.path + "': '" + raw + "'. Use true or false. Default will be used: " + option.value + ".");
        }
    }

    private static void validateInt(List<String> warnings, Config option, int min, int max) {
        if (!cfg.contains(option.path)) {
            return;
        }
        Object raw = cfg.get(option.path);
        if (!(raw instanceof Number)) {
            warnings.add("Invalid number for '" + option.path + "': '" + raw + "'. Default will be used: " + option.value + ".");
            return;
        }
        int value = ((Number) raw).intValue();
        if (value < min || value > max) {
            warnings.add("Value for '" + option.path + "' should be between " + min + " and " + max + ". Current: " + value + ".");
        }
    }

    private static void validateDouble(List<String> warnings, Config option, double min, double max) {
        if (!cfg.contains(option.path)) {
            return;
        }
        Object raw = cfg.get(option.path);
        if (!(raw instanceof Number)) {
            warnings.add("Invalid decimal number for '" + option.path + "': '" + raw + "'. Default will be used: " + option.value + ".");
            return;
        }
        double value = ((Number) raw).doubleValue();
        if (value < min || value > max) {
            warnings.add("Value for '" + option.path + "' should be between " + min + " and " + max + ". Current: " + value + ".");
        }
    }

    private static void validateList(List<String> warnings, Config option) {
        if (!cfg.contains(option.path)) {
            return;
        }
        if (!cfg.isList(option.path)) {
            warnings.add("Invalid list for '" + option.path + "'. Use YAML list syntax. Default will be used: " + option.value + ".");
        }
    }

    private static String rawString(Config option) {
        Object raw = getRawOrDefault(option);
        return raw == null ? "" : raw.toString();
    }

    private static Object getRawOrDefault(Config option) {
        if (cfg.contains(option.path)) {
            return cfg.get(option.path);
        }
        for (String deprecatedPath : option.deprecatedPaths) {
            if (cfg.contains(deprecatedPath)) {
                return cfg.get(deprecatedPath);
            }
        }
        return option.value;
    }

    private static boolean containsDeprecatedPath(Config option) {
        for (String deprecatedPath : option.deprecatedPaths) {
            if (cfg.contains(deprecatedPath)) {
                return true;
            }
        }
        return false;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public Object getDefaultValue() {
        return value;
    }

    public boolean getBoolean() {
        Object raw = getRawOrDefault(this);
        return raw instanceof Boolean ? (Boolean) raw : (Boolean) value;
    }

    public double getDouble() {
        Object raw = getRawOrDefault(this);
        return raw instanceof Number ? ((Number) raw).doubleValue() : ((Number) value).doubleValue();
    }

    public int getInt() {
        Object raw = getRawOrDefault(this);
        return raw instanceof Number ? ((Number) raw).intValue() : ((Number) value).intValue();
    }

    public String getString() {
        Object raw = getRawOrDefault(this);
        return Utils.replaceColors(raw == null ? "" : raw.toString());
    }

    public List<String> getStringList() {
        if (cfg.isList(path)) {
            return cfg.getStringList(path);
        }
        if (value instanceof List<?>) {
            return ((List<?>) value).stream().map(Object::toString).toList();
        }
        return List.of();
    }

    public ConfigurationSection getConfigurationSection() {
        return cfg.getConfigurationSection(path);
    }

    public void set(Object value, boolean save) {
        cfg.set(path, value);
        if (save) {
            try {
                cfg.save(f);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            reload(false);
        }
    }

    public record ValidationResult(List<String> warnings, boolean generatedNewConfig) {

        public boolean hasWarnings() {
            return !warnings.isEmpty();
        }
    }

    private static String defaultConfigTemplate() {
        return """
                # ChatEx-Reborn configuration
                #
                # This file keeps the original ChatEx option names for backward compatibility.
                # You can update from old ChatEx configs without renaming your existing settings.
                #
                # If ChatEx detects an older config structure, it will generate config-new.yml.
                # Your current config.yml will never be overwritten automatically.

                # Internal configuration template version.
                #
                # Do not change this unless you are intentionally testing config migration.
                config-version: 2

                # ---------------------------------------------------------------------------
                # General
                # ---------------------------------------------------------------------------

                # Checks if a new ChatEx version is available.
                #
                # Disable this if your server cannot reach external update services
                # or if you prefer managing plugin updates manually.
                check-for-updates: true

                # Enables anonymous bStats metrics.
                #
                # bStats helps plugin maintainers understand which server versions
                # and plugin integrations are commonly used.
                enable-bstats: true

                # Enables extra debug messages in the server console.
                #
                # Keep this disabled unless you are troubleshooting ChatEx behavior.
                debug: false

                # Selects the locale file used for plugin messages.
                #
                # Bundled locales: de-DE, en-EN, es-ES, fr-FR, ja-JP, pt-BR,
                # ru-RU, tr-TR, zh-CN.
                Locale: en-EN

                # Controls when ChatEx handles the Bukkit chat event.
                #
                # LOWEST is the classic ChatEx behavior.
                # If another chat plugin conflicts with ChatEx, try LOW, NORMAL or HIGH.
                # Valid values: LOWEST, LOW, NORMAL, HIGH, HIGHEST, MONITOR.
                EventPriority: LOWEST

                # ---------------------------------------------------------------------------
                # Permissions
                # ---------------------------------------------------------------------------

                Permissions:
                  # Lets OP players bypass ChatEx restrictions automatically.
                  #
                  # When enabled, OP players bypass:
                  # - chatex.allowchat restrictions
                  # - anti-spam cooldown
                  # - advertisement blocking
                  # - blocked words filtering
                  # - global chat permission checks
                  # - mention use/notify permission checks
                  #
                  # Normal non-OP permission behavior is unchanged.
                  # Set this to false if you want OP players to follow the exact same
                  # permission rules as normal players.
                  OpBypassesRestrictions: true

                # ---------------------------------------------------------------------------
                # Chat Formatting
                # ---------------------------------------------------------------------------

                # Format for normal chat messages.
                #
                # Useful placeholders:
                # %prefix      - prefix from LuckPerms/Vault
                # %suffix      - suffix from LuckPerms/Vault
                # %displayname - Bukkit display name
                # %player      - player name
                # %world       - world name
                # %group       - first inherited group
                # %message     - chat message
                #
                # PlaceholderAPI placeholders also work when PlaceholderAPI is installed.
                message-format: "%prefix%displayname%suffix &8» &f%message"

                # Format for global chat messages.
                #
                # By default, normal chat is already global, so this uses the same
                # simple format as message-format.
                #
                # If ranged-mode is enabled, this is used when a player sends
                # a message with the global prefix, for example: !hello.
                global-message-format: "%prefix%displayname%suffix &8» &f%message"

                # Enables multiple prefixes from the permissions provider.
                #
                # Most servers should keep this disabled and use one clear prefix.
                multi-prefixes: false

                # Enables multiple suffixes from the permissions provider.
                #
                # Most servers should keep this disabled and use one clear suffix.
                multi-suffixes: false

                # ---------------------------------------------------------------------------
                # Local/Global Chat
                # ---------------------------------------------------------------------------

                # Enables ranged chat mode.
                #
                # Default mode is normal global chat:
                # - ranged-mode: false
                # - every normal message is visible globally
                # - players do not need to type ! for normal chat
                #
                # Enable ranged-mode only if you specifically want local chat.
                #
                # When ranged-mode is true:
                # - normal messages are local and only visible nearby
                # - messages starting with ranged-prefix are global
                ranged-mode: false

                # Prefix used to send a global message while ranged-mode is enabled.
                #
                # Example: !hello sends "hello" to global chat.
                ranged-prefix: "!"

                # Local chat range in blocks.
                #
                # Set to -1 to make local chat world-wide.
                chat-range: 100

                # Sends a warning when nobody nearby can hear the player's local message.
                #
                # This can reveal whether somebody is nearby, so it is disabled by default.
                # It only matters when ranged-mode is true.
                show-no-players-near: false

                # ---------------------------------------------------------------------------
                # Anti-Spam
                # ---------------------------------------------------------------------------

                # Enables the built-in anti-spam delay.
                AntiSpam:
                  Enable: true

                  # Minimum delay between messages in seconds.
                  #
                  # Players with chatex.antispam.bypass ignore this delay.
                  Seconds: 5

                # ---------------------------------------------------------------------------
                # Advertisement Blocking
                # ---------------------------------------------------------------------------

                Ads:
                  # Enables advertisement and link detection.
                  Enabled: true

                  # Messages containing these domains/IPs are allowed.
                  #
                  # Add your own website, Discord vanity domain or server address here
                  # if ChatEx blocks it by mistake.
                  Bypass:
                    - "127.0.0.1"
                    - "my-domain.com"

                  # Logs blocked advertisements to plugins/ChatEx/logs/ads.log.
                  Log: true

                  # Uses the smarter domain detector.
                  #
                  # Recommended for most public servers.
                  SmartManager: true

                  # Treats commas like dots for advertisement checks.
                  #
                  # Example: example,com can be detected like example.com.
                  ReplaceCommas: false

                  SmartConfig:
                    # Known domain endings used by the smart detector.
                    DomainEndings:
                      - "com"
                      - "net"
                      - "org"
                      - "de"
                      - "icu"
                      - "uk"
                      - "ru"
                      - "me"
                      - "info"
                      - "top"
                      - "xyz"
                      - "tk"
                      - "cn"
                      - "ga"
                      - "cf"
                      - "nl"
                      - "eu"

                    # Score multiplier for known domain endings.
                    Multiplier: 4

                    # Score multiplier for unknown endings.
                    UnMultiplier: 1

                  Threshold:
                    # Score required to block a message.
                    Block: 0.3

                    # Score removed after a clean message.
                    ReduceThreshold: 0.1

                    # Maximum detected link length used by the smart detector.
                    MaxLinkLength: 10

                # ---------------------------------------------------------------------------
                # Blocked Words
                # ---------------------------------------------------------------------------

                # Words or phrases blocked from chat.
                #
                # Matching is case-insensitive and checks if the message contains the text.
                BlockedWords:
                  - "shit"
                  - "@everyone"

                # ---------------------------------------------------------------------------
                # Mentions
                # ---------------------------------------------------------------------------

                Mentions:
                  # Enables @player mentions in chat.
                  #
                  # Example: "hello @Steve" will highlight Steve's name and can notify Steve.
                  Enabled: true

                  # Requires chatex.mentions.use before a player can mention others.
                  #
                  # Keep this false if all players should be able to mention each other.
                  RequirePermission: false

                  # Chat highlight format for mentioned names.
                  #
                  # Placeholders:
                  # %player - mentioned player name
                  # %sender - player who sent the message
                  Format: "&e@%player%&r"

                  Notify:
                    # Sends a notification to players when they are mentioned.
                    Enabled: true

                    # Requires chatex.mentions.notify before a player can receive mention notifications.
                    RequirePermission: false

                    Sound:
                      # Plays a sound for the mentioned player.
                      Enabled: true

                      # Bukkit sound name.
                      #
                      # If this sound does not exist on your server version,
                      # ChatEx will log a warning and skip the sound.
                      Name: "ENTITY_EXPERIENCE_ORB_PICKUP"

                      # Sound volume.
                      Volume: 1.0

                      # Sound pitch.
                      Pitch: 1.2

                    ActionBar:
                      # Shows a short actionbar message to the mentioned player.
                      Enabled: true

                      # Actionbar text.
                      Message: "&e%sender% mentioned you in chat."

                    Title:
                      # Shows a title to the mentioned player.
                      #
                      # Disabled by default to keep mentions noticeable but not intrusive.
                      Enabled: false

                      # Title text.
                      Title: "&eMentioned"

                      # Subtitle text.
                      Subtitle: "&f%sender% mentioned you in chat."

                # ---------------------------------------------------------------------------
                # Tablist
                # ---------------------------------------------------------------------------

                Tablist:
                  # Changes the player's name in the tablist.
                  Change: true

                  # Tablist display format.
                  #
                  # Example: "%prefix%player%suffix"
                  format: "%prefix%player%suffix"

                # ---------------------------------------------------------------------------
                # Logging
                # ---------------------------------------------------------------------------

                # Logs normal chat to plugins/ChatEx/logs/YYYY-MM-DD.log.
                logChat: false

                # ---------------------------------------------------------------------------
                # Placeholder Support
                # ---------------------------------------------------------------------------

                # PlaceholderAPI support is automatic when PlaceholderAPI is installed.
                #
                # You can use PlaceholderAPI placeholders in message-format,
                # global-message-format and tablist formats.

                # ---------------------------------------------------------------------------
                # Cross-Server Chat
                # ---------------------------------------------------------------------------

                # Enables cross-server chat through the BungeeCord plugin message channel.
                #
                # This is compatibility mode for proxy networks.
                # Your proxy/server setup must allow the BungeeCord plugin message channel.
                bungeecord: false

                # Drops cross-server messages older than this many seconds.
                cross_server_timeout: 3

                # ---------------------------------------------------------------------------
                # AFK Integration
                # ---------------------------------------------------------------------------

                AfkPlaceholder:
                  # Enables the %afk placeholder.
                  #
                  # Requires EssentialsX or Purpur.
                  Enabled: false

                  # Text inserted when a player is AFK.
                  format: "&r[&7AFK&r] "

                # ---------------------------------------------------------------------------
                # Join/Quit Messages
                # ---------------------------------------------------------------------------

                Messages:
                  JoinAndQuit:
                    # Enables custom join, first join, quit and kick messages.
                    #
                    # The message text is stored in the selected locale file.
                    Enabled: false

                # ---------------------------------------------------------------------------
                # RGB/HEX Colors
                # ---------------------------------------------------------------------------

                # Custom RGB shortcut colors.
                #
                # Example:
                # $g: "#00ff00"
                #
                # Then &g in formats/messages becomes that RGB color.
                colors:
                  $g: "#00ff00"
                """;
    }
}
