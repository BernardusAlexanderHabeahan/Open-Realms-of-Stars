package org.openRealmOfStars.game.States;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;

import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.buttons.IconButton;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.gui.infopanel.InfoPanel;
import org.openRealmOfStars.gui.labels.InfoTextArea;
import org.openRealmOfStars.gui.labels.TransparentLabel;
import org.openRealmOfStars.gui.panels.BlackPanel;
import org.openRealmOfStars.gui.panels.ImagePanel;
import org.openRealmOfStars.gui.panels.InvisiblePanel;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.newsCorp.NewsData;

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
* News corp view to show highlights from previous turn
*
*/
public class NewsCorpView extends BlackPanel {

  /**
  *
  */
  private static final long serialVersionUID = 1L;

  /**
   * News image
   */
  private ImagePanel newsImage;

  /**
   * Image of NewsReader
   */
  private BufferedImage newsReader;
  /**
   * News text
   */
  private InfoTextArea textArea;

  /**
   * Animation framing
   */
  private int animation;

  /**
   * News reader image panel
   */
  private ImagePanel newsReaderPanel;

  /**
   * List of news
   */
  private NewsData[] newsList;
  /**
   * Current news index
   */
  private int newsIndex = 0;

  /**
   * News list label
   */
  private TransparentLabel newsLabel;
  /**
   * Construtor for News Corp View.
   * @param news News Data
   * @param listener ActionListener
   */
  public NewsCorpView(final NewsData[] news, final ActionListener listener) {
    this.setLayout(new BorderLayout());
    newsList = news;
    InfoPanel base = new InfoPanel();
    base.setLayout(new BorderLayout());
    base.setTitle("Galactic Broadcasting News Company");
    newsReaderPanel = new ImagePanel(GuiStatics.IMAGE_HUMAN_RACE);
    newsReader = new BufferedImage(196, 700,
        BufferedImage.TYPE_4BYTE_ABGR);
    animation = 0;
    newsReaderPanel.setImage(newsReader);
    base.add(newsReaderPanel, BorderLayout.WEST);
    InfoPanel newsPanel = new InfoPanel();
    newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
    newsPanel.setTitle("News headline");
    BufferedImage image = new BufferedImage(800, 400,
        BufferedImage.TYPE_4BYTE_ABGR);
    image = ImageInstruction.parseImageInstructions(image,
        news[0].getImageInstructions());
    newsImage = new ImagePanel(image);
    newsPanel.add(newsImage, BorderLayout.WEST);
    newsPanel.add(Box.createRigidArea(new Dimension(15, 10)));
    textArea = new InfoTextArea();
    textArea.setCharacterWidth(9);
    textArea.setText(news[0].getNewsText());
    textArea.setLineWrap(true);
    textArea.setEditable(false);
    newsPanel.add(textArea);
    this.add(base, BorderLayout.CENTER);
    InvisiblePanel invis = new InvisiblePanel(newsPanel);
    invis.setLayout(new BoxLayout(invis, BoxLayout.X_AXIS));
    IconButton iBtn = new IconButton(GuiStatics.LEFT_ARROW,
        GuiStatics.LEFT_ARROW_PRESSED, false, GameCommands.COMMAND_PREV_TARGET,
        this);
    iBtn.addActionListener(listener);
    invis.add(iBtn);
    invis.add(Box.createRigidArea(new Dimension(10, 5)));
    newsLabel = new TransparentLabel(invis, "100/100");
    newsLabel.setText("1/" + newsList.length);
    invis.add(newsLabel);
    iBtn = new IconButton(GuiStatics.RIGHT_ARROW,
        GuiStatics.RIGHT_ARROW_PRESSED, false, GameCommands.COMMAND_NEXT_TARGET,
        this);
    iBtn.addActionListener(listener);
    invis.add(iBtn);
    newsPanel.add(Box.createRigidArea(new Dimension(5, 5)));
    newsPanel.add(invis);
    base.add(newsPanel, BorderLayout.CENTER);
    // Bottom panel
    InfoPanel bottomPanel = new InfoPanel();
    bottomPanel.setLayout(new BorderLayout());
    bottomPanel.setTitle(null);
    SpaceButton btn = new SpaceButton("Back to star map",
        GameCommands.COMMAND_VIEW_STARMAP);
    btn.addActionListener(listener);
    bottomPanel.add(btn, BorderLayout.CENTER);
    // Add panels to base
    this.add(bottomPanel, BorderLayout.SOUTH);
  }

  /**
   * Handle events for NewsCorpView.
   * @param arg0 ActionEvent
   */
  public void handleAction(final ActionEvent arg0) {
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_ANIMATION_TIMER)
        && animation < 180) {
      Graphics2D g = (Graphics2D) newsReader.getGraphics();
      g.drawImage(GuiStatics.STAR_FIELD_IMAGE, -10 - animation,
          -200 + animation, null);
      if (animation < 64) {
        g.drawImage(GuiStatics.IMAGE_GBNC,
            newsReader.getWidth() / 2 - GuiStatics.IMAGE_GBNC.getWidth() / 2,
            -16 + animation * 2, null);
        animation++;
      } else {
        g.drawImage(GuiStatics.IMAGE_GBNC,
            newsReader.getWidth() / 2 - GuiStatics.IMAGE_GBNC.getWidth() / 2,
            -16 + 128, null);
        if (animation < 162) {
          g.drawImage(GuiStatics.IMAGE_ANDROID,
              newsReader.getWidth() / 2
              - GuiStatics.IMAGE_ANDROID.getWidth() / 2,
              newsReader.getHeight() - (animation - 64) * 2, null);
          animation++;
        } else {
          g.drawImage(GuiStatics.IMAGE_ANDROID,
              newsReader.getWidth() / 2
              - GuiStatics.IMAGE_ANDROID.getWidth() / 2,
              newsReader.getHeight() - (162 - 64) * 2, null);
          animation++;
        }

      }
      newsReaderPanel.repaint();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_NEXT_TARGET)
        && newsIndex < newsList.length - 1) {
      newsIndex++;
      BufferedImage image = new BufferedImage(800, 400,
          BufferedImage.TYPE_4BYTE_ABGR);
      image = ImageInstruction.parseImageInstructions(image,
          newsList[newsIndex].getImageInstructions());
      newsImage.setImage(image);
      textArea.setText(newsList[newsIndex].getNewsText());
      newsLabel.setText(newsIndex + 1 + "/" + newsList.length);
      repaint();
    }
    if (arg0.getActionCommand().equals(GameCommands.COMMAND_PREV_TARGET)
        && newsIndex > 0) {
      newsIndex--;
      BufferedImage image = new BufferedImage(800, 400,
          BufferedImage.TYPE_4BYTE_ABGR);
      image = ImageInstruction.parseImageInstructions(image,
          newsList[newsIndex].getImageInstructions());
      newsImage.setImage(image);
      textArea.setText(newsList[newsIndex].getNewsText());
      newsLabel.setText(newsIndex + 1 + "/" + newsList.length);
      repaint();
    }
  }

  /**
   * Get current news index
   * @return current news index
   */
  public int getNewsIndex() {
    return newsIndex;
  }

}
