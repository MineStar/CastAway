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

package de.minestar.castaway.database;

import java.io.File;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import de.minestar.castaway.blocks.AbstractBlock;
import de.minestar.castaway.core.CastAwayCore;
import de.minestar.castaway.data.BlockEnum;
import de.minestar.castaway.data.BlockVector;
import de.minestar.castaway.data.Dungeon;
import de.minestar.minestarlibrary.database.AbstractMySQLHandler;
import de.minestar.minestarlibrary.database.DatabaseUtils;
import de.minestar.minestarlibrary.utils.ConsoleUtils;

public class DatabaseManager extends AbstractMySQLHandler {

    public DatabaseManager(String pluginName, File SQLConfigFile) {
        super(pluginName, SQLConfigFile);
    }

    @Override
    protected void createStructure(String pluginName, Connection con) throws Exception {
        DatabaseUtils.createStructure(DatabaseManager.class.getResourceAsStream("/structure.sql"), con, pluginName);
    }

    @Override
    protected void createStatements(String pluginName, Connection con) throws Exception {

        addDungeon = con.prepareStatement("INSERT INTO dungeon (name, creator) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

        deleteDungeon = con.prepareStatement("DELETE FROM dungeon WHERE id = ?");

        addActionBlock = con.prepareStatement("INSERT INTO actionBlock (dungeon, x, y, z, world, actionType) VALUES (?, ?, ?, ?, ?, ?)");

    }

    // ***************
    // *** DUNGEON ***
    // ***************

    private PreparedStatement addDungeon;
    private PreparedStatement deleteDungeon;

    public Map<Integer, Dungeon> loadDungeon() {

        Map<Integer, Dungeon> dungeonMap = new HashMap<Integer, Dungeon>();
        try {
            Statement stat = dbConnection.getConnection().createStatement();
            ResultSet rs = stat.executeQuery("SELECT id, name, creator FROM dungeon");

            // TEMP VARIABLES
            int id;
            String name;
            String creator;

            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2);
                creator = rs.getString(3);
                dungeonMap.put(id, new Dungeon(id, name, creator));
            }

        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't load dungeons from database!");
            dungeonMap.clear();
        }

        return dungeonMap;
    }

    public boolean addDungeon(Dungeon dungeon) {

        try {
            addDungeon.setString(1, dungeon.getDungeonName());
            addDungeon.setString(2, dungeon.getAuthor());

            addDungeon.executeUpdate();

            ResultSet rs = addDungeon.getGeneratedKeys();

            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
                dungeon.setDungeonID(id);
                return true;
            } else {
                ConsoleUtils.printError(CastAwayCore.NAME, "Can't get the id for the dungeon = " + dungeon);
                return false;
            }
        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't insert the dungeon = " + dungeon);
            return false;
        }
    }

    public boolean deleteDungeon(Dungeon dungeon) {

        try {
            deleteDungeon.setInt(1, dungeon.getDungeonID());

            return deleteDungeon.executeUpdate() == 1;
        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't delete the dungeon = " + dungeon);
            return false;
        }
    }

    // *********************
    // *** ACTION_BLOCKS ***
    // *********************

    private PreparedStatement addActionBlock;

    public Map<BlockVector, AbstractBlock> loadActionBlocks(Map<Integer, Dungeon> dungeonMap) {

        Map<BlockVector, AbstractBlock> actionBlocksMap = new HashMap<BlockVector, AbstractBlock>();

        try {
            Statement stat = dbConnection.getConnection().createStatement();
            ResultSet rs = stat.executeQuery("SELECT id, dungeon, x, y, z, world, actionType FROM actionBlock");

            // TEMP VARS
//            int id;
            int dungeonID;
            int x;
            int y;
            int z;
            String world;
            int actionType;
            BlockVector bVector;
            Class<? extends AbstractBlock> clazz;
            Constructor<?> constructor;

            while (rs.next()) {
                // GET VALUES
//                id = rs.getInt(1);
                dungeonID = rs.getInt(2);
                x = rs.getInt(3);
                y = rs.getInt(4);
                z = rs.getInt(5);
                world = rs.getString(6);
                // CHECK IF WORLD EXISTS
                if (Bukkit.getWorld(world) == null) {
                    ConsoleUtils.printWarning(CastAwayCore.NAME, "The world '" + world + "' was not found!");
                    continue;
                }
                bVector = new BlockVector(world, x, y, z);

                // GET ACTION
                actionType = rs.getInt(7);

                // GET CLASS FOR THE ACTION TYPE
                clazz = BlockEnum.byID(actionType);
                if (clazz == null) {
                    ConsoleUtils.printWarning(CastAwayCore.NAME, "Unknown action type id '" + actionType + "'!");
                    continue;
                }

                // CREATE AN INSTANCE OF THIS CLASS
                constructor = clazz.getDeclaredConstructor(BlockVector.class, Dungeon.class);
                AbstractBlock block = (AbstractBlock) constructor.newInstance(bVector, dungeonMap.get(dungeonID));

                actionBlocksMap.put(bVector, block);
            }

        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't load action blocks from database!");
            actionBlocksMap.clear();
        }

        return actionBlocksMap;
    }

    public boolean addActionBlock(AbstractBlock actionBlock) {

        try {
            addActionBlock.setInt(1, actionBlock.getDungeon().getDungeonID());
            addActionBlock.setInt(2, actionBlock.getVector().getX());
            addActionBlock.setInt(3, actionBlock.getVector().getY());
            addActionBlock.setInt(4, actionBlock.getVector().getZ());
            addActionBlock.setString(5, actionBlock.getVector().getWorldName());
            addActionBlock.setInt(6, actionBlock.getBlockType().getID());

            return addActionBlock.executeUpdate() == 1;
        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't add action block to database! ActionBlock = " + actionBlock);
            return false;
        }
    }
}
