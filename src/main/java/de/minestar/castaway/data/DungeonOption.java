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

public enum DungeonOption {

    ENABLE_HUNGER(1, "ENABLE_HUNGER"),

    ENABLE_AUTO_REGAIN_HEALTH(2, "ENABLE_AUTO_REGAIN_HEALTH"),

    KEEP_DUNGEON_MODE_ON_DEATH(4, "KEEP_MODE_ON_DEATH"),

    CLEAR_INVENTORY_ON_DEATH(8, "CLEAR_INV_ON_DEATH"),

    CLEAR_INVENTORY_ON_FINISH(16, "CLEAR_INV_ON_FINISH"),

    BLOCK_AUTO_REGAIN_HEALTH(32, "BLOCK_AUTO_REGAIN_HEALTH"),

    ALLOW_BLOCK_PLACE(64, "ALLOW_BLOCK_PLACE"),

    ALLOW_BLOCK_BREAK(128, "ALLOW_BLOCK_BREAK"),

    ALLOW_ONLY_ONE_PLAYER(256, "ALLOW_ONLY_ONE_PLAYER"),

    DISABLE_SAVE_ON_FINISH(512, "DISABLE_SAVE_ON_FINISH");

    private final int bit;
    private final String command;

    private DungeonOption(int bit, String command) {
        this.bit = bit;
        this.command = command.toLowerCase();
    }

    public int getBit() {
        return bit;
    }

    public String getCommand() {
        return command;
    }

    public static DungeonOption byString(String text) {
        for (DungeonOption option : DungeonOption.values()) {
            if (option.getCommand().equalsIgnoreCase(text)) {
                return option;
            }
        }
        return null;
    }

}
