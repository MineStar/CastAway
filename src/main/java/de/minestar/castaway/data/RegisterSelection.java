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

public class RegisterSelection {

    private ActionBlockType actionBlockType;
    private Dungeon dungeon;
    private boolean isSignEdit = false;

    public RegisterSelection(ActionBlockType actionBlockType, Dungeon dungeon) {
        this.actionBlockType = actionBlockType;
        this.dungeon = dungeon;
    }

    public RegisterSelection(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.actionBlockType = null;
        this.isSignEdit = true;
    }

    public boolean isSignEdit() {
        return isSignEdit;
    }

    public ActionBlockType getActionBlockType() {
        return actionBlockType;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

}
