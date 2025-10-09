package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.noinc.bloomsjeopardy.data.GameData;

public class MainGUI implements MouseListener{
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
    private JButton yesButton, noButton;

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

    public boolean showConfirmationDialog(String message, int type) {
        if (type == 1 && ((GUIGameScreen) getGameScreen()).getIsAnswerLocked()) {
            return false;
        }
        final boolean[] result = {false};
        JDialog dialog = new JDialog(mainFrame, "Confirm", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 200);
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel confirmationPanel = new JPanel();
        confirmationPanel.setLayout(new GridBagLayout());
        confirmationPanel.setBackground(brand.black);
        confirmationPanel.setBorder(BorderFactory.createLineBorder(brand.blue, 8));

        JLabel label = new JLabel(message, JLabel.CENTER);
        label.setFont(brand.CustomFontSmaller);
        label.setForeground(brand.white);

        yesButton = new JButton("Yes");
        noButton = new JButton("No");

        yesButton.setBackground(brand.blue);
        noButton.setBackground(brand.black);
        yesButton.setBorderPainted(false);
        noButton.setBorderPainted(false);
        yesButton.setFocusPainted(false);
        noButton.setFocusPainted(false);
        yesButton.setPreferredSize(new Dimension(100, 30));
        noButton.setPreferredSize(new Dimension(100, 30));
        yesButton.putClientProperty("type", "confirmation yes");
        noButton.putClientProperty("type", "confirmation no");
        yesButton.addMouseListener(this);
        noButton.addMouseListener(this);
        yesButton.setFont(brand.CustomFontSmaller);
        noButton.setFont(brand.CustomFontSmaller);
        yesButton.setForeground(brand.white);
        noButton.setForeground(brand.white);

        yesButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });
        noButton.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });

        JPanel buttonPanel;
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        GridBagConstraints confirmGBC = new GridBagConstraints();
        confirmGBC.insets = new Insets(0, 0, 20, 0);
        confirmGBC.gridy = 0;
        confirmGBC.ipady = 0;
        confirmationPanel.add(label, confirmGBC);
        confirmGBC.insets = new Insets(0, 0, 10, 0);
        confirmGBC.gridy = 1;
        confirmationPanel.add(buttonPanel, confirmGBC);

        dialog.add(confirmationPanel, BorderLayout.CENTER);

        dialog.setVisible(true);
        return result[0];
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
    
    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        Object src = e.getSource();
        if (src instanceof JButton) {
            JButton clicked = (JButton) src;
            if ("confirmation yes".equals(clicked.getClientProperty("type"))){
                yesButton.setBackground(brand.blue);
                noButton.setBackground(brand.black);
            }
            else if ("confirmation no".equals(clicked.getClientProperty("type"))){
                noButton.setBackground(brand.blue);
                yesButton.setBackground(brand.black);
            }
        }
    }

    @Override public void mouseExited(java.awt.event.MouseEvent e) {}
    @Override public void mouseClicked(java.awt.event.MouseEvent e) {}
    @Override public void mousePressed(java.awt.event.MouseEvent e) {}
    @Override public void mouseReleased(java.awt.event.MouseEvent e) {}
}
