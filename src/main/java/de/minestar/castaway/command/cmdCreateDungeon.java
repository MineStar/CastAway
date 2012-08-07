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

import org.bukkit.entity.Player;

import de.minestar.castaway.core.CastAwayCore;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdCreateDungeon extends AbstractCommand {

    public cmdCreateDungeon(String syntax, String arguments, String node) {
        super(CastAwayCore.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {

        String dungeonName = args[0];
        // CHECK IF DUNGEON NAME IS ALREADY EXISTING
        if (CastAwayCore.dungeonManager.getDungeon(dungeonName) != null) {
            PlayerUtils.sendError(player, pluginName, "Der Dungeon '" + args[0] + "' existiert bereits.");
            return;
        }

        // CREATE DUNGEON
        CastAwayCore.dungeonManager.addDungeon(dungeonName, player.getName());
        PlayerUtils.sendSuccess(player, pluginName, "Der Dungeon '" + args[0] + "' wurde erstellt!");
    }
}
