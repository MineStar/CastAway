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

import de.minestar.castaway.data.ActionBlockType;
import de.minestar.castaway.data.BlockVector;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.PlayerData;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class DungeonEndBlock extends AbstractActionBlock {

    public DungeonEndBlock(BlockVector vector, Dungeon dungeon) {
        super(vector, dungeon, ActionBlockType.DUNGEON_END);
        this.setHandlePhysical();
        this.setHandleLeftClick();
        this.setHandleRightClick();
        this.setExecuteIfNotInDungeon();
    }

    @Override
    public boolean execute(Player player, PlayerData data) {
        // Player must be in a dungeon
        if (data.isInDungeon() && data.getDungeon().equals(this.dungeon)) {
            this.dungeon.playerFinished(data);
            return false;
        }
        PlayerUtils.sendInfo(player, "Du bist momentan nicht in einem Dungeon.");
        return false;
    }
}
