package org.openRealmOfStars.AI.Mission;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.message.MessageList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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
* Mission Handling test
*
*/
public class MissionHandlingTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGatherAssault() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.GATHER, MissionPhase.BUILDING,
        new Coordinate(5, 5));
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    mission.setShipType(Mission.ASSAULT_TYPE);
    FleetList fleetList = new FleetList();
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Ship assaultShip = Mockito.mock(Ship.class);
    Mockito.when(assaultShip.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(assaultShip.hasBombs()).thenReturn(false);
    Mockito.when(assaultShip.isTrooperShip()).thenReturn(false);
    Ship troopShip = Mockito.mock(Ship.class);
    Mockito.when(troopShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(troopShip.hasBombs()).thenReturn(false);
    Mockito.when(troopShip.isTrooperShip()).thenReturn(true);
    Ship bombShip = Mockito.mock(Ship.class);
    Mockito.when(bombShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(bombShip.hasBombs()).thenReturn(true);
    Mockito.when(bombShip.isTrooperShip()).thenReturn(false);
    Fleet fleet = new Fleet(assaultShip, 5, 5);
    fleet.addShip(troopShip);
    fleet.addShip(bombShip);
    fleetList.add(fleet);
    MissionHandling.findGatheringShip(mission, info);
    assertEquals("Gather Assault #0", mission.getFleetName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGatherTrooper() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.GATHER, MissionPhase.BUILDING,
        new Coordinate(5, 5));
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    mission.setShipType(Mission.TROOPER_TYPE);
    FleetList fleetList = new FleetList();
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Ship assaultShip = Mockito.mock(Ship.class);
    Mockito.when(assaultShip.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(assaultShip.hasBombs()).thenReturn(false);
    Mockito.when(assaultShip.isTrooperShip()).thenReturn(false);
    Ship troopShip = Mockito.mock(Ship.class);
    Mockito.when(troopShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(troopShip.hasBombs()).thenReturn(false);
    Mockito.when(troopShip.isTrooperShip()).thenReturn(true);
    Ship bombShip = Mockito.mock(Ship.class);
    Mockito.when(bombShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(bombShip.hasBombs()).thenReturn(true);
    Mockito.when(bombShip.isTrooperShip()).thenReturn(false);
    Fleet fleet = new Fleet(assaultShip, 5, 5);
    fleet.addShip(troopShip);
    fleet.addShip(bombShip);
    fleetList.add(fleet);
    MissionHandling.findGatheringShip(mission, info);
    assertEquals("Gather Trooper #0", mission.getFleetName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGatherBomber() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.GATHER, MissionPhase.BUILDING,
        new Coordinate(5, 5));
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    mission.setShipType(Mission.BOMBER_TYPE);
    FleetList fleetList = new FleetList();
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Ship assaultShip = Mockito.mock(Ship.class);
    Mockito.when(assaultShip.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(assaultShip.hasBombs()).thenReturn(false);
    Mockito.when(assaultShip.isTrooperShip()).thenReturn(false);
    Ship troopShip = Mockito.mock(Ship.class);
    Mockito.when(troopShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(troopShip.hasBombs()).thenReturn(false);
    Mockito.when(troopShip.isTrooperShip()).thenReturn(true);
    Ship bombShip = Mockito.mock(Ship.class);
    Mockito.when(bombShip.getTotalMilitaryPower()).thenReturn(0);
    Mockito.when(bombShip.hasBombs()).thenReturn(true);
    Mockito.when(bombShip.isTrooperShip()).thenReturn(false);
    Fleet fleet = new Fleet(assaultShip, 5, 5);
    fleet.addShip(troopShip);
    fleet.addShip(bombShip);
    fleetList.add(fleet);
    MissionHandling.findGatheringShip(mission, info);
    assertEquals("Gather Bomber #0", mission.getFleetName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDeployStarbase() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.DEPLOY_STARBASE, MissionPhase.LOADING,
        new Coordinate(5, 5));
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Ship starbase = Mockito.mock(Ship.class);
    Mockito.when(starbase.getTotalMilitaryPower()).thenReturn(20);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.STARBASE);
    Mockito.when(starbase.getHull()).thenReturn(hull);
    Fleet fleet = new Fleet(starbase, 5, 5);
    FleetList fleetList = new FleetList();
    fleetList.add(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Game game = Mockito.mock(Game.class);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.DEEP_SPACE_ANCHOR2);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(Mockito.anyInt(), Mockito.anyInt())).thenReturn(tile);
    Mockito.when(game.getStarMap()).thenReturn(map);
    MissionHandling.handleDeployStarbase(mission, fleet, info, game);
    assertEquals(MissionPhase.EXECUTING, mission.getPhase());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDestroyStarbase() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = new Mission(MissionType.DESTROY_STARBASE, MissionPhase.PLANNING,
        new Coordinate(5, 5));
    mission.setTargetPlanet("Coordinate 5, 5");
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(missionList.noMoreGatherMissions(Mockito.anyString()))
    .thenReturn(true);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.NORMAL);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    Fleet fleet = new Fleet(ship, 5, 5);
    FleetList fleetList = new FleetList();
    fleetList.add(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Game game = Mockito.mock(Game.class);
    MissionHandling.handleDestroyStarbase(mission, fleet, info, game);
    assertEquals(MissionPhase.EXECUTING, mission.getPhase());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTrade() {
    StarMap map = Mockito.mock(StarMap.class);
    Planet planetTrader = Mockito.mock(Planet.class);
    Mockito.when(planetTrader.getName()).thenReturn("Trader I");
    Mockito.when(planetTrader.getCoordinate()).thenReturn(new Coordinate(6, 5));
    PlayerInfo owner = Mockito.mock(PlayerInfo.class);
    MessageList msgList = Mockito.mock(MessageList.class);
    Mockito.when(owner.getMsgList()).thenReturn(msgList);
    Mockito.when(planetTrader.getPlanetPlayerInfo()).thenReturn(owner);
    Planet planetHome = Mockito.mock(Planet.class);
    Mockito.when(planetHome.getName()).thenReturn("Homeworld I");
    Mockito.when(planetHome.getCoordinate()).thenReturn(new Coordinate(10, 10));
    Mockito.when(map.getPlanetByCoordinate(Mockito.anyInt(),
        Mockito.anyInt())).thenReturn(planetTrader);
    Mockito.when(map.getPlanetByName("Trader I")).thenReturn(planetTrader);
    Mockito.when(map.getPlanetByName("Homeworld I")).thenReturn(planetHome);
    DiplomacyBonusList diplomacyList = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacyList.isBonusType(DiplomacyBonusType.IN_ALLIANCE)).thenReturn(true);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getDiplomacyList(Mockito.anyInt())).thenReturn(diplomacyList);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info.getMsgList()).thenReturn(msgList);
    Mission mission = new Mission(MissionType.TRADE_FLEET, MissionPhase.LOADING,
        new Coordinate(6, 5));
    mission.setTargetPlanet("Trader I");
    mission.setPlanetBuilding("Homeworld I");
    MissionList missionList = Mockito.mock(MissionList.class);
    Mockito.when(missionList.getMissionForFleet(Mockito.anyString()))
        .thenReturn(null);
    Mockito.when(missionList.noMoreGatherMissions(Mockito.anyString()))
    .thenReturn(true);
    Mockito.when(info.getMissions()).thenReturn(missionList);
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getHullType()).thenReturn(ShipHullType.NORMAL);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getTotalMilitaryPower()).thenReturn(20);
    Mockito.when(ship.doTrade(planetTrader, info)).thenReturn(10);
    Fleet fleet = new Fleet(ship, 5, 5);
    FleetList fleetList = new FleetList();
    fleetList.add(fleet);
    Mockito.when(info.getFleets()).thenReturn(fleetList);
    Game game = Mockito.mock(Game.class);
    Mockito.when(game.getStarMap()).thenReturn(map);
    MissionHandling.handleTrade(mission, fleet, info, game);
    assertEquals(MissionPhase.LOADING, mission.getPhase());
    Mockito.when(diplomacy.getDiplomacyList(Mockito.anyInt())).thenReturn(null);
    MissionHandling.handleTrade(mission, fleet, info, game);
    assertEquals(MissionPhase.TREKKING, mission.getPhase());
    fleet.setPos(new Coordinate(10, 10));
    MissionHandling.handleTrade(mission, fleet, info, game);
    assertEquals(MissionPhase.LOADING, mission.getPhase());
  }

}
