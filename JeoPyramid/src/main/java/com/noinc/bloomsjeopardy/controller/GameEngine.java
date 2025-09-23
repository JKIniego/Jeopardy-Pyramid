package com.noinc.bloomsjeopardy.controller;

import java.util.Arrays;

import javax.swing.JButton;

import com.noinc.bloomsjeopardy.data.GameData;
import com.noinc.bloomsjeopardy.view.GUIGameScreen;
import com.noinc.bloomsjeopardy.view.GUIStartScreen;
import com.noinc.bloomsjeopardy.view.MainGUI;

public class GameEngine {
    private MainGUI mainGUI;
    private GameData gameData;
    private PlayerActionListener playerActionListener;

    public GameEngine(){
        gameData = new GameData();
        mainGUI = new MainGUI(gameData);
        playerActionListener = new PlayerActionListener(this);
        addActionListeners();
    }


    // ====== Initializations Section ======
    public void addActionListeners(){
        ((GUIStartScreen) mainGUI.getStartScreen()).getPlayButton().addActionListener(playerActionListener);
        ((GUIStartScreen) mainGUI.getStartScreen()).getExitButton().addActionListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getMenuButton().addActionListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getScreen2BackButton().addActionListener(playerActionListener);
        updateButtonListeners();

    }

    public void updateButtonListeners() {
        // Method Exclusive For Pyramid Buttons (Unlock Items)
        GUIGameScreen gameScreen = (GUIGameScreen) mainGUI.getGameScreen();
        JButton[][] itemButtonsArray = gameScreen.getItemButtonsArray();
        int levelUnlocked = gameData.getPlayerUnlockedLevels();
        for (int row = 0; row < itemButtonsArray.length; row++) {
            for (int col = 0; col < itemButtonsArray[row].length; col++) {
                JButton btn = itemButtonsArray[row][col];
                if (row <= levelUnlocked) {
                    // Ensure listener is attached
                    if (!Arrays.asList(btn.getActionListeners()).contains(playerActionListener)) {
                        btn.addActionListener(playerActionListener);
                    }
                }
            }
        }
    }


    // ====== Game State Management ======
    public void unlockNextLevel(){
        int currentLevel = gameData.getPlayerUnlockedLevels();
        int maxLevel = gameData.getLevelScores().length-1;
        if(currentLevel < maxLevel){
            gameData.setPlayerUnlockedLevels(gameData.getPlayerUnlockedLevels() + 1);
            System.out.println("level unlockedss: " + gameData.getPlayerUnlockedLevels());
            updateButtonListeners();
            ((GUIGameScreen) mainGUI.getGameScreen()).updateLevels();
        }
    }

    public void incrementHealth(){
        int maxPlayerHealth = gameData.getMaxPlayerHealth();
        int currentHealth = gameData.getPlayerHealth();
        if(currentHealth<maxPlayerHealth){
            gameData.setPlayerHealth(currentHealth+1);
            ((GUIGameScreen) mainGUI.getGameScreen()).updateHUD();
        }
    }

    public void decrementHealth(){
        int currentHealth = gameData.getPlayerHealth();
        if(currentHealth>0){
            gameData.setPlayerHealth(currentHealth-1);
            ((GUIGameScreen) mainGUI.getGameScreen()).updateHUD();
            if (gameData.getPlayerHealth()==0){
                endMainGame();
            }
        }
    }

    public void updatePlayerSore(int row){
        int currentPlayerScore = gameData.getPlayerScore();
        int addedScore = gameData.getLevelScores()[row];
        gameData.setPlayerScore(currentPlayerScore + addedScore);
        ((GUIGameScreen) mainGUI.getGameScreen()).updateHUD();
    }

    // ====== Game Flow/Events ======
    public void startMainGame(){
        updateButtonListeners();
        mainGUI.showGameScreen();
    }

    public void endMainGame(){
        mainGUI.showEndScreen();
    }

    public void terminateGame(){
        System.exit(0);
    }

    public void restartGame(){
        gameData = new GameData();
        mainGUI = new MainGUI(gameData);
    }


    // ====== Inside Main Game Screen Transitions ======
    public void showPyramidScreen(){
        ((GUIGameScreen) mainGUI.getGameScreen()).showScreen1();
    }
    public void showQNAScreen(){
        ((GUIGameScreen) mainGUI.getGameScreen()).showScreen2();
    }


    // ====== Getters and Setters ======
    public MainGUI getMainGUI(){
        return mainGUI;
    }

    public GameData getGameData(){
        return gameData;
    }


}
