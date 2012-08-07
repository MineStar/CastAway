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

import de.minestar.castaway.data.BlockEnum;
import de.minestar.castaway.data.BlockVector;
import de.minestar.castaway.data.Dungeon;
import de.minestar.castaway.data.PlayerData;

public abstract class AbstractActionBlock {

    protected final Dungeon dungeon;
    protected final BlockVector vector;

    private boolean handleLeftClick = false;
    private boolean handleRightClick = false;
    private boolean handlePhysical = false;
    private boolean executeIfNotInDungeon = false;
    private BlockEnum blockType = BlockEnum.UNKNOWN;

    private final int TypeID;
    private final byte SubID;

    public AbstractActionBlock(BlockVector vector, Dungeon dungeon, int TypeID) {
        this(vector, dungeon, TypeID, (byte) 0);
    }

    public AbstractActionBlock(BlockVector vector, Dungeon dungeon, int TypeID, byte SubID) {
        this.vector = vector;
        this.dungeon = dungeon;
        this.TypeID = TypeID;
        this.SubID = SubID;
    }

    /**
     * Execute-method. This is called when a block should be executed.
     * 
     * @param player
     * @param data
     * @return <b>true</b> if the event should be cancelled, otherwise
     *         <b>false</b>
     */
    public abstract boolean execute(Player player, PlayerData data);

    public int getTypeID() {
        return TypeID;
    }

    public byte getSubID() {
        return SubID;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public BlockVector getVector() {
        return vector;
    }

    /**
     * @return the handleLeftClick
     */
    public boolean isHandleLeftClick() {
        return handleLeftClick;
    }

    /**
     * @param handleLeftClick
     *            the handleLeftClick to set
     */
    protected void setHandleLeftClick() {
        this.handleLeftClick = true;
    }

    /**
     * @return the handleRightClick
     */
    public boolean isHandleRightClick() {
        return handleRightClick;
    }

    /**
     * @param handleRightClick
     *            the handleRightClick to set
     */
    protected void setHandleRightClick() {
        this.handleRightClick = true;
    }

    /**
     * @return the handlePhysical
     */
    public boolean isHandlePhysical() {
        return handlePhysical;
    }

    /**
     * @param handlePhysical
     *            the handlePhysical to set
     */
    protected void setHandlePhysical() {
        this.handlePhysical = true;
    }

    /**
     * @return the executeIfNotInDungeon
     */
    public boolean isExecuteIfNotInDungeon() {
        return executeIfNotInDungeon;
    }

    /**
     * @param executeIfNotInDungeon
     *            the executeIfNotInDungeon to set
     */
    protected void setExecuteIfNotInDungeon() {
        this.executeIfNotInDungeon = true;
    }

    /**
     * @return the blockType
     */
    public BlockEnum getBlockType() {
        return blockType;
    }

    /**
     * @param blockType
     *            the blockType to set
     */
    protected void setBlockType(BlockEnum blockType) {
        this.blockType = blockType;
    }
}
