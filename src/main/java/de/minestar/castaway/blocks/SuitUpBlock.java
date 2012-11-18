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

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.minestar.castaway.data.ActionBlockType;
import de.minestar.castaway.data.BlockVector;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.PlayerData;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class SuitUpBlock extends AbstractActionBlock {

    private static HashMap<Integer, Integer> helmetMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> chestplateMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> leggingsMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, Integer> bootsMap = new HashMap<Integer, Integer>();

    static {
        // init helmets
        helmetMap.put(Material.LEATHER_HELMET.getId(), 0);
        helmetMap.put(Material.GOLD_HELMET.getId(), 1);
        helmetMap.put(Material.CHAINMAIL_HELMET.getId(), 2);
        helmetMap.put(Material.IRON_HELMET.getId(), 3);
        helmetMap.put(Material.DIAMOND_HELMET.getId(), 4);

        // init chestplates
        chestplateMap.put(Material.LEATHER_CHESTPLATE.getId(), 0);
        chestplateMap.put(Material.GOLD_CHESTPLATE.getId(), 1);
        chestplateMap.put(Material.CHAINMAIL_CHESTPLATE.getId(), 2);
        chestplateMap.put(Material.IRON_CHESTPLATE.getId(), 3);
        chestplateMap.put(Material.DIAMOND_CHESTPLATE.getId(), 4);

        // init leggings
        leggingsMap.put(Material.LEATHER_LEGGINGS.getId(), 0);
        leggingsMap.put(Material.GOLD_LEGGINGS.getId(), 1);
        leggingsMap.put(Material.CHAINMAIL_LEGGINGS.getId(), 2);
        leggingsMap.put(Material.IRON_LEGGINGS.getId(), 3);
        leggingsMap.put(Material.DIAMOND_LEGGINGS.getId(), 4);

        // init boots
        bootsMap.put(Material.LEATHER_BOOTS.getId(), 0);
        bootsMap.put(Material.GOLD_BOOTS.getId(), 1);
        bootsMap.put(Material.CHAINMAIL_BOOTS.getId(), 2);
        bootsMap.put(Material.IRON_BOOTS.getId(), 3);
        bootsMap.put(Material.DIAMOND_BOOTS.getId(), 4);
    }

    public SuitUpBlock(BlockVector vector, Dungeon dungeon) {
        super(vector, dungeon, ActionBlockType.SUIT_UP);
        this.setHandlePhysical();
        this.setHandleLeftClick();
        this.setHandleRightClick();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean execute(Player player, PlayerData data) {
        // Player must be in a dungeon
        if (!data.isInDungeon() || !data.getDungeon().equals(this.dungeon)) {
            return false;
        }

        // only act, if we can do something...
        String name = "";
        ItemStack[] contents = player.getInventory().getContents();
        int index = -1;
        int changed = 0;
        for (ItemStack stack : contents) {
            index++;
            if (stack == null) {
                continue;
            }

            name = stack.getType().name().toLowerCase();
            if (name.contains("helmet")) {
                if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType().equals(Material.AIR)) {
                    player.getInventory().setHelmet(stack.clone());
                    player.getInventory().setItem(index, null);
                    changed++;
                } else {
                    if (helmetMap.get(stack.getTypeId()) > helmetMap.get(player.getInventory().getHelmet().getTypeId())) {
                        ItemStack otherStack = player.getInventory().getHelmet().clone();
                        player.getInventory().setHelmet(stack.clone());
                        player.getInventory().setItem(index, otherStack);
                        changed++;
                    }
                }
            } else if (name.contains("chestplate")) {
                if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType().equals(Material.AIR)) {
                    player.getInventory().setChestplate(stack.clone());
                    player.getInventory().setItem(index, null);
                    changed++;
                } else {
                    if (chestplateMap.get(stack.getTypeId()) > chestplateMap.get(player.getInventory().getChestplate().getTypeId())) {
                        ItemStack otherStack = player.getInventory().getChestplate().clone();
                        player.getInventory().setChestplate(stack.clone());
                        player.getInventory().setItem(index, otherStack);
                        changed++;
                    }
                }
            } else if (name.contains("leggings")) {
                if (player.getInventory().getLeggings() == null || player.getInventory().getLeggings().getType().equals(Material.AIR)) {
                    player.getInventory().setLeggings(stack.clone());
                    player.getInventory().setItem(index, null);
                    changed++;
                } else {
                    if (leggingsMap.get(stack.getTypeId()) > leggingsMap.get(player.getInventory().getLeggings().getTypeId())) {
                        ItemStack otherStack = player.getInventory().getLeggings().clone();
                        player.getInventory().setLeggings(stack.clone());
                        player.getInventory().setItem(index, otherStack);
                        changed++;
                    }
                }
            } else if (name.contains("boots")) {
                if (player.getInventory().getBoots() == null || player.getInventory().getBoots().getType().equals(Material.AIR)) {
                    player.getInventory().setBoots(stack.clone());
                    player.getInventory().setItem(index, null);
                    changed++;
                } else {
                    if (bootsMap.get(stack.getTypeId()) > bootsMap.get(player.getInventory().getBoots().getTypeId())) {
                        ItemStack otherStack = player.getInventory().getBoots().clone();
                        player.getInventory().setBoots(stack.clone());
                        player.getInventory().setItem(index, otherStack);
                        changed++;
                    }
                }
            }
        }

        player.updateInventory();

        if (changed > 0) {
            PlayerUtils.sendInfo(player, "Suit up!");
        }
        return false;
    }
}
