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

package de.minestar.castaway.stats;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

import de.minestar.castaway.core.CastAwayCore;
import de.minestar.minestarlibrary.stats.Statistic;
import de.minestar.minestarlibrary.stats.StatisticType;

public class BeginDungeonStat implements Statistic {

    private String playerName;
    private int dungeonID;
    private Timestamp date;

    public BeginDungeonStat() {
        // EMPTY CONSTRUCTION FOR REFLECTION ACCESS
    }

    public BeginDungeonStat(String playerName, int dungeonID) {
        this.playerName = playerName;
        this.dungeonID = dungeonID;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String getPluginName() {
        return CastAwayCore.NAME;
    }

    @Override
    public String getName() {
        return "BeginDungeon";
    }

    @Override
    public LinkedHashMap<String, StatisticType> getHead() {

        LinkedHashMap<String, StatisticType> head = new LinkedHashMap<String, StatisticType>();

        head.put("playerName", StatisticType.STRING);
        head.put("dungeonID", StatisticType.INT);
        head.put("date", StatisticType.DATETIME);

        return head;
    }

    @Override
    public Queue<Object> getData() {

        Queue<Object> data = new LinkedList<Object>();

        data.add(playerName);
        data.add(dungeonID);
        data.add(date);

        return data;
    }

}
