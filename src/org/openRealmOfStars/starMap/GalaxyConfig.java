package org.openRealmOfStars.starMap;

import org.openRealmOfStars.player.SpaceRace;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 * 
 * 
 * Galaxy config for creating new starmap with player
 * 
 */
public class GalaxyConfig {

  /**
   * Galaxy size X coordinates
   */
  private int sizeX;
  /**
   * Galaxy size Y coordinates
   */
  private int sizeY;
  
  /**
   * How many players there are
   */
  private int maxPlayers;
  
  /**
   * How far away solar systems are from each others
   */
  private int solarSystemDistance;

  /**
   * Players start from the map border
   */
  public final static int START_POSITION_BORDER = 0;

  /**
   * Players start from random position
   */
  public final static int START_POSITION_RANDOM = 1;

  /**
   * Where players start
   */
  private int startingPosition;
  
  /**
   * Player races
   */
  private SpaceRace[] playerRaces;
  
  /**
   * Constructor for galaxy config
   */
  public GalaxyConfig() {
    sizeX = 75;
    sizeY = 75;
    setMaxPlayers(4);
    setSolarSystemDistance(20);
    playerRaces = new SpaceRace[StarMapStatics.MAX_PLAYERS];
    for (int i=0;i<StarMapStatics.MAX_PLAYERS;i++) {
      setRace(i,SpaceRace.HUMAN);
    }
  }
  
  public void setRace(int index,SpaceRace race) {
    if (index >= 0 && index < StarMapStatics.MAX_PLAYERS) {
      playerRaces[index] = race;
    }
  }
  
  public SpaceRace getRace(int index) {
    if (index >= 0 && index < StarMapStatics.MAX_PLAYERS) {
      return playerRaces[index];
    }
    return null;
  }
  
  /**
   * Set galaxy size
   * @param x X size
   * @param y Y Size
   */
  public void setSize(int x, int y) {
    this.sizeX = x;
    this.sizeY = x;
  }
  
  public int getSizeX() {
    return sizeX;
  }
  public int getSizeY() {
    return sizeY;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(int maxPlayers) {
    if (maxPlayers >= 2 && maxPlayers <= 8) {
      this.maxPlayers = maxPlayers;
    }
  }

  public int getSolarSystemDistance() {
    return solarSystemDistance;
  }

  public void setSolarSystemDistance(int solarSystemDistance) {
    this.solarSystemDistance = solarSystemDistance;
  }

  public int getStartingPosition() {
    return startingPosition;
  }

  public void setStartingPosition(int startingPosition) {
    this.startingPosition = startingPosition;
  }
}