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

package de.minestar.castaway.data;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerData {
    private final String playerName;
    private boolean inDungeon;
    private Dungeon dungeon;
    private long startTime;
    private Location respawnLocation = null;

    public PlayerData(String playerName) {
        this.playerName = playerName;
        this.inDungeon = false;
    }

    public void joinDungeon(Dungeon dungeon) {
        if (dungeon == null) {
            this.quitDungeon();
            return;
        }

        this.dungeon = dungeon;
        this.inDungeon = true;
        this.respawnLocation = null;
        this.startTime = System.currentTimeMillis();
        if (!dungeon.hasOption(DungeonOption.KEEP_INVENTORY_ON_ENTER)) {
            this.updateBukkitPlayer();
        } else {
            Player player = Bukkit.getPlayerExact(this.playerName);
            if (player != null) {
                player.setAllowFlight(false);
                player.setGameMode(GameMode.ADVENTURE);
            }
        }
    }

    public void quitDungeon() {
        this.inDungeon = false;
        this.dungeon = null;
        this.respawnLocation = null;
        this.updateBukkitPlayer();
    }

    public Player getPlayer() {
        return Bukkit.getPlayerExact(this.playerName);
    }

    private void updateBukkitPlayer() {
        Player player = Bukkit.getPlayerExact(this.playerName);
        if (player != null) {
            if (this.inDungeon) {
                player.setAllowFlight(false);
                player.setGameMode(GameMode.ADVENTURE);
                player.getInventory().clear();
                player.getInventory().setBoots(null);
                player.getInventory().setChestplate(null);
                player.getInventory().setHelmet(null);
                player.getInventory().setLeggings(null);
                player.updateInventory();
            } else {
                player.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isInDungeon() {
        return inDungeon;
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public boolean hasRespawnLocation() {
        return this.getRespawnLocation() != null;
    }

    public Location getRespawnLocation() {
        return respawnLocation;
    }

    public void setRespawnLocation(Location respawnLocation) {
        this.respawnLocation = respawnLocation;
    }
}
