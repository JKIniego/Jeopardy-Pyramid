package com.noinc.bloomsjeopardy.controller;

import java.util.Arrays;

import javax.swing.JButton;

import com.noinc.bloomsjeopardy.data.GameData;
import com.noinc.bloomsjeopardy.model.GameState;
import com.noinc.bloomsjeopardy.model.Question;
import com.noinc.bloomsjeopardy.view.GUIEndScreen;
import com.noinc.bloomsjeopardy.view.GUIGameScreen;
import com.noinc.bloomsjeopardy.view.GUIModuleScreen;
import com.noinc.bloomsjeopardy.view.GUIStartScreen;
import com.noinc.bloomsjeopardy.view.MainGUI;

public class GameEngine {
    private MainGUI mainGUI;
    private GameData gameData;
    private GameState gameState;
    private PlayerActionListener playerActionListener;
    
    public GameEngine(){
        gameData = new GameData();
        gameState = new GameState();
        mainGUI = new MainGUI(gameData);
        playerActionListener = new PlayerActionListener(this);
        addActionListeners();
        
        // Debug: Print how many questions were loaded
        System.out.println("Total questions loaded: " + gameData.getQuestions().size());
        for (Question q : gameData.getQuestions()) {
            System.out.println("Category: " + q.getCategory() + " | Value: $" + q.getValue());
            System.out.println("Statement: " + q.getQuestionText());
            System.out.println("Correct Answer: " + q.getAnswer() + " (Index: " + q.getCorrectIndex() + ")");
            System.out.println("Choices: " + Arrays.toString(q.getChoices()));
            System.out.println("---");
        }
    }

    public void addActionListeners(){
        ((GUIStartScreen) mainGUI.getStartScreen()).getPlayButton().addActionListener(playerActionListener);
        ((GUIStartScreen) mainGUI.getStartScreen()).getExitButton().addActionListener(playerActionListener);
        ((GUIModuleScreen) mainGUI.getModuleScreen()).getModuleBackButton().addActionListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getMenuButton().addActionListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getScreen2BackButton().addActionListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getMenuResumeButton().addActionListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getMenuRestartButton().addActionListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getMenuExitButton().addActionListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getChoiceA().addMouseListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getChoiceB().addMouseListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getChoiceC().addMouseListener(playerActionListener);
        ((GUIGameScreen) mainGUI.getGameScreen()).getChoiceD().addMouseListener(playerActionListener);
        ((GUIEndScreen) mainGUI.getEndScreen()).getRestartButton().addActionListener(playerActionListener);
        ((GUIEndScreen) mainGUI.getEndScreen()).getExitButton().addActionListener(playerActionListener);
        
        JButton moduleButtons[] = ((GUIModuleScreen) mainGUI.getModuleScreen()).getModuleButtons();
        for(int i = 0; i < moduleButtons.length; i++){
            moduleButtons[i].addActionListener(playerActionListener);
        }
        updateButtonListeners();
    }

    public void updateButtonListeners() {
        GUIGameScreen gameScreen = (GUIGameScreen) mainGUI.getGameScreen();
        JButton[][] itemButtonsArray = gameScreen.getItemButtonsArray();
        int levelUnlocked = gameData.getPlayerUnlockedLevels();
        
        for (int row = 0; row < itemButtonsArray.length; row++) {
            for (int col = 0; col < itemButtonsArray[row].length; col++) {
                JButton btn = itemButtonsArray[row][col];
                for (java.awt.event.ActionListener al : btn.getActionListeners()) {
                    if (al instanceof PlayerActionListener) {
                        btn.removeActionListener(al);
                    }
                }
                if (row <= levelUnlocked) {
                    btn.addActionListener(playerActionListener);
                }
            }
        }
    }

    public void selectQuestion(int row, int col) {
        System.out.println("Selecting question at row: " + row + ", col: " + col);
        
        Question question = gameData.getQuestionForPosition(row, col);
        gameState.setCurrentQuestion(question);
        gameState.setSelectedRow(row);
        gameState.setSelectedCol(col);
        
        // Debug: Print the selected question
        if (question != null) {
            System.out.println("Selected Question - Category: " + question.getCategory());
            System.out.println("Statement: " + question.getQuestionText());
            System.out.println("Correct Answer: " + question.getAnswer() + " (Index: " + question.getCorrectIndex() + ")");
            System.out.println("Choices: " + Arrays.toString(question.getChoices()));
            System.out.println("Value: $" + question.getValue());
        } else {
            System.out.println("ERROR: No question found for position row=" + row + ", col=" + col);
        }
        
        updateQNAScreen(question);
        showQNAScreen();
    }
    
    private void updateQNAScreen(Question question) {
        if (question == null) {
            System.err.println("Question is null! Using default question.");
            question = new Question(
                "Default Answer",
                "No question available for this position",
                new String[]{"Choice A", "Choice B", "Choice C", "Choice D"},
                0,
                "General",
                100
            );
        }
        
        String[] qnaStrings = new String[6];
        qnaStrings[0] = "Category: " + question.getCategory() + " - $" + question.getValue();
        qnaStrings[1] = "Statement: " + question.getQuestionText();
        
        String[] choices = question.getChoices();
        if (choices.length < 4) {
            choices = Arrays.copyOf(choices, 4);
            for (int i = 0; i < 4; i++) {
                if (choices[i] == null || choices[i].isEmpty()) {
                    choices[i] = "Choice " + (char)('A' + i);
                }
            }
        }
        
        qnaStrings[2] = "A) " + choices[0];
        qnaStrings[3] = "B) " + choices[1];
        qnaStrings[4] = "C) " + choices[2];
        qnaStrings[5] = "D) " + choices[3];
        
        gameData.setQnaStrings(qnaStrings);
        ((GUIGameScreen) mainGUI.getGameScreen()).updateQNAScreen();
        
        // Debug: Print what we're setting
        System.out.println("Setting QNA strings:");
        for (int i = 0; i < qnaStrings.length; i++) {
            System.out.println(i + ": " + qnaStrings[i]);
        }
        
        System.out.println("Correct answer is at index: " + question.getCorrectIndex() +
                          " which is choice: " + (char)('A' + question.getCorrectIndex()));
    }
    
    public void submitAnswer(int choiceIndex) {
        if (gameState.getCurrentQuestion() != null && !gameState.isAnswerSubmitted()) {
            boolean correct = gameState.getCurrentQuestion().checkAnswer(choiceIndex);
            gameState.setAnswerSubmitted(true);
            gameState.setAnswerCorrect(correct);
            
            System.out.println("Answer submitted: " + (correct ? "CORRECT" : "WRONG"));
            System.out.println("Selected choice index: " + choiceIndex + " (" + (char)('A' + choiceIndex) + ")");
            System.out.println("Correct answer index: " + gameState.getCurrentQuestion().getCorrectIndex() +
                              " (" + (char)('A' + gameState.getCurrentQuestion().getCorrectIndex()) + ")");
            System.out.println("Correct answer text: " + gameState.getCurrentQuestion().getAnswer());
            
            ((GUIGameScreen) mainGUI.getGameScreen()).showAnswerFeedback(correct, choiceIndex, gameState.getCurrentQuestion().getCorrectIndex());
            
            if (correct) {
                handleCorrectAnswer();
            } else {
                handleIncorrectAnswer();
            }
        } else {
            System.err.println("Cannot submit answer: no current question or answer already submitted");
        }
    }
    
    private void handleCorrectAnswer() {
        updatePlayerScore(gameState.getSelectedRow());
        ((GUIGameScreen) mainGUI.getGameScreen()).updateItemStatus(gameState.getSelectedRow(), gameState.getSelectedCol(), true);
        gameState.getCurrentQuestion().setUsed(true);
        System.out.println("Correct! Score increased to: $" + gameData.getPlayerScore());
    }
    
    private void handleIncorrectAnswer() {
        decrementHealth();
        ((GUIGameScreen) mainGUI.getGameScreen()).updateItemStatus(gameState.getSelectedRow(), gameState.getSelectedCol(), false);
        System.out.println("Incorrect! Health decreased to: " + gameData.getPlayerHealth());
    }

    // Reset answer submission state
    private void resetAnswerSubmission() {
        gameState.setAnswerSubmitted(false);
        gameState.setAnswerCorrect(false);
        gameState.setCurrentQuestion(null);
    }

    public void unlockNextLevel(){
        int currentLevel = gameData.getPlayerUnlockedLevels();
        int maxLevel = gameData.getLevelScores().length - 1;
        if(currentLevel < maxLevel){
            gameData.setPlayerUnlockedLevels(currentLevel + 1);
            updateButtonListeners();
            ((GUIGameScreen) mainGUI.getGameScreen()).updateLevels();
            System.out.println("Level unlocked! Current level: " + gameData.getPlayerUnlockedLevels());
        }
    }

    public void incrementHealth(){
        int maxPlayerHealth = gameData.getMaxPlayerHealth();
        int currentHealth = gameData.getPlayerHealth();
        if(currentHealth < maxPlayerHealth){
            gameData.setPlayerHealth(currentHealth + 1);
            ((GUIGameScreen) mainGUI.getGameScreen()).updateHUD();
            System.out.println("Health increased to: " + gameData.getPlayerHealth());
        }
    }

    public void decrementHealth(){
        int currentHealth = gameData.getPlayerHealth();
        if(currentHealth > 0){
            gameData.setPlayerHealth(currentHealth - 1);
            ((GUIGameScreen) mainGUI.getGameScreen()).updateHUD();
            System.out.println("Health decreased to: " + gameData.getPlayerHealth());
            if (gameData.getPlayerHealth() == 0){
                endMainGame();
            }
        }
    }

    public void updatePlayerScore(int row){
        int currentPlayerScore = gameData.getPlayerScore();
        int addedScore = gameData.getLevelScores()[row];
        gameData.setPlayerScore(currentPlayerScore + addedScore);
        ((GUIGameScreen) mainGUI.getGameScreen()).updateHUD();
        System.out.println("Score updated: $" + currentPlayerScore + " + $" + addedScore + " = $" + gameData.getPlayerScore());
    }

    // Game Flow Methods
    public void chooseModule(){
        mainGUI.showModuleScreen();
    }

    public void showMenuPopUp(){
        ((GUIGameScreen) mainGUI.getGameScreen()).showMenuDialog();
    }

    public void showConfirmationDialog(int choice){
        if (((GUIGameScreen) mainGUI.getGameScreen()).showConfirmationDialog("Is that your final answer?")){
            submitAnswer(choice);
        }
    }

    public void startMainGame(){
        updateButtonListeners();
        gameState.setCurrentState(GameState.State.PYRAMID_SCREEN);
        mainGUI.showGameScreen();
        showPyramidScreen();
        System.out.println("Module: " + gameData.getModuleSelected());
        System.out.println("Game started!");
    }

    public void endMainGame(){
        gameState.setCurrentState(GameState.State.END_SCREEN);
        ((GUIEndScreen) mainGUI.getEndScreen()).updateFinalScore();
        mainGUI.showEndScreen();
        System.out.println("Game ended! Final score: $" + gameData.getPlayerScore());
    }

    public void terminateGame(){
        System.out.println("Game terminated");
        System.exit(0);
    }

    public void restartGame(){
        gameData = new GameData();
        gameState = new GameState();
        mainGUI.getMainFrame().dispose();
        mainGUI = new MainGUI(gameData);
        playerActionListener = new PlayerActionListener(this);
        addActionListeners();
    }

    public void resumeGame(){
        ((GUIGameScreen) mainGUI.getGameScreen()).hideMenuDialog();
    }

    public void showPyramidScreen(){
        resetAnswerSubmission();
        gameState.setCurrentState(GameState.State.PYRAMID_SCREEN);
        ((GUIGameScreen) mainGUI.getGameScreen()).showScreen1();
        System.out.println("Showing pyramid screen");
    }
    
    public void showQNAScreen(){
        gameState.setCurrentState(GameState.State.QUESTION_SCREEN);
        ((GUIGameScreen) mainGUI.getGameScreen()).showScreen2();
        System.out.println("Showing Q&A screen");
    }

    // Getters and Setters
    public MainGUI getMainGUI(){ return mainGUI; }
    public GameData getGameData(){ return gameData; }
    public GameState getGameState(){ return gameState; }
}