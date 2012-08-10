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

package de.minestar.castaway.blocks;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.minestar.castaway.core.CastAwayCore;
import de.minestar.castaway.data.ActionBlockType;
import de.minestar.castaway.data.BlockVector;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.PlayerData;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class VelocityReverseXZBlock extends AbstractActionBlock {

    public VelocityReverseXZBlock(BlockVector vector, Dungeon dungeon) {
        super(vector, dungeon, ActionBlockType.VELOCITY_REVERSE_XZ);
        this.setHandlePhysical();
        this.setHandleLeftClick();
        this.setHandleRightClick();
    }

    @Override
    public boolean execute(Player player, PlayerData data) {
        // Player must be in a dungeon
        if (!data.isInDungeon() || !data.getDungeon().equals(this.dungeon)) {
            PlayerUtils.sendError(player, CastAwayCore.NAME, "Du musst in einem Dungeon sein!");
            PlayerUtils.sendInfo(player, "Wende dich an einen Admin falls du es eigentlich bist.");
            return true;
        }

        // get the current velocity
        Vector velocity = player.getVelocity().clone();
        velocity.setX(-(velocity.getX() + 0.25));
        velocity.setZ(-(velocity.getX() + 0.25));
        player.setVelocity(velocity.clone());
        return false;
    }
}
