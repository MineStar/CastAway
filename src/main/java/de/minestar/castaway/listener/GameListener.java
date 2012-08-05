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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.minestar.castaway.blocks.AbstractBlock;
import de.minestar.castaway.core.CastAwayCore;
import de.minestar.castaway.data.BlockVector;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class GameListener {

    private BlockVector vector;
    private static HashSet<Action> acceptedActions;
    private static HashSet<String> acceptedCommands;
    private static HashSet<RegainReason> blockedRegainReasons;

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

        blockedRegainReasons = new HashSet<RegainReason>();
        blockedRegainReasons.add(RegainReason.REGEN);
        blockedRegainReasons.add(RegainReason.MAGIC);
        blockedRegainReasons.add(RegainReason.MAGIC_REGEN);
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

        // is the block registered?
        AbstractBlock block = CastAwayCore.blockManager.getBlock(vector);
        if (block == null) {
            return;
        }

        // handle action
        final Action action = event.getAction();

        boolean cancelEvent = false;

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

        if (cancelEvent) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        // only handle players
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (CastAwayCore.playerManager.getPlayerData(player.getName()).isInDungeon()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onRegainHealth(EntityRegainHealthEvent event) {
        // only handle players
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (CastAwayCore.playerManager.getPlayerData(player.getName()).isInDungeon()) {
            if (blockedRegainReasons.contains(event.getRegainReason())) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
