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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.minestar.minestarlibrary.config.MinestarConfig;
import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class Settings {

    /* USED FOR SETTING */
    private static MinestarConfig config;
    private static File configFile;

    /* VALUES */
    private static Set<String> acceptedCommands;

    private Settings() {

    }

    public static boolean init(File dataFolder, String pluginName, String pluginVersion) {
        configFile = new File(dataFolder, "config.yml");
        try {
            // LOAD EXISTING CONFIG FILE
            if (configFile.exists())
                config = new MinestarConfig(configFile, pluginName, pluginVersion);
            // CREATE A DEFAUL ONE
            else
                config = MinestarConfig.copyDefault(Settings.class.getResourceAsStream("/config.yml"), configFile);

            loadValues();
            return true;

        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't load the settings from " + configFile);
            return false;
        }
    }

    private static void loadValues() {

        loadAcceptedCommands();
    }

    private static void loadAcceptedCommands() {
        acceptedCommands = new HashSet<String>();

        List<?> list = config.getList("inDungeon.acceptedCommands");
        if (list == null || list.isEmpty()) {
            ConsoleUtils.printInfo(CastAwayCore.NAME, "All commands are allowed in dungeons!");
            return;
        }
        for (Object o : list)
            acceptedCommands.add(o.toString().toLowerCase());

        ConsoleUtils.printInfo(CastAwayCore.NAME, "The followning commands are only allowed in dungeons: " + acceptedCommands.toString());
    }

    public static Set<String> getAcceptedCommands() {
        return acceptedCommands;
    }
}
