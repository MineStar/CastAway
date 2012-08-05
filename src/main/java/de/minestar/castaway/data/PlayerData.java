package de.minestar.castaway.data;

public class PlayerData {
    private final String playerName;
    private boolean inDungeon;
    private String dungeonName = "";
    private int dungeonID = -1;

    public PlayerData(String playerName) {
        this.playerName = playerName;
        this.inDungeon = false;
    }

    public void updateDungeon(int dungeonID, String dungeonName) {
        this.dungeonID = dungeonID;
        this.dungeonName = dungeonName;
        this.inDungeon = true;
    }

    public void onDeath() {
        this.inDungeon = false;
        this.dungeonID = -1;
        this.dungeonName = "";
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isInDungeon() {
        return inDungeon;
    }

    public int getDungeonID() {
        return dungeonID;
    }

    public String getDungeonName() {
        return dungeonName;
    }
}
