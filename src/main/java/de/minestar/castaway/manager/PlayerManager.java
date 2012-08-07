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

package de.minestar.castaway.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.PlayerData;
import de.minestar.castaway.data.RegisterSelection;

public class PlayerManager {

    private HashMap<String, PlayerData> playerMap = new HashMap<String, PlayerData>();

    private Map<String, RegisterSelection> registerBlockMap = new HashMap<String, RegisterSelection>();

    public PlayerData getPlayerData(Player player) {
        return this.getPlayerData(player.getName());
    }

    public PlayerData getPlayerData(String playerName) {
        PlayerData data = this.playerMap.get(playerName);
        if (data == null) {
            data = new PlayerData(playerName);
            playerMap.put(playerName, data);
        }
        return data;
    }

    public void removePlayerData(String playerName) {
        this.playerMap.remove(playerName);
    }

    public boolean isInDungeon(String playerName) {
        return this.getPlayerData(playerName).isInDungeon();
    }

    public boolean isInDungeon(Player player) {
        return this.isInDungeon(player.getName());
    }

    public Dungeon getDungeon(String playerName) {
        return this.getPlayerData(playerName).getDungeon();
    }

    public Dungeon getDungeon(Player player) {
        return this.getDungeon(player.getName());
    }

    public void onDeath(String playerName) {
        this.getPlayerData(playerName).quitDungeon();
    }

    public void onDeath(Player player) {
        this.onDeath(player.getName());
    }

    public void addRegisterMode(String playerName, RegisterSelection registerSelection) {
        this.registerBlockMap.put(playerName, registerSelection);
    }

    public void removeRegisterMode(String playerName) {
        this.registerBlockMap.remove(playerName);
    }

    public RegisterSelection getBlockTypeToRegister(String playerName) {
        return this.registerBlockMap.remove(playerName);
    }
}
