package com.noinc.bloomsjeopardy.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.noinc.bloomsjeopardy.model.GameState;
import com.noinc.bloomsjeopardy.view.GUIGameScreen;

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
                }
                case "StartScreen exitButton" -> {
                    System.out.println("Exit button clicked");
                    gameEngine.terminateGame();
                }
                case "StartScreen howToPlayButton" -> {
                    System.out.println("How to Play button clicked");
                    gameEngine.showHowToPlay();
                }
                case "StartScreen settingsButton" -> {
                    System.out.println("Settings button clicked");
                    gameEngine.showSettings();
                }
                case "StartScreen aboutUsButton" -> {
                    System.out.println("About Us button clicked");
                    gameEngine.showAboutUs();
                }
                case "AboutUs backButton" -> {
                    System.out.println("About Us back button clicked");
                    gameEngine.getMainGUI().showStartScreen();
                }
                case "HowToPlay backButton" -> {
                    System.out.println("How to Play back button clicked");
                    gameEngine.getMainGUI().showStartScreen();
                }
                case "Settings backButton" -> {
                    System.out.println("Settings back button clicked");
                    if (gameEngine.getGameState().getCurrentState() == GameState.State.PYRAMID_SCREEN || 
                        gameEngine.getGameState().getCurrentState() == GameState.State.QUESTION_SCREEN) {
                        gameEngine.getMainGUI().showGameScreen();
                        ((GUIGameScreen) gameEngine.getMainGUI().getGameScreen()).showMenuDialog();
                    } else {
                        gameEngine.getMainGUI().showStartScreen();
                    }
                }
                case "ModuleScreen moduleBackButton" -> {
                    System.out.println("Back button clicked");
                    gameEngine.getMainGUI().showStartScreen();
                }
                case "GameScreen menuButton" -> {
                    System.out.println("Menu button clicked");
                    gameEngine.showMenuPopUp();
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
                case "GameScreen menuResumeButton" -> {
                    System.out.println("Resume button clicked");
                    gameEngine.resumeGame();
                }
                case "GameScreen menuSettingsButton" -> {
                    System.out.println("Settings button clicked");
                    gameEngine.showSettings();
                }
                case "GameScreen menuRestartButton" -> {
                    System.out.println("Restart button clicked");
                    gameEngine.restartGame();
                }
                case "GameScreen menuExitButton" -> {
                    System.out.println("Exit button clicked");
                    gameEngine.terminateGame();
                }
                case "GameScreen pyramidButton" -> {
                    int row = (int) btn.getClientProperty("row");
                    int col = (int) btn.getClientProperty("col");
                    System.out.println("Pyramid button clicked: " + btn.getText() + " at [" + row + "][" + col + "]");
                    
                    // check if this question has already been answered
                    if (gameEngine.getGameData().isQuestionAnswered(row, col)) {
                        System.out.println("Question already answered at [" + row + "][" + col + "] - ignoring click");
                        return; // don't proceed if already answered
                    }
                    
                    gameEngine.selectQuestion(row, col);
                    //btn.removeActionListener(this);
                    gameEngine.disableButtonListener(btn);
                    // gameEngine.unlockNextLevel(); // temporary for testing purposes
                }
                default -> {
                    System.out.println("Player Action Listener reached default");
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
            gameEngine.showAnswerConfirmationDialog(answerIndex);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}

    @Override public void mouseReleased(MouseEvent e) {}

    @Override public void mouseEntered(MouseEvent e) {}

    @Override public void mouseExited(MouseEvent e) {}
}