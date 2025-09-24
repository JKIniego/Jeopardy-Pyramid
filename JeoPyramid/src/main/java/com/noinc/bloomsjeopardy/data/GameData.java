package com.noinc.bloomsjeopardy.data;

import java.util.Arrays;

public class GameData {
    private int[] levelScores;
    private int playerHealth, maxPlayerHealth, playerScore, playerUnlockedLevels;
    private String[][] itemLabels;
    private String[] qnaStrings;

    public GameData(){        
        initializeConstantValues();     // All immutable vales
        initializeDefaultValues();      // Program modifies these values
    }

    public void initializeConstantValues(){
        maxPlayerHealth = 3;        // max value: 3
        levelScores = new int[]{100, 300, 500, 1000, 1500, 5000};

        itemLabels = new String[levelScores.length][];
        for (int i = 0; i < levelScores.length; i++) {
            // bottom row first: i=0 → 6 copies of $100
            // top row last: i=5 → 1 copy of $5000
            itemLabels[i] = new String[levelScores.length - i];
            Arrays.fill(itemLabels[i], "$" + levelScores[i]);
        }
    }

    public void initializeDefaultValues(){
        // all data here can be changed as the game progress
        playerHealth = 3;           // default value: 3
        playerScore = 0;            // max value: 15100
        playerUnlockedLevels = 0;   // base-0 max value: 5 

        //Temporary


        
        String catergoryString = "Evaluation $5000";
        String statementString = " The General Problem Solver of Newell and Simon was inefficient alongside its foolhardy claims that AI can copy entire workings of the human brain.";
        String choiceAString = " Did early AI systems fail to incorporate human-like emotions into their designs?";
        String choiceBString = " Why was the abandonment of the General Problem Solver primarily due to a lack of interest from funding agencies? ";
        String choiceCString = " How were early AI approaches deemed impractical and their claims about human intelligence replication exaggerated?";
        String choiceDString = " How did the lack of advanced programming languages hinder the General Problem Solver's development?  >> How did the lack of advanced programming languages hinder the General Problem Solver's development?How did the lack of advanced programming languages hinder the General Problem Solver's development?How did the lack of advanced programming languages hinder the General Problem Solver's development?How did the lack of advanced programming languages hinder the General Problem Solver's development?How did the lack of advanced programming languages hinder the General Problem Solver's development?How did the lack of advanced programming languages hinder the General Problem Solver's development?How did the lack of advanced programming languages hinder the General Problem Solver's development?How did the lack of advanced programming languages hinder the General Problem Solver's development?";

        qnaStrings = new String[]{
            catergoryString, statementString, choiceAString, choiceBString, choiceCString, choiceDString
        };
    }

    public int getPlayerScore(){
        return playerScore;
    }
    public void setPlayerScore(int newScore){
        playerScore = newScore;
    } 
    
    public int getPlayerHealth(){
        return playerHealth;
    }
    public void setPlayerHealth(int newPlayerHealth){
        playerHealth = newPlayerHealth;
    }

    public int getMaxPlayerHealth(){
        return maxPlayerHealth;
    }

    public int getPlayerUnlockedLevels(){
        return playerUnlockedLevels;
    }
    public void setPlayerUnlockedLevels(int newUnlockedLevels){
        playerUnlockedLevels = newUnlockedLevels;
    }

    public String[][] getItemLabels(){
        return itemLabels;
    }

    public String[] getQnaStrings(){
        return qnaStrings;
    }

    public int[] getLevelScores(){
        return levelScores;
    }



    


}