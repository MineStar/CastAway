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

package de.minestar.castaway.core;

import java.io.File;

import de.minestar.castaway.database.DatabaseManager;
import de.minestar.castaway.manager.BlockManager;
import de.minestar.castaway.manager.PlayerManager;
import de.minestar.minestarlibrary.AbstractCore;

public class CastAwayCore extends AbstractCore {

    public static final String NAME = "CastAway";

    public static DatabaseManager databaseManager;

    public static BlockManager blockManager;
    public static PlayerManager playerManager;

    public CastAwayCore() {
        super(NAME);
    }

    @Override
    protected boolean createManager() {

        databaseManager = new DatabaseManager(NAME, new File(getDataFolder(), "sqlconfig.yml"));
        if (!databaseManager.hasConnection())
            return false;

        blockManager = new BlockManager();
        playerManager = new PlayerManager();
        return true;
    }
}
