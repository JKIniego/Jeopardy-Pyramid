package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.noinc.bloomsjeopardy.data.GameData;

public class GUIStartScreen extends JPanel implements MouseListener{
    private JButton playButton, exitButton, aboutUsButton, howToPlayButton, settingsButton;
    private JPanel parentPanel;
    private GUIBrand brand;
    private GameData gameData;

    public GUIStartScreen(GameData gameData, JPanel parentPanel, GUIBrand brand) {
        this.parentPanel = parentPanel;
        this.brand = brand;
        this.gameData = gameData;
        this.setLayout(new BorderLayout());
        initializeStartScreen();
    }

    private void initializeStartScreen(){
        JPanel startScreenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(brand.backgroundIMG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JLabel titleLabel = new JLabel(brand.titleGIF);
        playButton = new JButton("Play");
        playButton.setActionCommand("StartScreen playButton");
        exitButton = new JButton("Exit");
        exitButton.setActionCommand("StartScreen exitButton");
        aboutUsButton = new JButton("About Us");
        aboutUsButton.setActionCommand("StartScreen aboutUsButton");
        howToPlayButton = new JButton("How to Play");
        howToPlayButton.setActionCommand("StartScreen howToPlayButton");
        settingsButton = new JButton("Settings");
        settingsButton.setActionCommand("StartScreen settingsButton");

        brand.buttonHighlight(playButton);
        brand.buttonTransparent(exitButton);
        brand.buttonTransparent(aboutUsButton);
        brand.buttonTransparent(howToPlayButton);
        brand.buttonTransparent(settingsButton);

        playButton.addMouseListener(this);
        exitButton.addMouseListener(this);
        aboutUsButton.addMouseListener(this);
        howToPlayButton.addMouseListener(this);
        settingsButton.addMouseListener(this);

        startScreenPanel.setLayout(new GridBagLayout());
        GridBagConstraints startScreenGBC = new GridBagConstraints();
        
        // Add title in the center
        startScreenGBC.gridx = 1;
        startScreenGBC.gridy = 0;
        startScreenGBC.anchor = GridBagConstraints.CENTER;
        startScreenGBC.ipadx = 0;
        startScreenGBC.ipady = 60;
        startScreenGBC.insets = new Insets(0, 0, 5, 0);
        startScreenPanel.add(titleLabel, startScreenGBC);
        
        // Add play button
        startScreenGBC.gridx = 1;
        startScreenGBC.gridy = 1;
        startScreenGBC.ipadx = 240;
        startScreenGBC.ipady = 20;
        startScreenGBC.insets = new Insets(0, 0, 8, 0);
        startScreenPanel.add(playButton, startScreenGBC);
        
        // Add settings button below play button
        startScreenGBC.gridx = 1;
        startScreenGBC.gridy = 2;
        startScreenGBC.anchor = GridBagConstraints.CENTER;
        startScreenGBC.ipadx = 130;
        startScreenGBC.ipady = 20;
        startScreenGBC.insets = new Insets(0, 0, 8, 0);
        startScreenPanel.add(settingsButton, startScreenGBC);
        
        // Add profile button between Settings and Exit
        startScreenGBC.gridx = 1;
        startScreenGBC.gridy = 3;
        startScreenGBC.anchor = GridBagConstraints.CENTER;
        startScreenGBC.ipadx = 130;
        startScreenGBC.ipady = 20;
        startScreenGBC.insets = new Insets(0, 0, 8, 0);
        startScreenPanel.add(aboutUsButton, startScreenGBC);
        
        // Add how to play button between About Us and Exit
        startScreenGBC.gridx = 1;
        startScreenGBC.gridy = 4;
        startScreenGBC.anchor = GridBagConstraints.CENTER;
        startScreenGBC.ipadx = 50;
        startScreenGBC.ipady = 20;
        startScreenGBC.insets = new Insets(0, 0, 8, 0);
        startScreenPanel.add(howToPlayButton, startScreenGBC);
        
        // Add exit button
        startScreenGBC.gridx = 1;
        startScreenGBC.gridy = 5;
        startScreenGBC.ipadx = 240;
        startScreenGBC.ipady = 20;
        startScreenGBC.insets = new Insets(0, 0, 0, 0);
        startScreenPanel.add(exitButton, startScreenGBC);

        this.add(startScreenPanel, BorderLayout.CENTER);
    }

    public JButton getPlayButton(){
        return playButton;
    }
    public JButton getExitButton(){
        return exitButton;
    }
    public JButton getAboutUsButton(){
        return aboutUsButton;
    }
    public JButton getHowToPlayButton(){
        return howToPlayButton;
    }
    public JButton getSettingsButton(){
        return settingsButton;
    }

    // Hover Cosmetics
    @Override
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();
        if (src == exitButton) {
            System.out.println("[GUIStartScreen] hover: exitButton");
            brand.buttonHighlight(exitButton);
            brand.buttonTransparent(playButton);
            brand.buttonTransparent(aboutUsButton);
            brand.buttonTransparent(howToPlayButton);
            brand.buttonTransparent(settingsButton);
            com.noinc.bloomsjeopardy.utils.SoundManager.getInstance().playExitHover();
        } else if (src == playButton) {
            System.out.println("[GUIStartScreen] hover: playButton");
            brand.buttonHighlight(playButton);
            brand.buttonTransparent(exitButton);
            brand.buttonTransparent(aboutUsButton);
            brand.buttonTransparent(howToPlayButton);
            brand.buttonTransparent(settingsButton);
        } else if (src == aboutUsButton) {
            System.out.println("[GUIStartScreen] hover: aboutUsButton");
            brand.buttonHighlight(aboutUsButton);
            brand.buttonTransparent(playButton);
            brand.buttonTransparent(exitButton);
            brand.buttonTransparent(howToPlayButton);
            brand.buttonTransparent(settingsButton);
        } else if (src == howToPlayButton) {
            System.out.println("[GUIStartScreen] hover: howToPlayButton");
            brand.buttonHighlight(howToPlayButton);
            brand.buttonTransparent(playButton);
            brand.buttonTransparent(exitButton);
            brand.buttonTransparent(aboutUsButton);
            brand.buttonTransparent(settingsButton);
        } else if (src == settingsButton) {
            System.out.println("[GUIStartScreen] hover: settingsButton");
            brand.buttonHighlight(settingsButton);
            brand.buttonTransparent(playButton);
            brand.buttonTransparent(exitButton);
            brand.buttonTransparent(aboutUsButton);
            brand.buttonTransparent(howToPlayButton);
        }
    }

    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
   
}
