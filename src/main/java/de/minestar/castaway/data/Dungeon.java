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

package de.minestar.castaway.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.minestar.castaway.blocks.AbstractActionBlock;
import de.minestar.castaway.core.CastAwayCore;
import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class Dungeon {

    private final String creator;
    private final String name;
    private int ID;

    private Map<BlockVector, AbstractActionBlock> registeredBlocks;

    private final int hash;

    public Dungeon(int dungeonID, String name, String creator) {
        this.ID = dungeonID;
        this.name = name;
        this.creator = creator;
        this.registeredBlocks = new HashMap<BlockVector, AbstractActionBlock>();

        this.hash = generateHash();
    }

    public Dungeon(String name, String creator) {
        this(0, name, creator);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return creator;
    }

    public void setID(int ID) {
        if (this.ID == 0)
            this.ID = ID;
        else
            ConsoleUtils.printError(CastAwayCore.NAME, "The dungeon " + this.toString() + " has already an database ID!");
    }

    public void registerBlocks(AbstractActionBlock... blocks) {
        for (AbstractActionBlock block : blocks)
            this.registeredBlocks.put(block.getVector(), block);
    }

    public void registerBlocks(Collection<AbstractActionBlock> blocks) {
        for (AbstractActionBlock block : blocks)
            this.registeredBlocks.put(block.getVector(), block);
    }

    public void unRegisterBlocks(BlockVector... blockPositions) {
        for (BlockVector blockPosition : blockPositions)
            this.registeredBlocks.remove(blockPosition);
    }

    public Map<BlockVector, AbstractActionBlock> getRegisteredBlocks() {
        return new HashMap<BlockVector, AbstractActionBlock>(this.registeredBlocks);
    }

    @Override
    public String toString() {
        return "Dungeon = { Name=" + name + ", Creator=" + creator + ", ID=" + ID + " }";
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (!(that instanceof Dungeon))
            return false;
        if (that == this)
            return true;

        return this.ID == ((Dungeon) that).ID;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    private int generateHash() {
        int tempHash = 17;
        tempHash *= this.name.hashCode();
        tempHash *= this.creator.hashCode();

        return (tempHash << 5) - tempHash;
    }
}
