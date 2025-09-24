package com.noinc.bloomsjeopardy.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PlayerActionListener implements ActionListener{
    private GameEngine gameEngine;

    public PlayerActionListener(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        String cmd = e.getActionCommand();

        if (src instanceof JButton btn) {
            switch (cmd) {
                case "StartScreen playButton" -> gameEngine.startMainGame();
                case "StartScreen exitButton" -> gameEngine.terminateGame();
                case "GameScreen menuButton" -> gameEngine.decrementHealth(); //Temporary Call
                case "GameScreen screen2BackButton" -> gameEngine.showPyramidScreen();
                case "GameScreen pyramidButton" -> {
                    int row = (int) btn.getClientProperty("row");
                    int col = (int) btn.getClientProperty("col");
                    System.out.println("Clicked brick: " + btn.getText() + " at [" + row + "][" + col + "]");
                    
                    gameEngine.unlockNextLevel();
                    gameEngine.incrementHealth();
                    gameEngine.showPyramidScreen();
                    gameEngine.showQNAScreen();
                    gameEngine.updatePlayerSore(row);
                }
            }
        }
    }
}
