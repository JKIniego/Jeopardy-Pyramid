package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.OverlayLayout;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.noinc.bloomsjeopardy.data.GameData;

public class GUIGameScreen extends JPanel implements MouseListener  {
    private JPanel parentPanel, mainGamePanel, hudPanel, menuPanel, overlayMenu, livesPanel, screen1, screen2, screensContainer, choiceA, choiceB, choiceC, choiceD;
    private JButton menuButton, menuResumeButton, menuRestartButton, menuExitButton, screen2BackButton, menuSettingsButton, yesButton, noButton;
    private JLabel scoreLabel;
    private JButton[][] itemButtonsArray;
    private GUIBrand brand;
    private GameData gameData;
    private boolean isAnswerLocked;
    private LayoutManager originalParentLayout;
    private int lastUnlockedLevel = 0;

    private JTextPane categoryLabel, statementLabel, choiceALabel, choiceBLabel, choiceCLabel, choiceDLabel;
    
    public GUIGameScreen(GameData gameData, JPanel parentPanel, GUIBrand brand) {
        this.parentPanel = parentPanel;
        this.brand = brand;
        this.gameData = gameData;
        this.setLayout(new BorderLayout());
        
        initializeMainContentPanel();
        initializeMenuPanel();
        initializeHUDPanel();
        initializeScreen1();
        initializeScreen2();

        updateLevels();
        screensContainer = new JPanel(new CardLayout());
        screensContainer.setOpaque(false);
        screensContainer.add(screen1, "screen1");
        screensContainer.add(screen2, "screen2");

        GridBagConstraints mainGamePanelGBC = new GridBagConstraints();
        mainGamePanelGBC.fill = GridBagConstraints.BOTH;
        mainGamePanelGBC.weightx = 1;
        mainGamePanelGBC.weighty = 0.01;
        mainGamePanelGBC.gridy = 0;
        mainGamePanel.add(hudPanel, mainGamePanelGBC);
        mainGamePanelGBC.weighty = 0.8;
        mainGamePanelGBC.gridy = 1;
        mainGamePanel.add(screensContainer, mainGamePanelGBC);
        this.add(mainGamePanel);
    }

    private void initializeMainContentPanel(){
        mainGamePanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(brand.backgroundIMG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainGamePanel.setLayout(new GridBagLayout());
    }

    private void initializeMenuPanel(){
        menuPanel = new JPanel();
        menuPanel.setBackground(brand.black);
        menuPanel.setBorder(BorderFactory.createLineBorder(brand.blue, 8));
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setPreferredSize(new Dimension(300, 300));

        menuResumeButton = new JButton("Resume");
        menuSettingsButton = new JButton("Settings");
        menuRestartButton = new JButton("Restart");
        menuExitButton = new JButton("Exit");

        menuResumeButton.setBackground(brand.black);
        menuSettingsButton.setBackground(brand.black);
        menuRestartButton.setBackground(brand.black);
        menuExitButton.setBackground(brand.black);
        menuResumeButton.setForeground(brand.white);
        menuSettingsButton.setForeground(brand.white);
        menuRestartButton.setForeground(brand.white);
        menuExitButton.setForeground(brand.white);
        menuResumeButton.setFont(brand.CustomFontSmall);
        menuSettingsButton.setFont(brand.CustomFontSmall);
        menuRestartButton.setFont(brand.CustomFontSmall);
        menuExitButton.setFont(brand.CustomFontSmall);
        menuResumeButton.setBorderPainted(false);
        menuSettingsButton.setBorderPainted(false);
        menuRestartButton.setBorderPainted(false);
        menuExitButton.setBorderPainted(false);
        menuResumeButton.setFocusable(false);
        menuSettingsButton.setFocusable(false);
        menuRestartButton.setFocusable(false);
        menuExitButton.setFocusable(false);
        menuResumeButton.addMouseListener(this);
        menuSettingsButton.addMouseListener(this);
        menuRestartButton.addMouseListener(this);
        menuExitButton.addMouseListener(this);
        menuResumeButton.setPreferredSize(new Dimension(200, 30));
        menuSettingsButton.setPreferredSize(new Dimension(200, 30));
        menuRestartButton.setPreferredSize(new Dimension(200, 30));
        menuExitButton.setPreferredSize(new Dimension(200, 30));

        menuResumeButton.setActionCommand("GameScreen menuResumeButton");
        menuSettingsButton.setActionCommand("GameScreen menuSettingsButton");
        menuRestartButton.setActionCommand("GameScreen menuRestartButton");
        menuExitButton.setActionCommand("GameScreen menuExitButton");

        GridBagConstraints menuPanelGBC = new GridBagConstraints();
        menuPanelGBC.insets = new Insets(0, 0, 20, 0);
        menuPanelGBC.gridy = 0;
        menuPanelGBC.ipadx = 0;
        menuPanelGBC.ipady = 5;
        menuPanel.add(menuResumeButton, menuPanelGBC);
        menuPanelGBC.gridy++;
        menuPanel.add(menuSettingsButton, menuPanelGBC);
        menuPanelGBC.gridy++;
        menuPanel.add(menuRestartButton, menuPanelGBC);
        menuPanelGBC.gridy++;
        menuPanel.add(menuExitButton, menuPanelGBC);
    }

    private void initializeHUDPanel(){
        hudPanel = new JPanel();
        hudPanel.setBackground(brand.darkBlue);
        hudPanel.setLayout(new GridLayout(1,3));
        hudPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        hudPanel.setPreferredSize(new Dimension(100,50));

        livesPanel = new JPanel();
        livesPanel.setOpaque(false);
        livesPanel.setLayout(new BoxLayout(livesPanel, BoxLayout.X_AXIS));

        for (int i =1; i<=3; i++){
            JLabel heart = new JLabel();
            heart.setIcon(new ImageIcon((i <= gameData.getPlayerHealth()) ? brand.heartFullIMG : brand.heartEmptyIMG));
            livesPanel.add(Box.createHorizontalStrut(10));
            livesPanel.add(heart);
        }

        JPanel scorePanel = new JPanel();
        scorePanel.setOpaque(false);
        scorePanel.setLayout(new GridBagLayout());
        scoreLabel = new JLabel();
        scoreLabel.setText("$" + gameData.getPlayerScore());
        scoreLabel.setFont(brand.CustomFontMedium);
        scoreLabel.setForeground(brand.white);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scorePanel.add(scoreLabel);

        menuButton = new JButton();
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusable(false);
        menuButton.setBorderPainted(false);
        menuButton.setIcon(new ImageIcon(brand.menuIMG));
        menuButton.setActionCommand("GameScreen menuButton");
        menuButton.addMouseListener(this);
        menuButton.setPreferredSize(new Dimension(20, 20));
        menuButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JPanel menuButtonPanel = new JPanel();
        menuButtonPanel.setLayout(new BoxLayout(menuButtonPanel, BoxLayout.X_AXIS));
        menuButtonPanel.setOpaque(false);
        menuButtonPanel.add(Box.createHorizontalGlue());
        menuButtonPanel.add(menuButton);

        hudPanel.add(livesPanel);
        hudPanel.add(scorePanel);
        hudPanel.add(menuButtonPanel);
    }

    private void initializeScreen1(){
        screen1 = new JPanel();
        screen1.setLayout(new GridBagLayout());
        screen1.setOpaque(false);

        JLabel titleInGameLabel = new JLabel(new ImageIcon(brand.titleInGameIMG));

        JPanel pyramidPanel = new JPanel();
        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS));
        pyramidPanel.setOpaque(false);

        itemButtonsArray = new JButton[gameData.getItemLabels().length][];

        for (int row = gameData.getItemLabels().length - 1; row >= 0; row--) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            rowPanel.setOpaque(false);

            itemButtonsArray[row] = new JButton[gameData.getItemLabels()[row].length];

            for (int col = 0; col < gameData.getItemLabels()[row].length; col++) {
                String text = gameData.getItemLabels()[row][col];
                JButton itemButton = new JButton(text);

                itemButton.setIcon(new ImageIcon(brand.brickIMG));
                itemButton.setHorizontalTextPosition(JLabel.CENTER);
                itemButton.setVerticalTextPosition(JLabel.CENTER);
                itemButton.setPreferredSize(new Dimension(170, 53));
                itemButton.setForeground(Color.WHITE);
                itemButton.setFont(brand.CustomFontMedium);
                itemButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                itemButton.setActionCommand("GameScreen pyramidButton");
                itemButton.putClientProperty("type", "pyramidButton");
                itemButton.putClientProperty("row", row);
                itemButton.putClientProperty("col", col);

                if (!Arrays.asList(itemButton.getMouseListeners()).contains(this)) {
                    itemButton.addMouseListener(this);
                }

                itemButtonsArray[row][col] = itemButton;
                rowPanel.add(itemButton);
            }

            pyramidPanel.add(rowPanel);
        }

        GridBagConstraints screen1GBC = new GridBagConstraints();
        screen1GBC.gridy = 0;
        screen1.add(titleInGameLabel, screen1GBC);
        screen1GBC.gridy = 1;
        screen1.add(pyramidPanel, screen1GBC);
    }

    private void initializeScreen2(){
        screen2 = new JPanel();
        screen2.setLayout(new GridBagLayout());
        screen2.setOpaque(false);
        screen2.setBackground(brand.blue);

        JPanel qnaPanel = new JPanel();
        qnaPanel.setPreferredSize(new Dimension(1000, 600));
        qnaPanel.setBackground(brand.black);
        qnaPanel.setBorder(BorderFactory.createLineBorder(brand.blue, 8));
        qnaPanel.setLayout(new GridBagLayout());
        
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JPanel statementPanel = new JPanel(new GridBagLayout());
        JPanel choiceContainerPanel = new JPanel(new GridLayout(2,2, 10, 10));
        JPanel choiceAPanel = new JPanel();
        JPanel choiceBPanel = new JPanel();
        JPanel choiceCPanel = new JPanel();
        JPanel choiceDPanel = new JPanel();

        choiceContainerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        choiceContainerPanel.setOpaque(false);
        choiceContainerPanel.add(choiceAPanel);
        choiceContainerPanel.add(choiceBPanel);
        choiceContainerPanel.add(choiceCPanel);
        choiceContainerPanel.add(choiceDPanel);

        categoryPanel.setBackground(brand.black);
        statementPanel.setBackground(brand.black);
        choiceAPanel.setBackground(brand.black);
        choiceBPanel.setBackground(brand.black);
        choiceCPanel.setBackground(brand.black);
        choiceDPanel.setBackground(brand.black);

        choiceAPanel.setLayout(new OverlayLayout(choiceAPanel));
        choiceBPanel.setLayout(new OverlayLayout(choiceBPanel));
        choiceCPanel.setLayout(new OverlayLayout(choiceCPanel));
        choiceDPanel.setLayout(new OverlayLayout(choiceDPanel));

        categoryPanel.setBorder(BorderFactory.createEmptyBorder(10,0, 10, 50));
        statementPanel.setBorder(BorderFactory.createMatteBorder(8,0, 0, 0, brand.blue));
        choiceAPanel.setBorder(BorderFactory.createMatteBorder(5,5, 5, 5, brand.blue));
        choiceBPanel.setBorder(BorderFactory.createMatteBorder(5,5, 5, 5, brand.blue));
        choiceCPanel.setBorder(BorderFactory.createMatteBorder(5,5, 5, 5, brand.blue));
        choiceDPanel.setBorder(BorderFactory.createMatteBorder(5,5, 5, 5, brand.blue));

        choiceAPanel.setName("choice1");
        choiceBPanel.setName("choice2");
        choiceCPanel.setName("choice3");
        choiceDPanel.setName("choice4");

        screen2BackButton = new JButton("<");
        screen2BackButton.setActionCommand("GameScreen screen2BackButton");
        screen2BackButton.setFont(brand.CustomFontMedium);
        screen2BackButton.setForeground(brand.white);
        screen2BackButton.setContentAreaFilled(false);
        screen2BackButton.setBorderPainted(false);
        screen2BackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JLabel bufferLabel = new JLabel("");
        bufferLabel.setFont(brand.CustomFontExtraSmall);
        categoryPanel.setLayout(new BorderLayout());

        categoryLabel = makeStyledLabel(0, "", brand.CustomFontSmall, brand.white, brand.black, 1f, StyleConstants.ALIGN_CENTER);
        statementLabel = makeStyledLabel(0, "", brand.CustomFontExtraSmall, brand.white, brand.black, 1.5f, StyleConstants.ALIGN_CENTER);
        choiceALabel = makeStyledLabel(1, "", brand.CustomFontExtraSmall, brand.white, brand.black, 1.5f, StyleConstants.ALIGN_CENTER);
        choiceBLabel = makeStyledLabel(1, "", brand.CustomFontExtraSmall, brand.white, brand.black, 1.5f, StyleConstants.ALIGN_CENTER);
        choiceCLabel = makeStyledLabel(1, "", brand.CustomFontExtraSmall, brand.white, brand.black, 1.5f, StyleConstants.ALIGN_CENTER);
        choiceDLabel = makeStyledLabel(1, "", brand.CustomFontExtraSmall, brand.white, brand.black, 1.5f, StyleConstants.ALIGN_CENTER);
        
        choiceA = new JPanel();
        choiceB = new JPanel();
        choiceC = new JPanel();
        choiceD = new JPanel();

        choiceAPanel.setName("choice1");
        choiceBPanel.setName("choice2");
        choiceCPanel.setName("choice3");
        choiceDPanel.setName("choice4");

        
        choiceA.addMouseListener(this);
        choiceB.addMouseListener(this);
        choiceC.addMouseListener(this);
        choiceD.addMouseListener(this);

        choiceA.putClientProperty("index", 0);
        choiceB.putClientProperty("index", 1);
        choiceC.putClientProperty("index", 2);
        choiceD.putClientProperty("index", 3);

        choiceA.setOpaque(false);
        choiceB.setOpaque(false);
        choiceC.setOpaque(false);
        choiceD.setOpaque(false);

        choiceA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        choiceB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        choiceC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        choiceD.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        categoryPanel.add(screen2BackButton, BorderLayout.WEST);
        categoryPanel.add(bufferLabel, BorderLayout.EAST);
        categoryPanel.add(categoryLabel, BorderLayout.CENTER);
        statementPanel.add(statementLabel);
        
        choiceAPanel.add(choiceA);
        choiceBPanel.add(choiceB);
        choiceCPanel.add(choiceC);
        choiceDPanel.add(choiceD);
        choiceAPanel.add(choiceALabel);
        choiceBPanel.add(choiceBLabel);
        choiceCPanel.add(choiceCLabel);
        choiceDPanel.add(choiceDLabel);

        GridBagConstraints qnaPanelGBC = new GridBagConstraints();
        qnaPanelGBC.fill = GridBagConstraints.BOTH;
        qnaPanelGBC.weightx = 1;
        qnaPanelGBC.weighty = 0.001;
        qnaPanelGBC.gridy = 0;
        qnaPanel.add(categoryPanel, qnaPanelGBC);
        qnaPanelGBC.weightx = 1;
        qnaPanelGBC.weighty = 0.5;
        qnaPanelGBC.gridy = 1;
        qnaPanel.add(statementPanel, qnaPanelGBC);
        qnaPanelGBC.weightx = 1;
        qnaPanelGBC.weighty = 0.1;
        qnaPanelGBC.gridy = 2;
        qnaPanel.add(choiceContainerPanel, qnaPanelGBC);

        screen2.add(qnaPanel);

        updateQNAScreen();
    }

    private JTextPane makeStyledLabel(int type, String text, Font font,  Color fg, Color bg, float lineSpacing, int alignment) {
        JTextPane pane = new JTextPane();
        pane.setText(text);
        pane.setFont(font);
        pane.setForeground(fg);
        pane.setBackground(bg);
        pane.setEditable(false);
        pane.setFocusable(false);
        pane.setOpaque(false);
        pane.setPreferredSize((type==1) ? new Dimension(470, 150) : new Dimension(800, 150));
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(attrs, lineSpacing);
        StyleConstants.setAlignment(attrs, alignment);
        doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
        return pane;
    }

    public void animatePyramidBuild() {
        // Store original colors and hide all buttons
        Color[][] originalColors = new Color[itemButtonsArray.length][];
        menuButton.setEnabled(false);
        for (int row = 0; row < itemButtonsArray.length; row++) {
            originalColors[row] = new Color[itemButtonsArray[row].length];
            for (int col = 0; col < itemButtonsArray[row].length; col++) {
                originalColors[row][col] = itemButtonsArray[row][col].getForeground();
                itemButtonsArray[row][col].setForeground(Color.WHITE);
                itemButtonsArray[row][col].setVisible(false);
            }
        }
        
        // Animate building from top to bottom (lower levels first)
        new Thread(() -> {
            try {
                for (int row = 0; row < itemButtonsArray.length; row++) {
                    final int currentRow = row;
                
                    for (int col = 0; col < itemButtonsArray[currentRow].length; col++) {
                        final int currentCol = col;
                        
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            itemButtonsArray[currentRow][currentCol].setVisible(true);
                            screen1.revalidate();
                            screen1.repaint();
                        });
                        Thread.sleep(70);
                    }
                    Thread.sleep(70);
                }

                //Flicker
                for(int i=0; i<3;i++){
                    Thread.sleep(100);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        for (int row = 0; row < itemButtonsArray.length; row++) {
                            for (int col = 0; col < itemButtonsArray[row].length; col++) {
                                itemButtonsArray[row][col].setForeground(brand.gray);
                            }
                        }
                        screen1.repaint();
                    });
                    Thread.sleep(200);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        for (int row = 0; row < itemButtonsArray.length; row++) {
                            for (int col = 0; col < itemButtonsArray[row].length; col++) {
                                itemButtonsArray[row][col].setForeground(brand.white);
                            }
                        }
                        screen1.repaint();
                    });
                }
                Thread.sleep(100);
                javax.swing.SwingUtilities.invokeLater(() -> {
                    for (int row = 0; row < itemButtonsArray.length; row++) {
                        for (int col = 0; col < itemButtonsArray[row].length; col++) {
                            itemButtonsArray[row][col].setForeground(originalColors[row][col]);
                        }
                    }
                    screen1.repaint();
                });
                menuButton.setEnabled(true);
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public void animateHUDScore() {
        // Animate Score Added
        new Thread(() -> {
            try {
                for(int i=0; i<3; i++){
                    Thread.sleep(200);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        scoreLabel.setForeground(brand.green);
                    });
                    Thread.sleep(200);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        scoreLabel.setForeground(brand.white);
                    });
                    parentPanel.repaint();
                }
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public void animateHUDHearts(boolean gainedHeart) {
        new Thread(() -> {
            try {
                int heartIndex = gainedHeart ? gameData.getPlayerHealth() - 1 : gameData.getPlayerHealth();

                for (int i = 0; i < 3; i++) {
                    Thread.sleep(200);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        JLabel heart = (JLabel) livesPanel.getComponent(heartIndex * 2 + 1); // skip spacer
                        heart.setIcon(new ImageIcon(brand.heartEmptyIMG));
                        livesPanel.repaint();
                    });
                    Thread.sleep(200);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        JLabel heart = (JLabel) livesPanel.getComponent(heartIndex * 2 + 1);
                        heart.setIcon(new ImageIcon(brand.heartFullIMG));
                        livesPanel.repaint();
                    });
                }

                // refresh to correct icons at the end
                javax.swing.SwingUtilities.invokeLater(this::updateHUD);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void animateUnlockedLevel() {
        int levelUnlocked = gameData.getPlayerUnlockedLevels();
        new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) { // flicker 3 times
                    Thread.sleep(100);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        for (JButton btn : itemButtonsArray[levelUnlocked]) {
                            btn.setForeground(brand.gray);
                        }
                        screen1.repaint();
                    });
                    Thread.sleep(150);
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        for (JButton btn : itemButtonsArray[levelUnlocked]) {
                            btn.setForeground(brand.white);
                        }
                        screen1.repaint();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updateQNAScreen() {
        String[] qnaStrings = gameData.getQnaStrings();
        
        if (qnaStrings != null && qnaStrings.length >= 6) {
            categoryLabel.setText(qnaStrings[0]);
            statementLabel.setText(qnaStrings[1]);
            choiceALabel.setText(qnaStrings[2]);
            choiceBLabel.setText(qnaStrings[3]);
            choiceCLabel.setText(qnaStrings[4]);
            choiceDLabel.setText(qnaStrings[5]);
        } else {
            categoryLabel.setText("Category: General");
            statementLabel.setText("Statement: Select a question from the pyramid");
            choiceALabel.setText("A) Choice A");
            choiceBLabel.setText("B) Choice B");
            choiceCLabel.setText("C) Choice C");
            choiceDLabel.setText("D) Choice D");
        }

        categoryLabel.revalidate();
        categoryLabel.repaint();
        statementLabel.revalidate();
        statementLabel.repaint();
        choiceALabel.revalidate();
        choiceALabel.repaint();
        choiceBLabel.revalidate();
        choiceBLabel.repaint();
        choiceCLabel.revalidate();
        choiceCLabel.repaint();
        choiceDLabel.revalidate();
        choiceDLabel.repaint();
    }

    public void updateLevels() {
        int levelUnlocked = gameData.getPlayerUnlockedLevels();

        for (int row = 0; row < itemButtonsArray.length; row++) {
            for (int col = 0; col < itemButtonsArray[row].length; col++) {
                
                if (row == levelUnlocked) { 
                    itemButtonsArray[row][col].setEnabled(true);
                    itemButtonsArray[row][col].setForeground(brand.white);

                    if (!Arrays.asList(itemButtonsArray[row][col].getMouseListeners()).contains(this)) {
                        itemButtonsArray[row][col].addMouseListener(this);
                    }

                } else if (row > levelUnlocked) { 
                    itemButtonsArray[row][col].removeMouseListener(this);
                    itemButtonsArray[row][col].setForeground(brand.gray);
                }
            }
        }
    }

    public void updateScreen2BackButton(){
        if (isAnswerLocked){
            screen2BackButton.setText("<");
            screen2BackButton.setEnabled(true);
        }else{
            screen2BackButton.setText(" ");
            screen2BackButton.setEnabled(false);
        }
        
    }

    public void updateHUD(){
        scoreLabel.setText("$" + gameData.getPlayerScore());
        livesPanel.removeAll();
        for (int i =1; i<=3; i++){
            JLabel heart = new JLabel();
            heart.setIcon(new ImageIcon((i <= gameData.getPlayerHealth()) ? brand.heartFullIMG : brand.heartEmptyIMG));
            livesPanel.add(Box.createHorizontalStrut(10));
            livesPanel.add(heart);
        }
        repaint();
        revalidate();
    }

    public void updateItemStatus(int row, int col, Boolean isCorrect){
        if (isCorrect){
            itemButtonsArray[row][col].setForeground(brand.green);
        } else if (!isCorrect){
            itemButtonsArray[row][col].setForeground(brand.red);
        }
    }

    public void showAnswerFeedback(boolean isCorrect, int selectedIndex, int correctIndex) {
        resetChoicePanels();

        Container choiceAParent = choiceA.getParent();
        Container choiceBParent = choiceB.getParent();
        Container choiceCParent = choiceC.getParent();
        Container choiceDParent = choiceD.getParent();

        switch (selectedIndex) {
            case 0 -> choiceAParent.setBackground(isCorrect ? brand.green : brand.red);
            case 1 -> choiceBParent.setBackground(isCorrect ? brand.green : brand.red);
            case 2 -> choiceCParent.setBackground(isCorrect ? brand.green : brand.red);
            case 3 -> choiceDParent.setBackground(isCorrect ? brand.green : brand.red);
        }

        if (!isCorrect) {
            switch (correctIndex) {
                case 0 -> choiceAParent.setBackground(brand.green);
                case 1 -> choiceBParent.setBackground(brand.green);
                case 2 -> choiceCParent.setBackground(brand.green);
                case 3 -> choiceDParent.setBackground(brand.green);
            }
        }

        isAnswerLocked = true;
        updateScreen2BackButton();
        choiceAParent.repaint();
        choiceBParent.repaint();
        choiceCParent.repaint();
        choiceDParent.repaint();
    }

    private void resetChoicePanels() {
        isAnswerLocked = false;
        updateScreen2BackButton();
        Container choiceAParent = choiceA.getParent();
        Container choiceBParent = choiceB.getParent();
        Container choiceCParent = choiceC.getParent();
        Container choiceDParent = choiceD.getParent();
        
        if (choiceAParent instanceof JPanel) ((JPanel) choiceAParent).setBackground(brand.black);
        if (choiceBParent instanceof JPanel) ((JPanel) choiceBParent).setBackground(brand.black);
        if (choiceCParent instanceof JPanel) ((JPanel) choiceCParent).setBackground(brand.black);
        if (choiceDParent instanceof JPanel) ((JPanel) choiceDParent).setBackground(brand.black);
    }

    public void showScreen1() {
        CardLayout cl = (CardLayout) screensContainer.getLayout();
        cl.show(screensContainer, "screen1");
        System.out.println("Showing pyramid screen");
        int currentUnlocked = gameData.getPlayerUnlockedLevels();
        if (currentUnlocked > lastUnlockedLevel) {
            animateUnlockedLevel();
            lastUnlockedLevel = currentUnlocked;
        }
    }

    public void showScreen2(){
        resetChoicePanels();
        updateScreen2BackButton();
        updateQNAScreen();
        CardLayout cl = (CardLayout) screensContainer.getLayout();
        cl.show(screensContainer, "screen2");
    }

    public void repaintMenuButtons(JButton button){
        JButton buttons[] = {menuResumeButton, menuSettingsButton, menuRestartButton, menuExitButton};
        for (int i = 0; i<buttons.length; i++){
            buttons[i].setBackground(brand.black);
        }
        button.setBackground(brand.blue);
    }

    public void showMenuDialog(){
        if (overlayMenu != null && overlayMenu.getParent() != null) {
            // If already showing, just bring it to front
            parentPanel.setComponentZOrder(overlayMenu, 0);
            overlayMenu.revalidate();
            overlayMenu.repaint();
            return;
        }
        
        System.out.println("Menu Shown");
        overlayMenu = new JPanel(new GridBagLayout());
        overlayMenu.setBackground(new Color(0, 0, 0, 150));
        overlayMenu.setOpaque(true);
        overlayMenu.add(menuPanel);
        
        if (originalParentLayout == null) {
            originalParentLayout = parentPanel.getLayout();
        }
        
        if (!(parentPanel.getLayout() instanceof OverlayLayout)) {
            parentPanel.setLayout(new OverlayLayout(parentPanel));
        }
        
        parentPanel.add(overlayMenu, 0);
        
        overlayMenu.setBounds(0, 0, parentPanel.getWidth(), parentPanel.getHeight());
        overlayMenu.setAlignmentX(0.5f);
        overlayMenu.setAlignmentY(0.5f);
        
        overlayMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == overlayMenu) {
                    hideMenuDialog();
                }
            }
        });
        
        menuPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
            }
        });
        
        parentPanel.setComponentZOrder(overlayMenu, 0);
        
        // Force overlay to stay on top
        javax.swing.SwingUtilities.invokeLater(() -> {
            parentPanel.setComponentZOrder(overlayMenu, 0);
            overlayMenu.requestFocusInWindow();
            parentPanel.revalidate();
            parentPanel.repaint();
        });
    }

    public void hideMenuDialog(){
        if (overlayMenu != null && overlayMenu.getParent() != null) {
            Container parent = overlayMenu.getParent();
            parent.remove(overlayMenu);
            overlayMenu = null;
            
            if (originalParentLayout != null) {
                parent.setLayout(originalParentLayout);
                originalParentLayout = null; 
            }
            
            parent.revalidate();
            parent.repaint();
        }
    }
    
    @Override 
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();
        if (src instanceof JButton) {
            JButton clicked = (JButton) src;
            if ("pyramidButton".equals(clicked.getClientProperty("type"))) {
                int row = (int) clicked.getClientProperty("row");
                int col = (int) clicked.getClientProperty("col");
                itemButtonsArray[row][col].setBorder(
                    BorderFactory.createLineBorder(brand.lightBlue, 8)
                );
            }
            else{
                repaintMenuButtons((JButton) src);
            }
        } else if (src instanceof JPanel && !isAnswerLocked) {
            JPanel overlay = (JPanel) src;
            Container parent = overlay.getParent(); 
            if (parent instanceof JPanel) {
                JPanel choicePanel = (JPanel) parent;
                choicePanel.setBackground(brand.darkBlue);  
            }
        }
    }

    @Override 
    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();
        if (src instanceof JButton) {
            JButton clicked = (JButton) src;
            if ("pyramidButton".equals(((JButton) clicked).getClientProperty("type"))){
                int row = (int) clicked.getClientProperty("row");
                int col = (int) clicked.getClientProperty("col");
                itemButtonsArray[row][col].setBorder(null);
            }
        }else if (src instanceof JPanel && !isAnswerLocked) {
            JPanel overlay = (JPanel) src;
            Container parent = overlay.getParent(); 
            if (parent instanceof JPanel) {
                JPanel choicePanel = (JPanel) parent;
                choicePanel.setBackground(brand.black);  
            }
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}

    // Getters and Setters
    public JButton getMenuButton(){return menuButton;}
    public JButton getMenuResumeButton(){return menuResumeButton;}
    public JButton getMenuSettingsButton(){return menuSettingsButton;}
    public JButton getMenuRestartButton(){return menuRestartButton;}
    public JButton getMenuExitButton(){return menuExitButton;}
    public JButton getScreen2BackButton(){return screen2BackButton;}
    public JButton[][] getItemButtonsArray(){return itemButtonsArray;}
    public Boolean getIsAnswerLocked(){return isAnswerLocked;}
    public JPanel getChoiceA(){return choiceA;}
    public JPanel getChoiceB(){return choiceB;}
    public JPanel getChoiceC(){return choiceC;}
    public JPanel getChoiceD(){return choiceD;}
}