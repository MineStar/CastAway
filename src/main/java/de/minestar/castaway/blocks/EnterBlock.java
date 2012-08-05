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

package de.minestar.castaway.blocks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.minestar.castaway.core.CastAwayCore;
import de.minestar.castaway.data.BlockVector;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.PlayerData;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class EnterBlock extends AbstractBlock {

    public EnterBlock(BlockVector vector, Dungeon dungeon) {
        super(vector, dungeon);
        this.setHandlePhysical();
    }

    @Override
    public boolean execute(Player player, PlayerData data) {
        // Player must be in normal mode
        if (data.isInDungeon()) {
            PlayerUtils.sendError(player, CastAwayCore.NAME, "Du bist momentan im Dungeon!");
            PlayerUtils.sendInfo(player, "Gib /respawn ein um dem Grauen zu entkommen.");
            return true;
        }

        // update the player & the data
        data.updateDungeon(this.dungeon);

        // regain health
        player.setHealth(20);

        // regain food
        player.setFoodLevel(20);

        // send info
        PlayerUtils.sendMessage(player, ChatColor.DARK_AQUA, "------------------------------");
        PlayerUtils.sendSuccess(player, "Herzlich Willkommen im Dungeon '" + dungeon.getDungeonName() + "'.");
        PlayerUtils.sendInfo(player, "Ersteller: " + dungeon.getAuthor());
        PlayerUtils.sendMessage(player, ChatColor.DARK_AQUA, "------------------------------");
        return false;
    }
}
