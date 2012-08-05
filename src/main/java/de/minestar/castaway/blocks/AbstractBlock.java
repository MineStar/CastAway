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

import de.minestar.castaway.data.BlockVector;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.PlayerData;

public abstract class AbstractBlock {

    protected final Dungeon dungeon;
    protected final BlockVector vector;

    public AbstractBlock(BlockVector vector, Dungeon dungeon) {
        this.vector = vector;
        this.dungeon = dungeon;
    }

    public abstract void execute(Player player, PlayerData data);

    public Dungeon getDungeon() {
        return dungeon;
    }

    public BlockVector getVector() {
        return vector;
    }
}
