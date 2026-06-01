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
package de.jeter.chatex;

import de.jeter.chatex.utils.Config;
import de.jeter.chatex.utils.Locales;
import de.jeter.chatex.utils.PermissionUtil;
import de.jeter.chatex.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§aChatEx plugin by " + ChatEx.getInstance().getDescription().getAuthors() + " (" + ChatEx.getInstance().getDescription().getVersion() + ")");
            return true;
        } else if (args.length > 1) {
            sender.sendMessage(Locales.COMMAND_RESULT_WRONG_USAGE.getString(null).replaceAll("%cmd", command.getName()));
            return true;
        } else {
            if (args[0].equalsIgnoreCase("reload")) {
                if (PermissionUtil.canUseAdminCommand(sender, "chatex.reload")) {
                    Config.ValidationResult validationResult = Config.reload(true);
                    Locales.load();
                    sender.sendMessage(ChatColor.GREEN + "ChatEx configuration and locale messages were reloaded.");

                    int updatedTablistNames = 0;
                    if (Config.CHANGE_TABLIST_NAME.getBoolean()) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            String name = Config.TABLIST_FORMAT.getString();
                            name = Utils.replacePlayerPlaceholders(p, name);
                            p.setPlayerListName(name);
                            updatedTablistNames++;
                        }
                    }

                    sender.sendMessage(ChatColor.GRAY + "Updated tablist names: " + updatedTablistNames);
                    if (validationResult.generatedNewConfig()) {
                        sender.sendMessage(ChatColor.YELLOW + "Your config is older. A new template was generated beside config.yml.");
                    }
                    if (validationResult.hasWarnings()) {
                        sender.sendMessage(ChatColor.YELLOW + "Config warnings: " + validationResult.warnings().size() + ". Check the server console for details.");
                    } else {
                        sender.sendMessage(ChatColor.GREEN + "No config warnings detected.");
                    }
                } else {
                    sender.sendMessage(Locales.COMMAND_RESULT_NO_PERM.getString(null).replaceAll("%perm", "chatex.reload"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("clear")) {
                if (PermissionUtil.canUseAdminCommand(sender, "chatex.clear")) {
                    for (int i = 0; i < 50; i++) {
                        Bukkit.broadcastMessage("\n");
                    }

                    Player clearer = null;

                    String who = Locales.COMMAND_CLEAR_UNKNOWN.getString(null);
                    if ((sender instanceof ConsoleCommandSender) || (sender instanceof BlockCommandSender)) {
                        who = Locales.COMMAND_CLEAR_CONSOLE.getString(null);
                    } else if (sender instanceof Player) {
                        who = sender.getName();
                        clearer = (Player) sender;
                    }
                    Bukkit.broadcastMessage(Locales.MESSAGES_CLEAR.getString(clearer) + who);
                } else {
                    sender.sendMessage(Locales.COMMAND_RESULT_NO_PERM.getString(null).replaceAll("%perm", "chatex.clear"));
                }
                return true;
            } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                sender.sendMessage("§a/" + command.getName() + " reload - " + Locales.COMMAND_RELOAD_DESCRIPTION.getString(null));
                sender.sendMessage("§a/" + command.getName() + " clear - " + Locales.COMMAND_CLEAR_DESCRIPTION.getString(null));
                return true;
            } else {
                sender.sendMessage(Locales.COMMAND_RESULT_WRONG_USAGE.getString(null).replaceAll("%cmd", "/chatex"));
                return true;
            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> possibleTabs = new ArrayList<>();
        if (PermissionUtil.canUseAdminCommand(commandSender, "chatex.clear")) {
            possibleTabs.add("clear");
        }
        if (PermissionUtil.canUseAdminCommand(commandSender, "chatex.reload")) {
            possibleTabs.add("reload");
        }
        return possibleTabs;
    }
}
