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

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.minestar.castaway.data.ActionBlockType;
import de.minestar.castaway.data.BlockVector;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.PlayerData;

public class SuitUpBlock extends AbstractActionBlock {

    public SuitUpBlock(BlockVector vector, Dungeon dungeon) {
        super(vector, dungeon, ActionBlockType.SUIT_UP);
        this.setHandlePhysical();
        this.setHandleLeftClick();
        this.setHandleRightClick();
    }

    @Override
    public boolean execute(Player player, PlayerData data) {
        // Player must be in a dungeon
        if (!data.isInDungeon() || !data.getDungeon().equals(this.dungeon)) {
            return false;
        }

        // only act, if we can do something...
        String name = "";
        ItemStack[] contents = player.getInventory().getContents();
        int count = 0;
        for (ItemStack stack : contents) {
            if (stack == null) {
                continue;
            }

            if (count > 3) {
                return false;
            }

            name = stack.getType().name().toLowerCase();
            if (name.contains("helmet")) {
                player.getInventory().setHelmet(stack);
                count++;
                continue;
            } else if (name.contains("chestplate")) {
                player.getInventory().setChestplate(stack);
                count++;
                continue;
            } else if (name.contains("leggings")) {
                player.getInventory().setLeggings(stack);
                count++;
                continue;
            } else if (name.contains("boots")) {
                player.getInventory().setBoots(stack);
                count++;
                continue;
            }
        }
        return false;
    }
}
