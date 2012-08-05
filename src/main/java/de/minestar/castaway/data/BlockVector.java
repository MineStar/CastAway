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
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockVector implements Comparable<BlockVector> {
    private int x, y, z;
    private String worldName;
    private int hashCode = Integer.MIN_VALUE;
    private Location location = null;

    /**
     * Constructor
     * 
     * @param the
     *            worldName
     * @param the
     *            x
     * @param the
     *            y
     * @param the
     *            z
     */
    public BlockVector(String worldName, int x, int y, int z) {
        this.update(worldName, x, y, z);
    }

    /**
     * Constructor
     * 
     * @param the
     *            location
     */
    public BlockVector(Location location) {
        this.update(location);
    }

    /**
     * Constructor
     * 
     * @param the
     *            block
     */
    public BlockVector(Block block) {
        this(block.getLocation());
    }

    /**
     * Update the BlockVector
     * 
     * @param the
     *            worldName
     * @param the
     *            x
     * @param the
     *            y
     * @param the
     *            z
     */
    public void update(String worldName, int x, int y, int z) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.location = null;
        this.hashCode = Integer.MIN_VALUE;
    }

    /**
     * Update the BlockVector
     * 
     * @param location
     */
    public void update(Location location) {
        this.update(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        this.location = location;
    }

    /**
     * Update the BlockVector
     * 
     * @param block
     */
    public void update(Block block) {
        this.update(block.getLocation());
    }

    /**
     * Get the Location
     * 
     * @return the location
     */
    public Location getLocation() {
        if (this.location == null) {
            World world = Bukkit.getWorld(this.worldName);
            if (world != null) {
                this.location = new Location(world, this.x, this.y, this.z);
            }
        }
        return this.location;
    }

    /**
     * @return the location relative to this one
     */
    public Location getRelativeLocation(BlockFace face) {
        Location result = this.getLocation();
        if (result != null) {
            result = result.getBlock().getRelative(face).getLocation();
        }
        return result;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the z
     */
    public int getZ() {
        return z;
    }

    /**
     * @return the worldName
     */
    public String getWorldName() {
        return worldName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj instanceof BlockVector) {
            return this.equals((BlockVector) obj);
        }
        return false;
    }

    /**
     * Returns a new BlockVector that is relative to this BlockVector with the
     * given positions
     * 
     * @param x
     * @param y
     * @param z
     * @return the relative BlockVector
     */
    public BlockVector getRelative(int x, int y, int z) {
        return new BlockVector(this.worldName, this.x + x, this.y + y, this.z + z);
    }

    /**
     * Check if another BlockVector equals this BlockVector
     * 
     * @param other
     * @return <b>true</b> if the vectors are equal, otherwise <b>false</b>
     */
    public boolean equals(BlockVector other) {
        return (this.x == other.x && this.y == other.y && this.z == other.z && this.worldName.equalsIgnoreCase(other.worldName));
    }

    @Override
    public int hashCode() {
        if (this.hashCode == Integer.MIN_VALUE) {
            this.hashCode = this.toString().hashCode();
        }
        return this.hashCode;
    }

    @Override
    public BlockVector clone() {
        return new BlockVector(this.worldName, this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "BlockVector={ " + this.worldName + " ; " + this.x + " ; " + this.y + " ; " + this.z + " }";
    }

    @Override
    public int compareTo(BlockVector other) {
        return this.hashCode() - other.hashCode();
    }
}
