package com.noinc.bloomsjeopardy.model;

public class GameState {
    public enum State {
        START_SCREEN,
        PYRAMID_SCREEN,
        QUESTION_SCREEN,
        END_SCREEN
    }
    
    private State currentState;
    private Question currentQuestion;
    private int selectedRow;
    private int selectedCol;
    private boolean answerSubmitted;
    private boolean answerCorrect;
    
    public GameState() {
        this.currentState = State.START_SCREEN;
        this.answerSubmitted = false;
        this.answerCorrect = false;
    }
    
    // Getters and Setters
    public State getCurrentState() { return currentState; }
    public void setCurrentState(State currentState) { this.currentState = currentState; }
    
    public Question getCurrentQuestion() { return currentQuestion; }
    public void setCurrentQuestion(Question currentQuestion) { this.currentQuestion = currentQuestion; }
    
    public int getSelectedRow() { return selectedRow; }
    public void setSelectedRow(int selectedRow) { this.selectedRow = selectedRow; }
    
    public int getSelectedCol() { return selectedCol; }
    public void setSelectedCol(int selectedCol) { this.selectedCol = selectedCol; }
    
    public boolean isAnswerSubmitted() { return answerSubmitted; }
    public void setAnswerSubmitted(boolean answerSubmitted) { this.answerSubmitted = answerSubmitted; }
    
    public boolean isAnswerCorrect() { return answerCorrect; }
    public void setAnswerCorrect(boolean answerCorrect) { this.answerCorrect = answerCorrect; }
}