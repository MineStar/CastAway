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

import de.minestar.castaway.core.CastAwayCore;
import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class Dungeon {
    private final String author;
    private final String dungeonName;
    private int dungeonID;

    private final int hash;

    public Dungeon(int dungeonID, String dungeonName, String author) {
        this.dungeonID = dungeonID;
        this.dungeonName = dungeonName;
        this.author = author;

        this.hash = generateHash();
    }

    public Dungeon(String dungeonName, String author) {
        this(0, dungeonName, author);
    }

    public int getDungeonID() {
        return dungeonID;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public String getAuthor() {
        return author;
    }

    public void setDungeonID(int id) {
        if (this.dungeonID == 0)
            this.dungeonID = id;
        else
            ConsoleUtils.printError(CastAwayCore.NAME, "The dungeon " + this.toString() + " has already an database ID!");
    }

    @Override
    public String toString() {
        return "Dungeon = { Name=" + dungeonName + ", Creator=" + author + ", ID=" + dungeonID + " }";
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (!(that instanceof Dungeon))
            return false;
        if (that == this)
            return true;

        return this.dungeonID == ((Dungeon) that).dungeonID;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    private int generateHash() {
        int tempHash = 17;
        tempHash *= this.dungeonName.hashCode();
        tempHash *= this.author.hashCode();

        return (tempHash << 5) - tempHash;
    }
}
