package com.noinc.bloomsjeopardy.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import com.noinc.bloomsjeopardy.model.Question;

public class GameData {
    private int playerScore;
    private int playerHealth;
    private int maxPlayerHealth = 3;
    private int playerUnlockedLevels;
    
    private String[][] itemLabels;
    private String[] qnaStrings;
    
    private final int[] levelScores = {100, 200, 300, 400, 500, 600};
    private final String[] categories = {"Knowledge", "Comprehension", "Application", "Analysis", "Synthesis", "Evaluation"};
    
    private List<Question> questions;
    private Random random;
    
    public GameData() {
        this.playerScore = 0;
        this.playerHealth = maxPlayerHealth;
        this.playerUnlockedLevels = 0;
        this.random = new Random();
        this.questions = new ArrayList<>();
        
        initializeItemLabels();
        loadQuestionsFromCSV();
    }
    
    private void initializeItemLabels() {
        itemLabels = new String[][] {
            {"$100", "$100", "$100", "$100", "$100", "$100"},  
            {"$200", "$200", "$200", "$200", "$200"},          
            {"$300", "$300", "$300", "$300"},                  
            {"$400", "$400", "$400"},                          
            {"$500", "$500"},                                  
            {"$600"}                                           
        };
    }
    
    private void loadQuestionsFromCSV() {
        String[] csvFiles = {
            "/data/Module 1/Knowledge.csv",
            "/data/Module 1/Comprehension.csv", 
            "/data/Module 1/Application.csv",
            "/data/Module 1/Analysis.csv",
            "/data/Module 1/Synthesis.csv",
            "/data/Module 1/Evaluation.csv"
        };
        
        for (int i = 0; i < csvFiles.length; i++) {
            String category = categories[i];
            try (InputStream is = getClass().getResourceAsStream(csvFiles[i]);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("#");
                    if (parts.length >= 5) {
                        String statement = parts[0].trim(); // The statement
                        String correctAnswer = parts[1].trim(); // The correct question choice
                        String[] wrongAnswers = new String[]{
                            parts[2].trim(), 
                            parts[3].trim(), 
                            parts[4].trim()
                        };
                        
                        // Combine all answers and shuffle them
                        List<String> allAnswers = new ArrayList<>();
                        allAnswers.add(correctAnswer);
                        allAnswers.addAll(Arrays.asList(wrongAnswers));
                        Collections.shuffle(allAnswers);
                        
                        // Find the index of the correct answer after shuffling
                        int correctIndex = allAnswers.indexOf(correctAnswer);
                        
                        Question question = new Question(
                            correctAnswer, // Store the correct answer text
                            statement, // Use the statement as the main display text
                            allAnswers.toArray(new String[0]), // All shuffled choices
                            correctIndex, // Store the index of the correct answer
                            category,
                            levelScores[i] // Assign score based on row (category)
                        );
                        questions.add(question);
                    }
                }
            } catch (IOException | NullPointerException e) {
                System.err.println("Error loading CSV file: " + csvFiles[i]);
                e.printStackTrace();
            }
        }
        
        // Shuffle questions to ensure random distribution
        Collections.shuffle(questions);
    }
    
    public Question getRandomQuestion(String category, int level) {
        List<Question> filteredQuestions = new ArrayList<>();
        
        for (Question q : questions) {
            if (q.getCategory().equals(category) && 
                q.getValue() == levelScores[level]) {
                filteredQuestions.add(q);
            }
        }
        
        if (!filteredQuestions.isEmpty()) {
            return filteredQuestions.get(random.nextInt(filteredQuestions.size()));
        }

        for (Question q : questions) {
            if (q.getValue() == levelScores[level]) {
                filteredQuestions.add(q);
            }
        }
        
        return filteredQuestions.isEmpty() ? 
            new Question("Default Answer", "Default Statement", 
                        new String[]{"Choice A", "Choice B", "Choice C", "Choice D"}, 
                        0, 
                        "General", 100) 
            : filteredQuestions.get(random.nextInt(filteredQuestions.size()));
    }
    
    public Question getQuestionForPosition(int row, int col) {
        String category = categories[row];
        return getRandomQuestion(category, row);
    }
    
    // Getters and Setters
    public int getPlayerScore() { return playerScore; }
    public void setPlayerScore(int playerScore) { this.playerScore = playerScore; }
    
    public int getPlayerHealth() { return playerHealth; }
    public void setPlayerHealth(int playerHealth) { this.playerHealth = playerHealth; }
    
    public int getMaxPlayerHealth() { return maxPlayerHealth; }
    
    public int getPlayerUnlockedLevels() { return playerUnlockedLevels; }
    public void setPlayerUnlockedLevels(int playerUnlockedLevels) { 
        this.playerUnlockedLevels = playerUnlockedLevels; 
    }
    
    public String[][] getItemLabels() { return itemLabels; }
    
    public String[] getQnaStrings() { 
        if (qnaStrings == null) {
            qnaStrings = new String[]{
                "Category: General",
                "Statement: Select a question from the pyramid",
                "A) Choice A", "B) Choice B", "C) Choice C", "D) Choice D"
            };
        }
        return qnaStrings; 
    }
    
    public void setQnaStrings(String[] qnaStrings) { this.qnaStrings = qnaStrings; }
    
    public int[] getLevelScores() { return levelScores; }
    
    public String[] getCategories() { return categories; }
    
    public List<Question> getQuestions() { return questions; }
}