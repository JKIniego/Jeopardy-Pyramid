package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.noinc.bloomsjeopardy.data.GameData;

public class GUIStartScreen extends JPanel implements MouseListener{
    private JButton playButton, exitButton;
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
        
        JLabel titleLabel = new JLabel(new ImageIcon(brand.titleIMG));
        playButton = new JButton("Play");
        playButton.setActionCommand("StartScreen playButton");
        exitButton = new JButton("Exit");
        exitButton.setActionCommand("StartScreen exitButton");

        brand.buttonHighlight(playButton);
        brand.buttonTransparent(exitButton);

        playButton.addMouseListener(this);
        exitButton.addMouseListener(this);

        startScreenPanel.setLayout(new GridBagLayout());
        GridBagConstraints startScreenGBC = new GridBagConstraints();
        startScreenGBC.gridx = 0;
        startScreenGBC.gridy = 0;
        startScreenGBC.ipady = 100;
        startScreenGBC.insets = new Insets(0, 0, 30, 0);
        startScreenPanel.add(titleLabel, startScreenGBC);
        startScreenGBC.gridx = 0;
        startScreenGBC.gridy = 1;
        startScreenGBC.ipadx = 150;
        startScreenGBC.ipady = 30;
        startScreenPanel.add(playButton, startScreenGBC);
        startScreenGBC.gridx = 0;
        startScreenGBC.gridy = 2;
        startScreenGBC.ipadx = 150;
        startScreenGBC.ipady = 30;
        startScreenPanel.add(exitButton, startScreenGBC);

        this.add(startScreenPanel, BorderLayout.CENTER);
    }

    public JButton getPlayButton(){
        return playButton;
    }
    public JButton getExitButton(){
        return exitButton;
    }

    // Hover Cosmetics
    @Override
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();
        if (src == exitButton) {
            brand.buttonHighlight(exitButton);
            brand.buttonTransparent(playButton);
        } else if (src == playButton) {
            brand.buttonHighlight(playButton);
            brand.buttonTransparent(exitButton);
        }
    }

    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
   
}
