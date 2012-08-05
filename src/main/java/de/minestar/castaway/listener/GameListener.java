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

package de.minestar.castaway.listener;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.minestar.castaway.core.CastAwayCore;
import de.minestar.castaway.data.BlockVector;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class GameListener {

    private BlockVector vector;
    private static HashSet<Action> acceptedActions;
    private static HashSet<String> acceptedCommands;

    static {
        acceptedActions = new HashSet<Action>();
        acceptedActions.add(Action.LEFT_CLICK_BLOCK);
        acceptedActions.add(Action.RIGHT_CLICK_BLOCK);
        acceptedActions.add(Action.PHYSICAL);

        acceptedCommands = new HashSet<String>();
        acceptedCommands.add("/respawn");
        acceptedCommands.add("/message");
        acceptedCommands.add("/m");
        acceptedCommands.add("/r");
    }

    public GameListener() {
        this.vector = new BlockVector("", 0, 0, 0);
    }

    // //////////////////////////////////////
    //
    // COMMAND-HANDLING
    //
    // //////////////////////////////////////

    private String getCommand(String message) {
        String[] split = message.split(" ");
        if (split != null && split.length > 0) {
            return split[0];
        }
        return "";
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        // is the player in DungeonMode
        if (!CastAwayCore.playerManager.isInDungeon(event.getPlayer()))
            return;

        // is the command accepted
        if (!acceptedCommands.contains(this.getCommand(event.getMessage()))) {
            PlayerUtils.sendError(event.getPlayer(), CastAwayCore.NAME, "Du befindest dich zur Zeit in einem Dungeon!");
            PlayerUtils.sendInfo(event.getPlayer(), "Nutzbare Kommandos:");
            PlayerUtils.sendInfo(event.getPlayer(), "/respawn | /message | /m | /r");
            event.setCancelled(true);
            return;
        }
    }

    // //////////////////////////////////////
    //
    // PLAYER-HANDLING
    //
    // //////////////////////////////////////

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        // player must be in DungeonMode
        if (!CastAwayCore.playerManager.isInDungeon(event.getPlayer()))
            return;

        // do we have a valid action?
        if (!acceptedActions.contains(event.getAction()))
            return;

        // update BlockVector
        this.vector.update(event.getClickedBlock());

        // handle action
        final Action action = event.getAction();

        switch (action) {
            case LEFT_CLICK_BLOCK : {
                // handle left-click on a block
                break;
            }
            case RIGHT_CLICK_BLOCK : {
                // handle right-click on a block
                break;
            }
            case PHYSICAL : {
                // handle physical action - we need a stoneplate
                if (!event.getClickedBlock().getType().equals(Material.STONE_PLATE))
                    break;

                // if we have a registered block -> handle the action

                break;
            }
            default : {
                // do nothing :{
                break;
            }
        }
    }

}
