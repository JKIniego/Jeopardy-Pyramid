package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.noinc.bloomsjeopardy.data.GameData;

public class GUIEndScreen extends JPanel implements ActionListener, MouseListener {
    private JButton restartButton, exitButton;
    private JPanel parentPanel;
    private GUIBrand brand;
    private GameData gameData;

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
        
        JLabel titleLabel = new JLabel("GAME OVER");
        restartButton = new JButton("Restart");
        exitButton = new JButton("Exit");

        brand.buttonHighlight(restartButton);
        brand.buttonTransparent(exitButton);

        restartButton.addMouseListener(this);
        exitButton.addMouseListener(this);

        restartButton.addActionListener(this);
        exitButton.addActionListener(this);

        endScreenPanel.setLayout(new GridBagLayout());
        GridBagConstraints endScreenGBC = new GridBagConstraints();
        endScreenGBC.gridx = 0;
        endScreenGBC.gridy = 0;
        endScreenGBC.ipady = 100;
        endScreenGBC.insets = new Insets(0, 0, 30, 0);
        endScreenPanel.add(titleLabel, endScreenGBC);
        endScreenGBC.gridx = 0;
        endScreenGBC.gridy = 1;
        endScreenGBC.ipadx = 150;
        endScreenGBC.ipady = 30;
        endScreenPanel.add(restartButton, endScreenGBC);
        endScreenGBC.gridx = 0;
        endScreenGBC.gridy = 2;
        endScreenGBC.ipadx = 150;
        endScreenGBC.ipady = 30;
        endScreenPanel.add(exitButton, endScreenGBC);

        this.add(endScreenPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == restartButton) {
            CardLayout cl = (CardLayout) parentPanel.getLayout();
            cl.show(parentPanel, "GameScreen");
        } else if (src == exitButton) {
            System.exit(0);
        }
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