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
import de.minestar.castaway.data.Dungeon;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdDeleteDungeon extends AbstractCommand {

    public cmdDeleteDungeon(String syntax, String arguments, String node) {
        super(CastAwayCore.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {

        // CHECK IF DUNGEON NAME IS EXISTING
        Dungeon dungeon = CastAwayCore.gameManager.getDungeonByName(args[0]);
        if (dungeon == null) {
            PlayerUtils.sendError(player, pluginName, "Der Dungeon '" + args[0] + "' existiert nicht.");
            return;
        }

        // CREATE DUNGEON
        CastAwayCore.gameManager.deleteDungeon(dungeon);
        PlayerUtils.sendSuccess(player, pluginName, "Der Dungeon '" + args[0] + "' wurde gelöscht!");
    }
}
