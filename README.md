# ChatEx-Reborn

ChatEx-Reborn is a modernized fork of ChatEx for Paper and Purpur servers. It keeps the classic ChatEx configuration style and compatibility-first behavior while moving the project to a current Java and dependency baseline.

This project is based on the original ChatEx by TheJeterLP, itself a recode of the original ChatManager from PermissionsEx.

## Features

- Paper and Purpur support for Minecraft 1.20.1 through 1.21.11
- Java 21 build target
- Vault support for prefixes, suffixes, groups, and permissions
- PlaceholderAPI support
- LuckPerms support through Vault
- Bukkit compatibility mode using the legacy chat event path
- Paper chat mode foundation for modern Paper chat APIs
- Legacy color code support with RGB/hex color handling
- Range chat and global chat compatibility with old ChatEx configs

## Building

Build with Java 21 and Maven:

```bash
mvn clean package
```

The compiled plugin jar is written to `target/ChatEx-Reborn.jar`.

## Compatibility Notes

ChatEx-Reborn is designed to avoid NMS and CraftBukkit internals. Compatibility with older ChatEx permissions, commands, and configuration formats is preferred over adding features that would require version-specific server internals.

## Credits

- Maintainer: Fatalixshell
- Original ChatEx: TheJeterLP
- Additional original contribution: Wizard_x
