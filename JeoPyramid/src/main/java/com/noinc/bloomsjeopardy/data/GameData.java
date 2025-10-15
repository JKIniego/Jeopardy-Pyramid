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
    private int moduleSelected;
    private String[][] itemLabels;
    private String[] qnaStrings;
    private boolean[][] questionsAnswered;
    
    private final int[] levelScores = {100, 200, 300, 400, 500, 600};
    private final String[] categories = {"Knowledge", "Comprehension", "Application", "Analysis", "Synthesis", "Evaluation"};
    private final String[] modules = {"Module 1", "Module 2", "Module 3", "Module 4"};
    
    private List<Question> questions;
    private Random random;
    
    public GameData() {
        this.playerScore = 0;
        this.playerHealth = maxPlayerHealth;
        this.playerUnlockedLevels = 0;
        this.moduleSelected = 0;
        this.random = new Random();
        this.questions = new ArrayList<>();
        
        initializeItemLabels();
        initializeQuestionsAnswered();
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

    private void initializeQuestionsAnswered() {
        questionsAnswered = new boolean[itemLabels.length][];
        for (int i = 0; i < itemLabels.length; i++) {
            questionsAnswered[i] = new boolean[itemLabels[i].length];
        }
    }
    
    private void loadQuestionsFromCSV() {
        String modulePath = "/data/" + modules[moduleSelected] + "/";
        String[] csvFiles = {
            modulePath + "Knowledge.csv",
            modulePath + "Comprehension.csv", 
            modulePath + "Application.csv",
            modulePath + "Analysis.csv",
            modulePath + "Synthesis.csv",
            modulePath + "Evaluation.csv"
        };

        questions.clear();
        
        for (int i = 0; i < csvFiles.length; i++) {
            String category = categories[i];
            try (InputStream is = getClass().getResourceAsStream(csvFiles[i]);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("#");
                    if (parts.length >= 5) {
                        String statement = parts[0].trim();
                        String correctAnswer = parts[1].trim();
                        String[] wrongAnswers = new String[]{
                            parts[2].trim(),
                            parts[3].trim(),
                            parts[4].trim()
                        };
                        
                        // Store the question WITHOUT shuffling
                        // We'll shuffle when retrieving the question
                        Question question = new Question(
                            correctAnswer,
                            statement,
                            wrongAnswers,  // Store only wrong answers
                            0,  // Placeholder - will be calculated on retrieval
                            category,
                            levelScores[i]
                        );
                        questions.add(question);
                    }
                }
            } catch (IOException | NullPointerException e) {
                System.err.println("Error loading CSV file: " + csvFiles[i]);
                System.err.println("Trying fallback to Module 1...");

                if (moduleSelected != 0) {
                    moduleSelected = 0;
                    loadQuestionsFromCSV();
                    return;
                }
                e.printStackTrace();
            }
        }
        
        Collections.shuffle(questions);
        System.out.println("Loaded " + questions.size() + " questions from " + modules[moduleSelected]);
    }
    
    // Reload questions when module changes
    public void reloadQuestionsForModule(int newModule) {
        if (newModule >= 0 && newModule < modules.length) {
            this.moduleSelected = newModule;
            loadQuestionsFromCSV();
        }
    }
    
    // Helper method to randomize answer positions
    private Question randomizeAnswers(Question originalQuestion) {
        // Create a list with correct answer and wrong answers
        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(originalQuestion.getAnswer());
        allAnswers.addAll(Arrays.asList(originalQuestion.getChoices()));
        
        // Shuffle the answers
        Collections.shuffle(allAnswers, random);
        
        // Find the new index of the correct answer
        int newCorrectIndex = allAnswers.indexOf(originalQuestion.getAnswer());
        
        // Create a new question with shuffled answers
        Question randomizedQuestion = new Question(
            originalQuestion.getAnswer(),
            originalQuestion.getQuestionText(),
            allAnswers.toArray(new String[0]),
            newCorrectIndex,
            originalQuestion.getCategory(),
            originalQuestion.getValue()
        );
        
        return randomizedQuestion;
    }
    
    public Question getRandomQuestion(String category, int level) {
        List<Question> filteredQuestions = new ArrayList<>();
        
        for (Question q : questions) {
            if (q.getCategory().equals(category) && 
                q.getValue() == levelScores[level]) {
                filteredQuestions.add(q);
            }
        }
        
        if (filteredQuestions.isEmpty()) {
            for (Question q : questions) {
                if (q.getValue() == levelScores[level]) {
                    filteredQuestions.add(q);
                }
            }
        }
        
        if (filteredQuestions.isEmpty()) {
            // Default question as fallback
            Question defaultQ = new Question("Default Answer", "Default Statement",
                        new String[]{"Wrong 1", "Wrong 2", "Wrong 3"},
                        0, "General", 100);
            return randomizeAnswers(defaultQ);
        }
        
        // Select a random question and randomize its answers
        Question selectedQuestion = filteredQuestions.get(random.nextInt(filteredQuestions.size()));
        return randomizeAnswers(selectedQuestion);
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
    
    public int getModuleSelected() { return moduleSelected; }
    public void setModuleSelected(int moduleSelected) { 
        this.moduleSelected = moduleSelected;
        reloadQuestionsForModule(moduleSelected);
    }
    
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

    public void markQuestionAnswered(int row, int col) {
        if (questionsAnswered == null) initializeQuestionsAnswered();
        if (row >= 0 && row < questionsAnswered.length && col >= 0 && col < questionsAnswered[row].length) {
            questionsAnswered[row][col] = true;
        }
    }

    public boolean isQuestionAnswered(int row, int col) {
        if (questionsAnswered == null) return false;
        if (row >= 0 && row < questionsAnswered.length && col >= 0 && col < questionsAnswered[row].length) {
            return questionsAnswered[row][col];
        }
        return false;
    }

    public boolean areAllQuestionsAnswered(int level) {
        if (questionsAnswered == null) return false;
        for (boolean b : questionsAnswered[level]) {
            if (!b) return false;
        }
        return true;
    }
    
    public void setQnaStrings(String[] qnaStrings) { this.qnaStrings = qnaStrings; }
    
    public int[] getLevelScores() { return levelScores; }
    
    public String[] getModules() { return modules; }
    
    public String[] getCategories() { return categories; }
    
    public List<Question> getQuestions() { return questions; }

    public boolean[][] getQuestionsAnswered() { return questionsAnswered; }
}