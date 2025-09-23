package com.noinc.bloomsjeopardy.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayerActionListener implements ActionListener{
    private GameEngine gameEngine;

    public PlayerActionListener(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        String cmd = e.getActionCommand();

        System.out.println("Action performed: " + cmd + " from " + src.getClass().getSimpleName());

        if (src instanceof JButton btn) {
            switch (cmd) {
                case "StartScreen playButton" -> {
                    System.out.println("Play button clicked");
                    gameEngine.startMainGame();
                }
                case "StartScreen exitButton" -> {
                    System.out.println("Exit button clicked");
                    gameEngine.terminateGame();
                }
                case "GameScreen menuButton" -> {
                    System.out.println("Menu button clicked");
                    gameEngine.decrementHealth(); // Temporary
                }
                case "GameScreen screen2BackButton" -> {
                    System.out.println("Back button clicked");
                    gameEngine.showPyramidScreen();
                }
                case "GameScreen pyramidButton" -> {
                    int row = (int) btn.getClientProperty("row");
                    int col = (int) btn.getClientProperty("col");
                    System.out.println("Pyramid button clicked: " + btn.getText() + " at [" + row + "][" + col + "]");
                    
                    gameEngine.selectQuestion(row, col);
                }
                default -> {
                    // Check if this is one of the choice panels
                    if (src instanceof JPanel) {
                        JPanel panel = (JPanel) src;
                        String panelName = panel.getName();
                        if (panelName != null && panelName.startsWith("choice")) {
                            int choiceIndex = Integer.parseInt(panelName.substring(6)) - 1; // choice1 -> 0, etc.
                            System.out.println("Choice panel clicked: " + panelName + " -> index " + choiceIndex);
                            gameEngine.submitAnswer(choiceIndex);
                            gameEngine.showPyramidScreen();
                        }
                    }
                }
            }
        }
    }
}