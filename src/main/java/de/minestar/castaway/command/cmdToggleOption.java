/*
 * Copyright (C) 2012 MineStar.de 
 * 
 * This file is part of CastAway.
 * 
 * CastAway is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * CastAway is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CastAway.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.castaway.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.minestar.castaway.core.CastAwayCore;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.DungeonOption;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdToggleOption extends AbstractCommand {

    public cmdToggleOption(String syntax, String arguments, String node) {
        super(CastAwayCore.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {

        String dungeonName = args[0];
        Dungeon dungeon = CastAwayCore.dungeonManager.getDungeon(dungeonName);
        if (dungeon == null) {
            PlayerUtils.sendError(player, pluginName, "Der Dungeon '" + dungeonName + "' wurde nicht gefunden!");
            return;
        }

        // SEARCH FOR KIND OF OPTION
        String optionName = args[1];
        DungeonOption option = DungeonOption.byString(optionName);

        // NAME WORKS
        if (option == null) {
            if (!args[1].equalsIgnoreCase("list")) {
                PlayerUtils.sendError(player, pluginName, "Unbekannte Option!");
            }
            for (DungeonOption curOption : DungeonOption.values()) {
                if (dungeon.hasOption(curOption)) {
                    PlayerUtils.sendInfo(player, ChatColor.GREEN + curOption.getCommand());
                } else {
                    PlayerUtils.sendInfo(player, ChatColor.RED + curOption.getCommand());
                }
            }
            return;
        }

        String statusText = ChatColor.RED + "AUS";
        if (dungeon.toggleOption(option)) {
            statusText = ChatColor.GREEN + "AN";
        }

        if (CastAwayCore.databaseManager.updateDungeonOption(dungeon)) {
            PlayerUtils.sendInfo(player, pluginName, "Option '" + option.getCommand() + "' => " + statusText);
        } else {
            PlayerUtils.sendError(player, pluginName, "Option '" + option.getCommand() + "' konnte nicht in der DB gespeichert werden!");
            dungeon.toggleOption(option);
        }
    }
}
