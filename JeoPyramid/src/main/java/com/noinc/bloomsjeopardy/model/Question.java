package com.noinc.bloomsjeopardy.model;

public class Question {
    private String answer;        // The correct answer
    private String questionText;  // The statement
    private String[] choices;     // All question choices (shuffled)
    private int correctIndex;     // Index of the correct answer in the choices array
    private String category;
    private int value;
    private boolean used;
    
    public Question(String answer, String questionText, String[] choices, int correctIndex, String category, int value) {
        this.answer = answer;
        this.questionText = questionText;
        this.choices = choices;
        this.correctIndex = correctIndex;
        this.category = category;
        this.value = value;
        this.used = false;
    }
    
    public boolean checkAnswer(String playerAnswer) {
        return answer.equalsIgnoreCase(playerAnswer.trim());
    }
    
    public boolean checkAnswer(int choiceIndex) {
        return choiceIndex == correctIndex;
    }
    
    // Getters and Setters
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    
    public String[] getChoices() { return choices; }
    public void setChoices(String[] choices) { this.choices = choices; }
    
    public int getCorrectIndex() { return correctIndex; }
    public void setCorrectIndex(int correctIndex) { this.correctIndex = correctIndex; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    
    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }
}