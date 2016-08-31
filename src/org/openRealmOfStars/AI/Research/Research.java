package org.openRealmOfStars.AI.Research;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechType;

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
 * AI handling for research
 * 
 */

public class Research {

  /**
   * Handle research for AI player
   * @param info PlayerInfo
   */
  public static void handle(PlayerInfo info) {
    Tech[] techs = info.getTechList().getList();
    boolean basicLab = false;
    for (int i=0;i<techs.length;i++) {
      if (techs[i].getName().equals("Basic lab")) {
        basicLab = true;
      }
    }
    if (!basicLab) {
      info.getTechList().setTechFocus(TechType.Improvements,40);
    } else {
      switch (info.getRace()) {
      case HUMAN: {
        info.getTechList().setTechFocus(TechType.Combat,20);
        info.getTechList().setTechFocus(TechType.Defense,16);
        info.getTechList().setTechFocus(TechType.Hulls,16);
        info.getTechList().setTechFocus(TechType.Propulsion,16);
        info.getTechList().setTechFocus(TechType.Improvements,16);
        info.getTechList().setTechFocus(TechType.Electrics,16);
        break;
      }
      case CENTAURS: {
        info.getTechList().setTechFocus(TechType.Combat,16);
        info.getTechList().setTechFocus(TechType.Defense,16);
        info.getTechList().setTechFocus(TechType.Hulls,20);
        info.getTechList().setTechFocus(TechType.Propulsion,16);
        info.getTechList().setTechFocus(TechType.Improvements,16);
        info.getTechList().setTechFocus(TechType.Electrics,16);
        break;
      }
      case GREYANS: {
        info.getTechList().setTechFocus(TechType.Combat,16);
        info.getTechList().setTechFocus(TechType.Defense,16);
        info.getTechList().setTechFocus(TechType.Hulls,16);
        info.getTechList().setTechFocus(TechType.Propulsion,16);
        info.getTechList().setTechFocus(TechType.Improvements,20);
        info.getTechList().setTechFocus(TechType.Electrics,16);
        break;
      }
      case SPORKS: {
        info.getTechList().setTechFocus(TechType.Electrics,12);
        info.getTechList().setTechFocus(TechType.Propulsion,12);
        info.getTechList().setTechFocus(TechType.Combat,20);
        info.getTechList().setTechFocus(TechType.Defense,20);
        info.getTechList().setTechFocus(TechType.Hulls,20);
        info.getTechList().setTechFocus(TechType.Improvements,16);
        break;
      }
      case MECHIONS: {
        info.getTechList().setTechFocus(TechType.Electrics,12);
        info.getTechList().setTechFocus(TechType.Combat,20);
        info.getTechList().setTechFocus(TechType.Propulsion,20);
        info.getTechList().setTechFocus(TechType.Defense,16);
        info.getTechList().setTechFocus(TechType.Hulls,16);
        info.getTechList().setTechFocus(TechType.Improvements,16);
        break;
      }
      }
    }
  }
}