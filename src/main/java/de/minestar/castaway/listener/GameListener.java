package de.minestar.castaway.listener;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.minestar.castaway.core.CastAwayCore;

public class GameListener {

    private static HashSet<Action> acceptedActions;

    static {
        acceptedActions = new HashSet<Action>();
        acceptedActions.add(Action.LEFT_CLICK_BLOCK);
        acceptedActions.add(Action.RIGHT_CLICK_BLOCK);
        acceptedActions.add(Action.PHYSICAL);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {

        // is the player in DungeonMode
        if (!CastAwayCore.playerManager.isInDungeon(event.getPlayer()))
            return;

        // do we have a valid action?
        if (!acceptedActions.contains(event.getAction()))
            return;

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
