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

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.castaway.core.CastAwayCore;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.commands.AbstractSuperCommand;

public class cmdCastAway extends AbstractSuperCommand {

    public cmdCastAway(String syntax, String arguments, String node, AbstractCommand... subCommands) {
        super(CastAwayCore.NAME, syntax, arguments, node, subCommands);
    }

    @Override
    public void execute(String[] args, Player player) {
        // NOTHING TO DO HERE
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        // NOTHING TO DO HERE
    }

}
