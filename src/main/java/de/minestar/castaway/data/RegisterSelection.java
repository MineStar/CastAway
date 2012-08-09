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
    private RegisterAction action = RegisterAction.ADD_BLOCK;
    // Constructor used for : /castaway register <Dungeon> <BlockType>
    public RegisterSelection(ActionBlockType actionBlockType, Dungeon dungeon) {
        this.actionBlockType = actionBlockType;
        this.dungeon = dungeon;
    }
    // Constructor used for : Registering of HoF-Signs
    public RegisterSelection(Dungeon dungeon, RegisterAction action) {
        this.dungeon = dungeon;
        this.actionBlockType = null;
        this.action = action;
    }

    // Constructor used for : /castaway unregister
    public RegisterSelection(RegisterAction action) {
        this.dungeon = null;
        this.actionBlockType = null;
        this.action = action;
    }

    public RegisterAction getAction() {
        return action;
    }

    public ActionBlockType getActionBlockType() {
        return actionBlockType;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

}
