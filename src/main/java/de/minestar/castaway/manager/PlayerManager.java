package de.minestar.castaway.manager;
import java.util.HashMap;

import org.bukkit.entity.Player;

import de.minestar.castaway.data.PlayerData;

public class PlayerManager {
    private HashMap<String, PlayerData> playerMap = new HashMap<String, PlayerData>();

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

    public int getDungeonID(String playerName) {
        return this.getPlayerData(playerName).getDungeonID();
    }

    public int getDungeonID(Player player) {
        return this.getDungeonID(player.getName());
    }

    public String getDungeonName(String playerName) {
        return this.getPlayerData(playerName).getPlayerName();
    }

    public String getDungeonName(Player player) {
        return this.getDungeonName(player.getName());
    }

    public void onDeath(String playerName) {
        this.getPlayerData(playerName).onDeath();
    }

    public void onDeath(Player player) {
        this.onDeath(player.getName());
    }
}
