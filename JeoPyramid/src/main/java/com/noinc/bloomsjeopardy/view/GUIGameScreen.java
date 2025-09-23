package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.noinc.bloomsjeopardy.data.GameData;

public class GUIGameScreen extends JPanel implements MouseListener  {
    private JPanel parentPanel, mainGamePanel, hudPanel, livesPanel, screen1, screen2, screensContainer;
    private JButton menuButton, screen2BackButton;
    private JLabel scoreLabel;
    private JButton[][] itemButtonsArray;
    private GUIBrand brand;
    private GameData gameData;

    private JTextPane categoryLabel, statementLabel, choiceALabel, choiceBLabel, choiceCLabel, choiceDLabel;
    
    public GUIGameScreen(GameData gameData, JPanel parentPanel, GUIBrand brand) {
        this.parentPanel = parentPanel;
        this.brand = brand;
        this.gameData = gameData;
        this.setLayout(new BorderLayout());
        
        initializeMainContentPanel();
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
        this.add(mainGamePanel);
        parentPanel.add(this);
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

                itemButton.setActionCommand("GameScreen pyramidButton");
                itemButton.putClientProperty("type", "pyramidButton");
                itemButton.putClientProperty("row", row); // logical row (0 = $100)
                itemButton.putClientProperty("col", col);

                if (!Arrays.asList(itemButton.getMouseListeners()).contains(this)) {
                    itemButton.addMouseListener(this);
                }

                itemButtonsArray[row][col] = itemButton;
                rowPanel.add(itemButton);
            }

            pyramidPanel.add(rowPanel); // add in reverse order
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
        JPanel statementPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JPanel choiceContainerPanel = new JPanel(new GridLayout(2,2));
        JPanel choiceAPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JPanel choiceBPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JPanel choiceCPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JPanel choiceDPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));

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

        categoryPanel.setBorder(BorderFactory.createMatteBorder(0,0, 8, 0, brand.blue));
        statementPanel.setBorder(BorderFactory.createMatteBorder(0,0, 8, 0, brand.blue));
        choiceAPanel.setBorder(BorderFactory.createMatteBorder(0,0, 4, 4, brand.blue));
        choiceBPanel.setBorder(BorderFactory.createMatteBorder(0,4, 4, 0, brand.blue));
        choiceCPanel.setBorder(BorderFactory.createMatteBorder(4,0, 0, 4, brand.blue));
        choiceDPanel.setBorder(BorderFactory.createMatteBorder(4,4, 0, 0, brand.blue));

        choiceAPanel.addMouseListener(this);
        choiceBPanel.addMouseListener(this);
        choiceCPanel.addMouseListener(this);
        choiceDPanel.addMouseListener(this);

        choiceAPanel.setName("choice1");
        choiceBPanel.setName("choice2");
        choiceCPanel.setName("choice3");
        choiceDPanel.setName("choice4");

        categoryLabel = makeStyledLabel(0, "", brand.CustomFontExtraSmall, brand.white, brand.black, 0.7f, StyleConstants.ALIGN_CENTER);
        statementLabel = makeStyledLabel(0, "", brand.CustomFontExtraSmall, brand.white, brand.black, 0.7f, StyleConstants.ALIGN_CENTER);
        choiceALabel = makeStyledLabel(1, "", brand.CustomFontExtraSmall, brand.white, brand.black, 0.7f, StyleConstants.ALIGN_CENTER);
        choiceBLabel = makeStyledLabel(1, "", brand.CustomFontExtraSmall, brand.white, brand.black, 0.7f, StyleConstants.ALIGN_CENTER);
        choiceCLabel = makeStyledLabel(1, "", brand.CustomFontExtraSmall, brand.white, brand.black, 0.7f, StyleConstants.ALIGN_CENTER);
        choiceDLabel = makeStyledLabel(1, "", brand.CustomFontExtraSmall, brand.white, brand.black, 0.7f, StyleConstants.ALIGN_CENTER);
        
        categoryPanel.add(categoryLabel);
        statementPanel.add(statementLabel);
        choiceAPanel.add(choiceALabel);
        choiceBPanel.add(choiceBLabel);
        choiceCPanel.add(choiceCLabel);
        choiceDPanel.add(choiceDLabel);

        GridBagConstraints qnaPanelGBC = new GridBagConstraints();
        qnaPanelGBC.fill = GridBagConstraints.BOTH;
        qnaPanelGBC.weightx = 1;
        qnaPanelGBC.weighty = 0.1;
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

        screen2BackButton = new JButton("Back");
        screen2BackButton.setActionCommand("GameScreen screen2BackButton");
        categoryPanel.add(screen2BackButton);

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

    public void updateLevels(){
        int levelUnlocked = gameData.getPlayerUnlockedLevels();
        // Lock/Unlock Levels
        for (int row = 0; row < itemButtonsArray.length; row++) {
            for (int col = 0; col < itemButtonsArray[row].length; col++) {
                if(row <= levelUnlocked){
                    itemButtonsArray[row][col].setEnabled(true);
                    itemButtonsArray[row][col].setForeground(brand.white);

                    if (!Arrays.asList(itemButtonsArray[row][col].getMouseListeners()).contains(this)) {
                        itemButtonsArray[row][col].addMouseListener(this);
                    }
                }else{
                    itemButtonsArray[row][col].removeMouseListener(this);
                    itemButtonsArray[row][col].setForeground(brand.gray);
                }
            }
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

    public void showScreen1(){
        CardLayout cl = (CardLayout) screensContainer.getLayout();
        cl.show(screensContainer, "screen1");
    }

    public void showScreen2(){
        updateQNAScreen();
        CardLayout cl = (CardLayout) screensContainer.getLayout();
        cl.show(screensContainer, "screen2");
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
        } else if (src instanceof JPanel) {
            JPanel panel = (JPanel) src;
            panel.setBackground(brand.darkBlue);
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
        }else if (src instanceof JPanel) {
            JPanel panel = (JPanel) src;
            panel.setBackground(brand.black);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    
    public JButton getMenuButton(){
        return menuButton;
    }
    public JButton getScreen2BackButton(){
        return screen2BackButton;
    }

    public JButton[][] getItemButtonsArray(){
        return itemButtonsArray;
    }
}