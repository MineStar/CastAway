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

import java.util.Date;

public class Winner {

    private final String playerName;
    private final Date date;
    private final Dungeon dungeon;
    private final int position;

    private final int hash;

    public Winner(String playerName, long date, int position, Dungeon dungeon) {
        this.playerName = playerName;
        this.date = new Date(date);
        this.dungeon = dungeon;
        this.position = position;

        hash = generateHash();
    }

    public String getPlayerName() {
        return playerName;
    }

    public Date getDate() {
        return date;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Winner= { Player=" + playerName + ", Date=" + date + ", Dungeon=" + dungeon + ", Position=" + position + "}";
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    private int generateHash() {
        int tempHash = 17;
        tempHash *= playerName.hashCode();
        tempHash *= date.hashCode();
        tempHash *= dungeon.hashCode();
        tempHash *= position;

        return (tempHash << 5) - tempHash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Winner))
            return false;
        if (obj == this)
            return true;

        Winner that = (Winner) obj;
        return this.position == that.position && this.date.equals(that.date) && this.playerName.equals(that.playerName) && this.dungeon.equals(that.dungeon);
    }

}
