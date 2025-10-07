package com.noinc.bloomsjeopardy.view;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.noinc.bloomsjeopardy.data.GameData;

public class MainGUI {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private GUIStartScreen GUIStartScreen;
    private GUIModuleScreen GUIModuleScreen;
    private GUIGameScreen GUIGameScreen;
    private GUIEndScreen GUIEndScreen;
    private GUIAboutUsScreen GUIAboutUsScreen;
    private GUIHowToPlayScreen GUIHowToPlayScreen;
    private GUIBrand brand;
    private GameData gameData;

    public MainGUI(GameData gameData){
        this.gameData = gameData;
        brand = new GUIBrand();
        initializeFrame();
        initializeMainPanel();                 
    }

    private void initializeClasses(){
        GUIStartScreen = new GUIStartScreen(gameData, mainPanel, brand); 
        GUIModuleScreen = new GUIModuleScreen(gameData, mainPanel, brand);
        GUIGameScreen = new GUIGameScreen(gameData, mainPanel, brand);
        GUIEndScreen = new GUIEndScreen(gameData, mainPanel, brand);
        GUIAboutUsScreen = new GUIAboutUsScreen(gameData, mainPanel, brand);
        GUIHowToPlayScreen = new GUIHowToPlayScreen(gameData, mainPanel, brand);
    }

    private void initializeFrame(){
        mainFrame = new JFrame("JeoPyramid");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);
        mainFrame.setIconImage(brand.gameIconIMG);
        mainFrame.setMinimumSize(new Dimension(1200, 690));
        mainFrame.setLocationRelativeTo(null);
    }

    private void initializeMainPanel(){
        mainPanel = new JPanel(new java.awt.CardLayout());

        initializeClasses();

        mainPanel.add(GUIStartScreen, "StartScreen");
        mainPanel.add(GUIModuleScreen, "ModuleScreen");
        mainPanel.add(GUIGameScreen, "GameScreen");
        mainPanel.add(GUIEndScreen, "EndScreen");
        mainPanel.add(GUIAboutUsScreen, "AboutUsScreen");
        mainPanel.add(GUIHowToPlayScreen, "HowToPlayScreen");
        
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);   
    }

    

    public void showStartScreen(){
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "StartScreen");
    }

    public void showModuleScreen(){
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "ModuleScreen");
    }

    public void showGameScreen(){
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "GameScreen");
    }

    public void showEndScreen(){
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "EndScreen");
    }

    public void showAboutUsScreen(){
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "AboutUsScreen");
    }

    public void showHowToPlayScreen(){
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "HowToPlayScreen");
    }

    public JPanel getStartScreen(){
        return GUIStartScreen;
    }
    public JPanel getModuleScreen(){
        return GUIModuleScreen;
    }
    public JPanel getGameScreen(){
        return GUIGameScreen;
    }
    public JPanel getEndScreen(){
        return GUIEndScreen;
    }
    public JPanel getAboutUsScreen(){
        return GUIAboutUsScreen;
    }
    public JPanel getHowToPlayScreen(){
        return GUIHowToPlayScreen;
    }

    public JFrame getMainFrame(){
        return mainFrame;
    }
}
