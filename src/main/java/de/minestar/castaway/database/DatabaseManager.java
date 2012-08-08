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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import de.minestar.castaway.blocks.AbstractActionBlock;
import de.minestar.castaway.core.CastAwayCore;
import de.minestar.castaway.data.ActionBlockType;
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

        /* DUNGEON */
        addDungeon = con.prepareStatement("INSERT INTO dungeon (name, creator) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

        deleteDungeon = con.prepareStatement("DELETE FROM dungeon WHERE id = ?");

        addWinner = con.prepareStatement("INSERT INTO winner ( playerName, dungeon, date ) VALUES ( ?, ?, NOW() )");

        addHighScore = con.prepareStatement("INSERT INTO highscore ( player, dungeon, time, date ) VALUES ( ?, ?, ?, NOW() )");

        isWinner = con.prepareStatement("SELECT 1 FROM winner WHERE dungeon = ? AND playerName = ?");

        /* ACTION BLOCKS */

        addActionBlock = con.prepareStatement("INSERT INTO actionBlock (dungeon, x, y, z, world, actionType) VALUES (?, ?, ?, ?, ?, ?)");

        deleteSingleRegisteredBlock = con.prepareStatement("DELETE FROM actionBlock WHERE dungeon = ? AND y = ? AND x = ? AND z = ? AND world = ?");

        deleteRegisteredBlocks = con.prepareStatement("DELETE FROM actionBlock WHERE dungeon = ?");

    }

    // ***************
    // *** DUNGEON ***
    // ***************

    private PreparedStatement addDungeon;
    private PreparedStatement deleteDungeon;
    private PreparedStatement addWinner;
    private PreparedStatement addHighScore;
    private PreparedStatement isWinner;

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
            addDungeon.setString(1, dungeon.getName());
            addDungeon.setString(2, dungeon.getAuthor());

            addDungeon.executeUpdate();

            ResultSet rs = addDungeon.getGeneratedKeys();

            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
                dungeon.setID(id);
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
            deleteDungeon.setInt(1, dungeon.getID());
            return deleteDungeon.executeUpdate() == 1;
        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't delete the dungeon = " + dungeon);
            return false;
        }
    }

    public boolean addWinner(Dungeon dungeon, String playerName, long time) {
        try {

            // PLAYER HAS NEVER BEATEN THIS DUNGEON BEFORE
            // STORE HIM ONCE IN WINNER TABLE
            if (!isWinner(dungeon, playerName)) {
                addWinner.setString(1, playerName);
                addWinner.setInt(2, dungeon.getID());
                if (addWinner.executeUpdate() != 1) {
                    ConsoleUtils.printError(CastAwayCore.NAME, "Can't add a '" + playerName + "' to the winner table!");
                    return false;
                }
            }

            // STORE CURRENT RUN
            addHighScore.setString(1, playerName);
            addHighScore.setInt(2, dungeon.getID());
            addHighScore.setLong(3, time);

            return addHighScore.executeUpdate() == 1;
        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't add a the winner '" + playerName + "' for the dungeon = " + dungeon);
            return false;
        }
    }

    public boolean isWinner(Dungeon dungeon, String playerName) {
        try {

            isWinner.setInt(1, dungeon.getID());
            isWinner.setString(2, playerName);
            return isWinner.executeQuery().next();
        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't check if '" + playerName + "' is a winner of the dungeon " + dungeon);
            return false;
        }

    }

    // *********************
    // *** ACTION_BLOCKS ***
    // *********************

    private PreparedStatement addActionBlock;
    private PreparedStatement deleteSingleRegisteredBlock;
    private PreparedStatement deleteRegisteredBlocks;

    public List<AbstractActionBlock> loadRegisteredActionBlocks(Dungeon dungeon) {

        List<AbstractActionBlock> actionBlockList = new LinkedList<AbstractActionBlock>();
        try {
            Statement stat = dbConnection.getConnection().createStatement();
            ResultSet rs = stat.executeQuery("SELECT id, dungeon, x, y, z, world, actionType FROM actionBlock WHERE dungeon = " + dungeon.getID());

            // TEMP VARS
//            int id;
//            int dungeonID;
            int x;
            int y;
            int z;
            String world;
            int actionType;
            BlockVector bVector;
            Class<? extends AbstractActionBlock> clazz;
            Constructor<?> constructor;

            while (rs.next()) {
                // GET VALUES
//                id = rs.getInt(1);
//                dungeonID = rs.getInt(2)
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
                clazz = ActionBlockType.get(actionType).getClazz();
                if (clazz == null) {
                    ConsoleUtils.printWarning(CastAwayCore.NAME, "Unknown action type id '" + actionType + "'!");
                    continue;
                }

                // CREATE AN INSTANCE OF THIS CLASS
                constructor = clazz.getDeclaredConstructor(BlockVector.class, Dungeon.class);
                AbstractActionBlock block = (AbstractActionBlock) constructor.newInstance(bVector, dungeon);

                actionBlockList.add(block);
            }

        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't load action blocks from database for dungeon = " + dungeon);
            actionBlockList.clear();
        }

        return actionBlockList;
    }

    public boolean addActionBlock(AbstractActionBlock actionBlock) {

        try {
            addActionBlock.setInt(1, actionBlock.getDungeon().getID());
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

    public boolean deleteSingleRegisteredBlock(AbstractActionBlock actionBlock) {
        // DELETE FROM actionBlock WHERE dungeon = ? AND y = ? AND x = ? AND z =
        // ? AND world = ?
        try {
            deleteSingleRegisteredBlock.setInt(1, actionBlock.getDungeon().getID());
            deleteSingleRegisteredBlock.setInt(2, actionBlock.getVector().getY());
            deleteSingleRegisteredBlock.setInt(3, actionBlock.getVector().getX());
            deleteSingleRegisteredBlock.setInt(4, actionBlock.getVector().getZ());
            deleteSingleRegisteredBlock.setString(5, actionBlock.getVector().getWorldName());

            return deleteSingleRegisteredBlock.executeUpdate() == 1;
        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't delete single registered blocks from database! ActionBlock =" + actionBlock);
            return false;
        }
    }

    public boolean deleteRegisteredBlocks(Dungeon dungeon) {
        try {
            deleteRegisteredBlocks.setInt(1, dungeon.getID());

            return deleteRegisteredBlocks.executeUpdate() >= 0;
        } catch (Exception e) {
            ConsoleUtils.printException(e, CastAwayCore.NAME, "Can't delete registered blocks from database! Dungeon = " + dungeon);
            return false;
        }
    }
}
