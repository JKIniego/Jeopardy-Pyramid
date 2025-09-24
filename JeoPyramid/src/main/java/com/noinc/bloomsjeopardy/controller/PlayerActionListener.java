package com.noinc.bloomsjeopardy.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayerActionListener implements ActionListener, MouseListener{
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
                    gameEngine.chooseModule();
                    //gameEngine.startMainGame();
                }
                case "StartScreen exitButton" -> {
                    System.out.println("Exit button clicked");
                    gameEngine.terminateGame();
                }
                case "ModuleScreen moduleBackButton" -> {
                    System.out.println("Back button clicked");
                    gameEngine.getMainGUI().showStartScreen();
                }
                case "GameScreen menuButton" -> {
                    System.out.println("Menu button clicked");
                    gameEngine.decrementHealth(); // Temporary
                }
                case "GameScreen screen2BackButton" -> {
                    System.out.println("Back button clicked");
                    gameEngine.showPyramidScreen();
                }
                case "EndScreen restartButton" -> {
                    System.out.println("Restart button clicked");
                    gameEngine.restartGame();
                }
                case "EndScreen exitButton" -> {
                    System.out.println("Exit button clicked");
                    gameEngine.terminateGame();
                }
                case "ModuleScreen moduleButton" -> {
                    System.out.println("Module button clicked");
                    int moduleChosen = (int) btn.getClientProperty("module");
                    gameEngine.getGameData().setModuleSelected(moduleChosen);
                    gameEngine.startMainGame();
                }
                case "GameScreen pyramidButton" -> {
                    int row = (int) btn.getClientProperty("row");
                    int col = (int) btn.getClientProperty("col");
                    System.out.println("Pyramid button clicked: " + btn.getText() + " at [" + row + "][" + col + "]");
                    
                    gameEngine.selectQuestion(row, col);
                    gameEngine.unlockNextLevel(); // temporary for testing purposes
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

    @Override
    public void mouseClicked(MouseEvent e) {
         Object src = e.getSource();

        if (src instanceof JPanel) {
            JPanel panel = (JPanel) src;
            System.out.println("Panel clicked: " + panel.getName());
            int answerIndex = (int) panel.getClientProperty("index");
            gameEngine.submitAnswer(answerIndex);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}

    @Override public void mouseReleased(MouseEvent e) {}

    @Override public void mouseEntered(MouseEvent e) {}

    @Override public void mouseExited(MouseEvent e) {}
}