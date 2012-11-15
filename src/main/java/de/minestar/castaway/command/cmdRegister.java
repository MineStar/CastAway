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
import de.minestar.castaway.data.ActionBlockType;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.RegisterAction;
import de.minestar.castaway.data.RegisterSelection;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdRegister extends AbstractCommand {

    public cmdRegister(String syntax, String arguments, String node) {
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

        // SEARCH FOR KIND OF ACTION BLOCK
        String actionTypeName = args[1];
        ActionBlockType actionBlockType;
        try {
            // TRY ID FIRST
            actionBlockType = ActionBlockType.get(Integer.parseInt(actionTypeName));
        } catch (Exception e) {
            // IS NO NUMBER - TRY NAME
            actionBlockType = ActionBlockType.get(actionTypeName);
        }

        // ID NOR NAME WORKS
        if (actionBlockType == null) {
            PlayerUtils.sendError(player, pluginName, "Unbekannter ActionBlockTyp!");
            for (ActionBlockType type : ActionBlockType.values()) {
                PlayerUtils.sendInfo(player, type.getCommandName());
            }
            return;
        }

        if (actionBlockType != ActionBlockType.HALL_OF_FAME_SIGN) {
            CastAwayCore.playerManager.addRegisterMode(player.getName(), new RegisterSelection(actionBlockType, dungeon));
            PlayerUtils.sendSuccess(player, pluginName, "Klicke auf einen Block um diesen zu registieren!");
        } else {
            CastAwayCore.playerManager.addRegisterMode(player.getName(), new RegisterSelection(dungeon, RegisterAction.HALL_OF_FAME));
            PlayerUtils.sendSuccess(player, pluginName, "Klicke auf Schilder um diese zu registieren!");
            PlayerUtils.sendInfo(player, "Linksklick: hinzufügen");
            PlayerUtils.sendInfo(player, "Rechtsklick: entfernen");
        }
    }
}
