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

public class Dungeon {
    private final String author;
    private final String dungeonName;
    private final int dungeonID;

    public Dungeon(int dungeonID, String dungeonName, String author) {
        this.dungeonID = dungeonID;
        this.dungeonName = dungeonName;
        this.author = author;
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
}
