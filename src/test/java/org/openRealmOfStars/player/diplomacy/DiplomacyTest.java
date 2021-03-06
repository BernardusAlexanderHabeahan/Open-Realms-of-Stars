package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
 * Tests for Diplomacy
 */
public class DiplomacyTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertNotEquals(null, diplomacy.getDiplomacyList(2));
    assertNotEquals(null, diplomacy.getDiplomacyList(3));
    assertEquals(null, diplomacy.getDiplomacyList(1));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic2() {
    Diplomacy diplomacy = new Diplomacy(4);
    assertEquals(4, diplomacy.getDiplomacySize());
    assertEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(null, diplomacy.getDiplomacyList(1));
    assertEquals(null, diplomacy.getDiplomacyList(2));
    assertEquals(null, diplomacy.getDiplomacyList(3));
    DiplomacyBonusList list = Mockito.mock(DiplomacyBonusList.class);
    diplomacy.setList(list, 0);
    assertEquals(list, diplomacy.getDiplomacyList(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsMultipleBorderCrossong() {
    Diplomacy diplomacy = new Diplomacy(4);
    assertEquals(4, diplomacy.getDiplomacySize());
    assertEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(null, diplomacy.getDiplomacyList(1));
    assertEquals(null, diplomacy.getDiplomacyList(2));
    assertEquals(null, diplomacy.getDiplomacyList(3));
    DiplomacyBonusList list = Mockito.mock(DiplomacyBonusList.class);
    DiplomacyBonus bonusBorderCross = Mockito.mock(DiplomacyBonus.class);
    Mockito.when(bonusBorderCross.getType()).thenReturn(DiplomacyBonusType.BORDER_CROSSED);
    DiplomacyBonus bonus = Mockito.mock(DiplomacyBonus.class);
    Mockito.when(bonus.getType()).thenReturn(DiplomacyBonusType.DIPLOMATIC_TRADE);
    Mockito.when(list.getListSize()).thenReturn(5);
    Mockito.when(list.get(0)).thenReturn(bonus);
    Mockito.when(list.get(1)).thenReturn(bonusBorderCross);
    Mockito.when(list.get(2)).thenReturn(bonusBorderCross);
    Mockito.when(list.get(3)).thenReturn(bonus);
    Mockito.when(list.get(4)).thenReturn(bonusBorderCross);
    diplomacy.setList(list, 0);
    assertEquals(true, diplomacy.isMultipleBorderCrossing(0));
    Mockito.when(list.get(1)).thenReturn(bonus);
    Mockito.when(list.get(2)).thenReturn(bonus);
    Mockito.when(list.get(4)).thenReturn(bonus);
    assertEquals(false, diplomacy.isMultipleBorderCrossing(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInWar() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals("", diplomacy.getDiplomaticRelation(0));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.IN_WAR,
        SpaceRace.SPORKS);
    assertEquals("War", diplomacy.getDiplomaticRelation(0));
    assertEquals(true, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(-30, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.HATE, diplomacy.getLiking(0));
    assertEquals("Hate", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DESTROYED, diplomacy.getLikingAsColor(0));
    assertEquals(false, diplomacy.isWar(256));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.LONG_PEACE,
        SpaceRace.SPORKS);
    assertEquals(Diplomacy.NEUTRAL, diplomacy.getLiking(0));
    assertEquals("Peace", diplomacy.getDiplomaticRelation(0));
    assertEquals("Neutral", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DAMAGE_HALF, diplomacy.getLikingAsColor(0));
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.WAR_DECLARTION,
        SpaceRace.SPORKS);
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.DIPLOMAT_CAPTURED,
        SpaceRace.SPORKS);
    diplomacy.getDiplomacyList(0).addBonus(DiplomacyBonusType.DIPLOMAT_CAPTURED,
        SpaceRace.SPORKS);
    assertEquals(Diplomacy.DISLIKE, diplomacy.getLiking(0));
    assertEquals("Dislike", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DAMAGE_MUCH, diplomacy.getLikingAsColor(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInTradeAlliance() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertEquals(Diplomacy.NEUTRAL, diplomacy.getLiking(0));
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(0));
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_TRADE_ALLIANCE, SpaceRace.HUMAN);
    assertEquals("Trade alliance", diplomacy.getDiplomaticRelation(0));
    assertEquals(true, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(12, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.LIKE, diplomacy.getLiking(0));
    assertEquals("Like", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_DAMAGE_LITTLE, diplomacy.getLikingAsColor(0));
    assertEquals(false, diplomacy.isTradeAlliance(256));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInAlliance() {
    Diplomacy diplomacy = new Diplomacy(4, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    diplomacy.getDiplomacyList(0).addBonus(
        DiplomacyBonusType.IN_ALLIANCE, SpaceRace.CENTAURS);
    assertEquals("Alliance", diplomacy.getDiplomaticRelation(0));
    assertEquals(25, diplomacy.getDiplomacyList(0).getDiplomacyBonus());
    assertEquals(Diplomacy.FRIENDS, diplomacy.getLiking(0));
    assertEquals("Friends", diplomacy.getLikingAsString(0));
    assertEquals(GuiStatics.COLOR_GREEN_TEXT, diplomacy.getLikingAsColor(0));
    assertEquals(true, diplomacy.isAlliance(0));
    assertEquals(false, diplomacy.isTradeAlliance(0));
    assertEquals(false, diplomacy.isWar(0));
    assertEquals(false, diplomacy.isAlliance(256));
  }

}
