package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Image instruction class.
*
*/
public class ImageInstructionTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackground() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    assertEquals("background(grey gradient)", instruction.toString());
    assertEquals("background(grey gradient)", instruction.build());
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    assertEquals("background(grey gradient)+background(black)", instruction.build());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBackgroundBlackDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_BLACK);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(5, 5));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBackgroundStarsDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(-16579836, image.getRGB(5, 5));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBackgroundNebulaeDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(-16377056, image.getRGB(5, 5));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBackgroundGradientDraw() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image, instruction.build());
    assertEquals(100, image.getWidth());
    assertEquals(100, image.getHeight());
    assertEquals(Color.BLACK.getRGB(), image.getRGB(49, 0));
    assertEquals(-14211289, image.getRGB(49, 99));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndText() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addText("Test called background and(+) Text");
    assertEquals("background(stars)+text(Test called background and Text)", instruction.build());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBackgroundAndPlanet() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground(ImageInstruction.BACKGROUND_STARS);
    instruction.addPlanet(ImageInstruction.POSITION_CENTER, ImageInstruction.PLANET_IRONWORLD1);
    assertEquals("background(stars)+planet(position center,ironworld1)", instruction.build());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSanitize() {
    ImageInstruction instruction = new ImageInstruction();
    instruction.addBackground("grad+ient");
    assertEquals("background(gradient)", instruction.toString());
    instruction = new ImageInstruction();
    instruction.addBackground("grad(ient");
    assertEquals("background(gradient)", instruction.toString());
    instruction = new ImageInstruction();
    instruction.addBackground("grad)ient");
    assertEquals("background(gradient)", instruction.toString());
    instruction = new ImageInstruction();
    instruction.addBackground("grad,ient");
  }

}
