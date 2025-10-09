package com.noinc.bloomsjeopardy.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.noinc.bloomsjeopardy.data.GameData;

public class GUIHowToPlayScreen extends JPanel implements MouseListener {
    private JPanel parentPanel;
    private JButton backButton;
    private GUIBrand brand;
    private GameData gameData;

    public GUIHowToPlayScreen(GameData gameData, JPanel parentPanel, GUIBrand brand) {
        this.parentPanel = parentPanel;
        this.brand = brand;
        this.gameData = gameData;
        this.setLayout(new BorderLayout());
        initializeHowToPlayScreen();
    }

    private void initializeHowToPlayScreen() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(brand.backgroundIMG, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Create the content panel similar to question panel
        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(1000, 600));
        contentPanel.setBackground(brand.black);
        contentPanel.setBorder(BorderFactory.createLineBorder(brand.blue, 8));
        contentPanel.setLayout(new GridBagLayout());

        // Title section
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        titlePanel.setBackground(brand.black);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("How to Play");
        titleLabel.setFont(brand.CustomFontMedium);
        titleLabel.setForeground(brand.blue);
        titlePanel.add(titleLabel);

        // Content section
        JPanel howToPlayContentPanel = new JPanel(new BorderLayout());
        howToPlayContentPanel.setBackground(brand.black);
        howToPlayContentPanel.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, brand.blue));

        JTextPane howToPlayTextPane = new JTextPane();
        howToPlayTextPane.setBackground(brand.black);
        howToPlayTextPane.setForeground(brand.white);
        // Change the font size while maintaining the font family
        // You can modify the number (12f) to any size you want:
        // 8f = very small, 10f = small, 12f = medium-small, 14f = medium, 16f = large, 18f = extra large
        howToPlayTextPane.setFont(brand.CustomFontMedium.deriveFont(12f));
        howToPlayTextPane.setEditable(false);
        howToPlayTextPane.setMargin(new Insets(20, 20, 20, 20));
        
        // Center align the text
        StyledDocument doc = howToPlayTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
        StyleConstants.setLineSpacing(center, 1.5f);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        String howToPlayText = "How to Play JeoPyramid\n\n" +
                              "OBJECTIVE:\n" +
                              "Answer questions correctly to progress through different cognitive levels\n" +
                              "based on Bloom's Taxonomy and achieve the highest score possible.\n\n" +
                              "GAME MECHANICS:\n\n" +
                              "1. MODULE SELECTION\n" +
                              "   • Choose from available learning modules\n" +
                              "   • Each module contains questions across 6 cognitive levels\n\n" +
                              "2. PYRAMID STRUCTURE\n" +
                              "   • Questions are organized in a pyramid format\n" +
                              "   • Higher levels require mastery of lower levels\n" +
                              "   • Progress unlocks more challenging questions\n\n" +
                              "3. COGNITIVE LEVELS (Bloom's Taxonomy)\n" +
                              "   • Knowledge: Basic recall of facts\n" +
                              "   • Comprehension: Understanding concepts\n" +
                              "   • Application: Using knowledge in new situations\n" +
                              "   • Analysis: Breaking down complex information\n" +
                              "   • Synthesis: Creating new ideas from existing knowledge\n" +
                              "   • Evaluation: Making judgments and assessments\n\n" +
                              "4. SCORING SYSTEM\n" +
                              "   • Correct answers earn points\n" +
                              "   • Higher cognitive levels award more points\n" +
                              "   • Track your progress and challenge yourself\n\n" +
                              "5. HEALTH SYSTEM\n" +
                              "   • You have 3 lives (hearts) per game\n" +
                              "   • Incorrect answers reduce your health\n" +
                              "   • Game ends when all lives are lost\n\n" +
                              "TIPS FOR SUCCESS:\n\n" +
                              "• Read questions carefully before selecting answers\n" +
                              "• Start with lower levels to build confidence\n" +
                              "• Think critically about higher-level questions\n" +
                              "• Use the menu to pause or restart if needed\n\n" +
                              "Good luck and enjoy learning!";
        
        howToPlayTextPane.setText(howToPlayText);
        
        // Create scroll pane and customize scrollbars
        JScrollPane scrollPane = new JScrollPane(howToPlayTextPane);
        scrollPane.setPreferredSize(new Dimension(900, 400));
        scrollPane.setBackground(brand.black);
        scrollPane.setBorder(BorderFactory.createLineBorder(brand.blue, 2));
        scrollPane.getViewport().setBackground(brand.black);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Customize vertical scrollbar
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = brand.blue;
                this.trackColor = brand.black;
            }
            
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(brand.blue);
                button.setBorder(BorderFactory.createLineBorder(brand.blue));
                return button;
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(brand.blue);
                button.setBorder(BorderFactory.createLineBorder(brand.blue));
                return button;
            }
        });
        
        scrollPane.getVerticalScrollBar().setBackground(brand.black);
        
        howToPlayContentPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(brand.black);
        buttonPanel.setBorder(BorderFactory.createMatteBorder(8, 0, 0, 0, brand.blue));
        
        backButton = new JButton("Back");
        backButton.setActionCommand("HowToPlay backButton");
        backButton.setPreferredSize(new Dimension(200, 30));
        backButton.setBorderPainted(false);
        backButton.setForeground(brand.white);
        backButton.setBackground(brand.black);
        backButton.setFont(brand.CustomFontMedium);
        backButton.addMouseListener(this);
        buttonPanel.add(backButton);

        // Layout the content panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPanel.add(titlePanel, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(howToPlayContentPanel, gbc);
        
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(buttonPanel, gbc);

        // Add content panel to main panel
        GridBagConstraints mainGBC = new GridBagConstraints();
        mainGBC.gridx = 0;
        mainGBC.gridy = 0;
        mainGBC.anchor = GridBagConstraints.CENTER;
        mainPanel.add(contentPanel, mainGBC);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    public JButton getBackButton() {
        return backButton;
    }

    // Mouse listener methods for hover effects
    @Override
    public void mouseEntered(MouseEvent e) {
        Object src = e.getSource();
        if (src == backButton) {
            backButton.setBackground(brand.blue);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object src = e.getSource();
        if (src == backButton) {
            backButton.setBackground(brand.black);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
}