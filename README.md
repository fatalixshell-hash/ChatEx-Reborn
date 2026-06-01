# ChatEx-Reborn

ChatEx-Reborn is a modernized fork of ChatEx for Paper and Purpur servers.
It keeps the classic ChatEx behavior while updating the project for modern
Minecraft servers and Java 21.

Based on the original ChatEx by TheJeterLP.
Additional original contribution: Wizard_x.
Maintainer: Fatalixshell.

## Server Support

- Paper / Purpur
- Minecraft 1.20.1-1.21.11
- Java 21
- Bukkit legacy chat compatibility mode
- Paper-compatible build without NMS/CraftBukkit internals

## What It Does

- Formats chat messages with prefixes, suffixes, display names and worlds.
- Supports LuckPerms directly.
- Supports Vault chat providers.
- Supports PlaceholderAPI placeholders in chat formats.
- Supports local/ranged chat.
- Supports global chat with a configurable prefix.
- Supports BungeeCord/Velocity-style cross-server chat through plugin messages.
- Supports chat colors with the `&` character when the player has permission.
- Supports RGB/hex colors on Minecraft 1.16+.
- Supports `@player` mentions with highlight, sound, actionbar and optional title notifications.
- Can change tablist names using prefixes and suffixes.
- Can change join, quit and kick messages.
- Has built-in anti-spam.
- Has built-in advertisement/link blocking.
- Has blocked words filtering.
- Can log chat messages to files.
- Can log advertisement attempts to files.
- Can show AFK status in formats when EssentialsX or Purpur is available.
- Exposes API events for other plugins.

## Optional Dependencies

The plugin can run without all of these, but features depend on them.

- LuckPerms: prefixes, suffixes and groups through LuckPerms API.
- Vault: fallback chat provider support.
- PlaceholderAPI: external placeholders inside formats.
- EssentialsX: AFK placeholder support.
- Purpur: AFK placeholder support.

Hook priority:

1. LuckPerms
2. Vault
3. No permissions/chat provider fallback

## Commands

| Command | Permission | Description |
| --- | --- | --- |
| `/chatex` | none | Shows plugin name, authors and version. |
| `/chatex help` | none | Shows command help. |
| `/chatex reload` | `chatex.reload` | Reloads config and locale messages. |
| `/chatex clear` | `chatex.clear` | Clears chat for players. |

`/chatex reload` also reports:

- how many tablist names were refreshed
- whether `config-new.yml` was generated
- how many config warnings were found

Detailed validation warnings are printed to the server console.

## Permissions

| Permission | Default | Description |
| --- | --- | --- |
| `chatex.allowchat` | true | Allows the player to chat. |
| `chatex.chat.global` | false | Allows global chat in ranged/cross-server mode. |
| `chatex.chat.color` | false | Allows `&` color codes in player messages. |
| `chatex.mentions.use` | false | Allows mentioning players when `Mentions.RequirePermission` is enabled. |
| `chatex.mentions.notify` | false | Allows receiving mention notifications when `Mentions.Notify.RequirePermission` is enabled. |
| `chatex.antispam.bypass` | op | Bypasses anti-spam delay. |
| `chatex.bypassads` | op | Bypasses advertisement blocking. |
| `chatex.blockedwords.bypass` | op | Bypasses blocked words filtering. |
| `chatex.notifyad` | op | Receives notification when ads are blocked. |
| `chatex.notifyupdate` | op | Receives update notifications on join. |
| `chatex.clear` | op | Allows `/chatex clear`. |
| `chatex.reload` | op | Allows `/chatex reload`. |

## Default Placeholders

These placeholders work in ChatEx formats:

| Placeholder | Meaning |
| --- | --- |
| `%prefix` | Player prefix from LuckPerms/Vault. |
| `%suffix` | Player suffix from LuckPerms/Vault. |
| `%displayname` | Bukkit player display name. |
| `%player` | Player username. |
| `%world` | Player world name. |
| `%group` | First inherited group/display group. |
| `%message` | Chat message. |
| `%afk` | AFK marker when enabled with EssentialsX/Purpur. |

PlaceholderAPI placeholders also work if PlaceholderAPI is installed.

## Important Config Settings

The plugin creates `plugins/ChatEx/config.yml` on first start.

ChatEx-Reborn keeps the original ChatEx option names for compatibility.
Existing `config.yml` files continue to work.

### Recommended Default Behavior

Fresh installs are configured for simple global chat:

- `ranged-mode: false`
- normal chat is global
- players do not need to type `!` for normal chat
- `show-no-players-near: false`
- no player proximity leaks by default
- local/ranged chat remains fully supported, but is opt-in

Enable `ranged-mode` only if you want local chat. When it is enabled, normal
messages become local and messages starting with `ranged-prefix` are global.

### Config Version And Migration

| Setting | Default | Description |
| --- | --- | --- |
| `config-version` | `2` | Internal template version used to detect older config layouts. |

Migration behavior:

- ChatEx never overwrites an existing `config.yml` automatically.
- A fresh install receives the new commented and organized `config.yml`.
- If an old config is detected, ChatEx keeps using it and generates `config-new.yml`.
- `config-new.yml` is a documented template for manual migration.
- Missing settings use sensible defaults at runtime.
- Deprecated/alternative setting names are read when possible and logged as warnings.

Currently recognized deprecated aliases:

| Deprecated alias | Current setting |
| --- | --- |
| `BungeeCord` | `bungeecord` |
| `cross-server-timeout` | `cross_server_timeout` |
| `log-chat` | `logChat` |
| `event-priority` | `EventPriority` |
| `locale` | `Locale` |

The config is organized into these sections:

- General
- Permissions
- Chat Formatting
- Local/Global Chat
- Anti-Spam
- Advertisement Blocking
- Blocked Words
- Mentions
- Tablist
- Logging
- Placeholder Support
- Cross-Server Chat
- AFK Integration
- RGB/HEX Colors

### Validation

ChatEx validates configuration on startup and on `/chatex reload`.

It warns about:

- missing important settings
- invalid booleans, such as `yes` instead of `true`
- invalid numbers
- values outside safe ranges
- unknown bundled locale names
- invalid `EventPriority`
- deprecated aliases
- missing optional RGB color section

Invalid values do not crash the server. ChatEx logs a readable warning and uses
the built-in default for that setting where possible.

### General

| Setting | Default | Description |
| --- | --- | --- |
| `check-for-updates` | `true` | Checks for plugin updates. |
| `enable-bstats` | `true` | Enables bStats metrics. |
| `debug` | `false` | Enables debug logging. |
| `Locale` | `en-EN` | Locale file to use. Included: `de-DE`, `es-ES`, `fr-FR`, `ja-JP`, `pt-BR`, `ru-RU`, `tr-TR`, `zh-CN`. |
| `EventPriority` | `LOWEST` | Chat event priority: `LOWEST`, `LOW`, `NORMAL`, `HIGH`, `HIGHEST`, `MONITOR`. |

### Permissions

| Setting | Default | Description |
| --- | --- | --- |
| `Permissions.OpBypassesRestrictions` | `true` | Lets OP players bypass ChatEx chat restrictions and bypass-style checks. |

When `Permissions.OpBypassesRestrictions` is enabled, OP players bypass:

- `chatex.allowchat` chat restriction
- anti-spam cooldown
- advertisement blocking
- blocked words filtering
- global chat permission checks
- chat color permission checks
- mention use/notify permission checks

This does not remove normal permissions for non-OP players. Set it to `false`
if OP players should follow the same ChatEx permission rules as normal players.

### Chat Format

| Setting | Default | Description |
| --- | --- | --- |
| `message-format` | `%prefix%displayname%suffix &8» &f%message` | Normal chat format. |
| `global-message-format` | `%prefix%displayname%suffix &8» &f%message` | Global chat format. |
| `multi-prefixes` | `false` | Uses multiple prefixes from the permission provider. |
| `multi-suffixes` | `false` | Uses multiple suffixes from the permission provider. |

Example:

```yaml
message-format: "%prefix%player%suffix &8» &f%message"
global-message-format: "%prefix%player%suffix &8» &f%message"
```

### Ranged And Global Chat

| Setting | Default | Description |
| --- | --- | --- |
| `ranged-mode` | `false` | Enables local/ranged chat. |
| `ranged-prefix` | `!` | Prefix for global chat while ranged mode is enabled. |
| `chat-range` | `100` | Chat range in blocks. `-1` means world-wide chat. |
| `show-no-players-near` | `false` | Warns player if nobody can hear local chat. Disabled by default to avoid proximity leaks. |

When `ranged-mode` is disabled:

- normal message = global chat
- players do not need `!`
- `show-no-players-near` has no effect

When `ranged-mode` is enabled:

- normal message = local chat
- `!message` = global chat, requires `chatex.chat.global`

### Cross-Server Chat

| Setting | Default | Description |
| --- | --- | --- |
| `bungeecord` | `false` | Enables cross-server chat through the `BungeeCord` plugin message channel. |
| `cross_server_timeout` | `3` | Drops cross-server messages older than this many seconds. |

This is compatibility mode for proxy networks. The server/proxy must allow the
BungeeCord plugin messaging channel.

### Anti-Spam

| Setting | Default | Description |
| --- | --- | --- |
| `AntiSpam.Enable` | `true` | Enables anti-spam. |
| `AntiSpam.Seconds` | `5` | Delay between messages in seconds. |

Bypass permission: `chatex.antispam.bypass`.

### Advertisement Blocking

| Setting | Default | Description |
| --- | --- | --- |
| `Ads.Enabled` | `true` | Enables ad/link blocking. |
| `Ads.Bypass` | `127.0.0.1`, `my-domain.com` | Allowed domains/IPs. |
| `Ads.Log` | `true` | Logs blocked ads to `plugins/ChatEx/logs/ads.log`. |
| `Ads.SmartManager` | `true` | Uses smart ad detection. |
| `Ads.ReplaceCommas` | `false` | Treats commas like dots for ad detection. |
| `Ads.Threshold.Block` | `0.3` | Score required to block a message. |
| `Ads.Threshold.ReduceThreshold` | `0.1` | Score reduction per message. |
| `Ads.Threshold.MaxLinkLength` | `10` | Max detected link length. |
| `Ads.SmartConfig.Multiplier` | `4` | Score multiplier for known domain endings. |
| `Ads.SmartConfig.UnMultiplier` | `1` | Score multiplier for unknown endings. |
| `Ads.SmartConfig.DomainEndings` | list | Domain endings checked by the smart manager. |

Bypass permission: `chatex.bypassads`.
Notification permission: `chatex.notifyad`.

### Blocked Words

| Setting | Default | Description |
| --- | --- | --- |
| `BlockedWords` | `shit`, `@everyone` | Words blocked from chat. |

### Mentions

Mentions let players type `@player` in chat. ChatEx highlights the mentioned
name and can notify the mentioned player.

This is useful on public SMP, survival, anarchy and grief servers where chat can
move quickly and players need a lightweight way to get someone's attention.

| Setting | Default | Description |
| --- | --- | --- |
| `Mentions.Enabled` | `true` | Enables `@player` mentions. |
| `Mentions.RequirePermission` | `false` | Requires `chatex.mentions.use` to mention players. |
| `Mentions.Format` | `&e@%player%&r` | Highlight format inserted into chat. |
| `Mentions.Notify.Enabled` | `true` | Enables mention notifications. |
| `Mentions.Notify.RequirePermission` | `false` | Requires `chatex.mentions.notify` to receive notifications. |
| `Mentions.Notify.Sound.Enabled` | `true` | Plays a sound when mentioned. |
| `Mentions.Notify.Sound.Name` | `ENTITY_EXPERIENCE_ORB_PICKUP` | Bukkit sound name. |
| `Mentions.Notify.Sound.Volume` | `1.0` | Sound volume. |
| `Mentions.Notify.Sound.Pitch` | `1.2` | Sound pitch. |
| `Mentions.Notify.ActionBar.Enabled` | `true` | Shows an actionbar notification. |
| `Mentions.Notify.ActionBar.Message` | `&e%sender% mentioned you in chat.` | Actionbar text. |
| `Mentions.Notify.Title.Enabled` | `false` | Shows a title notification. |
| `Mentions.Notify.Title.Title` | `&eMentioned` | Title text. |
| `Mentions.Notify.Title.Subtitle` | `&f%sender% mentioned you in chat.` | Subtitle text. |

Mention placeholders:

| Placeholder | Meaning |
| --- | --- |
| `%player` | Mentioned player name. |
| `%sender` | Player who sent the message. |

Mention formats also support normal ChatEx placeholders and PlaceholderAPI where
applicable.

If the configured sound does not exist on the running server version, ChatEx
logs a warning and skips the sound instead of breaking chat.

### Tablist

| Setting | Default | Description |
| --- | --- | --- |
| `Tablist.Change` | `true` | Changes player tablist names. |
| `Tablist.format` | `%prefix%player%suffix` | Tablist name format. |

### Join, Quit And Kick Messages

| Setting | Default | Description |
| --- | --- | --- |
| `Messages.JoinAndQuit.Enabled` | `false` | Enables custom join/quit/kick messages. |

The actual messages are stored in locale files.

### Logs

| Setting | Default | Description |
| --- | --- | --- |
| `logChat` | `false` | Logs chat to `plugins/ChatEx/logs/YYYY-MM-DD.log`. |
| `Ads.Log` | `true` | Logs blocked ads to `plugins/ChatEx/logs/ads.log`. |

### RGB And Custom Colors

| Setting | Default | Description |
| --- | --- | --- |
| `colors.$g` | `#00ff00` | Custom color shortcut. `&g` becomes green. |

Supported color styles:

- legacy colors: `&a`, `&c`, `&l`, etc.
- inline hex: `&#00ff00`
- hex code text: `#00ff00`
- custom shortcuts from `colors`

Player messages require `chatex.chat.color` to use colors.

### AFK Placeholder

| Setting | Default | Description |
| --- | --- | --- |
| `AfkPlaceholder.Enabled` | `false` | Enables `%afk` placeholder support. |
| `AfkPlaceholder.format` | `&r[&7AFK&r] ` | AFK text format. |

Requires EssentialsX or Purpur.

## API Events

Other plugins can listen to:

- `MessageBlockedByAdManagerEvent`
- `MessageBlockedBySpamManagerEvent`
- `MessageContainsBlockedWordEvent`
- `PlayerUsesGlobalChatEvent`
- `PlayerUsesRangeModeEvent`

## Build

Build with Java 21 and Maven:

```bash
mvn clean package
```

The compiled plugin is created here:

```text
target/ChatEx-Reborn.jar
```

## Example Configs

### Simple Global Chat

```yaml
config-version: 2
Permissions:
  OpBypassesRestrictions: true
message-format: "%prefix%player%suffix &8» &f%message"
global-message-format: "%prefix%player%suffix &8» &f%message"
ranged-mode: false
show-no-players-near: false
logChat: false
AntiSpam:
  Enable: true
  Seconds: 3
Ads:
  Enabled: true
  Log: true
BlockedWords:
  - "@everyone"
Tablist:
  Change: true
  format: "%prefix%player%suffix"
Mentions:
  Enabled: true
  Format: "&e@%player%&r"
  Notify:
    Enabled: true
    Sound:
      Enabled: true
      Name: "ENTITY_EXPERIENCE_ORB_PICKUP"
    ActionBar:
      Enabled: true
      Message: "&e%sender% mentioned you in chat."
```

### Local Chat With Global Prefix

```yaml
config-version: 2
message-format: "&8[&7L&8] %prefix%player%suffix &8» &f%message"
global-message-format: "&8[&cG&8] %prefix%player%suffix &8» &f%message"
ranged-mode: true
ranged-prefix: "!"
chat-range: 100
show-no-players-near: false
```

### RGB Color Shortcut

```yaml
colors:
  $g: "#00ff00"
  $r: "#ff5555"

message-format: "$g%prefix%player &8» &f%message"
```

## License

GPL-2.0-or-later.
