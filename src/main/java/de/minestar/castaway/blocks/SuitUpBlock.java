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

    private static HashMap<Material, Integer> helmetMap = new HashMap<Material, Integer>();
    private static HashMap<Material, Integer> chestplateMap = new HashMap<Material, Integer>();
    private static HashMap<Material, Integer> leggingsMap = new HashMap<Material, Integer>();
    private static HashMap<Material, Integer> bootsMap = new HashMap<Material, Integer>();

    static {
        // init helmets
        helmetMap.put(Material.LEATHER_HELMET, 0);
        helmetMap.put(Material.GOLD_HELMET, 1);
        helmetMap.put(Material.CHAINMAIL_HELMET, 2);
        helmetMap.put(Material.IRON_HELMET, 3);
        helmetMap.put(Material.DIAMOND_HELMET, 4);

        // init chestplates
        chestplateMap.put(Material.LEATHER_CHESTPLATE, 0);
        chestplateMap.put(Material.GOLD_CHESTPLATE, 1);
        chestplateMap.put(Material.CHAINMAIL_CHESTPLATE, 2);
        chestplateMap.put(Material.IRON_CHESTPLATE, 3);
        chestplateMap.put(Material.DIAMOND_CHESTPLATE, 4);

        // init leggings
        leggingsMap.put(Material.LEATHER_LEGGINGS, 0);
        leggingsMap.put(Material.GOLD_LEGGINGS, 1);
        leggingsMap.put(Material.CHAINMAIL_LEGGINGS, 2);
        leggingsMap.put(Material.IRON_LEGGINGS, 3);
        leggingsMap.put(Material.DIAMOND_LEGGINGS, 4);

        // init boots
        bootsMap.put(Material.LEATHER_BOOTS, 0);
        bootsMap.put(Material.GOLD_BOOTS, 1);
        bootsMap.put(Material.CHAINMAIL_BOOTS, 2);
        bootsMap.put(Material.IRON_BOOTS, 3);
        bootsMap.put(Material.DIAMOND_BOOTS, 4);
    }

    public SuitUpBlock(BlockVector vector, Dungeon dungeon) {
        super(vector, dungeon, ActionBlockType.SUIT_UP);
        this.setExecuteIfNotInDungeon();
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
                    if (helmetMap.get(stack.getType()) > helmetMap.get(player.getInventory().getHelmet().getType())) {
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
                    if (chestplateMap.get(stack.getType()) > chestplateMap.get(player.getInventory().getChestplate().getType())) {
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
                    if (leggingsMap.get(stack.getType()) > leggingsMap.get(player.getInventory().getLeggings().getType())) {
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
                    if (bootsMap.get(stack.getType()) > bootsMap.get(player.getInventory().getBoots().getType())) {
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
