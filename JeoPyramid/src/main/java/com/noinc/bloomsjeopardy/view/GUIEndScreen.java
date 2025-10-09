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

public class GUIEndScreen extends JPanel implements MouseListener {
    private JButton restartButton, exitButton;
    private JPanel parentPanel;
    private JLabel finalScore;
    private GameData gameData;
    private GUIBrand brand;
    

    public GUIEndScreen(GameData gameData, JPanel parentPanel, GUIBrand brand) {
        this.parentPanel = parentPanel;
        this.brand = brand;
        this.gameData = gameData;
        this.setLayout(new BorderLayout());
        initializeEndScreen();
    }

    private void initializeEndScreen(){
        JPanel endScreenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(brand.backgroundIMG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        JLabel titleLabel = new JLabel(brand.gameOverGIF);

        finalScore = new JLabel("$" + gameData.getPlayerScore());
        finalScore.setForeground(brand.white);
        finalScore.setFont(brand.CustomFontFinalScore);

        restartButton = new JButton("Restart");
        exitButton = new JButton("Exit");
        
        restartButton.setActionCommand("EndScreen restartButton");
        exitButton.setActionCommand("EndScreen exitButton");

        brand.buttonHighlight(restartButton);
        brand.buttonTransparent(exitButton);

        restartButton.addMouseListener(this);
        exitButton.addMouseListener(this);

        endScreenPanel.setLayout(new GridBagLayout());
        GridBagConstraints endScreenGBC = new GridBagConstraints();
        endScreenGBC.gridx = 0;
        endScreenGBC.gridy = 0;
        endScreenGBC.ipady = 70;
        endScreenGBC.insets = new Insets(0, 0, 0, 0);
        endScreenPanel.add(titleLabel, endScreenGBC);
        endScreenGBC.gridx = 0;
        endScreenGBC.gridy = 1;
        endScreenGBC.ipadx = 00;
        endScreenGBC.ipady = 30;
        endScreenGBC.insets = new Insets(0, 0, 70, 0);
        endScreenPanel.add(finalScore, endScreenGBC);
        endScreenGBC.gridx = 0;
        endScreenGBC.gridy = 2;
        endScreenGBC.ipadx = 70;
        endScreenGBC.ipady = 30;
        endScreenGBC.insets = new Insets(0, 0, 30, 0);
        endScreenPanel.add(restartButton, endScreenGBC);
        endScreenGBC.gridx = 0;
        endScreenGBC.gridy = 3;
        endScreenGBC.ipadx = 150;
        endScreenGBC.ipady = 30;
        endScreenPanel.add(exitButton, endScreenGBC);

        this.add(endScreenPanel, BorderLayout.CENTER);
    }

    public void updateFinalScore(){
        finalScore.setText("$"+gameData.getPlayerScore());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();

        if (src == exitButton) {
            brand.buttonHighlight(exitButton);
            brand.buttonTransparent(restartButton);
        } else if (src == restartButton) {
            brand.buttonHighlight(restartButton);
            brand.buttonTransparent(exitButton);
        }
    }

    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}

    public JButton getRestartButton(){
        return restartButton;
    }

    public JButton getExitButton(){
        return exitButton;
    }

}