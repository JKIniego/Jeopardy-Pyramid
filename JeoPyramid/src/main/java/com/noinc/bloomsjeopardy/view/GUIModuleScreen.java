package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.noinc.bloomsjeopardy.data.GameData;

public class GUIModuleScreen extends JPanel implements MouseListener{
    private JButton moduleBackButton;
    private JButton[] moduleButtons;
    private JPanel parentPanel;
    private GUIBrand brand;
    private GameData gameData;

    public GUIModuleScreen(GameData gameData, JPanel parentPanel, GUIBrand brand) {
        this.parentPanel = parentPanel;
        this.brand = brand;
        this.gameData = gameData;
        this.setLayout(new BorderLayout());
        initializeStartScreen();
    }

    private void initializeStartScreen(){
        JPanel moduleScreenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(brand.backgroundIMG, 0, 0, getWidth(), getHeight(), this);
            }
        };

        moduleScreenPanel.setLayout(new GridBagLayout());

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.setPreferredSize(new Dimension(400, 600));
        containerPanel.setBackground(brand.black);
        containerPanel.setBorder(BorderFactory.createLineBorder(brand.blue, 8, true));

        moduleBackButton = new JButton("< Back");
        moduleBackButton.setContentAreaFilled(false);
        moduleBackButton.setForeground(brand.white);
        moduleBackButton.setFont(brand.CustomFontSmall);
        moduleBackButton.setBorderPainted(false);
        moduleBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        moduleBackButton.setActionCommand("ModuleScreen moduleBackButton");
        JPanel moduleBackPanel = new JPanel();
        moduleBackPanel.setLayout(new BorderLayout());
        moduleBackPanel.setOpaque(false);
        moduleBackPanel.add(moduleBackButton, BorderLayout.WEST);

        JLabel moduleContainerLabel = new JLabel("Select Module");
        moduleContainerLabel.setFont(brand.CustomFontSmall);
        moduleContainerLabel.setForeground(brand.white);

        JPanel moduleButtonsPanel = new JPanel();
        moduleButtonsPanel.setOpaque(false);
        moduleButtonsPanel.setLayout(new GridBagLayout());

        GridBagConstraints moduleButtonsPanelGBC = new GridBagConstraints();
        moduleButtonsPanelGBC.gridy = 0;
        moduleButtonsPanelGBC.ipady = 40;
        moduleButtonsPanel.add(moduleContainerLabel, moduleButtonsPanelGBC);
        moduleButtonsPanelGBC.ipady = 0;
        
        GridBagConstraints containerPanelGBC = new GridBagConstraints();
        containerPanelGBC.fill = GridBagConstraints.BOTH;
        containerPanelGBC.weightx = 1;
        containerPanelGBC.weighty = 0.01;
        containerPanelGBC.gridy = 0;
        containerPanelGBC.ipady = 10;
        containerPanel.add(moduleBackPanel, containerPanelGBC);
        containerPanelGBC.weighty = 0.8;
        containerPanelGBC.gridy = 1;
        containerPanelGBC.ipady = 0;
        containerPanel.add(moduleButtonsPanel, containerPanelGBC);

        moduleButtons = new JButton[gameData.getModules().length];

        for(int i = 0; i<gameData.getModules().length; i++){
            JButton button = new JButton();
            button.setText(gameData.getModules()[i]);
            button.setForeground(brand.white);
            button.setBackground(brand.black);
            button.setFont(brand.CustomFontSmall);
            button.setActionCommand("ModuleScreen moduleButton");
            button.putClientProperty("module", i+1); // change to 1-base
            button.addMouseListener(this);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            moduleButtonsPanelGBC.insets = new Insets(0, 0, 10, 0);
            moduleButtonsPanelGBC.gridy++;
            moduleButtonsPanel.add(button, moduleButtonsPanelGBC);
            moduleButtons[i] = button;
        }

        moduleScreenPanel.add(containerPanel);
        
        

        this.add(moduleScreenPanel, BorderLayout.CENTER);
    }

    public JButton[] getModuleButtons(){
        return moduleButtons;
    }
    public JButton getModuleBackButton(){
        return moduleBackButton;
    }


    // Hover Cosmetics
    @Override
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();
        if (src instanceof JButton) {
            JButton btn = (JButton) src;
            btn.setBackground(brand.blue);
        }
    }
    @Override 
    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();
        if (src instanceof JButton) {
            JButton btn = (JButton) src;
            btn.setBackground(brand.black);
        }
    }
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
   
}
