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

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import de.minestar.castaway.database.DatabaseManager;
import de.minestar.castaway.listener.GameListener;
import de.minestar.castaway.listener.RegisterListener;
import de.minestar.castaway.manager.GameManager;
import de.minestar.castaway.manager.PlayerManager;
import de.minestar.minestarlibrary.AbstractCore;

public class CastAwayCore extends AbstractCore {

    public static final String NAME = "CastAway";

    public static DatabaseManager databaseManager;

    public static GameManager gameManager;
    public static PlayerManager playerManager;

    private Listener gameListener, registerListener;

    public CastAwayCore() {
        super(NAME);
    }

    @Override
    protected boolean loadingConfigs(File dataFolder) {
        return Settings.init(dataFolder, NAME, getDescription().getVersion());
    }

    @Override
    protected boolean createManager() {

        databaseManager = new DatabaseManager(NAME, new File(getDataFolder(), "sqlconfig.yml"));
        if (!databaseManager.hasConnection())
            return false;

        gameManager = new GameManager();
        playerManager = new PlayerManager();

        gameManager.init();

        return true;
    }

    @Override
    protected boolean createListener() {
        this.registerListener = new RegisterListener();
        this.gameListener = new GameListener();
        return true;
    }

    @Override
    protected boolean registerEvents(PluginManager pm) {
        pm.registerEvents(this.registerListener, this);
        pm.registerEvents(this.gameListener, this);
        return true;
    }

    @Override
    protected boolean commonDisable() {
        databaseManager.closeConnection();
        return !databaseManager.hasConnection();
    }
}
